package loadero.exceptions;

public class LoaderoNegativeStartInterval extends LoaderoException {
    
    public LoaderoNegativeStartInterval() {
        super("Start interval cannot be negative.");
    }
}
