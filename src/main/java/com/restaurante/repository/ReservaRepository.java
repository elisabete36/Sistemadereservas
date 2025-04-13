package com.restaurante.repository;

import com.restaurante.model.Reserva;
import com.restaurante.model.FormaPagamento;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.Date;
import java.util.List;

public class ReservaRepository extends AbstractRepository<Reserva> {
    public ReservaRepository() {
        super(Reserva.class);
    }

    public List<Reserva> findWithClientes() {
        try (Session session = openSession()) {
            Query<Reserva> query = session.createQuery(
                "SELECT r FROM Reserva r JOIN FETCH r.cliente", Reserva.class);
            return query.getResultList();
        }
    }

    public List<Reserva> findByDataBetween(Date inicio, Date fim) {
        try (Session session = openSession()) {
            Query<Reserva> query = session.createQuery(
                "FROM Reserva WHERE data BETWEEN :inicio AND :fim", Reserva.class)
                .setParameter("inicio", inicio)
                .setParameter("fim", fim);
            return query.getResultList();
        }
    }

    public Double getMediaReservasPorDia() {
        try (Session session = openSession()) {
            Query<Double> query = session.createQuery(
                "SELECT COUNT(r)/COUNT(DISTINCT CAST(r.data AS date)) FROM Reserva r", Double.class);
            return query.uniqueResult();
        }
    }

    public List<Reserva> findByStatusPagamento(boolean pago) {
        try (Session session = openSession()) {
            Query<Reserva> query = session.createQuery(
                "FROM Reserva WHERE pago = :pago", Reserva.class)
                .setParameter("pago", pago);
            return query.getResultList();
        }
    }

    public void atualizarPagamento(Long reservaId, boolean pago, Long formaPagamentoId) {
        try (Session session = openSession()) {
            Transaction tx = session.beginTransaction();
            try {
                Reserva reserva = session.get(Reserva.class, reservaId);
                if (reserva != null) {
                    reserva.setPago(pago);
                    if (formaPagamentoId != null) {
                        FormaPagamento formaPagamento = session.get(FormaPagamento.class, formaPagamentoId);
                        reserva.setFormaPagamento(formaPagamento);
                    }
                    session.merge(reserva);
                }
                tx.commit();
            } catch (Exception e) {
                if (tx != null) tx.rollback();
                throw e;
            }
        }
    }

    public Double getTotalReceitas(Date inicio, Date fim) {
        try (Session session = openSession()) {
            Query<Double> query = session.createQuery(
                "SELECT SUM(r.valorTotal) FROM Reserva r WHERE r.pago = true AND r.data BETWEEN :inicio AND :fim", 
                Double.class)
                .setParameter("inicio", inicio)
                .setParameter("fim", fim);
            Double result = query.uniqueResult();
            return result != null ? result : 0.0;
        }
    }
}