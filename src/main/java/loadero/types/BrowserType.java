package loadero.types;

import lombok.Getter;

/**
 * Class to define browser for a Participant.
 */
@Getter
public class BrowserType {
    private String browser;
    
    public BrowserType(String browser) {
        this.browser = browser;
    }
    public BrowserType(BrowserTypeLatest browser) {this.browser = browser.toString();}
    
    public void setBrowser(String browser) {
        this.browser = browser;
    }
    
    public void setBrowser(BrowserTypeLatest browser) {
        this.browser = browser.toString();
    }
}
