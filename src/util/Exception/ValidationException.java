package util.Exception;

public class ValidationException extends RuntimeException {
    private final int status;

    public ValidationException(String message) {
        super(message);
        this.status = 400; // Bad Request
    }

    public int getStatus() {
        return status;
    }
}
