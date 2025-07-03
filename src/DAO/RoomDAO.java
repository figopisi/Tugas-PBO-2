package DAO;

import config.Database;
import model.RoomType;
import util.Exception.ApiException;

import java.sql.*;
import java.util.List;

public class RoomDAO {

    public List<RoomType> findAll() {
        try (Connection conn = Database.getConnection()) {
            String query = "SELECT * FROM room_types";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            return RoomType.fromResultSet(rs);
        } catch (Exception e) {
            throw new ApiException.InternalServerErrorApiException("Gagal mengambil data Room Type.");
        }
    }

    public RoomType findById(int id) {
        try (Connection conn = Database.getConnection()) {
            String query = "SELECT * FROM room_types WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            List<RoomType> result = RoomType.fromResultSet(rs);
            if (result.isEmpty()) {
                throw new ApiException.NotFoundApiException("Room type dengan id " + id + " tidak ditemukan.");
            }
            return result.get(0);
        } catch (ApiException e) {
            throw e;
        } catch (Exception e) {
            throw new ApiException.InternalServerErrorApiException("Gagal mengambil detail Room Type.");
        }
    }

    public int create(RoomType room) {
        try (Connection conn = Database.getConnection()) {
            String query = "INSERT INTO room_types (villa, name, quantity, capacity, price, bed_size, " +
                    "has_desk, has_ac, has_tv, has_wifi, has_shower, has_hotwater, has_fridge) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
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

            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) {
                return keys.getInt(1);
            } else {
                throw new ApiException.InternalServerErrorApiException("Gagal mengambil ID baru Room Type.");
            }
        } catch (Exception e) {
            throw new ApiException.InternalServerErrorApiException("Gagal membuat Room Type.");
        }
    }

    public void update(int id, RoomType room) {
        try (Connection conn = Database.getConnection()) {
            String check = "SELECT id FROM room_types WHERE id = ?";
            PreparedStatement checkStmt = conn.prepareStatement(check);
            checkStmt.setInt(1, id);
            ResultSet rs = checkStmt.executeQuery();
            if (!rs.next()) {
                throw new ApiException.NotFoundApiException("Room type dengan id " + id + " tidak ditemukan.");
            }

            String query = "UPDATE room_types SET villa = ?, name = ?, quantity = ?, capacity = ?, price = ?, " +
                    "bed_size = ?, has_desk = ?, has_ac = ?, has_tv = ?, has_wifi = ?, has_shower = ?, has_hotwater = ?, has_fridge = ? " +
                    "WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
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
        } catch (ApiException e) {
            throw e;
        } catch (Exception e) {
            throw new ApiException.InternalServerErrorApiException("Gagal memperbarui Room Type.");
        }
    }

    public void delete(int id) {
        try (Connection conn = Database.getConnection()) {
            String check = "SELECT id FROM room_types WHERE id = ?";
            PreparedStatement checkStmt = conn.prepareStatement(check);
            checkStmt.setInt(1, id);
            ResultSet rs = checkStmt.executeQuery();
            if (!rs.next()) {
                throw new ApiException.NotFoundApiException("Room type dengan id " + id + " tidak ditemukan.");
            }

            String query = "DELETE FROM room_types WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (ApiException e) {
            throw e;
        } catch (Exception e) {
            throw new ApiException.InternalServerErrorApiException("Gagal menghapus Room Type.");
        }
    }
}
