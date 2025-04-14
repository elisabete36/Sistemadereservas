package com.restaurante.repository;

import com.restaurante.model.Mesa;
import javax.persistence.*;
import java.util.*;

public class MesaRepository {
    private final EntityManager em;

    public MesaRepository(EntityManager em) {
        this.em = em;
    }

    public void salvar(Mesa mesa) {
        em.getTransaction().begin();
        if(mesa.getId() == null) {
            em.persist(mesa);
        } else {
            em.merge(mesa);
        }
        em.getTransaction().commit();
    }

    public List<Mesa> listarTodas() {
        return em.createQuery("SELECT m FROM Mesa m", Mesa.class).getResultList();
    }

    public List<Mesa> buscarPorCapacidade(int capacidade) {
        return em.createQuery("SELECT m FROM Mesa m WHERE m.capacidade >= :capacidade", Mesa.class)
                .setParameter("capacidade", capacidade)
                .getResultList();
    }

    public List<Mesa> buscarMesasVip(boolean vip) {
        return em.createQuery("SELECT m FROM Mesa m WHERE m.vip = :vip", Mesa.class)
                .setParameter("vip", vip)
                .getResultList();
    }

    public Optional<Mesa> buscarPorId(Long id) {
        return Optional.ofNullable(em.find(Mesa.class, id));
    }
}