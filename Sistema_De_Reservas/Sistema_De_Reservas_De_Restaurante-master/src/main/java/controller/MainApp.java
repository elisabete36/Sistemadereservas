package controller;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import model.Cliente;
import model.FormaPagamento;
import model.Mesa;
import model.Reserva;
import repository.ClienteRepository;
import repository.ReservaRepository;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class MainApp {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("reservaPU");
        EntityManager em = emf.createEntityManager();

        ReservaRepository reservaRepo = new ReservaRepository(em);
        ClienteRepository clienteRepo = new ClienteRepository(em); // Adiciona o repositório do cliente

        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n--- MENU PRINCIPAL ---");
            System.out.println("1. Cadastrar Restaurante");
            System.out.println("2. Cadastrar Usuário");
            System.out.println("3. Cadastrar Funcionário");
            System.out.println("4. Criar Reserva");
            System.out.println("5. Cancelar Reserva");
            System.out.println("6. Registrar Pagamento");
            System.out.println("7. Listar Reservas Canceladas");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar buffer

            switch (opcao) {
                case 1:
                    System.out.println("Funcionalidade de cadastro de restaurante...");
                    break;
                case 2:
                    System.out.println("Funcionalidade de cadastro de usuário...");
                    break;
                case 3:
                    System.out.println("Funcionalidade de cadastro de funcionário...");
                    break;
                case 4:
                    System.out.println("Criando uma nova reserva...");

                    // Solicitar os dados do cliente
                    System.out.print("Nome do cliente: ");
                    String nomeCliente = scanner.nextLine();

                    Cliente cliente = new Cliente(); // Instanciando um novo cliente
                    cliente.setNome(nomeCliente); // Definindo o nome do cliente
                    clienteRepo.salvar(cliente); // Salva o cliente no banco de dados

                    // Solicitar os dados da mesa
                    System.out.print("Número da mesa: ");
                    int numeroMesa = scanner.nextInt();
                    scanner.nextLine(); // Limpar buffer
                    Mesa mesa = new Mesa(); // Criar a mesa (você precisará de uma classe Mesa)
                    mesa.setNumero(numeroMesa); // Defina os parâmetros de mesa de acordo com o que sua classe `Mesa` requer

                    // Solicitar a data da reserva
                    System.out.print("Data da reserva (format YYYY-MM-DD): ");
                        String dataStr = scanner.nextLine();
                           Date dataReserva = Date.valueOf(dataStr); // Convertendo a string para um objeto Date (ou usar um DateTimeFormatter dependendo de como você deseja tratar a data)

                    // Solicitar forma de pagamento
                    System.out.print("Forma de pagamento (DINHEIRO ou CARTAO): ");
                        String formaStr = scanner.nextLine().toUpperCase();
                          FormaPagamento formaPagamento = FormaPagamento.valueOf(formaStr);

                    // Solicitar o valor da reserva
                    System.out.print("Valor da reserva: ");
                        double valor = scanner.nextDouble();
                           scanner.nextLine(); // Limpar buffer

                    // Agora, você cria a reserva passando os parâmetros corretos para o construtor
                    Reserva reserva = new Reserva(cliente, mesa, dataReserva, formaPagamento, valor);
                    reservaRepo.salvar(reserva); // Salva a reserva no banco de dados

                    System.out.println("Reserva criada com sucesso!");
                    break;

                case 5:
                    System.out.print("ID da reserva a cancelar: ");
                    Long idCancelar = scanner.nextLong();
                    reservaRepo.cancelarReserva(idCancelar);
                    System.out.println("Reserva cancelada com sucesso.");
                    break;
                case 6:
                    System.out.print("ID da reserva para registrar pagamento: ");
                    Long idPagar = scanner.nextLong();

                    System.out.print("Valor do pagamento: ");
                    double valor = scanner.nextDouble();
                    scanner.nextLine(); // Limpar buffer

                    System.out.print("Forma de pagamento (DINHEIRO ou CARTAO): ");
                    String formaStrPagar = scanner.nextLine().toUpperCase();

                    FormaPagamento formaPagar = FormaPagamento.valueOf(formaStrPagar);

                    reservaRepo.registrarPagamento(idPagar, valor, formaPagar);
                    System.out.println("Pagamento registrado com sucesso.");
                    break;

                case 7:
                    List<Reserva> canceladas = reservaRepo.listarReservasCanceladas();
                    System.out.println("--- Reservas Canceladas ---");
                    for (Reserva r : canceladas) {
                        System.out.println("ID: " + r.getId() + " | Cliente: " + r.getCliente().getNome());
                    }
                    break;
                case 0:
                    System.out.println("Encerrando o sistema.");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }

        } while (opcao != 0);

        em.close();
        emf.close();
        scanner.close();
    }
}
