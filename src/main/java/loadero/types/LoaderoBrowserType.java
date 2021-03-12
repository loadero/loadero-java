package loadero.types;

import com.google.gson.annotations.SerializedName;

/**
 * Enum to define browser types provided for usage in Loadero.
 */
public enum LoaderoBrowserType implements Labeled {
    @SerializedName("firefoxLatest")
    FIREFOX_LATEST("firefoxLatest"),
    @SerializedName("firefox82")
    FIREFOX_82("firefox82"),
    @SerializedName("firefox83")
    FIREFOX_83("firefox83"),
    @SerializedName("firefox84")
    FIREFOX_84("firefox84"),
    @SerializedName("firefox85")
    FIREFOX_85("firefox85"),
    @SerializedName("firefox86")
    FIREFOX_86("firefox86"),
    @SerializedName("chromeLatest")
    CHROME_LATEST("chromeLatest"),
    @SerializedName("chrome85")
    CHROME_85("chrome85"),
    @SerializedName("chrome86")
    CHROME_86("chrome86"),
    @SerializedName("chrome87")
    CHROME_87("chrome87"),
    @SerializedName("chrome88")
    CHROME_88("chrome88"),
    @SerializedName("chrome89")
    CHROME_89("chrome89");
    
    private final String label;
    
    LoaderoBrowserType(String label) {
        this.label = label;
    }
    
    @Override
    public String label() {
        return label;
    }
}
