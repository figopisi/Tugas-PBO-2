package Controller.VoucherController;

import com.sun.net.httpserver.HttpExchange;
import util.Request;
import util.Response;
import web.Server;
import service.VoucherService.VoucherService;
import util.Exception.ApiException;

import java.net.HttpURLConnection;
import java.util.Map;

public class VoucherHandler {

    public static void handle(HttpExchange exchange, String method, String path) throws Exception {
        Request req = new Request(exchange);
        Response res = new Response(exchange);

        try {
            if (method.equals("GET") && path.equals("/vouchers")) {
                VoucherService.index(res);
            } else if (method.equals("POST") && path.equals("/vouchers")) {
                VoucherService.create(req, res);
            } else if (method.equals("GET") && path.matches("/vouchers/\\d+")) {
                int id = Integer.parseInt(path.split("/")[2]);
                VoucherService.show(id, res);
            } else if (method.equals("PUT") && path.matches("/vouchers/\\d+")) {
                int id = Integer.parseInt(path.split("/")[2]);
                VoucherService.update(id, req, res);
            } else if (method.equals("DELETE") && path.matches("/vouchers/\\d+")) {
                int id = Integer.parseInt(path.split("/")[2]);
                VoucherService.destroy(id, res);
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