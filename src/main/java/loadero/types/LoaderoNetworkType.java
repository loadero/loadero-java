package loadero.types;

import com.google.gson.annotations.SerializedName;

/**
 * Enum to define network condition types provided for usage in Loadero.
 */
public enum LoaderoNetworkType {
    @SerializedName("default")
    DEFAULT("default"),
    @SerializedName("20packet")
    PACKET_20("20packet"),
    @SerializedName("10packet")
    PACKET_10("10packet"),
    @SerializedName("50packet")
    PACKET_50("50packet"),
    @SerializedName("5packet")
    PACKET_5("5packet"),
    @SerializedName("100packet")
    PACKET_100("100packet"),
    @SerializedName("jitter")
    JITTER("jitter"),
    @SerializedName("edge")
    EDGE("edge"),
    @SerializedName("satellite")
    SATELLITE("satellite"),
    @SerializedName("asymmetric")
    ASYMMETRIC("asymmetric"),
    @SerializedName("latency")
    LATENCY("latency"),
    @SerializedName("3g")
    CONNECTION_3G("3g"),
    @SerializedName("4g")
    CONNECTION_4G("4g"),
    @SerializedName("hsdpa")
    HSDPA("hsdpa"),
    @SerializedName("gprs")
    GPRS("gprs");
    
    private final String label;
    
    LoaderoNetworkType(String label) {
        this.label = label;
    }
    
    @Override
    public String toString() {
        return label;
    }
}
