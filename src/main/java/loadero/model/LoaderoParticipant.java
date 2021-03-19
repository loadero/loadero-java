package loadero.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import loadero.exceptions.LoaderoClientInternalException;
import loadero.types.*;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

/**
 * LoaderoParticipant class is responsible to represent information retrieved/updated
 * from/to Loadero API /participants/participantId endpoint.
 *
 * Due to nature of Loadero API design, this class requires to have default values
 * in order to behave as expected.
 */
@Data
@NoArgsConstructor
@Generated // Need this for JaCoco to ignore getters and setters
public final class LoaderoParticipant implements LoaderoModel {
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
    
    /**
     * Constructor for Loadero participants.
     * @param name        Participant name.
     * @param count       How many participants you need. At least 1 is required.
     * @param computeUnit What compute units should to be used.
     * @param browser     What browser should to be used.
     * @param network     What kind of network should be used.
     * @param location    Geo location from where tests should be run.
     * @param mediaType   Type of media, that should be used.
     * @throws LoaderoClientInternalException if name is blank or null.
     * @throws LoaderoClientInternalException if count is less than 1.
     */
    public LoaderoParticipant(String name, int count, LoaderoComputeUnitsType computeUnit,
                              LoaderoBrowserType browser, LoaderoNetworkType network,
                              LoaderoLocationType location, LoaderoMediaType mediaType) {
        if (name.isBlank()) throw new LoaderoClientInternalException("Name should not be blank.");
        if (count < 1)      throw new LoaderoClientInternalException("Amount of participants should be at least 1.");
        
        this.name = name;
        this.count = count;
        this.computeUnit = computeUnit;
        this.browser = browser;
        this.network = network;
        this.location = location;
        this.mediaType = mediaType;
    }
}
