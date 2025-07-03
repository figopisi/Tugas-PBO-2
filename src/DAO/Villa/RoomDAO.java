package DAO.Villa;

import config.Database;
import model.RoomType;
import util.Exception.ApiException;
import util.Exception.ValidationException;

import java.sql.*;
import java.util.List;

public class RoomDAO {

    public List<RoomType> findAll() {
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM room_types");
             ResultSet rs = stmt.executeQuery()) {

            return RoomType.fromResultSet(rs);

        } catch (Exception e) {
            throw new ApiException.InternalServerErrorApiException("Gagal mengambil data Room Type.");
        }
    }

    public RoomType findById(int id) {
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM room_types WHERE id = ?")) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                List<RoomType> result = RoomType.fromResultSet(rs);
                if (result.isEmpty()) {
                    throw new ApiException.NotFoundApiException("Room type dengan id " + id + " tidak ditemukan.");
                }
                return result.get(0);
            }

        } catch (ApiException e) {
            throw e;
        } catch (Exception e) {
            throw new ApiException.InternalServerErrorApiException("Gagal mengambil detail Room Type.");
        }
    }

    public int create(RoomType room) {
        String query = "INSERT INTO room_types (villa, name, quantity, capacity, price, bed_size, " +
                "has_desk, has_ac, has_tv, has_wifi, has_shower, has_hotwater, has_fridge) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, room.getVilla());
            stmt.setString(2, room.getName());
            stmt.setInt(3, room.getQuantity());
            stmt.setInt(4, room.getCapacity());
            stmt.setDouble(5, room.getPrice());
            stmt.setString(6, room.getBedSize());
            stmt.setBoolean(7, room.isHasDesk());
            stmt.setBoolean(8, room.isHasAc());
            stmt.setBoolean(9, room.isHasTv());
            stmt.setBoolean(10, room.isHasWifi());
            stmt.setBoolean(11, room.isHasShower());
            stmt.setBoolean(12, room.isHasHotwater());
            stmt.setBoolean(13, room.isHasFridge());

            stmt.executeUpdate();

            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getInt(1);
                } else {
                    throw new ApiException.InternalServerErrorApiException("Gagal mengambil ID baru Room Type.");
                }
            }

        } catch (Exception e) {
            throw new ApiException.InternalServerErrorApiException("Gagal membuat Room Type.");
        }
    }

    public void update(int id, RoomType room) {
        String checkQuery = "SELECT id FROM room_types WHERE id = ?";
        String updateQuery = "UPDATE room_types SET villa = ?, name = ?, quantity = ?, capacity = ?, price = ?, " +
                "bed_size = ?, has_desk = ?, has_ac = ?, has_tv = ?, has_wifi = ?, has_shower = ?, has_hotwater = ?, has_fridge = ? " +
                "WHERE id = ?";

        try (Connection conn = Database.getConnection()) {

            try (PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {
                checkStmt.setInt(1, id);
                try (ResultSet rs = checkStmt.executeQuery()) {
                    if (!rs.next()) {
                        throw new ApiException.NotFoundApiException("Room type dengan id " + id + " tidak ditemukan.");
                    }
                }
            }

            try (PreparedStatement stmt = conn.prepareStatement(updateQuery)) {
                stmt.setInt(1, room.getVilla());
                stmt.setString(2, room.getName());
                stmt.setInt(3, room.getQuantity());
                stmt.setInt(4, room.getCapacity());
                stmt.setDouble(5, room.getPrice());
                stmt.setString(6, room.getBedSize());
                stmt.setBoolean(7, room.isHasDesk());
                stmt.setBoolean(8, room.isHasAc());
                stmt.setBoolean(9, room.isHasTv());
                stmt.setBoolean(10, room.isHasWifi());
                stmt.setBoolean(11, room.isHasShower());
                stmt.setBoolean(12, room.isHasHotwater());
                stmt.setBoolean(13, room.isHasFridge());
                stmt.setInt(14, id);

                stmt.executeUpdate();
            }

        } catch (ApiException e) {
            throw e;
        } catch (Exception e) {
            throw new ApiException.InternalServerErrorApiException("Gagal memperbarui Room Type.");
        }
    }

    public void delete(int id) {
        String checkQuery = "SELECT id FROM room_types WHERE id = ?";
        String deleteQuery = "DELETE FROM room_types WHERE id = ?";

        try (Connection conn = Database.getConnection()) {

            try (PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {
                checkStmt.setInt(1, id);
                try (ResultSet rs = checkStmt.executeQuery()) {
                    if (!rs.next()) {
                        throw new ApiException.NotFoundApiException("Room type dengan id " + id + " tidak ditemukan.");
                    }
                }
            }

            try (PreparedStatement stmt = conn.prepareStatement(deleteQuery)) {
                stmt.setInt(1, id);
                stmt.executeUpdate();
            }

        } catch (ApiException e) {
            throw e;
        } catch (Exception e) {
            throw new ApiException.InternalServerErrorApiException("Gagal menghapus Room Type.");
        }
    }

    public List<RoomType> findAvailable(int villaId, String ciDate, String coDate) {
        if (ciDate == null || ciDate.trim().isEmpty()) {
            throw new ValidationException("Tanggal check-in tidak boleh kosong.");
        }
        if (coDate == null || coDate.trim().isEmpty()) {
            throw new ValidationException("Tanggal check-out tidak boleh kosong.");
        }

        String query = "SELECT * FROM room_types rt " +
                "WHERE rt.villa = ? AND rt.id NOT IN (" +
                "   SELECT room_type_id FROM bookings " +
                "   WHERE (? < checkout_date AND ? > checkin_date) " +
                ")";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, villaId);
            stmt.setString(2, ciDate);
            stmt.setString(3, coDate);

            try (ResultSet rs = stmt.executeQuery()) {
                return RoomType.fromResultSet(rs);
            }

        } catch (Exception e) {
            throw new ApiException.InternalServerErrorApiException("Gagal mengambil Room Type yang tersedia.");
        }
    }
}
