package util.validator;

import util.exception.exception;

import java.util.Map;

public class VillaValidator {
    public static void validateInput(Map<String, Object> body) throws exception.BadRequestException {
        if (body == null) {
            throw new exception.BadRequestException("Data villa tidak boleh kosong");
        }

        Object name = body.get("name");
        Object description = body.get("description");
        Object address = body.get("address");

        if (!(name instanceof String) || ((String) name).trim().isEmpty()) {
            throw new exception.BadRequestException("Nama villa wajib diisi");
        }

        if (!(description instanceof String) || ((String) description).trim().isEmpty()) {
            throw new exception.BadRequestException("Deskripsi villa wajib diisi");
        }

        if (!(address instanceof String) || ((String) address).trim().isEmpty()) {
            throw new exception.BadRequestException("Alamat villa wajib diisi");
        }
    }
}
