import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ReservaDAO {
    public void criarReserva(Reserva reserva) throws SQLException {
        String sql = "INSERT INTO reservas (nome_cliente, data_reserva, hora_reserva, num_pessoas, telefone) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, reserva.getNomeCliente());
            stmt.setDate(2, Date.valueOf(reserva.getDataReserva()));
            stmt.setTime(3, Time.valueOf(reserva.getHoraReserva()));
            stmt.setInt(4, reserva.getNumPessoas());
            stmt.setString(5, reserva.getTelefone());
            stmt.executeUpdate();
            
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    reserva.setId(rs.getInt(1));
                }
            }
        }
    }

    public List<Reserva> listarReservas() throws SQLException {
        List<Reserva> reservas = new ArrayList<>();
        String sql = "SELECT * FROM reservas ORDER BY data_reserva, hora_reserva";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Reserva reserva = new Reserva();
                reserva.setId(rs.getInt("id"));
                reserva.setNomeCliente(rs.getString("nome_cliente"));
                reserva.setDataReserva(rs.getDate("data_reserva").toLocalDate());
                reserva.setHoraReserva(rs.getTime("hora_reserva").toLocalTime());
                reserva.setNumPessoas(rs.getInt("num_pessoas"));
                reserva.setTelefone(rs.getString("telefone"));
                reservas.add(reserva);
            }
        }
        return reservas;
    }

    public void cancelarReserva(int id) throws SQLException {
        String sql = "DELETE FROM reservas WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}