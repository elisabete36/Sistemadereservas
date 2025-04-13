package com.restaurante.repository;

import com.restaurante.model.FormaPagamento;
import org.hibernate.Session;
import org.hibernate.query.Query;
import java.util.List;

public class FormaPagamentoRepository extends AbstractRepository<FormaPagamento> {
    public FormaPagamentoRepository() {
        super(FormaPagamento.class);
    }

    public List<FormaPagamento> listarAtivas() {
        try (Session session = openSession()) {
            Query<FormaPagamento> query = session.createQuery(
                "FROM FormaPagamento WHERE ativo = true", 
                FormaPagamento.class);
            return query.getResultList();
        }
    }

    public FormaPagamento buscarPorDescricao(String descricao) {
        try (Session session = openSession()) {
            Query<FormaPagamento> query = session.createQuery(
                "FROM FormaPagamento WHERE descricao = :descricao", 
                FormaPagamento.class)
                .setParameter("descricao", descricao);
            return query.uniqueResult();
        }
    }
}