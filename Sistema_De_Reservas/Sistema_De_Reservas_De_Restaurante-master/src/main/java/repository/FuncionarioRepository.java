package repository;

import model.Funcionario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class FuncionarioRepository {

    private EntityManager em;

    public FuncionarioRepository(EntityManager em) {
        this.em = em;
    }

    public void salvar(Funcionario funcionario) {
        em.getTransaction().begin();
        em.persist(funcionario);
        em.getTransaction().commit();
    }

    public Funcionario buscarPorId(Long id) {
        return em.find(Funcionario.class, id);
    }

    public List<Funcionario> buscarTodos() {
        TypedQuery<Funcionario> query = em.createQuery("SELECT f FROM Funcionario f", Funcionario.class);
        return query.getResultList();
    }

    public void atualizar(Funcionario funcionario) {
        em.getTransaction().begin();
        em.merge(funcionario);
        em.getTransaction().commit();
    }

    public void deletar(Funcionario funcionario) {
        em.getTransaction().begin();
        em.remove(em.contains(funcionario) ? funcionario : em.merge(funcionario));
        em.getTransaction().commit();
    }
}
