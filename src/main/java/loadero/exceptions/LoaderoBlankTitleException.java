package loadero.exceptions;

public class LoaderoBlankTitleException extends LoaderoException {
    
    public LoaderoBlankTitleException() {
        super("Name is blank or null.");
    }
}
