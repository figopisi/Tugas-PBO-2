package util.Exception;

public class GeneralException extends RuntimeException {

    private final String module;
    private final String errorCode;
    private final String detail;
    public GeneralException(String module, String errorCode, String message) {
        super(message);
        this.module = module;
        this.errorCode = errorCode;
        this.detail = null;
    }

    public GeneralException(String module, String errorCode, String message, String detail) {
        super(message);
        this.module = module;
        this.errorCode = errorCode;
        this.detail = detail;
    }

    public String getModule() {
        return module;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getDetail() {
        return detail;
    }

    @Override
    public String toString() {
        return "GeneralException{" +
                "module='" + module + '\'' +
                ", errorCode='" + errorCode + '\'' +
                ", message='" + getMessage() + '\'' +
                ", detail='" + detail + '\'' +
                '}';
    }

    /** Digunakan untuk error DB seperti koneksi, query gagal, dsb */
    public static class DatabaseException extends GeneralException {
        public DatabaseException(String message) {
            super("Database", "DB_ERROR", message);
        }

        public DatabaseException(String message, String detail) {
            super("Database", "DB_ERROR", message, detail);
        }
    }

    /** Digunakan untuk error di Service Layer seperti logic error, runtime error */
    public static class ServiceException extends GeneralException {
        public ServiceException(String message) {
            super("Service", "SRV_ERROR", message);
        }

        public ServiceException(String message, String detail) {
            super("Service", "SRV_ERROR", message, detail);
        }
    }

    /** Digunakan untuk error di Utility atau Helper */
    public static class UtilityException extends GeneralException {
        public UtilityException(String message) {
            super("Utility", "UTIL_ERROR", message);
        }

        public UtilityException(String message, String detail) {
            super("Utility", "UTIL_ERROR", message, detail);
        }
    }

    /** Digunakan untuk error tak terduga */
    public static class UnexpectedException extends GeneralException {
        public UnexpectedException(String message) {
            super("Unexpected", "UNEXPECTED_ERROR", message);
        }

        public UnexpectedException(String message, String detail) {
            super("Unexpected", "UNEXPECTED_ERROR", message, detail);
        }
    }
}
