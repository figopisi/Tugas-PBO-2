package controller.CustomerController;

import com.sun.net.httpserver.HttpExchange;
import util.Request;
import util.Response.ResponseHelper;
import web.Server;
import service.CustomerService.CustomerService;
import service.CustomerService.BookingService;
import service.CustomerService.ReviewService;
import util.Exception.ApiException;

import java.net.HttpURLConnection;
import java.util.Map;

public class CustomerHandler {

    public static void handle(HttpExchange exchange, String method, String path) throws Exception {
        // Hapus trailing slash
        path = path.replaceAll("/+$", "");

        Request req = new Request(exchange);
        ResponseHelper res = new ResponseHelper(exchange);

        try {
            // --- REVIEW: /customers/{customerId}/bookings/{bookingId}/reviews
            if (method.equals("POST") && path.matches("/customers/\\d+/bookings/\\d+/reviews")) {
                String[] parts = path.split("/");
                int customerId = Integer.parseInt(parts[2]);
                int bookingId = Integer.parseInt(parts[4]);
                ReviewService.createByBooking(customerId, bookingId, req, res);
            }

            // --- REVIEW: /customers/{customerId}/reviews
            else if (method.equals("GET") && path.matches("/customers/\\d+/reviews")) {
                int customerId = Integer.parseInt(path.split("/")[2]);
                ReviewService.indexByCustomer(customerId, res);
            }

            // --- BOOKING: /customers/{customerId}/bookings
            else if (method.equals("GET") && path.matches("/customers/\\d+/bookings")) {
                int customerId = Integer.parseInt(path.split("/")[2]);
                BookingService.indexByCustomer(customerId, res);
            } else if (method.equals("POST") && path.matches("/customers/\\d+/bookings")) {
                int customerId = Integer.parseInt(path.split("/")[2]);
                BookingService.create(customerId, req, res);
            }

            // --- CUSTOMER: /customers
            else if (method.equals("GET") && path.equals("/customers")) {
                System.out.println(" Masuk ke CustomerService.index()");
                CustomerService.index(res);
            } else if (method.equals("POST") && path.equals("/customers")) {
                CustomerService.create(req, res);
            }

            // --- CUSTOMER: /customers/{id}
            else if (method.equals("GET") && path.matches("/customers/\\d+")) {
                int customerId = Integer.parseInt(path.split("/")[2]);
                CustomerService.show(customerId, res);
            } else if (method.equals("PUT") && path.matches("/customers/\\d+")) {
                int customerId = Integer.parseInt(path.split("/")[2]);
                CustomerService.update(customerId, req, res);
            }

            // --- Not Found
            else {
                res.setBody(Server.jsonMap(Map.of("status", 405, "message", "Method Not Allowed")));
                res.send(HttpURLConnection.HTTP_BAD_METHOD);
            }

        } catch (ApiException e) {
            res.setBody(Server.jsonMap(Map.of("status", e.getStatus(), "message", e.getMessage())));
            res.send(e.getStatus());
        } catch (Exception e) {
            e.printStackTrace();
            res.setBody(Server.jsonMap(Map.of("status", 500, "message", "Internal Server Error")));
            res.send(HttpURLConnection.HTTP_INTERNAL_ERROR);
        }
    }
}
