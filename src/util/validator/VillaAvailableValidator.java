package util.validator;

import util.Exception.ValidationException;

public class VillaAvailableValidator {
    public static void validate(String ciDate, String coDate) throws ValidationException {
        if (ciDate == null || ciDate.trim().isEmpty()) {
            throw new ValidationException("Tanggal check-in tidak boleh kosong");
        }

        if (coDate == null || coDate.trim().isEmpty()) {
            throw new ValidationException("Tanggal check-out tidak boleh kosong");
        }

        if (!ciDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
            throw new ValidationException("Format tanggal check-in tidak valid (YYYY-MM-DD)");
        }

        if (!coDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
            throw new ValidationException("Format tanggal check-out tidak valid (YYYY-MM-DD)");
        }
    }
}
