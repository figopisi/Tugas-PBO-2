package web;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import util.Exception.ApiException;
import util.Response.JsonHelper;
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
            ResponseHelper res = new ResponseHelper(exchange);
            try {
                router.route(exchange);

            } catch (ApiException apiEx) {
                apiEx.printStackTrace();
                try {
                    Map<String, Object> errorBody = JsonHelper.error(
                            apiEx.getError() != null ? apiEx.getError() : "ApiException",
                            apiEx.getMessage()
                    );
                    res.setBody(jsonMap(errorBody));
                    res.send(apiEx.getStatus());
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                try {
                    res.setBody(jsonMap(JsonHelper.error("InternalServerError", "Terjadi kesalahan pada server")));
                    res.send(500);
                } catch (Exception inner) {
                    inner.printStackTrace();
                }

            } finally {
                exchange.close();
            }
        }
    }

    public static String jsonMap(Map<String, Object> map) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(map);
    }
}
