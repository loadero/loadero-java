package loadero.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.Generated;

/**
 * LoaderoParticipant class is responsible to represent information retrieved/updated
 * from/to Loadero API /participants/participantId endpoint.
 *
 * Due to nature of Loadero API design, this class requires to have default values
 * in order to behave as expected.
 */
@Data
@Generated // Need this for JaCoco to ignore getters and setters
public class LoaderoParticipant implements LoaderoModel {
    private long id = 0;
    @Expose(serialize = false)
    @SerializedName("group_id")
    private long groupId = 0;
    @Expose(serialize = false)
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
