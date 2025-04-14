package com.restaurante.model;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "formas_pagamento")
public class FormaPagamento {
    public enum Tipo {
        CARTAO_CREDITO, 
        CARTAO_DEBITO, 
        DINHEIRO, 
        PIX
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Tipo tipo;

    @Column(length = 100)
    private String descricao;

    @OneToMany(mappedBy = "formaPagamento", cascade = CascadeType.ALL)
    private List<Reserva> reservas = new ArrayList<>();

    public FormaPagamento() {}

    public FormaPagamento(Tipo tipo, String descricao) {
        this.tipo = tipo;
        this.descricao = descricao;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Tipo getTipo() { return tipo; }
    public void setTipo(Tipo tipo) { this.tipo = tipo; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public List<Reserva> getReservas() { return reservas; }
    public void setReservas(List<Reserva> reservas) { this.reservas = reservas; }

    @Override
    public String toString() {
        return tipo.toString() + (descricao != null ? " (" + descricao + ")" : "");
    }
}