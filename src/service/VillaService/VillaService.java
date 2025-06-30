package service.VillaService;

import config.Database;
import model.Villa;
import util.Request;
import util.Exception.ApiException;
import util.validator.VillaValidator;
import util.Response.ResponseHelper;
import web.Server;

import java.net.HttpURLConnection;
import java.sql.*;
import java.util.List;
import java.util.Map;

public class VillaService {

    public static void index(ResponseHelper res) throws Exception {
        try (Connection connection = Database.getConnection()) {
            String query = "SELECT * FROM villas";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            List<Villa> villas = Villa.fromResultSet(resultSet);
            res.setBody(Server.jsonMap(Map.of("status", 200, "data", villas)));
            res.send(HttpURLConnection.HTTP_OK);
        }
    }

    public static void show(int id, ResponseHelper res) throws Exception {
        try (Connection connection = Database.getConnection()) {
            String query = "SELECT * FROM villas WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                throw new ApiException.NotFoundApiException("Villa dengan id " + id + " tidak ditemukan");
            }

            Villa villa = new Villa(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("description"),
                    resultSet.getString("address")
            );

            res.setBody(Server.jsonMap(Map.of("status", 200, "data", villa)));
            res.send(HttpURLConnection.HTTP_OK);
        }
    }

    public static void create(Request req, ResponseHelper res) throws Exception {
        Map<String, Object> body = req.getJSON();
        VillaValidator.validateInput(body);

        try (Connection connection = Database.getConnection()) {
            String query = "INSERT INTO villas(name, description, address) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, (String) body.get("name"));
            statement.setString(2, (String) body.get("description"));
            statement.setString(3, (String) body.get("address"));
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            generatedKeys.next();

            res.setBody(Server.jsonMap(Map.of(
                    "status", 201,
                    "id", generatedKeys.getInt(1),
                    "message", "Villa berhasil dibuat"
            )));
            res.send(HttpURLConnection.HTTP_CREATED);
        }
    }

    public static void update(int id, Request req, ResponseHelper res) throws Exception {
        Map<String, Object> body = req.getJSON();
        VillaValidator.validateInput(body);

        try (Connection connection = Database.getConnection()) {
            String checkQuery = "SELECT id FROM villas WHERE id = ?";
            PreparedStatement checkStatement = connection.prepareStatement(checkQuery);
            checkStatement.setInt(1, id);
            ResultSet rs = checkStatement.executeQuery();

            if (!rs.next()) {
                throw new ApiException.NotFoundApiException("Villa dengan id " + id + " tidak ditemukan");
            }

            String updateQuery = "UPDATE villas SET name = ?, description = ?, address = ? WHERE id = ?";
            PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
            updateStatement.setString(1, (String) body.get("name"));
            updateStatement.setString(2, (String) body.get("description"));
            updateStatement.setString(3, (String) body.get("address"));
            updateStatement.setInt(4, id);
            updateStatement.executeUpdate();

            res.setBody(Server.jsonMap(Map.of("status", 200, "message", "Villa berhasil diubah")));
            res.send(HttpURLConnection.HTTP_OK);
        }
    }

    public static void destroy(int id, ResponseHelper res) throws Exception {
        try (Connection connection = Database.getConnection()) {
            String checkQuery = "SELECT id FROM villas WHERE id = ?";
            PreparedStatement checkStatement = connection.prepareStatement(checkQuery);
            checkStatement.setInt(1, id);
            ResultSet rs = checkStatement.executeQuery();

            if (!rs.next()) {
                throw new ApiException.NotFoundApiException("Villa dengan id " + id + " tidak ditemukan");
            }

            String deleteQuery = "DELETE FROM villas WHERE id = ?";
            PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery);
            deleteStatement.setInt(1, id);
            deleteStatement.executeUpdate();

            res.setBody(Server.jsonMap(Map.of("status", 200, "message", "Villa berhasil dihapus")));
            res.send(HttpURLConnection.HTTP_OK);
        }
    }
}
