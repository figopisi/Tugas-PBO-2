package service.VillaService;

import config.Database;
import model.RoomType;
import util.Request;
import util.Response;
import util.Exception.ApiException;
import util.validator.VillaRoomValidator;
import web.Server;

import java.net.HttpURLConnection;
import java.sql.*;
import java.util.*;

public class VillaRoomService {

    public static void index(Response res) throws Exception {
        try (Connection connection = Database.getConnection()) {
            String query = "SELECT * FROM room_types";
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            List<RoomType> roomTypes = new ArrayList<>();
            while (rs.next()) {
                roomTypes.add(fromResultSet(rs));
            }

            res.setBody(Server.jsonMap(Map.of("status", 200, "data", roomTypes)));
            res.send(HttpURLConnection.HTTP_OK);
        }
    }

    public static void show(int id, Response res) throws Exception {
        try (Connection connection = Database.getConnection()) {
            String query = "SELECT * FROM room_types WHERE id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) {
                throw new ApiException.NotFoundApiException("Room type dengan id " + id + " tidak ditemukan");
            }

            RoomType room = fromResultSet(rs);
            res.setBody(Server.jsonMap(Map.of("status", 200, "data", room)));
            res.send(HttpURLConnection.HTTP_OK);
        }
    }

    public static void create(Request req, Response res) throws Exception {
        Map<String, Object> body = req.getJSON();
        VillaRoomValidator.validateInput(body);

        try (Connection connection = Database.getConnection()) {
            String query = "INSERT INTO room_types " +
                    "(villa, name, quantity, capacity, price, bed_size, has_desk, has_ac, has_tv, has_wifi, has_shower, has_hotwater, has_fridge) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, (int) body.get("villa"));
            stmt.setString(2, (String) body.get("name"));
            stmt.setInt(3, (int) body.get("quantity"));
            stmt.setInt(4, (int) body.get("capacity"));
            stmt.setDouble(5, ((Number) body.get("price")).doubleValue());
            stmt.setString(6, (String) body.get("bed_size"));
            stmt.setBoolean(7, (boolean) body.get("has_desk"));
            stmt.setBoolean(8, (boolean) body.get("has_ac"));
            stmt.setBoolean(9, (boolean) body.get("has_tv"));
            stmt.setBoolean(10, (boolean) body.get("has_wifi"));
            stmt.setBoolean(11, (boolean) body.get("has_shower"));
            stmt.setBoolean(12, (boolean) body.get("has_hotwater"));
            stmt.setBoolean(13, (boolean) body.get("has_fridge"));
            stmt.executeUpdate();

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            generatedKeys.next();

            res.setBody(Server.jsonMap(Map.of(
                    "status", 201,
                    "id", generatedKeys.getInt(1),
                    "message", "Room type berhasil dibuat"
            )));
            res.send(HttpURLConnection.HTTP_CREATED);
        }
    }

    public static void update(int id, Request req, Response res) throws Exception {
        Map<String, Object> body = req.getJSON();
        VillaRoomValidator.validateInput(body);

        try (Connection connection = Database.getConnection()) {
            String checkQuery = "SELECT id FROM room_types WHERE id = ?";
            PreparedStatement checkStmt = connection.prepareStatement(checkQuery);
            checkStmt.setInt(1, id);
            ResultSet rs = checkStmt.executeQuery();
            if (!rs.next()) {
                throw new ApiException.NotFoundApiException("Room type dengan id " + id + " tidak ditemukan");
            }

            String updateQuery = "UPDATE room_types SET villa = ?, name = ?, quantity = ?, capacity = ?, " +
                    "price = ?, bed_size = ?, has_desk = ?, has_ac = ?, has_tv = ?, has_wifi = ?, has_shower = ?, has_hotwater = ?, has_fridge = ? WHERE id = ?";

            PreparedStatement stmt = connection.prepareStatement(updateQuery);
            stmt.setInt(1, (int) body.get("villa"));
            stmt.setString(2, (String) body.get("name"));
            stmt.setInt(3, (int) body.get("quantity"));
            stmt.setInt(4, (int) body.get("capacity"));
            stmt.setDouble(5, ((Number) body.get("price")).doubleValue());
            stmt.setString(6, (String) body.get("bed_size"));
            stmt.setBoolean(7, (boolean) body.get("has_desk"));
            stmt.setBoolean(8, (boolean) body.get("has_ac"));
            stmt.setBoolean(9, (boolean) body.get("has_tv"));
            stmt.setBoolean(10, (boolean) body.get("has_wifi"));
            stmt.setBoolean(11, (boolean) body.get("has_shower"));
            stmt.setBoolean(12, (boolean) body.get("has_hotwater"));
            stmt.setBoolean(13, (boolean) body.get("has_fridge"));
            stmt.setInt(14, id);
            stmt.executeUpdate();

            res.setBody(Server.jsonMap(Map.of("status", 200, "message", "Room type berhasil diubah")));
            res.send(HttpURLConnection.HTTP_OK);
        }
    }

    public static void destroy(int id, Response res) throws Exception {
        try (Connection connection = Database.getConnection()) {
            String checkQuery = "SELECT id FROM room_types WHERE id = ?";
            PreparedStatement checkStmt = connection.prepareStatement(checkQuery);
            checkStmt.setInt(1, id);
            ResultSet rs = checkStmt.executeQuery();
            if (!rs.next()) {
                throw new ApiException.NotFoundApiException("Room type dengan id " + id + " tidak ditemukan");
            }

            String deleteQuery = "DELETE FROM room_types WHERE id = ?";
            PreparedStatement stmt = connection.prepareStatement(deleteQuery);
            stmt.setInt(1, id);
            stmt.executeUpdate();

            res.setBody(Server.jsonMap(Map.of("status", 200, "message", "Room type berhasil dihapus")));
            res.send(HttpURLConnection.HTTP_OK);
        }
    }

    // Helper method to map ResultSet to RoomType
    private static RoomType fromResultSet(ResultSet rs) throws SQLException {
        return new RoomType(
                rs.getInt("id"),
                rs.getInt("villa"),
                rs.getString("name"),
                rs.getInt("quantity"),
                rs.getInt("capacity"),
                rs.getDouble("price"),
                rs.getString("bed_size"),
                rs.getBoolean("has_desk"),
                rs.getBoolean("has_ac"),
                rs.getBoolean("has_tv"),
                rs.getBoolean("has_wifi"),
                rs.getBoolean("has_shower"),
                rs.getBoolean("has_hotwater"),
                rs.getBoolean("has_fridge")
        );
    }
}
