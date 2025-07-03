package service.CustomerService;

import DAO.Customer.CustomerReviewDAO;
import model.Review;
import util.Request;
import util.Response.JsonHelper;
import util.Response.ResponseHelper;

import java.net.HttpURLConnection;
import java.util.Map;

public class CustomerReviewService {
    private static final CustomerReviewDAO reviewDAO = new CustomerReviewDAO();

    public static void indexByCustomer(int customerId, ResponseHelper res) {
        var reviews = reviewDAO.findByCustomerId(customerId);
        res.setBody(JsonHelper.success(reviews));
        res.send(HttpURLConnection.HTTP_OK);
    }

    public static void createByBooking(int customerId, int bookingId, Request req, ResponseHelper res) {
        Map<String, Object> body = req.getJSON();

        Review review = new Review(
                bookingId,
                (Integer) body.get("star"),
                (String) body.get("title"),
                (String) body.get("content")
        );

        int newId = reviewDAO.create(customerId, bookingId, review);

        res.setBody(JsonHelper.created(newId, "Review berhasil diberikan pada vila"));
        res.send(HttpURLConnection.HTTP_CREATED);
    }
}