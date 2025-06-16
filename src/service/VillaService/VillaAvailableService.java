package service.VillaService;

import model.RoomType;
import config.Database;
import util.Exception.ValidationException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class VillaAvailableService {

    public static List<RoomType> getAvailableRooms(int villaId, String ciDate, String coDate) throws Exception {
        validateDateParams(ciDate, coDate);

        List<RoomType> availableRooms = new ArrayList<>();
        String sql = "SELECT * FROM room_type rt " +
                "WHERE rt.villa_id = ? AND rt.id NOT IN (" +
                "    SELECT room_type_id FROM bookings " +
                "    WHERE (? < co_date AND ? > ci_date)" +
                ")";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, villaId);
            stmt.setString(2, ciDate);
            stmt.setString(3, coDate);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                RoomType room = new RoomType(
                        rs.getInt("id"),
                        rs.getInt("villa_id"),
                        rs.getString("name"),
                        rs.getInt("quantity"),
                        rs.getInt("capacity"),
                        rs.getDouble("price"),
                        rs.getString("bed_size"),
                        rs.getBoolean("has_desk"),
                        rs.getBoolean("has_ac"),
                        rs.getBoolean("has_wifi"),
                        rs.getBoolean("has_bathtub")
                );
                availableRooms.add(room);
            }
        }

        return availableRooms;
    }

    private static void validateDateParams(String ciDate, String coDate) throws ValidationException {
        if (ciDate == null || ciDate.trim().isEmpty()) {
            throw new ValidationException("Tanggal check-in tidak boleh kosong");
        }

        if (coDate == null || coDate.trim().isEmpty()) {
            throw new ValidationException("Tanggal check-out tidak boleh kosong");
        }
    }
}
