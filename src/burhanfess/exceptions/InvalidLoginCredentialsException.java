package burhanfess.exceptions;

public class InvalidLoginCredentialsException extends Exception{
    public InvalidLoginCredentialsException() {
        super();
    }

    public InvalidLoginCredentialsException(String message) {
        super(message);
    }

    public InvalidLoginCredentialsException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidLoginCredentialsException(Throwable cause) {
        super(cause);
    }
}
