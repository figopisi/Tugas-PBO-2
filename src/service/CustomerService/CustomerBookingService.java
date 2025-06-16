package service.CustomerService;

import config.Database;
import model.Booking;
import util.Response;
import util.Exception.ApiException;
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

            String query = "SELECT * FROM bookings WHERE customer_id = ?";
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
