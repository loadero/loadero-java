package loadero.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import loadero.exceptions.ClientInternalException;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

/**
 * Group class is responsible for representing information retrieved
 * from Loadero API /groups/groupId endpoint.
 */
@Data
@NoArgsConstructor
@Generated // Need this for JaCoco to ignore getters and setters
public final class Group implements LoaderoModel {
    @Expose(serialize = false)
    private int id = 0;
    @Expose(serialize = false)
    @SerializedName("test_id")
    private int testId = 0;
    private String name = "";
    private int count = 0;
    
    /**
     * Constructor for Loadero groups.
     * @param name  Name of the group.
     * @param count How many groups you need. At least 1 group is required.
     * @throws ClientInternalException if name is an empty string or null.
     * @throws ClientInternalException if count is less than 1.
     */
    public Group(String name, int count) {
        if (name.isBlank()) {throw new ClientInternalException("Name shouldn't be blank."); }
        if (count < 1) {throw new ClientInternalException("Amount of groups should at least 1."); }
        
        this.name = name;
        this.count = count;
    }
}
