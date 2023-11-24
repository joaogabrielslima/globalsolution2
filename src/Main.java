import java.sql.*;

public class Main {

    private static final String JDBC_URL = "jdbc:mysql://your_mysql_host:mysql_port/database_name";
    private static final String JDBC_USER = "mysql_username";
    private static final String JDBC_PASSWORD = "mysql_password";

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {

                // Create entities in both tables
                createCrianca(connection, 1, "Child1", "2023-01-01", 100.5, 20.3, "Neutro");
                createResponsavel(connection, 1, "email@example.com", "password", "Parent1");

                updateResponsavelSenha(connection, 1, "newPassword");

                updateCriancaInfo(connection, 1, 110.5, 25.0, "Neutro");

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void createCrianca(Connection connection, int criancaId, String nome, String aniversario, double altura, double peso, String emocional) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO criança VALUES (?, ?, ?, ?, ?, ?)")) {
            preparedStatement.setInt(1, criancaId);
            preparedStatement.setString(2, nome);
            preparedStatement.setString(3, aniversario);
            preparedStatement.setDouble(4, altura);
            preparedStatement.setDouble(5, peso);
            preparedStatement.setString(6, emocional);
            preparedStatement.executeUpdate();
        }
    }

    private static void createResponsavel(Connection connection, int responsavelId, String email, String senha, String nome) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO responsavel VALUES (?, ?, ?, ?, ?)")) {
            preparedStatement.setInt(1, responsavelId);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, senha);
            preparedStatement.setString(4, nome);
            preparedStatement.setInt(5, 1); // Assuming 1 is the criança_id associated with this responsavel
            preparedStatement.executeUpdate();
        }
    }

    private static void updateResponsavelSenha(Connection connection, int responsavelId, String newSenha) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE responsavel SET senha_hash = ? WHERE responsavel_id = ?")) {
            preparedStatement.setString(1, newSenha);
            preparedStatement.setInt(2, responsavelId);
            preparedStatement.executeUpdate();
        }
    }

    private static void updateCriancaInfo(Connection connection, int criancaId, double newAltura, double newPeso, String newEmocional) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE criança SET altura = ?, peso = ?, emocional = ? WHERE criança_id = ?")) {
            preparedStatement.setDouble(1, newAltura);
            preparedStatement.setDouble(2, newPeso);
            preparedStatement.setString(3, newEmocional);
            preparedStatement.setInt(4, criancaId);
            preparedStatement.executeUpdate();
        }
    }
}
