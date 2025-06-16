package util.validator;

import util.exception.ValidationException;

import java.util.Map;

public class VoucherValidator {
    public static void validateInput(Map<String, Object> body) throws ValidationException {
        if (body == null || body.isEmpty()) {
            throw new ValidationException("Data voucher tidak boleh kosong");
        }

        try {
            String code = (String) body.get("code");
            String description = (String) body.get("description");
            Object discountObj = body.get("discount");
            String startDate = (String) body.get("start_date");
            String endDate = (String) body.get("end_date");

            if (code == null || code.trim().isEmpty()) {
                throw new ValidationException("Kode voucher wajib diisi");
            }

            if (description == null || description.trim().isEmpty()) {
                throw new ValidationException("Deskripsi voucher wajib diisi");
            }

            if (discountObj == null) {
                throw new ValidationException("Diskon voucher wajib diisi");
            }

            if (!(discountObj instanceof Number)) {
                throw new ValidationException("Diskon harus berupa angka");
            }

            double discount = ((Number) discountObj).doubleValue();
            if (discount <= 0 || discount > 100) {
                throw new ValidationException("Diskon harus lebih dari 0 dan maksimal 100");
            }

            if (startDate == null || startDate.trim().isEmpty()) {
                throw new ValidationException("Tanggal mulai voucher wajib diisi");
            }

            if (endDate == null || endDate.trim().isEmpty()) {
                throw new ValidationException("Tanggal akhir voucher wajib diisi");
            }

        } catch (ClassCastException e) {
            throw new ValidationException("Format input tidak valid. Pastikan semua field sesuai tipe data.");
        }
    }
}
