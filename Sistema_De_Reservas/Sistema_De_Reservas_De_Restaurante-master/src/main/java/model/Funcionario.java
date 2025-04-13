package model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "funcionarios")
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String cargo;

    @Temporal(TemporalType.DATE)
    @Column(name = "data_contratacao")
    private Date dataContratacao;

    @ManyToOne
    @JoinColumn(name = "restaurante_id")
    private Restaurante restaurante;

    // === Construtores ===

    public Funcionario() {
    }

    public Funcionario(String nome, String cargo, Date dataContratacao, Restaurante restaurante) {
        this.nome = nome;
        this.cargo = cargo;
        this.dataContratacao = dataContratacao;
        this.restaurante = restaurante;
    }

    // === Getters e Setters ===

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public Date getDataContratacao() {
        return dataContratacao;
    }

    public void setDataContratacao(Date dataContratacao) {
        this.dataContratacao = dataContratacao;
    }

    public Restaurante getRestaurante() {
        return restaurante;
    }

    public void setRestaurante(Restaurante restaurante) {
        this.restaurante = restaurante;
    }

    // === toString ===

    @Override
    public String toString() {
        return "Funcionario{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", cargo='" + cargo + '\'' +
                ", dataContratacao=" + dataContratacao +
                ", restaurante=" + (restaurante != null ? restaurante.getNome() : "Nenhum") +
                '}';
    }
}
