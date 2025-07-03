package DAO.Villa;

import config.Database;
import model.Booking;
import util.Exception.ApiException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {

    public static List<Booking> findByVillaId(int villaId) {
        try (Connection conn = Database.getConnection()) {
            String query =  "SELECT * FROM bookings WHERE room_type IN " +
                            "(SELECT id FROM room_types WHERE villa_id = ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, villaId);
            ResultSet rs = stmt.executeQuery();

            List<Booking> bookings = new ArrayList<>();
            while (rs.next()) {
                bookings.add(new Booking(
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
                ));
            }
            return bookings;
        } catch (Exception e) {
            throw new ApiException.InternalServerErrorApiException("Gagal mengambil booking untuk villa ini.");
        }
    }
}
