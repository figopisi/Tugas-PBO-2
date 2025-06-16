package util.validator;

import util.Exception.ValidationException;

import java.util.Map;

public class VillaRoomValidator {
    public static void validateInput(Map<String, Object> body) throws ValidationException {
        if (body == null || body.isEmpty()) {
            throw new ValidationException("Data kamar tidak boleh kosong");
        }

        try {
            String roomType = (String) body.get("room_type");
            String facilities = (String) body.get("facilities");
            Object priceObj = body.get("price");

            if (roomType == null || roomType.trim().isEmpty()) {
                throw new ValidationException("Tipe kamar wajib diisi");
            }

            if (facilities == null || facilities.trim().isEmpty()) {
                throw new ValidationException("Fasilitas wajib diisi");
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
