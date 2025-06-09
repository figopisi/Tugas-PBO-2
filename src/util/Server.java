package util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.util.Map;

import util.exception.exception;
import service.VillaService.VillaService;

public class Server {

    private static class RequestHandler implements HttpHandler {
        public void handle(HttpExchange httpExchange) {
            Server.processHttpExchange(httpExchange);
        }
    }

    public Server(int port) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 128);
        server.createContext("/", new RequestHandler());
        server.start();
    }

    public static void processHttpExchange(HttpExchange httpExchange) {
        try {
            // Delegasikan penanganan request berdasarkan path
            route(httpExchange);
        } catch (Exception e) {
            e.printStackTrace();
            try {
                Response res = new Response(httpExchange);
                res.setBody(jsonMap(Map.of(
                        "status", 500,
                        "message", "Internal Server Error"
                )));
                res.send(HttpURLConnection.HTTP_INTERNAL_ERROR);
            } catch (Exception innerEx) {
                innerEx.printStackTrace();
            }
        } finally {
            httpExchange.close();
        }
    }

    public static String jsonMap(Map<String, Object> map) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(map);
    }

    public static void route(HttpExchange httpExchange) throws Exception {
        String path = httpExchange.getRequestURI().getPath();

        if (path.startsWith("/villas")) {
            handleVillaRequest(httpExchange);
        } else if (path.startsWith("/customers")) {
            handleCustomerRequest(httpExchange);
        } else {
            // FIXED: Jangan panggil processHttpExchange lagi!
            Response res = new Response(httpExchange);
            res.setBody(jsonMap(Map.of(
                    "status", 404,
                    "message", "Endpoint not found"
            )));
            res.send(HttpURLConnection.HTTP_NOT_FOUND);
        }
    }

    private static void handleVillaRequest(HttpExchange httpExchange) throws Exception {
        Request req = new Request(httpExchange);
        Response res = new Response(httpExchange);

        String method = req.getRequestMethod();
        String path = httpExchange.getRequestURI().getPath();

        System.out.printf("Received %s request to path: %s\n", method, path);

        try {
            if (method.equals("GET") && path.equals("/villas")) {
                VillaService.index(res);
            } else if (method.equals("POST") && path.equals("/villas")) {
                VillaService.create(req, res);
            } else if (method.equals("GET") && path.matches("/villas/\\d+")) {
                int id = Integer.parseInt(path.split("/")[2]);
                VillaService.show(id, res);
            } else {
                res.setBody(jsonMap(Map.of("status", 405, "message", "Method Not Allowed")));
                res.send(HttpURLConnection.HTTP_BAD_METHOD);
            }
        } catch (exception e) {
            res.setBody(Server.jsonMap(Map.of("status", e.getStatus(), "message", e.getMessage())));
            res.send(e.getStatus());
        } catch (Exception e) {
            res.setBody(Server.jsonMap(Map.of("status", 500, "message", "Internal Server Error")));
            res.send(HttpURLConnection.HTTP_INTERNAL_ERROR);
        }
    }

    private static void handleCustomerRequest(HttpExchange httpExchange) {
        // Future expansion for customers
        System.out.println("Customer route not yet implemented.");
    }
}
