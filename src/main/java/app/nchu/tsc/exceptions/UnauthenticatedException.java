package app.nchu.tsc.exceptions;

public class UnauthenticatedException extends RuntimeException {

    public UnauthenticatedException() {
        super("Please login first!");
    }

    public UnauthenticatedException(String message) {
        super(message);
    }

    public UnauthenticatedException(Throwable cause) {
        super("Please login first!", cause);
    }

    public UnauthenticatedException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
