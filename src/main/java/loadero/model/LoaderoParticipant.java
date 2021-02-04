package loadero.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class LoaderoParticipant {
    private String name;
    @SerializedName("group_id")
    private long groupId;
    private int count;
    @SerializedName("compute_unit")
    private String computeUnit;
    private String browser;
    private String network;
    private String location;
    @SerializedName("media_type")
    private String mediaType;
}
