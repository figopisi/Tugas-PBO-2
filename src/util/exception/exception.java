package util.exception;

public class exception extends Exception {
    private final int status;

    public exception(int status, String message) {
        super(message);
        this.status = status;
    }

    public int getStatus() {
        return this.status;
    }

    public static class BadRequestException extends exception {
        public BadRequestException(String message) {
            super(400, message);
        }
    }

    public static class NotFoundException extends exception {
        public NotFoundException(String message) {
            super(404, message);
        }
    }
}
