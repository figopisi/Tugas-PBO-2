package service.VillaService;

import DAO.Villa.ReviewDAO;
import DAO.Villa.VillaDAO;
import model.Review;
import util.Exception.ApiException;
import util.Response.ResponseHelper;
import web.Server;

import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;

public class ReviewService {

    public static void indexByVilla(int villaId, ResponseHelper res) throws Exception {
        VillaDAO villaDAO = new VillaDAO();
        if (!villaDAO.existsById(villaId)) {
            throw new ApiException.NotFoundApiException("Villa dengan id " + villaId + " tidak ditemukan");
        }

        List<Review> reviews = new ReviewDAO().findByVillaId(villaId);

        res.setBody(Server.jsonMap(Map.of("status", 200, "data", reviews)));
        res.send(HttpURLConnection.HTTP_OK);
    }
}
