package model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "reservas")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Cliente cliente;

    @ManyToOne
    private Mesa mesa;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dataReserva;

    @Enumerated(EnumType.STRING)
    private FormaPagamento formaPagamento;

    private double valorPago;

    // ✅ Adicione este campo
    private boolean cancelada;

    public Reserva(Cliente cliente, Mesa mesa, Date dataReserva, FormaPagamento formaPagamento, double valorPago) {
        this.cliente = cliente;
        this.mesa = mesa;
        this.dataReserva = dataReserva;
        this.formaPagamento = formaPagamento;
        this.valorPago = valorPago;
        this.cancelada = false; // padrão: reserva não está cancelada
    }

    // Getters
    public Long getId() { return id; }
    public Cliente getCliente() { return cliente; }
    public Mesa getMesa() { return mesa; }
    public Date getDataReserva() { return dataReserva; }
    public FormaPagamento getFormaPagamento() { return formaPagamento; }
    public double getValorPago() { return valorPago; }
    public boolean isCancelada() { return cancelada; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
    public void setMesa(Mesa mesa) { this.mesa = mesa; }
    public void setDataReserva(Date dataReserva) { this.dataReserva = dataReserva; }
    public void setFormaPagamento(FormaPagamento formaPagamento) { this.formaPagamento = formaPagamento; }
    public void setValorPago(double valorPago) { this.valorPago = valorPago; }
    public void setCancelada(boolean cancelada) { this.cancelada = cancelada; }

    @Override
    public String toString() {
        return "Reserva{" +
                "id=" + id +
                ", cliente=" + cliente.getNome() +
                ", mesa=" + mesa.getId() +
                ", dataReserva=" + dataReserva +
                ", formaPagamento=" + formaPagamento +
                ", valorPago=" + valorPago +
                ", cancelada=" + cancelada +
                '}';
    }
}
