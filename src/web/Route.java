package web;

import com.sun.net.httpserver.HttpExchange;
import Controller.VillaController.VillaHandler;

public class Route {

    public void route(HttpExchange exchange) throws Exception {
        String path = exchange.getRequestURI().getPath();
        String method = exchange.getRequestMethod();

        if (path.startsWith("/villas")) {
            VillaHandler.handle(exchange, method, path);
        } else {
            throw new Exception("Route not found");
        }
    }
}