package service.VillaService;

import DAO.Villa.RoomDAO;
import model.RoomType;
import util.Exception.ValidationException;
import util.Response.ResponseHelper;
import web.Server;

import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;

public class AvailableService {

    private static final RoomDAO roomDAO = new RoomDAO();

    public static List<RoomType> findAvailable(int villaId, String ciDate, String coDate) {
        validateDateParams(ciDate, coDate);
        return roomDAO.findAvailable(villaId, ciDate, coDate);
    }

    public static void index(int villaId, String ciDate, String coDate, ResponseHelper res) throws Exception {
        validateDateParams(ciDate, coDate);

        List<RoomType> rooms = roomDAO.findAvailable(villaId, ciDate, coDate);
        res.setBody(Server.jsonMap(Map.of("status", 200, "data", rooms)));
        res.send(HttpURLConnection.HTTP_OK);
    }

    // 👉 Validasi tanggal tetap dipakai bersama
    private static void validateDateParams(String ciDate, String coDate) throws ValidationException {
        if (ciDate == null || ciDate.trim().isEmpty()) {
            throw new ValidationException("Tanggal check-in tidak boleh kosong");
        }
        if (coDate == null || coDate.trim().isEmpty()) {
            throw new ValidationException("Tanggal check-out tidak boleh kosong");
        }
    }
}
