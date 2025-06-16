package service.VillaService;

import config.Database;
import model.Review;
import util.Response;
import util.Exception.ApiException;
import web.Server;

import java.net.HttpURLConnection;
import java.sql.*;
import java.util.*;

public class VillaReviewService {

    public static void indexByVilla(int villaId, Response res) throws Exception {
        try (Connection conn = Database.getConnection()) {
            String checkVilla = "SELECT id FROM villas WHERE id = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkVilla);
            checkStmt.setInt(1, villaId);
            ResultSet checkRs = checkStmt.executeQuery();
            if (!checkRs.next()) {
                throw new ApiException.NotFoundApiException("Villa dengan id " + villaId + " tidak ditemukan");
            }

            String query = "SELECT r.* FROM reviews r " +
                    "JOIN bookings b ON r.booking = b.id " +
                    "JOIN room_types rt ON b.room_type = rt.id " +
                    "WHERE rt.villa_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, villaId);
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
