package loadero.exceptions;

public class LoaderoNegativeTimeout extends LoaderoException {
    
    public LoaderoNegativeTimeout() {
        super("Participant timeout cannot be negative.");
    }
}
