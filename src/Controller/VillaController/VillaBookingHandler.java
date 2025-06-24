package Controller.VillaController;

import com.sun.net.httpserver.HttpExchange;
import util.Response;
import service.VillaService.VillaBookingService;
import util.Exception.ApiException;
import web.Server;

import java.net.HttpURLConnection;
import java.util.Map;

public class VillaBookingHandler {

    public static void handle(HttpExchange exchange, String method, String path) throws Exception {
        path = path.replaceAll("/$", "");
        Response res = new Response(exchange);

        try {
            if (method.equals("GET") && path.matches("/villas/\\d+/bookings")) {
                int villaId = Integer.parseInt(path.split("/")[2]);
                VillaBookingService.indexByVilla(villaId, res);
            } else {
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
