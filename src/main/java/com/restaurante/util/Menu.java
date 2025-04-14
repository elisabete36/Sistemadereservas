package com.restaurante.util;

import com.restaurante.model.*;
import com.restaurante.repository.*;
import javax.persistence.*;
import java.util.*;
import java.util.Scanner;

public class Menu {
    private final Scanner scanner;
    private final EntityManager em;
    
    public Menu(EntityManager em) {
        this.scanner = new Scanner(System.in);
        this.em = em;
    }

    public void exibirMenuPrincipal() {
        while(true) {
            System.out.println("\n=== SISTEMA DE RESERVAS ===");
            System.out.println("1. Gerenciar Clientes");
            System.out.println("2. Gerenciar Mesas"); 
            System.out.println("3. Gerenciar Reservas");
            System.out.println("4. Sair");
            System.out.print("Escolha: ");
            
            int opcao = scanner.nextInt();
            scanner.nextLine();
            
            switch(opcao) {
                case 1: menuClientes(); break;
                case 2: menuMesas(); break;
                case 3: menuReservas(); break;
                case 4: return;
                default: System.out.println("Opção inválida!");
            }
        }
    }

    private void menuClientes() {
        ClienteRepository repo = new ClienteRepository(em);
        while(true) {
            System.out.println("\n=== CLIENTES ===");
            System.out.println("1. Cadastrar");
            System.out.println("2. Listar");
            System.out.println("3. Buscar por nome");
            System.out.println("4. Voltar");
            System.out.print("Escolha: ");
            
            int opcao = scanner.nextInt();
            scanner.nextLine();
            
            switch(opcao) {
                case 1:
                    System.out.print("Nome: ");
                    String nome = scanner.nextLine();
                    System.out.print("Email: ");
                    String email = scanner.nextLine();
                    repo.salvar(new Cliente(nome, email));
                    break;
                case 2:
                    repo.listarTodos().forEach(System.out::println);
                    break;
                case 3:
                    System.out.print("Nome para buscar: ");
                    String busca = scanner.nextLine();
                    repo.buscarPorNome(busca).forEach(System.out::println);
                    break;
                case 4: return;
                default: System.out.println("Opção inválida!");
            }
        }
    }

    private void menuMesas() {
        MesaRepository repo = new MesaRepository(em);
        while(true) {
            System.out.println("\n=== MESAS ===");
            System.out.println("1. Cadastrar");
            System.out.println("2. Listar");
            System.out.println("3. Buscar por capacidade");
            System.out.println("4. Listar VIPs");
            System.out.println("5. Voltar");
            System.out.print("Escolha: ");
            
            int opcao = scanner.nextInt();
            scanner.nextLine();
            
            switch(opcao) {
                case 1:
                    System.out.print("Número: ");
                    String numero = scanner.nextLine();
                    System.out.print("Capacidade: ");
                    int capacidade = scanner.nextInt();
                    System.out.print("VIP (true/false): ");
                    boolean vip = scanner.nextBoolean();
                    repo.salvar(new Mesa(numero, capacidade, vip));
                    break;
                case 2:
                    repo.listarTodas().forEach(System.out::println);
                    break;
                case 3:
                    System.out.print("Capacidade mínima: ");
                    int min = scanner.nextInt();
                    repo.buscarPorCapacidade(min).forEach(System.out::println);
                    break;
                case 4:
                    repo.buscarMesasVip(true).forEach(System.out::println);
                    break;
                case 5: return;
                default: System.out.println("Opção inválida!");
            }
        }
    }

    private void menuReservas() {
        ReservaRepository repo = new ReservaRepository(em);
        while(true) {
            System.out.println("\n=== RESERVAS ===");
            System.out.println("1. Nova reserva");
            System.out.println("2. Listar reservas");
            System.out.println("3. Cancelar reserva");
            System.out.println("4. Voltar");
            System.out.print("Escolha: ");
            
            int opcao = scanner.nextInt();
            scanner.nextLine();
            
            switch(opcao) {
                case 1:
                    // Implementar criação de reserva
                    break;
                case 2:
                    repo.listarPorStatus(Reserva.Status.CONFIRMADA).forEach(System.out::println);
                    break;
                case 3:
                    // Implementar cancelamento
                    break;
                case 4: return;
                default: System.out.println("Opção inválida!");
            }
        }
    }
}