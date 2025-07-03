package service.VillaService;

import DAO.VillaDAO;
import model.Villa;
import util.Exception.ApiException;
import util.Request;
import util.Response.JsonHelper;
import util.Response.ResponseHelper;

import java.net.HttpURLConnection;
import java.util.Map;

public class VillaService {

    private static final VillaDAO villaDAO = new VillaDAO();

    public static void index(ResponseHelper res) {
        var villas = villaDAO.findAll();
        res.setBody(JsonHelper.success(villas));
        res.send(HttpURLConnection.HTTP_OK);
    }

    public static void show(int id, ResponseHelper res) {
        Villa villa = villaDAO.findById(id);
        res.setBody(JsonHelper.success(villa));
        res.send(HttpURLConnection.HTTP_OK);
    }

    public static void create(Request req, ResponseHelper res) {
        Map<String, Object> body = req.getJSON();
        validateVillaInput(body);

        Villa villa = buildVilla(body);

        int newId = villaDAO.create(villa);

        res.setBody(JsonHelper.created(newId, "Villa berhasil dibuat."));
        res.send(HttpURLConnection.HTTP_CREATED);
    }

    public static void update(int id, Request req, ResponseHelper res) {
        Map<String, Object> body = req.getJSON();
        validateVillaInput(body);

        Villa villa = buildVilla(body);

        villaDAO.update(id, villa);

        res.setBody(JsonHelper.success("Villa berhasil diperbarui."));
        res.send(HttpURLConnection.HTTP_OK);
    }

    public static void destroy(int id, ResponseHelper res) {
        villaDAO.delete(id);

        res.setBody(JsonHelper.success("Villa berhasil dihapus."));
        res.send(HttpURLConnection.HTTP_OK);
    }

    private static void validateVillaInput(Map<String, Object> body) {
        if (!body.containsKey("name") || !body.containsKey("description") || !body.containsKey("address")) {
            throw new ApiException.BadRequestApiException("Data villa tidak lengkap. Wajib: name, description, address.");
        }
    }

    private static Villa buildVilla(Map<String, Object> body) {
        Villa villa = new Villa();
        villa.setName((String) body.get("name"));
        villa.setDescription((String) body.get("description"));
        villa.setAddress((String) body.get("address"));
        return villa;
    }
}
