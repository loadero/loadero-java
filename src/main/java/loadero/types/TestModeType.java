package loadero.types;

import com.google.gson.annotations.SerializedName;

/**
 * Enum to define test mode types provided for usage in Loadero.
 */
public enum TestModeType {
    @SerializedName("load")
    LOAD("load"),
    @SerializedName("performance")
    PERFORMANCE("performance"),
    @SerializedName("session-record")
    SESSION_RECORD("session-record");
    
    private final String label;
    
    TestModeType(String label) {
        this.label = label;
    }
    
    @Override
    public String toString() { return label; }
}
