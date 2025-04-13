package com.restaurante.repository;

import com.restaurante.model.Mesa;
import org.hibernate.Session;
import org.hibernate.query.Query;
import java.util.List;

public class MesaRepository extends AbstractRepository<Mesa> {
    public MesaRepository() {
        super(Mesa.class);
    }

    public List<Mesa> findByCapacidade(int capacidade) {
        try (Session session = openSession()) {
            Query<Mesa> query = session.createQuery(
                "FROM Mesa WHERE capacidade >= :capacidade", 
                Mesa.class
            );
            query.setParameter("capacidade", capacidade);
            return query.getResultList();
        }
    }
}