package util.Response;

import java.util.HashMap;
import java.util.Map;

public class JsonHelper {

    public static Map<String, Object> success(Object data) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", 200);
        response.put("data", data);
        return response;
    }

    public static Map<String, Object> success(String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", 200);
        response.put("message", message);
        return response;
    }

    public static Map<String, Object> created(int id, String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", 201);
        response.put("id", id);
        response.put("message", message);
        return response;
    }

    // 🔑 Method error baru: support `type` + `message`
    public static Map<String, Object> error(String type, String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", 400); // Default 400, status final diatur di ResponseHelper.send()
        response.put("error", type);
        response.put("message", message);
        return response;
    }
}
