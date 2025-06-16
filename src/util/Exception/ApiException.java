package util.Exception;

public class ApiException extends Exception {
    private final int status;

    public ApiException(int status, String message) {
        super(message);
        this.status = status;
    }

    public int getStatus() {
        return this.status;
    }

    public static class BadRequestApiException extends ApiException {
        public BadRequestApiException(String message) {
            super(400, message);
        }
    }

    public static class NotFoundApiException extends ApiException {
        public NotFoundApiException(String message) {
            super(404, message);
        }
    }
}
