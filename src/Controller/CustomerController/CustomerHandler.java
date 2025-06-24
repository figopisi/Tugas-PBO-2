package Controller.CustomerController;

import com.sun.net.httpserver.HttpExchange;
import util.Request;
import util.Response;
import web.Server;
import service.CustomerService.CustomerService;
import service.CustomerService.CustomerBookingService;
import service.CustomerService.CustomerReviewService;
import util.Exception.ApiException;

import java.net.HttpURLConnection;
import java.util.Map;

public class CustomerHandler {

    public static void handle(HttpExchange exchange, String method, String path) throws Exception {
        path = path.replaceAll("/$", "");
        Request req = new Request(exchange);
        Response res = new Response(exchange);

        try {
            if (method.equals("GET") && path.equals("/customers")) {
                CustomerService.index(res);
            } else if (method.equals("POST") && path.equals("/customers")) {
                CustomerService.create(req, res);
            } else if (method.equals("GET") && path.matches("/customers/\\d+")) {
                int id = Integer.parseInt(path.split("/")[2]);
                CustomerService.show(id, res);
            } else if (method.equals("PUT") && path.matches("/customers/\\d+")) {
                int id = Integer.parseInt(path.split("/")[2]);
                CustomerService.update(id, req, res);
            }

            // Booking-related endpoints
            else if (method.equals("GET") && path.matches("/customers/\\d+/bookings")) {
                int id = Integer.parseInt(path.split("/")[2]);
                CustomerBookingService.indexByCustomer(id, res);
            } else if (method.equals("POST") && path.matches("/customers/\\d+/bookings")) {
                int id = Integer.parseInt(path.split("/")[2]);
                CustomerBookingService.create(id, req, res);
            }

            // Review-related endpoints
            else if (method.equals("GET") && path.matches("/customers/\\d+/reviews")) {
                int id = Integer.parseInt(path.split("/")[2]);
                CustomerReviewService.indexByCustomer(id, res);
            } else if (method.equals("POST") && path.matches("/customers/\\d+/bookings/\\d+/reviews")) {
                String[] parts = path.split("/");
                int customerId = Integer.parseInt(parts[2]);
                int bookingId = Integer.parseInt(parts[4]);
                CustomerReviewService.createByBooking(customerId, bookingId, req, res);
            }

            // Method or route not allowed
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
