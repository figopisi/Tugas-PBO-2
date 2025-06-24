package service.CustomerService;

import config.Database;
import model.Booking;
import model.Customer;
import util.Request;
import util.Response;
import util.Exception.ApiException;
import util.validator.CustomerValidator;
import web.Server;

import java.net.HttpURLConnection;
import java.sql.*;
import java.util.*;

public class CustomerBookingService {

    public static void indexByCustomer(int customerId, Response res) throws Exception {
        try (Connection conn = Database.getConnection()) {
            String checkCustomer = "SELECT id FROM customers WHERE id = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkCustomer);
            checkStmt.setInt(1, customerId);
            ResultSet checkRs = checkStmt.executeQuery();

            if (!checkRs.next()) {
                throw new ApiException.NotFoundApiException("Customer dengan id " + customerId + " tidak ditemukan");
            }

            String query = "SELECT * FROM bookings WHERE customer = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();

            List<Booking> bookings = new ArrayList<>();
            while (rs.next()) {
                bookings.add(fromResultSet(rs));
            }

            res.setBody(Server.jsonMap(Map.of("status", 200, "data", bookings)));
            res.send(HttpURLConnection.HTTP_OK);
        }
    }

    public static void create(int customerId, Request req, Response res) throws Exception {
        Map<String, Object> body = req.getJSON();
        CustomerValidator.validateInput(body);

        try (Connection connection = Database.getConnection()) {
            String checkCustomer = "SELECT id FROM customers WHERE id = ?";
            PreparedStatement checkStmt = connection.prepareStatement(checkCustomer);
            checkStmt.setInt(1, customerId);
            ResultSet checkRs = checkStmt.executeQuery();

            if (!checkRs.next()) {
                throw new ApiException.NotFoundApiException("Customer dengan id " + customerId + " tidak ditemukan");
            }

            String query = "INSERT INTO bookings(customer, room_type, checkin_date, checkout_date, price, voucher, final_price, payment_status, has_checkedin, has_checkedout) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, customerId);
            statement.setInt(2, (Integer) body.get("room_type"));
            statement.setString(3, (String) body.get("checkin_date"));
            statement.setString(4, (String) body.get("checkout_date"));
            statement.setDouble(5, (Double) body.get("price"));
            statement.setObject(6, body.get("voucher"), Types.INTEGER);
            statement.setDouble(7, (Double) body.get("final_price"));
            statement.setString(8, (String) body.get("payment_status"));
            statement.setBoolean(9, (Boolean) body.getOrDefault("has_checkedin", false));
            statement.setBoolean(10, (Boolean) body.getOrDefault("has_checkedout", false));
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            generatedKeys.next();

            res.setBody(Server.jsonMap(Map.of(
                    "status", 201,
                    "id", generatedKeys.getInt(1),
                    "message", "Pemesanan vila berhasil dibuat"
            )));
            res.send(HttpURLConnection.HTTP_CREATED);
        }
    }

    private static Booking fromResultSet(ResultSet rs) throws SQLException {
        return new Booking(
                rs.getInt("id"),
                rs.getInt("customer"),
                rs.getInt("room_type"),
                rs.getString("checkin_date"),
                rs.getString("checkout_date"),
                rs.getDouble("price"),
                (Integer) rs.getObject("voucher"),
                rs.getDouble("final_price"),
                rs.getString("payment_status"),
                rs.getBoolean("has_checkedin"),
                rs.getBoolean("has_checkedout")
        );
    }
}