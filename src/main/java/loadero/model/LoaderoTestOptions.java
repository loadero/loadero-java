package loadero.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * LoaderoTestDescription object is a configuration profile to specify
 * test parameters for creation of Loadero tests.
 * Class will be created using Builder pattern so we can add
 * optional parameters to it. Params that are not specified is just empty String.
 */
@Data
@NoArgsConstructor
public class LoaderoTestOptions implements LoaderoModel {
    private long id;
    private String name;
    @SerializedName("start_interval")
    private int startInterval;
    @SerializedName("participant_timeout")
    private int participantTimeout;
    private String mode;
    @SerializedName("increment_strategy")
    private String incrementStrategy;
    private String script;
    @Expose
    private String uri;

}
