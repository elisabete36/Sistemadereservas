package com.restaurante.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "mesas")
public class Mesa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private Integer numero;
    
    @Column(nullable = false, length = 20)
    private String status;
    
    // Construtores
    public Mesa() {
    }
    
    public Mesa(Integer numero, String status) {
        this.numero = numero;
        this.status = status;
    }
    
    public Mesa(Mesa outra) {
        this.id = outra.id;
        this.numero = outra.numero;
        this.status = outra.status;
    }
    
    // Getters e Setters
    public Long getId() {
        return id;
    }
    
    public Integer getNumero() {
        return numero;
    }
    
    public void setNumero(Integer numero) {
        this.numero = numero;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mesa mesa = (Mesa) o;
        return Objects.equals(id, mesa.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return "Mesa{" +
                "id=" + id +
                ", numero=" + numero +
                ", status='" + status + '\'' +
                '}';
    }
}