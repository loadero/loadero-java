package loadero.model;

import com.google.gson.annotations.SerializedName;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LoaderoGroup implements LoaderoModel {
    private long id;
    @SerializedName("test_id")
    private long testId;
    private String name;
    private int count;
}
