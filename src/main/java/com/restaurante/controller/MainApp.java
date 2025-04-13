package com.restaurante.controller;

import java.util.Date;
import com.restaurante.model.*;
import com.restaurante.repository.*;
import com.restaurante.util.PopularBanco;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class MainApp {
    public static void main(String[] args) {
        try {
            // Configura e inicializa o Hibernate
            Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
            
            // Registra todas as entidades
            configuration.addAnnotatedClass(Cliente.class)
                       .addAnnotatedClass(Mesa.class)
                       .addAnnotatedClass(Reserva.class)
                       .addAnnotatedClass(FormaPagamento.class);
            
            SessionFactory sessionFactory = configuration.buildSessionFactory();

            // Popula o banco de dados
            PopularBanco.popular();

            // Testa as consultas
            testarConsultas();

            System.out.println("Aplicação executada com sucesso!");
        } catch (Exception e) {
            System.err.println("Erro na aplicação: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void testarConsultas() {
        System.out.println("\n=== TESTANDO CONSULTAS ===");
        
        ClienteRepository clienteRepo = new ClienteRepository();
        ReservaRepository reservaRepo = new ReservaRepository();
        FormaPagamentoRepository formaPagamentoRepo = new FormaPagamentoRepository();

        try {
            // Testa consultas
            System.out.println("\nClientes com nome similar a 'João':");
            clienteRepo.findByNomeSimilar("João").forEach(System.out::println);

            System.out.println("\nReservas com clientes:");
            reservaRepo.findWithClientes().forEach(System.out::println);

            Date hoje = new Date();
            Date amanha = new Date(hoje.getTime() + (1000 * 60 * 60 * 24));
            System.out.println("\nReservas entre hoje e amanhã:");
            reservaRepo.findByDataBetween(hoje, amanha).forEach(System.out::println);

            System.out.println("\nMédia de reservas por dia:");
            System.out.println(reservaRepo.getMediaReservasPorDia());

            System.out.println("\nFormas de pagamento ativas:");
            formaPagamentoRepo.listarAtivas().forEach(System.out::println);

            System.out.println("\nReservas pagas:");
            reservaRepo.findByStatusPagamento(true).forEach(System.out::println);

            System.out.println("\nReservas não pagas:");
            reservaRepo.findByStatusPagamento(false).forEach(System.out::println);

            if (!reservaRepo.findByStatusPagamento(false).isEmpty()) {
                Long reservaId = reservaRepo.findByStatusPagamento(false).get(0).getId();
                System.out.println("\nRegistrando pagamento para reserva ID: " + reservaId);
                reservaRepo.atualizarPagamento(reservaId, true, 1L);
                System.out.println("Pagamento registrado com sucesso!");
            }

            System.out.println("\nTotal de receitas:");
            System.out.println("R$ " + reservaRepo.getTotalReceitas(hoje, amanha));
        } finally {
            AbstractRepository.shutdown();
        }
    }
}