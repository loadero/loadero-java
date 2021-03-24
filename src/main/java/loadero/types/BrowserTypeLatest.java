package loadero.types;

import com.google.gson.annotations.SerializedName;

/**
 * Enum to define browser types provided for usage in Loadero.
 */
public enum BrowserTypeLatest {
    @SerializedName("firefoxLatest")
    FIREFOX_LATEST("firefoxLatest"),
    @SerializedName("chromeLatest")
    CHROME_LATEST("chromeLatest");
    
    private final String label;
    
    BrowserTypeLatest(String label) {
        this.label = label;
    }
    
    @Override
    public String toString() {
        return label;
    }
}
