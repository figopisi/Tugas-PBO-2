package service.CustomerService;

import DAO.Customer.BookingDAO;
import model.Booking;
import util.Request;
import util.Response.ResponseHelper;
import util.Exception.ApiException;
import web.Server;
import util.validator.BookingValidator;

import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;

public class BookingService {

    private static final BookingDAO bookingDAO = new BookingDAO();

    public static void indexByCustomer(int customerId, ResponseHelper res) {
        if (!bookingDAO.existsCustomerById(customerId)) {
            throw new ApiException.NotFoundApiException("Customer dengan id " + customerId + " tidak ditemukan");
        }

        List<Booking> bookings = bookingDAO.findByCustomerId(customerId);

        try {
            res.setBody(Server.jsonMap(Map.of(
                    "status", 200,
                    "data", bookings
            )));
            res.send(HttpURLConnection.HTTP_OK);
        } catch (Exception e) {
            throw new ApiException.InternalServerErrorApiException("Gagal memproses response JSON");
        }
    }

    public static void create(int customerId, Request req, ResponseHelper res) {
        Map<String, Object> body = req.getJSON();
        BookingValidator.validateInput(body);

        if (!bookingDAO.existsCustomerById(customerId)) {
            throw new ApiException.NotFoundApiException("Customer dengan id " + customerId + " tidak ditemukan");
        }

        Booking booking = new Booking(
                0,
                customerId,
                (Integer) body.get("room_type"),
                (String) body.get("checkin_date"),
                (String) body.get("checkout_date"),
                (Double) body.get("price"),
                (Integer) body.get("voucher"),
                (Double) body.get("final_price"),
                (String) body.get("payment_status"),
                (Boolean) body.getOrDefault("has_checkedin", false),
                (Boolean) body.getOrDefault("has_checkedout", false)
        );

        int newId = bookingDAO.create(booking);

        try {
            res.setBody(Server.jsonMap(Map.of(
                    "status", 201,
                    "id", newId,
                    "message", "Pemesanan vila berhasil dibuat"
            )));
            res.send(HttpURLConnection.HTTP_CREATED);
        } catch (Exception e) {
            throw new ApiException.InternalServerErrorApiException("Gagal memproses response JSON");
        }
    }
}