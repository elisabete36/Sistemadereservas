package restaurante.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "reservas")
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;
    
    @ManyToOne
    @JoinColumn(name = "mesa_id", nullable = false)
    private Mesa mesa;
    
    @Column(name = "data_reserva", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataReserva;
    
    @Column(name = "valor_total", nullable = false)
    private double valorTotal;
    
    @Column(name = "pago", nullable = false)
    private boolean pago;
    
    @ManyToOne
    @JoinColumn(name = "forma_pagamento_id")
    private FormaPagamento formaPagamento;

    // Construtores
    public Reserva() {
    }
    
    public Reserva(Cliente cliente, Mesa mesa, Date dataReserva, double valorTotal, boolean pago, FormaPagamento formaPagamento) {
        this.cliente = cliente;
        this.mesa = mesa;
        this.dataReserva = dataReserva;
        this.valorTotal = valorTotal;
        this.pago = pago;
        this.formaPagamento = formaPagamento;
    }
    
    public Reserva(Reserva outra) {
        this.id = outra.id;
        this.cliente = new Cliente(outra.cliente);
        this.mesa = new Mesa(outra.mesa);
        this.dataReserva = outra.dataReserva;
        this.valorTotal = outra.valorTotal;
        this.pago = outra.pago;
        this.formaPagamento = outra.formaPagamento; // Mantemos a mesma instância
    }
    
    // Getters e Setters
    public Long getId() {
        return id;
    }
    
    public Cliente getCliente() {
        return cliente;
    }
    
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    
    public Mesa getMesa() {
        return mesa;
    }
    
    public void setMesa(Mesa mesa) {
        this.mesa = mesa;
    }
    
    public Date getDataReserva() {
        return dataReserva;
    }
    
    public void setDataReserva(Date dataReserva) {
        this.dataReserva = dataReserva;
    }
    
    public double getValorTotal() {
        return valorTotal;
    }
    
    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }
    
    public boolean isPago() {
        return pago;
    }
    
    public void setPago(boolean pago) {
        this.pago = pago;
    }
    
    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }
    
    public void setFormaPagamento(FormaPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reserva reserva = (Reserva) o;
        return Objects.equals(id, reserva.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return "Reserva{" +
                "id=" + id +
                ", cliente=" + cliente +
                ", mesa=" + mesa +
                ", dataReserva=" + dataReserva +
                ", valorTotal=" + valorTotal +
                ", pago=" + pago +
                ", formaPagamento=" + formaPagamento +
                '}';
    }
}