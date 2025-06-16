package util.validator;

import util.Exception.ValidationException;

import java.util.Map;

public class VillaValidator {
    public static void validateInput(Map<String, Object> body) throws ValidationException {
        if (body == null || body.isEmpty()) {
            throw new ValidationException("Data villa tidak boleh kosong");
        }

        try {
            String name = (String) body.get("name");
            String description = (String) body.get("description");
            String address = (String) body.get("address");

            if (name == null || name.trim().isEmpty()) {
                throw new ValidationException("Nama villa wajib diisi");
            }

            if (description == null || description.trim().isEmpty()) {
                throw new ValidationException("Deskripsi villa wajib diisi");
            }

            if (address == null || address.trim().isEmpty()) {
                throw new ValidationException("Alamat villa wajib diisi");
            }

        } catch (ClassCastException e) {
            throw new ValidationException("Format input tidak valid. Pastikan semua field berupa string.");
        }
    }
}
