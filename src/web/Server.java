package web;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import util.Response.ResponseHelper;

import java.net.InetSocketAddress;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Server {

    private final Route router;

        public Server(int port, Route router) throws Exception {
        this.router = router;
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/", new RequestHandler());
        server.start();
        System.out.println("Server started on port " + port);
    }

    private class RequestHandler implements HttpHandler {
        public void handle(HttpExchange exchange) {
            try {
                router.route(exchange);
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    ResponseHelper res = new ResponseHelper(exchange);
                    res.setBody(jsonMap(Map.of(
                            "status", 500,
                            "message", "Internal Server Error"
                    )));
                    res.send(500);
                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    exchange.close();
                }
            }
        }
    }

    public static String jsonMap(Map<String, Object> map) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(map);
    }
}