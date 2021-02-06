package loadero.model;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class LoaderoGroup implements LoaderoModel {
    private long id;
    @SerializedName("test_id")
    private long testId;
    private String name;
    private int count;
}
