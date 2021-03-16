package loadero.exceptions;

/**
 * Custom exception class. Should be used to handle errors from Loadero.
 */
public class LoaderoException extends RuntimeException {

    public LoaderoException() {}
    
    public LoaderoException(String message) {
        super(message);
    }

    public LoaderoException(String message, Throwable cause) {
        super(message, cause);
    }

}
