package burhanfess.exceptions;

public class InvallidPasswordException extends Exception {
    public InvallidPasswordException() {
        super();
    }

    public InvallidPasswordException(String message) {
        super(message);
    }

    public InvallidPasswordException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvallidPasswordException(Throwable cause) {
        super(cause);
    }
}
