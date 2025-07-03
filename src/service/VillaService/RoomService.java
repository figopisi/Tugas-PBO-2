package service.VillaService;

import DAO.RoomDAO;
import model.RoomType;
import util.Request;
import util.Response.JsonHelper;
import util.Response.ResponseHelper;
import util.validator.VillaRoomValidator;

import java.net.HttpURLConnection;
import java.util.Map;

public class RoomService {

    private static final RoomDAO roomDAO = new RoomDAO();

    public static void index(ResponseHelper res) {
        var rooms = roomDAO.findAll();
        res.setBody(JsonHelper.success(rooms));
        res.send(HttpURLConnection.HTTP_OK);
    }

    public static void show(int id, ResponseHelper res) {
        var room = roomDAO.findById(id);
        res.setBody(JsonHelper.success(room));
        res.send(HttpURLConnection.HTTP_OK);
    }

    public static void create(Request req, ResponseHelper res) {
        Map<String, Object> body = req.getJSON();
        VillaRoomValidator.validateInput(body);

        RoomType room = buildRoomType(body);
        int newId = roomDAO.create(room);

        res.setBody(JsonHelper.created(newId, "Room type berhasil dibuat."));
        res.send(HttpURLConnection.HTTP_CREATED);
    }

    public static void update(int id, Request req, ResponseHelper res) {
        Map<String, Object> body = req.getJSON();
        VillaRoomValidator.validateInput(body);

        RoomType room = buildRoomType(body);
        roomDAO.update(id, room);

        res.setBody(JsonHelper.success("Room type berhasil diperbarui."));
        res.send(HttpURLConnection.HTTP_OK);
    }

    public static void destroy(int id, ResponseHelper res) {
        roomDAO.delete(id);
        res.setBody(JsonHelper.success("Room type berhasil dihapus."));
        res.send(HttpURLConnection.HTTP_OK);
    }

    private static RoomType buildRoomType(Map<String, Object> body) {
        return new RoomType(
                (int) body.get("villa"),
                (String) body.get("name"),
                (int) body.get("quantity"),
                (int) body.get("capacity"),
                ((Number) body.get("price")).doubleValue(),
                (String) body.get("bed_size"),
                (boolean) body.get("has_desk"),
                (boolean) body.get("has_ac"),
                (boolean) body.get("has_tv"),
                (boolean) body.get("has_wifi"),
                (boolean) body.get("has_shower"),
                (boolean) body.get("has_hotwater"),
                (boolean) body.get("has_fridge")
        );
    }
}
