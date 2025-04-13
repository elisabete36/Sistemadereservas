package com.restaurante.util;

import com.restaurante.model.*;
import com.restaurante.repository.*;
import java.util.Date;

public class PopularBanco {
    public static void popular() {
        // Inicializa os repositórios
        ClienteRepository clienteRepo = new ClienteRepository();
        MesaRepository mesaRepo = new MesaRepository();
        ReservaRepository reservaRepo = new ReservaRepository();
        FormaPagamentoRepository formaPagamentoRepo = new FormaPagamentoRepository();

        try {
            // Cria formas de pagamento iniciais
            FormaPagamento cartao = new FormaPagamento("Cartão de Crédito", true);
            FormaPagamento dinheiro = new FormaPagamento("Dinheiro", true);
            FormaPagamento pix = new FormaPagamento("PIX", true);
            
            formaPagamentoRepo.save(cartao);
            formaPagamentoRepo.save(dinheiro);
            formaPagamentoRepo.save(pix);

            // Cria clientes de exemplo
            Cliente cliente1 = new Cliente("João Silva", "joao@email.com");
            Cliente cliente2 = new Cliente("Maria Souza", "maria@email.com");
            Cliente cliente3 = new Cliente("Carlos Oliveira", "carlos@email.com");
            
            clienteRepo.save(cliente1);
            clienteRepo.save(cliente2);
            clienteRepo.save(cliente3);

            // Cria mesas de exemplo
            Mesa mesa1 = new Mesa(1, "Disponível");
            Mesa mesa2 = new Mesa(2, "Disponível");
            Mesa mesa3 = new Mesa(3, "Reservada");
            Mesa mesa4 = new Mesa(4, "Disponível");
            
            mesaRepo.save(mesa1);
            mesaRepo.save(mesa2);
            mesaRepo.save(mesa3);
            mesaRepo.save(mesa4);

            // Cria reservas de exemplo
            Date hoje = new Date();
            Date amanha = new Date(hoje.getTime() + (1000 * 60 * 60 * 24));
            
            Reserva reserva1 = new Reserva(cliente1, mesa1, hoje, 150.0, true, cartao);
            Reserva reserva2 = new Reserva(cliente2, mesa3, hoje, 200.0, false, null);
            Reserva reserva3 = new Reserva(cliente3, mesa2, amanha, 180.0, true, pix);
            
            reservaRepo.save(reserva1);
            reservaRepo.save(reserva2);
            reservaRepo.save(reserva3);

            System.out.println("Banco de dados populado com sucesso!");
        } catch (Exception e) {
            System.err.println("Erro ao popular banco de dados: " + e.getMessage());
            e.printStackTrace();
        }
        // Removido o shutdown() para não fechar a SessionFactory
    }
}