package service.VillaService;

import config.Database;
import model.Booking;
import util.Response;
import util.Exception.ApiException;
import web.Server;

import java.net.HttpURLConnection;
import java.sql.*;
import java.util.*;

public class VillaBookingService {

    public static void indexByVilla(int villaId, Response res) throws Exception {
        try (Connection conn = Database.getConnection()) {
            String checkVilla = "SELECT id FROM villas WHERE id = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkVilla);
            checkStmt.setInt(1, villaId);
            ResultSet checkRs = checkStmt.executeQuery();
            if (!checkRs.next()) {
                throw new ApiException.NotFoundApiException("Villa dengan id " + villaId + " tidak ditemukan");
            }

            String query = "SELECT * FROM bookings WHERE room_type IN " +
                    "(SELECT id FROM room_types WHERE villa_id = ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, villaId);
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
