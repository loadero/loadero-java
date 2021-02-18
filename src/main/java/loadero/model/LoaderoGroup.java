package loadero.model;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * LoaderoGroup class is responsible for representing information retrieved
 * from Loadero API /groups/groupId endpoint.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoaderoGroup implements LoaderoModel {
    private long id;
    @SerializedName("test_id")
    private long testId;
    private String name;
    private int count;
}
