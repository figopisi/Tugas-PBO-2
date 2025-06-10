package util.validator;

import util.exception.exception;

import java.util.Map;

public class VillaValidator {
    public static void validateInput(Map<String, Object> body) throws exception.BadRequestException {
        if (body == null || body.isEmpty()) {
            throw new exception.BadRequestException("Data villa tidak boleh kosong");
        }

        try {
            String name = (String) body.get("name");
            String description = (String) body.get("description");
            String address = (String) body.get("address");

            if (name == null || name.trim().isEmpty()) {
                throw new exception.BadRequestException("Nama villa wajib diisi");
            }

            if (description == null || description.trim().isEmpty()) {
                throw new exception.BadRequestException("Deskripsi villa wajib diisi");
            }

            if (address == null || address.trim().isEmpty()) {
                throw new exception.BadRequestException("Alamat villa wajib diisi");
            }

        } catch (ClassCastException e) {
            throw new exception.BadRequestException("Format input tidak valid. Pastikan semua field berupa string.");
        }
    }
}
