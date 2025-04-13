package repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import model.FormaPagamento;
import model.Reserva;

import java.util.List;

public class ReservaRepository{

    private EntityManager em;

    public ReservaRepository(EntityManager em) {
        this.em = em;
    }

    public void cancelarReserva(Long idReserva) {
        Reserva reserva = em.find(Reserva.class, idReserva);
        if (reserva != null) {
            em.getTransaction().begin();
            reserva.setCancelada(true);
            em.merge(reserva);
            em.getTransaction().commit();
        }
    }

    public void registrarPagamento(Long idReserva, double valor, FormaPagamento formaPagamento) {
        Reserva reserva = em.find(Reserva.class, idReserva);
        if (reserva != null && !reserva.isCancelada()) {
            em.getTransaction().begin();
            reserva.setValorPago(valor);
            reserva.setFormaPagamento(formaPagamento);
            em.merge(reserva);
            em.getTransaction().commit();
        }
    }

    public List<Reserva> listarReservasCanceladas() {
        TypedQuery<Reserva> query = em.createQuery(
                "SELECT r FROM Reserva r WHERE r.cancelada = true", Reserva.class);
        return query.getResultList();
    }

    public void salvar(Reserva reserva) {
        em.getTransaction().begin();
        em.persist(reserva);
        em.getTransaction().commit();
    }

    public Reserva buscarPorId(Long id) {
        return em.find(Reserva.class, id);
    }
}
