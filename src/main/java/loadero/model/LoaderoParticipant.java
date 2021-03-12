package loadero.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import loadero.types.*;
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
    private int id = 0;
    @Expose(serialize = false)
    @SerializedName("group_id")
    private int groupId = 0;
    @Expose(serialize = false)
    @SerializedName("test_id")
    private int testId = 0;
    private String name = "";
    private int count = 0;
    @SerializedName("compute_unit")
    private LoaderoComputeUnitsType computeUnit;
    private LoaderoBrowserType browser;
    private LoaderoNetworkType network;
    private LoaderoLocationType location;
    @SerializedName("media_type")
    private LoaderoMediaType mediaType;
}
