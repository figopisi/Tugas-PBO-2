package DAO.Customer;

import config.Database;
import model.Booking;
import util.Exception.ApiException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerBookingDAO {

    public boolean existsCustomerById(int customerId) {
        try (Connection conn = Database.getConnection()) {
            String sql = "SELECT 1 FROM customers WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new ApiException.InternalServerErrorApiException("Gagal memeriksa customer: " + e.getMessage());
        }
    }

    public List<Booking> findByCustomerId(int customerId) {
        List<Booking> bookings = new ArrayList<>();
        try (Connection conn = Database.getConnection()) {
            String sql = "SELECT * FROM bookings WHERE customer = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                bookings.add(fromResultSet(rs));
            }
            return bookings;
        } catch (SQLException e) {
            throw new ApiException.InternalServerErrorApiException("Gagal mengambil data booking: " + e.getMessage());
        }
    }

    public int create(Booking booking) {
        try (Connection conn = Database.getConnection()) {
            String sql = "INSERT INTO bookings (" +
                    "customer, room_type, checkin_date, checkout_date, " +
                    "price, voucher, final_price, payment_status, " +
                    "has_checkedin, has_checkedout" +
                    ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, booking.getCustomerId());
            stmt.setInt(2, booking.getRoomTypeId());
            stmt.setString(3, booking.getCheckinDate());
            stmt.setString(4, booking.getCheckoutDate());
            stmt.setDouble(5, booking.getPrice());

            if (booking.getVoucherId() != null) {
                stmt.setInt(6, booking.getVoucherId());
            } else {
                stmt.setNull(6, Types.INTEGER);
            }

            stmt.setDouble(7, booking.getFinalPrice());
            stmt.setString(8, booking.getPaymentStatus());
            stmt.setBoolean(9, booking.isHasCheckedIn());
            stmt.setBoolean(10, booking.isHasCheckedOut());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new ApiException.InternalServerErrorApiException("Gagal membuat booking.");
            }

            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) {
                return keys.getInt(1);
            } else {
                throw new ApiException.InternalServerErrorApiException("Gagal mengambil ID booking baru.");
            }
        } catch (SQLException e) {
            throw new ApiException.InternalServerErrorApiException("Gagal menyimpan booking: " + e.getMessage());
        }
    }

    private Booking fromResultSet(ResultSet rs) throws SQLException {
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
