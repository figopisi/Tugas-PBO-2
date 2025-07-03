package DAO.Customer;

import config.Database;
import model.Review;
import util.Exception.ApiException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerReviewDAO {
    public List<Review> findByCustomerId(int customerId) {
        try (Connection conn = Database.getConnection()) {
            if (!existsCustomer(conn, customerId)) {
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

            return reviews;
        } catch (SQLException e) {
            throw new ApiException.InternalServerErrorApiException("Gagal mengambil review customer.");
        }
    }

    public int create(int customerId, int bookingId, Review review) {
        try (Connection conn = Database.getConnection()) {
            if (!existsCustomer(conn, customerId)) {
                throw new ApiException.NotFoundApiException("Customer dengan id " + customerId + " tidak ditemukan");
            }

            if (!bookingBelongsToCustomer(conn, bookingId, customerId)) {
                throw new ApiException.NotFoundApiException("Booking dengan id " + bookingId + " tidak ditemukan atau bukan milik customer ini");
            }

            String query = "INSERT INTO reviews(booking, star, title, content) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, bookingId);
            stmt.setInt(2, review.getStar());
            stmt.setString(3, review.getTitle());
            stmt.setString(4, review.getContent());
            stmt.executeUpdate();

            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) return keys.getInt(1);
            else throw new ApiException.InternalServerErrorApiException("Gagal menyimpan review.");
        } catch (SQLException e) {
            throw new ApiException.InternalServerErrorApiException("Gagal menyimpan review.");
        }
    }

    private boolean existsCustomer(Connection conn, int customerId) throws SQLException {
        String check = "SELECT id FROM customers WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(check);
        stmt.setInt(1, customerId);
        ResultSet rs = stmt.executeQuery();
        return rs.next();
    }

    private boolean bookingBelongsToCustomer(Connection conn, int bookingId, int customerId) throws SQLException {
        String check = "SELECT id FROM bookings WHERE id = ? AND customer = ?";
        PreparedStatement stmt = conn.prepareStatement(check);
        stmt.setInt(1, bookingId);
        stmt.setInt(2, customerId);
        ResultSet rs = stmt.executeQuery();
        return rs.next();
    }

    private Review fromResultSet(ResultSet rs) throws SQLException {
        return new Review(
                rs.getInt("booking"),
                rs.getInt("star"),
                rs.getString("title"),
                rs.getString("content")
        );
    }
}