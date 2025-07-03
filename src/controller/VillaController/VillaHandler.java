package controller.VillaController;

import com.sun.net.httpserver.HttpExchange;
import util.Request;
import util.Response.ResponseHelper;
import web.Server;
import service.VillaService.VillaService;
import util.Exception.ApiException;

import java.net.HttpURLConnection;
import java.util.Map;

public class VillaHandler {

    public static void handle(HttpExchange exchange, String method, String path) throws Exception {
        path = path.replaceAll("/$", "");
        Request req = new Request(exchange);
        ResponseHelper res = new ResponseHelper(exchange);

        try {
            if (method.equals("GET") && path.equals("/villas")) {
                VillaService.index(res);
            } else if (method.equals("POST") && path.equals("/villas")) {
                VillaService.create(req, res);
            } else if (method.equals("GET") && path.matches("/villas/\\d+")) {
                int id = Integer.parseInt(path.split("/")[2]);
                VillaService.show(id, res);
            } else if (method.equals("PUT") && path.matches("/villas/\\d+")) {
                int id = Integer.parseInt(path.split("/")[2]);
                VillaService.update(id, req, res);
            } else if (method.equals("DELETE") && path.matches("/villas/\\d+")) {
                int id = Integer.parseInt(path.split("/")[2]);
                VillaService.destroy(id, res);
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
