package loadero.types;

import com.google.gson.annotations.SerializedName;

/**
 * Enum to define test mode types provided for usage in Loadero.
 */
public enum LoaderoTestModeType implements Labeled {
    @SerializedName("load")
    LOAD("load"),
    @SerializedName("performance")
    PERFORMANCE("performance"),
    @SerializedName("session-record")
    SESSION_RECORD("session-record");
    
    private final String label;
    
    LoaderoTestModeType(String label) {
        this.label = label;
    }
    
    @Override
    public String label() { return label; }
}