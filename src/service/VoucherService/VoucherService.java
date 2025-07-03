package service.VoucherService;

import DAO.Voucher.VoucherDAO;
import model.Voucher;
import util.Exception.ApiException;
import util.Request;
import util.Response.JsonHelper;
import util.Response.ResponseHelper;

import java.net.HttpURLConnection;
import java.util.Map;

public class VoucherService {

    private static final VoucherDAO voucherDAO = new VoucherDAO();

    public static void index(ResponseHelper res) {
        var vouchers = voucherDAO.findAll();
        res.setBody(JsonHelper.success(vouchers));
        res.send(HttpURLConnection.HTTP_OK);
    }

    public static void show(int id, ResponseHelper res) {
        Voucher voucher = voucherDAO.findById(id);
        res.setBody(JsonHelper.success(voucher));
        res.send(HttpURLConnection.HTTP_OK);
    }

    public static void create(Request req, ResponseHelper res) {
        Map<String, Object> body = req.getJSON();
        validateVoucherInput(body);

        Voucher voucher = buildVoucher(0, body);

        int newId = voucherDAO.create(voucher);

        res.setBody(JsonHelper.created(newId, "Voucher berhasil dibuat."));
        res.send(HttpURLConnection.HTTP_CREATED);
    }

    public static void update(int id, Request req, ResponseHelper res) {
        Map<String, Object> body = req.getJSON();
        validateVoucherInput(body);

        Voucher voucher = buildVoucher(id, body);

        voucherDAO.update(id, voucher);

        res.setBody(JsonHelper.success("Voucher berhasil diperbarui."));
        res.send(HttpURLConnection.HTTP_OK);
    }

    public static void destroy(int id, ResponseHelper res) {
        voucherDAO.delete(id);

        res.setBody(JsonHelper.success("Voucher berhasil dihapus."));
        res.send(HttpURLConnection.HTTP_OK);
    }

    private static void validateVoucherInput(Map<String, Object> body) {
        if (!body.containsKey("code") || !body.containsKey("description") ||
                !body.containsKey("discount") || !body.containsKey("start_date") ||
                !body.containsKey("end_date")) {
            throw new ApiException.BadRequestApiException(
                    "Data voucher tidak lengkap. Wajib: code, description, discount, start_date, end_date."
            );
        }
    }

    private static Voucher buildVoucher(int id, Map<String, Object> body) {
        return new Voucher(
                id,
                (String) body.get("code"),
                (String) body.get("description"),
                (Double) body.get("discount"),
                (String) body.get("start_date"),
                (String) body.get("end_date")
        );
    }
}
