package DAO.Villa;

import config.Database;
import model.Review;
import util.Exception.ApiException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ReviewDAO {

    public List<Review> findByVillaId(int villaId) {
        try (Connection conn = Database.getConnection()) {
            String query = "SELECT r.* FROM reviews r " +
                    "JOIN bookings b ON r.booking = b.id " +
                    "JOIN room_types rt ON b.room_type = rt.id " +
                    "WHERE rt.villa = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, villaId);
            ResultSet rs = stmt.executeQuery();

            List<Review> reviews = new ArrayList<>();
            while (rs.next()) {
                reviews.add(new Review(
                        rs.getInt("booking"),
                        rs.getInt("star"),
                        rs.getString("title"),
                        rs.getString("content")
                ));
            }
            return reviews;

        } catch (Exception e) {
            throw new ApiException.InternalServerErrorApiException("Gagal mengambil data review.");
        }
    }
}
