package config;
import java.sql.Connection;
import java.sql.DriverManager;

public class Database {
    public static Connection getConnection() throws Exception {
        String url = "jdbc:sqlite:db/villas.db";
        return DriverManager.getConnection(url);
    }
}

