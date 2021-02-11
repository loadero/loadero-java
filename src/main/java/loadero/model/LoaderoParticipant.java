package loadero.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class LoaderoParticipant implements LoaderoModel {
    private long id = 0L;
    @SerializedName("group_id")
    private long groupId = 0L;
    @SerializedName("test_id")
    private long testId = 0;
    private String name = "";
    private int count = 0;
    @SerializedName("compute_unit")
    private String computeUnit = "";
    private String browser = "";
    private String network = "";
    private String location = "";
    @SerializedName("media_type")
    private String mediaType = "";
}
