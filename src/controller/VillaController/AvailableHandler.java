package controller.VillaController;

import service.VillaService.AvailableService;
import util.validator.AvailableValidator;
import util.Exception.ApiException;
import util.Response.ResponseHelper;
import model.RoomType;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;

public class AvailableHandler {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void getAvailableRooms(Map<String, String> queryParams, int villaId, ResponseHelper res) {
        try {
            String ciDate = queryParams.get("ci_date");
            String coDate = queryParams.get("co_date");
            AvailableValidator.validate(ciDate, coDate);

            List<RoomType> rooms = AvailableService.findAvailable(villaId, ciDate, coDate);

            String jsonResponse = objectMapper.writeValueAsString(rooms);

            res.setBody(jsonResponse);
            res.send(200);
        } catch (ApiException e) {
            sendError(res, e.getStatus(), e.getMessage());
        } catch (Exception e) {
            sendError(res, 500, "Terjadi kesalahan server: " + e.getMessage());
        }
    }

    private static void sendError(ResponseHelper res, int status, String message) {
        try {
            String jsonError = objectMapper.writeValueAsString(Map.of("error", message));
            res.setBody(jsonError);
            res.send(status);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
