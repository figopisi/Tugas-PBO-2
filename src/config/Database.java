package config;

import util.Exception.ApiException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    public static Connection getConnection() {
        String url = "jdbc:sqlite:db/villas.db";
        try {
            return DriverManager.getConnection(url);
        } catch (SQLException e) {
            throw new ApiException.InternalServerErrorApiException(
                    "Gagal membuat koneksi ke database: " + e.getMessage()
            );
        }
    }
}
