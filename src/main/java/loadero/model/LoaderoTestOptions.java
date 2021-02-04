package loadero.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.ToString;
import lombok.AllArgsConstructor;

/**
 * LoaderoTestDescription object is a configuration profile to specify
 * test parameters for creation of Loadero tests.
 * Class will be created using Builder pattern so we can add
 * optional parameters to it. Params that are not specified is just empty String.
 */
@Data
@ToString
@AllArgsConstructor
public class LoaderoTestOptions {
    private final String name;
    @SerializedName("start_interval")
    private final int startInterval;
    @SerializedName("participant_timeout")
    private final int participantTimeout;
    private final String mode;
    @SerializedName("increment_strategy")
    private final String incrementStrategy;
    private final String script;
}
