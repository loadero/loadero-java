package com.loadero.types;

/**
 * Class to define browser for a {@link com.loadero.model.Participant}.
 */
public class Browser {
    private String browser;

    public Browser(String browser) {
        this.browser = browser;
    }

    public Browser(BrowserLatest browser) {
        this.browser = browser.toString();
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public void setBrowser(BrowserLatest browser) {
        this.browser = browser.toString();
    }

    @Override
    public String toString() {
        return "Browser{" +
            "browser='" + browser + '\'' +
            '}';
    }
}
