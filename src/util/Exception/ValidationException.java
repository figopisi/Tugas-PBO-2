package util.Exception;

public class ValidationException extends ApiException {
    public ValidationException(String message) {
        super(400, message);
    }
}
