package repository;

import model.Cliente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class ClienteRepository {

    private EntityManager em;

    public ClienteRepository(EntityManager em) {
        this.em = em;
    }

    public void salvar(Cliente cliente) {
        em.getTransaction().begin();
        em.persist(cliente);
        em.getTransaction().commit();
    }

    public Cliente buscarPorId(Long id) {
        return em.find(Cliente.class, id);
    }

    public List<Cliente> buscarTodos() {
        TypedQuery<Cliente> query = em.createQuery("SELECT c FROM Cliente c", Cliente.class);
        return query.getResultList();
    }

    public void atualizar(Cliente cliente) {
        em.getTransaction().begin();
        em.merge(cliente);
        em.getTransaction().commit();
    }

    public void deletar(Cliente cliente) {
        em.getTransaction().begin();
        em.remove(em.contains(cliente) ? cliente : em.merge(cliente));
        em.getTransaction().commit();
    }
}
