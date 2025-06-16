package service.VoucherService;

import config.Database;
import model.Voucher;
import util.Request;
import util.Exception.ApiException;
import util.validator.VoucherValidator;
import util.Response;
import web.Server;

import java.net.HttpURLConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class VoucherService {

    public static void index(Response res) throws Exception {
        try (Connection connection = Database.getConnection()) {
            String query = "SELECT * FROM vouchers";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            List<Voucher> vouchers = new ArrayList<>();
            while (resultSet.next()) {
                vouchers.add(new Voucher(
                        resultSet.getInt("id"),
                        resultSet.getString("code"),
                        resultSet.getString("description"),
                        resultSet.getDouble("discount"),
                        resultSet.getString("start_date"),
                        resultSet.getString("end_date")
                ));
            }

            res.setBody(Server.jsonMap(Map.of("status", 200, "data", vouchers)));
            res.send(HttpURLConnection.HTTP_OK);
        }
    }

    public static void show(int id, Response res) throws Exception {
        try (Connection connection = Database.getConnection()) {
            String query = "SELECT * FROM vouchers WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                throw new ApiException.NotFoundApiException("Voucher dengan id " + id + " tidak ditemukan");
            }

            Voucher voucher = new Voucher(
                    resultSet.getInt("id"),
                    resultSet.getString("code"),
                    resultSet.getString("description"),
                    resultSet.getDouble("discount"),
                    resultSet.getString("start_date"),
                    resultSet.getString("end_date")
            );

            res.setBody(Server.jsonMap(Map.of("status", 200, "data", voucher)));
            res.send(HttpURLConnection.HTTP_OK);
        }
    }

    public static void create(Request req, Response res) throws Exception {
        Map<String, Object> body = req.getJSON();
        VoucherValidator.validateInput(body);

        try (Connection connection = Database.getConnection()) {
            String query = "INSERT INTO vouchers(code, description, discount, start_date, end_date) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, (String) body.get("code"));
            statement.setString(2, (String) body.get("description"));
            statement.setDouble(3, (Double) body.get("discount"));
            statement.setString(4, (String) body.get("start_date"));
            statement.setString(5, (String) body.get("end_date"));
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            generatedKeys.next();

            res.setBody(Server.jsonMap(Map.of(
                    "status", 201,
                    "id", generatedKeys.getInt(1),
                    "message", "Voucher berhasil dibuat"
            )));
            res.send(HttpURLConnection.HTTP_CREATED);
        }
    }

    public static void update(int id, Request req, Response res) throws Exception {
        Map<String, Object> body = req.getJSON();
        VoucherValidator.validateInput(body);

        try (Connection connection = Database.getConnection()) {
            String checkQuery = "SELECT id FROM vouchers WHERE id = ?";
            PreparedStatement checkStatement = connection.prepareStatement(checkQuery);
            checkStatement.setInt(1, id);
            ResultSet rs = checkStatement.executeQuery();

            if (!rs.next()) {
                throw new ApiException.NotFoundApiException("Voucher dengan id " + id + " tidak ditemukan");
            }

            String updateQuery = "UPDATE vouchers SET code = ?, description = ?, discount = ?, start_date = ?, end_date = ? WHERE id = ?";
            PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
            updateStatement.setString(1, (String) body.get("code"));
            updateStatement.setString(2, (String) body.get("description"));
            updateStatement.setDouble(3, (Double) body.get("discount"));
            updateStatement.setString(4, (String) body.get("start_date"));
            updateStatement.setString(5, (String) body.get("end_date"));
            updateStatement.setInt(6, id);
            updateStatement.executeUpdate();

            res.setBody(Server.jsonMap(Map.of("status", 200, "message", "Voucher berhasil diubah")));
            res.send(HttpURLConnection.HTTP_OK);
        }
    }

    public static void destroy(int id, Response res) throws Exception {
        try (Connection connection = Database.getConnection()) {
            String checkQuery = "SELECT id FROM vouchers WHERE id = ?";
            PreparedStatement checkStatement = connection.prepareStatement(checkQuery);
            checkStatement.setInt(1, id);
            ResultSet rs = checkStatement.executeQuery();

            if (!rs.next()) {
                throw new ApiException.NotFoundApiException("Voucher dengan id " + id + " tidak ditemukan");
            }

            String deleteQuery = "DELETE FROM vouchers WHERE id = ?";
            PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery);
            deleteStatement.setInt(1, id);
            deleteStatement.executeUpdate();

            res.setBody(Server.jsonMap(Map.of("status", 200, "message", "Voucher berhasil dihapus")));
            res.send(HttpURLConnection.HTTP_OK);
        }
    }
}
