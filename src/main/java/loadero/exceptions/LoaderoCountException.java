package loadero.exceptions;

public class LoaderoCountException extends LoaderoException {
    
    public LoaderoCountException() {
        super("Count cannot be less than 1.");
    }
}
