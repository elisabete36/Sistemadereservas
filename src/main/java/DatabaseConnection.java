import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
    private static Connection connection;
    private static final String DB_URL = "jdbc:h2:mem:testdb";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            initializeDatabase();
        }
        return connection;
    }

    private static void initializeDatabase() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            // Tabela de usuários
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS usuarios (
                    id INT PRIMARY KEY AUTO_INCREMENT,
                    nome VARCHAR(100) NOT NULL,
                    email VARCHAR(100) UNIQUE NOT NULL,
                    senha VARCHAR(100) NOT NULL,
                    tipo ENUM('CLIENTE','ADMIN') NOT NULL
                )""");
                
            // Tabela de mesas
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS mesas (
                    id INT PRIMARY KEY AUTO_INCREMENT,
                    capacidade INT NOT NULL,
                    disponivel BOOLEAN DEFAULT TRUE
                )""");
                
            // Tabela de reservas
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS reservas (
                    id INT PRIMARY KEY AUTO_INCREMENT,
                    usuario_id INT NOT NULL,
                    mesa_id INT NOT NULL,
                    data_reserva DATE NOT NULL,
                    hora_reserva TIME NOT NULL,
                    num_pessoas INT NOT NULL,
                    status ENUM('PENDENTE','CONFIRMADA','CANCELADA') NOT NULL,
                    metodo_pagamento ENUM('DINHEIRO','CARTAO','PIX'),
                    FOREIGN KEY (usuario_id) REFERENCES usuarios(id),
                    FOREIGN KEY (mesa_id) REFERENCES mesas(id)
                )""");
                
            // Horário de funcionamento
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS horario_funcionamento (
                    dia_semana ENUM('SEG','TER','QUA','QUI','SEX','SAB','DOM') PRIMARY KEY,
                    abertura TIME NOT NULL,
                    fechamento TIME NOT NULL
                )""");
                
            // Inserir horário padrão
            stmt.execute("""
                INSERT INTO horario_funcionamento VALUES 
                ('SEG', '11:00:00', '22:00:00'),
                ('TER', '11:00:00', '22:00:00'),
                ('QUA', '11:00:00', '22:00:00'),
                ('QUI', '11:00:00', '22:00:00'),
                ('SEX', '11:00:00', '23:00:00'),
                ('SAB', '12:00:00', '23:00:00'),
                ('DOM', '12:00:00', '21:00:00')
                ON DUPLICATE KEY UPDATE
                abertura = VALUES(abertura),
                fechamento = VALUES(fechamento)""");
        }
    }

    public static void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}