package repository;

import jakarta.persistence.CascadeType;
import model.Restaurante;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class RestauranteRepository {

    private EntityManager em;

    public RestauranteRepository(EntityManager em) {
        this.em = em;
    }

    public void salvar(Restaurante restaurante) {
        em.getTransaction().begin();
        em.persist(restaurante);
        em.getTransaction().commit();
    }

    public Restaurante buscarPorId(Long id) {
        return em.find(Restaurante.class, id);
    }

    public List<Restaurante> buscarTodos() {
        TypedQuery<Restaurante> query = em.createQuery("SELECT r FROM Restaurante r", Restaurante.class);
        return query.getResultList();
    }

    public void atualizar(Restaurante restaurante) {
        em.getTransaction().begin();
        em.merge(restaurante);
        em.getTransaction().commit();
    }

    public void deletar(Restaurante restaurante) {
        em.getTransaction().begin();
        em.remove(em.contains(restaurante) ? restaurante : em.merge(restaurante));
        em.getTransaction().commit();
    }
}
