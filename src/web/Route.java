package web;

import com.sun.net.httpserver.HttpExchange;
import controller.VillaController.VillaHandler;
import controller.VillaController.RoomHandler;
import controller.VillaController.BookingHandler;
import controller.VillaController.ReviewHandler;
import controller.CustomerController.CustomerHandler;
import controller.VoucherController.VoucherHandler;

public class Route {

    public void route(HttpExchange exchange) throws Exception {
        String path = exchange.getRequestURI().getPath();
        String method = exchange.getRequestMethod();

        if (path.startsWith("/villas") && path.contains("/bookings")) {
            BookingHandler.handle(exchange, method, path);
        } else if (path.startsWith("/villas") && path.contains("/reviews")) {
            ReviewHandler.handle(exchange, method, path);
        } else if (path.startsWith("/rooms")) {
            RoomHandler.handle(exchange, method, path);
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
