package com.restaurante.repository;

import model.Mesa;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class MesaRepository {

    private EntityManager em;

    public MesaRepository(EntityManager em) {
        this.em = em;
    }

    public void salvar(Mesa mesa) {
        em.getTransaction().begin();
        em.persist(mesa);
        em.getTransaction().commit();
    }

    public Mesa buscarPorId(Long id) {
        return em.find(Mesa.class, id);
    }

    public List<Mesa> buscarTodos() {
        TypedQuery<Mesa> query = em.createQuery("SELECT m FROM Mesa m", Mesa.class);
        return query.getResultList();
    }

    public void atualizar(Mesa mesa) {
        em.getTransaction().begin();
        em.merge(mesa);
        em.getTransaction().commit();
    }

    public void deletar(Mesa mesa) {
        em.getTransaction().begin();
        em.remove(em.contains(mesa) ? mesa : em.merge(mesa));
        em.getTransaction().commit();
    }
}
