package util.validator;

import util.exception.ValidationException;

import java.util.Map;

public class CustomerValidator {
    public static void validateInput(Map<String, Object> body) throws ValidationException {
        if (body == null || body.isEmpty()) {
            throw new ValidationException("Data customer tidak boleh kosong");
        }

        try {
            String name = (String) body.get("name");
            String email = (String) body.get("email");
            String phone = (String) body.get("phone");

            if (name == null || name.trim().isEmpty()) {
                throw new ValidationException("Nama customer wajib diisi");
            }

            if (email == null || email.trim().isEmpty()) {
                throw new ValidationException("Email customer wajib diisi");
            }

            if (phone == null || phone.trim().isEmpty()) {
                throw new ValidationException("Nomor telepon customer wajib diisi");
            }

        } catch (ClassCastException e) {
            throw new ValidationException("Format input tidak valid. Pastikan semua field sesuai tipe data.");
        }
    }
}
