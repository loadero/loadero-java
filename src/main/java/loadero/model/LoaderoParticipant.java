package loadero.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class LoaderoParticipant implements LoaderoModel {
    private long id;
    private String name;
    private int count;
    @SerializedName("compute_unit")
    private String computeUnit;
    private String browser;
    private String network;
    private String location;
    @SerializedName("media_type")
    private String mediaType;
}
