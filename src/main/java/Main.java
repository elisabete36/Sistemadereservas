import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.DayOfWeek;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final ReservaDAO reservaDAO = new ReservaDAO();
    private static final UsuarioDAO usuarioDAO = new UsuarioDAO();
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    private static Usuario usuarioLogado = null;

    public static void main(String[] args) {
        try {
            DatabaseConnection.getConnection();
            
            // Menu principal
            while (true) {
                System.out.println("\n=== SISTEMA DE RESERVAS DE RESTAURANTE ===");
                if (usuarioLogado == null) {
                    menuLogin();
                } else {
                    menuPrincipal();
                }
            }
                
            }
        } catch (Exception e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }

    private static void criarReserva() {
        try {
            Reserva reserva = new Reserva();
            
            System.out.println("\nNova Reserva:");
            System.out.print("Nome do cliente: ");
            reserva.setNomeCliente(scanner.nextLine());
            
            System.out.print("Data (dd/mm/aaaa): ");
            reserva.setDataReserva(LocalDate.parse(scanner.nextLine(), dateFormatter));
            
            System.out.print("Hora (hh:mm): ");
            reserva.setHoraReserva(LocalTime.parse(scanner.nextLine(), timeFormatter));
            
            System.out.print("Número de pessoas: ");
            reserva.setNumPessoas(Integer.parseInt(scanner.nextLine()));
            
            System.out.print("Telefone: ");
            reserva.setTelefone(scanner.nextLine());
            
            reservaDAO.criarReserva(reserva);
            System.out.println("Reserva criada com sucesso! ID: " + reserva.getId());
            
        } catch (DateTimeParseException e) {
            System.out.println("Formato de data/hora inválido. Use dd/mm/aaaa para data e hh:mm para hora.");
        } catch (NumberFormatException e) {
            System.out.println("Número de pessoas inválido. Digite um valor numérico.");
        } catch (SQLException e) {
            System.out.println("Erro ao criar reserva: " + e.getMessage());
        }
    }

    private static void listarReservas() {
        try {
            List<Reserva> reservas = reservaDAO.listarReservas();
            
            if (reservas.isEmpty()) {
                System.out.println("\nNenhuma reserva cadastrada.");
                return;
            }
            
            System.out.println("\nLista de Reservas:");
            for (Reserva reserva : reservas) {
                System.out.printf("%s - %s - %s - %d pessoas - Tel: %s%n",
                        reserva.getDataReserva().format(dateFormatter),
                        reserva.getHoraReserva().format(timeFormatter),
                        reserva.getNomeCliente(),
                        reserva.getNumPessoas(),
                        reserva.getTelefone());
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar reservas: " + e.getMessage());
        }
    }

    private static void cancelarReserva() {
        try {
            System.out.print("\nDigite o ID da reserva a cancelar: ");
            int id = Integer.parseInt(scanner.nextLine());
            
            reservaDAO.cancelarReserva(id);
            System.out.println("Reserva cancelada com sucesso!");
            
        } catch (NumberFormatException e) {
            System.out.println("ID inválido. Digite um número.");
        } catch (SQLException e) {
            System.out.println("Erro ao cancelar reserva: " + e.getMessage());
        }
    }
}