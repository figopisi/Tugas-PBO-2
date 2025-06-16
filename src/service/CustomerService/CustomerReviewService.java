package service.CustomerService;

import config.Database;
import model.Review;
import util.Response;
import util.Exception.ApiException;
import web.Server;

import java.net.HttpURLConnection;
import java.sql.*;
import java.util.*;

public class CustomerReviewService {

    public static void indexByCustomer(int customerId, Response res) throws Exception {
        try (Connection conn = Database.getConnection()) {
            String checkCustomer = "SELECT id FROM customers WHERE id = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkCustomer);
            checkStmt.setInt(1, customerId);
            ResultSet checkRs = checkStmt.executeQuery();

            if (!checkRs.next()) {
                throw new ApiException.NotFoundApiException("Customer dengan id " + customerId + " tidak ditemukan");
            }

            String query = "SELECT * FROM reviews WHERE customer_id = ?";
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

    private static Review fromResultSet(ResultSet rs) throws SQLException {
        return new Review(
                rs.getInt("booking"),
                rs.getInt("star"),
                rs.getString("title"),
                rs.getString("content")
        );
    }
}
