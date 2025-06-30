package service.CustomerService;

import config.Database;
import model.Review;
import util.Request;
import util.Response.ResponseHelper;
import util.Exception.ApiException;
import util.validator.CustomerValidator;
import web.Server;

import java.net.HttpURLConnection;
import java.sql.*;
import java.util.*;

public class CustomerReviewService {

    public static void indexByCustomer(int customerId, ResponseHelper res) throws Exception {
        try (Connection conn = Database.getConnection()) {
            String checkCustomer = "SELECT id FROM customers WHERE id = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkCustomer);
            checkStmt.setInt(1, customerId);
            ResultSet checkRs = checkStmt.executeQuery();

            if (!checkRs.next()) {
                throw new ApiException.NotFoundApiException("Customer dengan id " + customerId + " tidak ditemukan");
            }

            String query = "SELECT r.* FROM reviews r " +
                    "JOIN bookings b ON r.booking = b.id " +
                    "WHERE b.customer = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();

            List<Review> reviews = new ArrayList<>();
            while (rs.next()) {
                reviews.add(fromResultSet(rs));
            }

            res.setBody(Server.jsonMap(Map.of("status", 200, "data", reviews)));
            res.send(HttpURLConnection.HTTP_OK);
        }
    }

    public static void createByBooking(int customerId, int bookingId, Request req, ResponseHelper res) throws Exception {
        Map<String, Object> body = req.getJSON();
        CustomerValidator.validateInput(body);

        try (Connection connection = Database.getConnection()) {
            String checkCustomer = "SELECT id FROM customers WHERE id = ?";
            PreparedStatement checkCustomerStmt = connection.prepareStatement(checkCustomer);
            checkCustomerStmt.setInt(1, customerId);
            ResultSet checkCustomerRs = checkCustomerStmt.executeQuery();

            if (!checkCustomerRs.next()) {
                throw new ApiException.NotFoundApiException("Customer dengan id " + customerId + " tidak ditemukan");
            }

            String checkBooking = "SELECT id FROM bookings WHERE id = ? AND customer = ?";
            PreparedStatement checkBookingStmt = connection.prepareStatement(checkBooking);
            checkBookingStmt.setInt(1, bookingId);
            checkBookingStmt.setInt(2, customerId);
            ResultSet checkBookingRs = checkBookingStmt.executeQuery();

            if (!checkBookingRs.next()) {
                throw new ApiException.NotFoundApiException("Booking dengan id " + bookingId + " tidak ditemukan atau bukan milik customer ini");
            }

            String query = "INSERT INTO reviews(booking, star, title, content) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, bookingId);
            statement.setInt(2, (Integer) body.get("star"));
            statement.setString(3, (String) body.get("title"));
            statement.setString(4, (String) body.get("content"));
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            generatedKeys.next();

            res.setBody(Server.jsonMap(Map.of(
                    "status", 201,
                    "id", generatedKeys.getInt(1),
                    "message", "Review berhasil diberikan pada vila"
            )));
            res.send(HttpURLConnection.HTTP_CREATED);
        }
    }

    private static Review fromResultSet(ResultSet rs) throws SQLException {
        return new Review(
                rs.getInt("booking"),
                rs.getInt("star"),
                rs.getString("title"),
                rs.getString("content")
        );
    }
}