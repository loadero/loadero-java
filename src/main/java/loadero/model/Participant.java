package loadero.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import loadero.exceptions.ClientInternalException;
import loadero.types.*;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

/**
 * Participant class is responsible to represent information retrieved/updated
 * from/to Loadero API /participants/participantId endpoint.
 *
 * Due to nature of Loadero API design, this class requires to have default values
 * in order to behave as expected.
 */
@Data
@NoArgsConstructor
@Generated // Need this for JaCoco to ignore getters and setters
public final class Participant implements LoaderoModel {
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
    private ComputeUnitsType computeUnit;
    private BrowserType browser;
    private NetworkType network;
    private LocationType location;
    @SerializedName("media_type")
    private MediaType mediaType;
    
    /**
     * Constructor for Loadero participants.
     * @param name        Participant name.
     * @param count       How many participants you need. At least 1 is required.
     * @param computeUnit What compute units should to be used.
     * @param browser     What browser should to be used.
     * @param network     What kind of network should be used.
     * @param location    Geo location from where tests should be run.
     * @param mediaType   Type of media, that should be used.
     * @throws ClientInternalException if name is blank or null.
     * @throws ClientInternalException if count is less than 1.
     */
    public Participant(String name, int count, ComputeUnitsType computeUnit,
                       BrowserType browser, NetworkType network,
                       LocationType location, MediaType mediaType) {
        if (name.isBlank()) throw new ClientInternalException("Name should not be blank.");
        if (count < 1)      throw new ClientInternalException("Amount of participants should be at least 1.");
        
        this.name = name;
        this.count = count;
        this.computeUnit = computeUnit;
        this.browser = browser;
        this.network = network;
        this.location = location;
        this.mediaType = mediaType;
    }
    
    /**
     * Constructor for Loadero participants.
     * Used for custom serialization/deserialization purposes.
     * @param id          Participant ID.
     * @param name        Participant name.
     * @param count       How many participants you need. At least 1 is required.
     * @param computeUnit What compute units should to be used.
     * @param browser     What browser should to be used.
     * @param network     What kind of network should be used.
     * @param location    Geo location from where tests should be run.
     * @param mediaType   Type of media, that should be used.
     * @throws ClientInternalException if name is blank or null.
     * @throws ClientInternalException if count is less than 1.
     */
    public Participant(int id, String name, int count, ComputeUnitsType computeUnit,
                              BrowserType browser, NetworkType network,
                              LocationType location, MediaType mediaType) {
        if (name.isBlank()) throw new ClientInternalException("Name should not be blank.");
        if (count < 1)      throw new ClientInternalException("Amount of participants should be at least 1.");
        this.id = id;
        this.name = name;
        this.count = count;
        this.computeUnit = computeUnit;
        this.browser = browser;
        this.network = network;
        this.location = location;
        this.mediaType = mediaType;
    }
}
