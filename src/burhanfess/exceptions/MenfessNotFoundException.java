package burhanfess.exceptions;

public class MenfessNotFoundException extends Exception {
    public MenfessNotFoundException() {
        super();
    }
    public MenfessNotFoundException(String message) {
        super(message);
    }
    public MenfessNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    public MenfessNotFoundException(Throwable cause) {
        super(cause);
    }

}
