package com.restaurante.controller;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.Date;
import com.restaurante.model.*;
import com.restaurante.repository.*;
import com.restaurante.util.PopularBanco;

public class MainApp {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("restaurantePU");
        EntityManager em = emf.createEntityManager();
        
        try {
            // Popula o banco de dados
            PopularBanco.popular(em);

            // Testa as consultas
            testarConsultas(em);

            System.out.println("Aplicação executada com sucesso!");
        } catch (Exception e) {
            System.err.println("Erro na aplicação: " + e.getMessage());
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }
    }

    private static void testarConsultas(EntityManager em) {
        System.out.println("\n=== TESTANDO CONSULTAS ===");

        ClienteRepository clienteRepo = new ClienteRepository(em);
        ReservaRepository reservaRepo = new ReservaRepository(em);
        FormaPagamentoRepository formaPagamentoRepo = new FormaPagamentoRepository(em);

        // Testa consulta com LIKE
        System.out.println("\nClientes com nome similar a 'João':");
        clienteRepo.findByNomeSimilar("João").forEach(System.out::println);

        // Testa consulta com JOIN
        System.out.println("\nReservas com clientes:");
        reservaRepo.findWithClientes().forEach(System.out::println);

        // Testa consulta com intervalo de datas
        Date hoje = new Date();
        Date amanha = new Date(hoje.getTime() + (1000 * 60 * 60 * 24));
        System.out.println("\nReservas entre hoje e amanhã:");
        reservaRepo.findByDataBetween(hoje, amanha).forEach(r -> 
            System.out.printf("Reserva #%d para %s em %s\n", 
                r.getId(), r.getCliente().getNome(), r.getDataHora())
        );

        // Testa consulta de agregação
        System.out.println("\nTotal de reservas:");
        System.out.println(reservaRepo.countAll());

        // Testa funcionalidades de pagamento
        System.out.println("\nFormas de pagamento ativas:");
        formaPagamentoRepo.listarAtivas().forEach(System.out::println);
    }
}
