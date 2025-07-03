package util.Response;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import util.Exception.GeneralException;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class ResponseHelper {

    private static final ObjectMapper mapper = new ObjectMapper();
    private final HttpExchange httpExchange;
    private final Headers headers;
    private final StringBuilder stringBuilder;

    public ResponseHelper(HttpExchange httpExchange) {
        this.httpExchange = httpExchange;
        this.headers = httpExchange.getResponseHeaders();
        this.stringBuilder = new StringBuilder();
    }

    public void setBody(String string) {
        stringBuilder.setLength(0);
        stringBuilder.append(string);
    }

    public void setBody(Object object) {
        try {
            stringBuilder.setLength(0);
            stringBuilder.append(mapper.writeValueAsString(object));
        } catch (Exception e) {
            throw new GeneralException.UtilityException(
                    "Gagal mengubah object menjadi JSON",
                    e.getMessage()
            );
        }
    }

    public void send(int statusCode) {
        try {
            headers.add("Content-Type", "application/json; charset=utf-8");
            byte[] responseBytes = stringBuilder.toString().getBytes(StandardCharsets.UTF_8);

            httpExchange.sendResponseHeaders(statusCode, responseBytes.length);
            OutputStream outputStream = httpExchange.getResponseBody();
            outputStream.write(responseBytes);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            throw new GeneralException.UtilityException(
                    "Gagal mengirim response",
                    e.getMessage()
            );
        } finally {
            httpExchange.close();
        }
    }
}
