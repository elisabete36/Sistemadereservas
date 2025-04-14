package com.restaurante.repository;

import com.restaurante.model.Cliente;
import javax.persistence.*;
import java.util.*;

public class ClienteRepository {
    private final EntityManager em;

    public ClienteRepository(EntityManager em) {
        this.em = em;
    }

    public void salvar(Cliente cliente) {
        em.getTransaction().begin();
        if(cliente.getId() == null) {
            em.persist(cliente);
        } else {
            em.merge(cliente);
        }
        em.getTransaction().commit();
    }

    public List<Cliente> buscarPorNome(String nome) {
        return em.createQuery("SELECT c FROM Cliente c WHERE c.nome LIKE :nome", Cliente.class)
                .setParameter("nome", "%" + nome + "%")
                .getResultList();
    }

    public Optional<Cliente> buscarPorId(Long id) {
        return Optional.ofNullable(em.find(Cliente.class, id));
    }

    public List<Cliente> listarTodos() {
        return em.createQuery("SELECT c FROM Cliente c", Cliente.class).getResultList();
    }
}