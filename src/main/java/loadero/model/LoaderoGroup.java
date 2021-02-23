package loadero.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

/**
 * LoaderoGroup class is responsible for representing information retrieved
 * from Loadero API /groups/groupId endpoint.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Generated // Need this for JaCoco to ignore getters and setters
public class LoaderoGroup implements LoaderoModel {
    @Expose(serialize = false)
    private long id = 0L;
    @Expose(serialize = false)
    @SerializedName("test_id")
    private long testId = 0L;
    private String name = "";
    private int count = 0;
}
