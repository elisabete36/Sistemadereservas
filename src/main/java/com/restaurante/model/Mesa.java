package com.restaurante.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.*;

@Entity
@Table(name = "mesas")
public class Mesa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 10)
    @NotBlank(message = "Número da mesa é obrigatório")
    private String numero;

    @Column(nullable = false)
    @Min(value = 1, message = "Capacidade mínima é 1")
    @Max(value = 20, message = "Capacidade máxima é 20") 
    private int capacidade;

    @Column(nullable = false)
    private boolean vip;

    @OneToMany(mappedBy = "mesa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reserva> reservas = new ArrayList<>();

    // Construtores
    public Mesa() {}

    public Mesa(String numero, int capacidade, boolean vip) {
        this.numero = numero;
        this.capacidade = capacidade;
        this.vip = vip;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }
    public int getCapacidade() { return capacidade; }
    public void setCapacidade(int capacidade) { this.capacidade = capacidade; }
    public boolean isVip() { return vip; }
    public void setVip(boolean vip) { this.vip = vip; }
    public List<Reserva> getReservas() { return reservas; }
    public void setReservas(List<Reserva> reservas) { this.reservas = reservas; }

    @Override
    public String toString() {
        return "Mesa " + numero + " (" + capacidade + " lugares)" + (vip ? " - VIP" : "");
    }
}