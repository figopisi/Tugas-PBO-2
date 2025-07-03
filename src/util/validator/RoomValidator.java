package util.validator;

import util.Exception.ValidationException;

import java.util.Map;

public class RoomValidator {
    public static void validateInput(Map<String, Object> body) throws ValidationException {
        if (body == null || body.isEmpty()) {
            throw new ValidationException("Data kamar tidak boleh kosong");
        }

        try {
            String name = (String) body.get("name");
            Object priceObj = body.get("price");

            if (name == null || name.trim().isEmpty()) {
                throw new ValidationException("Tipe kamar wajib diisi");
            }

            if (priceObj == null) {
                throw new ValidationException("Harga wajib diisi");
            }

            if (!(priceObj instanceof Number)) {
                throw new ValidationException("Harga harus berupa angka");
            }

        } catch (ClassCastException e) {
            throw new ValidationException("Format input tidak valid. Pastikan semua field memiliki format yang benar.");
        }
    }
}
