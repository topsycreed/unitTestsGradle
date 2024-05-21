package database.jdbc;

import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
    private static Connection connection = null;

    private static final String DB_HOST = "jdbc:h2:mem:myDb";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "sa";

    @SneakyThrows
    public static Connection createConnection() {
        if (connection == null) {
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection(DB_HOST, DB_USER, DB_PASSWORD);
        }
        return connection;
    }

    @SneakyThrows
    public static void closeConnection() {
        if (connection != null) {
            connection.close();
        }
    }
}
