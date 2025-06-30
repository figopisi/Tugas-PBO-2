package service.CustomerService;

import config.Database;
import model.Customer;
import util.Request;
import util.Response.ResponseHelper;
import util.Exception.ApiException;
import util.validator.CustomerValidator;
import web.Server;

import java.net.HttpURLConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CustomerService {

    public static void index(ResponseHelper res) throws Exception {
        try (Connection connection = Database.getConnection()) {
            String query = "SELECT * FROM customers";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            List<Customer> customers = new ArrayList<>();
            while (resultSet.next()) {
                customers.add(new Customer(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("email"),
                        resultSet.getString("phone")
                ));
            }

            res.setBody(Server.jsonMap(Map.of("status", 200, "data", customers)));
            res.send(HttpURLConnection.HTTP_OK);
        }
    }

    public static void show(int id, ResponseHelper res) throws Exception {
        try (Connection connection = Database.getConnection()) {
            String query = "SELECT * FROM customers WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                throw new ApiException.NotFoundApiException("Customer dengan id " + id + " tidak ditemukan");
            }

            Customer customer = new Customer(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("email"),
                    resultSet.getString("phone")
            );

            res.setBody(Server.jsonMap(Map.of("status", 200, "data", customer)));
            res.send(HttpURLConnection.HTTP_OK);
        }
    }

    public static void create(Request req, ResponseHelper res) throws Exception {
        Map<String, Object> body = req.getJSON();
        CustomerValidator.validateInput(body);

        try (Connection connection = Database.getConnection()) {
            String query = "INSERT INTO customers(name, email, phone) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, (String) body.get("name"));
            statement.setString(2, (String) body.get("email"));
            statement.setString(3, (String) body.get("phone"));
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            generatedKeys.next();

            res.setBody(Server.jsonMap(Map.of(
                    "status", 201,
                    "id", generatedKeys.getInt(1),
                    "message", "Customer berhasil dibuat (registrasi berhasil)"
            )));
            res.send(HttpURLConnection.HTTP_CREATED);
        }
    }

    public static void update(int id, Request req, ResponseHelper res) throws Exception {
        Map<String, Object> body = req.getJSON();
        CustomerValidator.validateInput(body);

        try (Connection connection = Database.getConnection()) {
            String checkQuery = "SELECT id FROM customers WHERE id = ?";
            PreparedStatement checkStatement = connection.prepareStatement(checkQuery);
            checkStatement.setInt(1, id);
            ResultSet rs = checkStatement.executeQuery();

            if (!rs.next()) {
                throw new ApiException.NotFoundApiException("Customer dengan id " + id + " tidak ditemukan");
            }

            String updateQuery = "UPDATE customers SET name = ?, email = ?, phone = ? WHERE id = ?";
            PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
            updateStatement.setString(1, (String) body.get("name"));
            updateStatement.setString(2, (String) body.get("email"));
            updateStatement.setString(3, (String) body.get("phone"));
            updateStatement.setInt(4, id);
            updateStatement.executeUpdate();

            res.setBody(Server.jsonMap(Map.of("status", 200, "message", "Data customer berhasil diubah")));
            res.send(HttpURLConnection.HTTP_OK);
        }
    }
}
