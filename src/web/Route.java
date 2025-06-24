package web;

import com.sun.net.httpserver.HttpExchange;
import Controller.VillaController.VillaHandler;
import Controller.VillaController.VillaRoomHandler;
import Controller.VillaController.VillaBookingHandler;
import Controller.VillaController.VillaReviewHandler;
import Controller.CustomerController.CustomerHandler;
import Controller.VoucherController.VoucherHandler;

public class Route {

    public void route(HttpExchange exchange) throws Exception {
        String path = exchange.getRequestURI().getPath();
        String method = exchange.getRequestMethod();

        if (path.startsWith("/villas") && path.contains("/bookings")) {
            VillaBookingHandler.handle(exchange, method, path);
        } else if (path.startsWith("/villas") && path.contains("/reviews")) {
            VillaReviewHandler.handle(exchange, method, path);
        } else if (path.startsWith("/rooms")) {
            VillaRoomHandler.handle(exchange, method, path);
        } else if (path.startsWith("/villas")) {
            VillaHandler.handle(exchange, method, path);
        } else if (path.startsWith("/customers")) {
            CustomerHandler.handle(exchange, method, path);
        } else if (path.startsWith("/vouchers")) {
            VoucherHandler.handle(exchange, method, path);
        } else {
            throw new Exception("Route not found");
        }
    }
}
