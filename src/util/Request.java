package util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;

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

    public String getBody() {
        if (this.rawBody == null) {
            this.rawBody = new BufferedReader(
                    new InputStreamReader(httpExchange.getRequestBody(), StandardCharsets.UTF_8)
            )
                    .lines()
                    .collect(Collectors.joining("\n"));
        }

        return this.rawBody;
    }

    public String getRequestMethod() {
        return httpExchange.getRequestMethod();
    }

    public String getContentType() {
        return headers.getFirst("Content-Type");
    }

    public Map<String, Object> getJSON() throws JsonProcessingException {
        if (!getContentType().equalsIgnoreCase("application/json")) {
            return null;
        }

        Map<String, Object> jsonMap = new HashMap<>();
        if (jsonBody == null) {
            ObjectMapper objectMapper = new ObjectMapper();
            jsonMap = objectMapper.readValue(this.getBody(), new TypeReference<>(){});
        }

        return jsonMap;
    }


}