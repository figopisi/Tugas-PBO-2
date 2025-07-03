package Controller.VillaController;

import com.sun.net.httpserver.HttpExchange;
import util.Request;
import util.Response.ResponseHelper;
import util.Exception.ApiException;
import service.VillaService.RoomService;
import web.Server;

import java.net.HttpURLConnection;
import java.util.Map;

public class RoomHandler {

    public static void handle(HttpExchange exchange, String method, String path) throws Exception {
        path = path.replaceAll("/$", "");
        Request req = new Request(exchange);
        ResponseHelper res = new ResponseHelper(exchange);

        try {
            if (method.equals("GET") && path.equals("/rooms")) {
                RoomService.index(res);
            } else if (method.equals("GET") && path.matches("/rooms/\\d+")) {
                int id = Integer.parseInt(path.split("/")[2]);
                RoomService.show(id, res);
            } else if (method.equals("POST") && path.equals("/rooms")) {
                RoomService.create(req, res);
            } else if (method.equals("PUT") && path.matches("/rooms/\\d+")) {
                int id = Integer.parseInt(path.split("/")[2]);
                RoomService.update(id, req, res);
            } else if (method.equals("DELETE") && path.matches("/rooms/\\d+")) {
                int id = Integer.parseInt(path.split("/")[2]);
                RoomService.destroy(id, res);
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
