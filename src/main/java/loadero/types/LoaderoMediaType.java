package loadero.types;

import com.google.gson.annotations.SerializedName;

/**
 * Enum to define media types provided for usage in Loadero.
 */
public enum LoaderoMediaType {
    @SerializedName("default")
    DEFAULT("default"),
    @SerializedName("240pAV")
    AV_240P("240pAV"),
    @SerializedName("360pAV")
    AV_360P("360pAV"),
    @SerializedName("480pAV")
    AV_480P("480pAV"),
    @SerializedName("720pAV")
    AV_720P("720pAV"),
    @SerializedName("1080pAV")
    AV_1080P("1080pAV"),
    @SerializedName("1080p-20db")
    DB_1080P_20("1080p-20db"),
    @SerializedName("1080p-30db")
    DB_1080P_30("1080p-30db"),
    @SerializedName("1080p-50db")
    DB_1080P_50("1080p-50db"),
    @SerializedName("720p-marked")
    MARKED_720P("720p-marked");
    
    private final String label;
    
    LoaderoMediaType(String label) {
        this.label = label;
    }
    
    @Override
    public String toString() {
        return label;
    }
}
