package Controller.VillaController;

import com.sun.net.httpserver.HttpExchange;
import util.Request;
import util.Response;
import util.Exception.ApiException;
import service.VillaService.VillaRoomService;
import web.Server;

import java.net.HttpURLConnection;
import java.util.Map;

public class VillaRoomHandler {

    public static void handle(HttpExchange exchange, String method, String path) throws Exception {
        path = path.replaceAll("/$", "");
        Request req = new Request(exchange);
        Response res = new Response(exchange);

        try {
            if (method.equals("GET") && path.equals("/rooms")) {
                VillaRoomService.index(res);
            } else if (method.equals("GET") && path.matches("/rooms/\\d+")) {
                int id = Integer.parseInt(path.split("/")[2]);
                VillaRoomService.show(id, res);
            } else if (method.equals("POST") && path.equals("/rooms")) {
                VillaRoomService.create(req, res);
            } else if (method.equals("PUT") && path.matches("/rooms/\\d+")) {
                int id = Integer.parseInt(path.split("/")[2]);
                VillaRoomService.update(id, req, res);
            } else if (method.equals("DELETE") && path.matches("/rooms/\\d+")) {
                int id = Integer.parseInt(path.split("/")[2]);
                VillaRoomService.destroy(id, res);
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
