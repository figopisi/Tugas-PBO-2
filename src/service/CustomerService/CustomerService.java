package service.CustomerService;

import config.Database;
import model.Customer;
import util.Request;
import util.Exception.ApiException;
import util.validator.CustomerValidator;
import util.Response;
import web.Server;

import java.net.HttpURLConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CustomerService {

    public static void index(Response res) throws Exception {
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

    public static void show(int id, Response res) throws Exception {
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
}
