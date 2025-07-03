package util.validator;

import util.Exception.ValidationException;

import java.util.Map;

public class BookingValidator {
    public static void validateInput(Map<String, Object> body) {
        if (body == null || body.isEmpty()) {
            throw new ValidationException("Data booking tidak boleh kosong");
        }
        if (!body.containsKey("room_type") || body.get("room_type") == null) {
            throw new ValidationException("Tipe kamar wajib diisi");
        }
        if (!body.containsKey("checkin_date") || body.get("checkin_date") == null) {
            throw new ValidationException("Tanggal check-in wajib diisi");
        }
        if (!body.containsKey("checkout_date") || body.get("checkout_date") == null) {
            throw new ValidationException("Tanggal check-out wajib diisi");
        }
        if (!body.containsKey("price") || !(body.get("price") instanceof Number)) {
            throw new ValidationException("Harga wajib diisi dan harus berupa angka");
        }
    }
}
