package service.VillaService;

import DAO.Villa.BookingDAO;
import DAO.Villa.VillaDAO;
import util.Exception.ApiException;
import util.Response.ResponseHelper;
import web.Server;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;
import model.Booking;

public class BookingService {

    public static void indexByVilla(int villaId, ResponseHelper res) throws Exception {
        VillaDAO villaDAO = new VillaDAO();
        if (!villaDAO.existsById(villaId)) {
            throw new ApiException.NotFoundApiException("Villa dengan id " + villaId + " tidak ditemukan");
        }

        List<Booking> bookings = BookingDAO.findByVillaId(villaId);

        res.setBody(Server.jsonMap(Map.of("status", 200, "data", bookings)));
        res.send(HttpURLConnection.HTTP_OK);
    }
}

