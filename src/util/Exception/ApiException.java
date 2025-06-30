package util.Exception;

public class ApiException extends Exception {
    private final int status;
    private final String error; // error type, opsional

    public ApiException(int status, String message) {
        super(message);
        this.status = status;
        this.error = null;
    }

    public ApiException(int status, String error, String message) {
        super(message);
        this.status = status;
        this.error = error;
    }

    public int getStatus() {
        return this.status;
    }

    public String getError() {
        return this.error;
    }

    // 400 Bad Request
    public static class BadRequestApiException extends ApiException {
        public BadRequestApiException(String message) {
            super(400, "BadRequest", message);
        }
    }

    // 401 Unauthorized
    public static class UnauthorizedApiException extends ApiException {
        public UnauthorizedApiException(String message) {
            super(401, "Unauthorized", message);
        }
    }

    // 403 Forbidden
    public static class ForbiddenApiException extends ApiException {
        public ForbiddenApiException(String message) {
            super(403, "Forbidden", message);
        }
    }

    // 404 Not Found
    public static class NotFoundApiException extends ApiException {
        public NotFoundApiException(String message) {
            super(404, "NotFound", message);
        }
    }

    // 409 Conflict
    public static class ConflictApiException extends ApiException {
        public ConflictApiException(String message) {
            super(409, "Conflict", message);
        }
    }

    // 500 Internal Server Error
    public static class InternalServerErrorApiException extends ApiException {
        public InternalServerErrorApiException(String message) {
            super(500, "InternalServerError", message);
        }
    }
}
