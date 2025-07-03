package util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import util.Exception.GeneralException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Request {

    private final HttpExchange httpExchange;
    private final Headers headers;
    private String rawBody;

    private final String jsonBody;

    public Request(HttpExchange httpExchange, String jsonBody) {
        this.httpExchange = httpExchange;
        this.headers = httpExchange.getRequestHeaders();
        this.jsonBody = jsonBody;
    }

    public Request(HttpExchange httpExchange) {
        this(httpExchange, null);
    }

    public String getBody() {
        if (this.rawBody == null) {
            this.rawBody = new BufferedReader(
                    new InputStreamReader(httpExchange.getRequestBody(), StandardCharsets.UTF_8)
            ).lines().collect(Collectors.joining("\n"));
        }

        return this.rawBody;
    }

    public String getRequestMethod() {
        return httpExchange.getRequestMethod();
    }

    public String getContentType() {
        return headers.getFirst("Content-Type");
    }

    public Map<String, Object> getJSON() {
        if (!"application/json".equalsIgnoreCase(getContentType())) {
            return null;
        }

        Map<String, Object> jsonMap = new HashMap<>();
        if (jsonBody == null) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                jsonMap = objectMapper.readValue(this.getBody(), new TypeReference<>() {});
            } catch (Exception e) {
                throw new GeneralException.UtilityException(
                        "Gagal memproses JSON request",
                        e.getMessage()
                );
            }
        }

        return jsonMap;
    }
}
