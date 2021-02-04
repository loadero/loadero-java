package loadero.model;

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
    private String name;
    @SerializedName("start_interval")
    private int startInterval;
    @SerializedName("participant_timeout")
    private int participantTimeout;
    private String mode;
    @SerializedName("increment_strategy")
    private String incrementStrategy;
    @Setter(AccessLevel.NONE)
    private String script;

    public LoaderoTestOptions(String name, int startInterval,
                              int participantTimeout, String mode,
                              String incrementStrategy, String script) {
        this.name = name;
        this.startInterval = startInterval;
        this.participantTimeout = participantTimeout;
        this.mode = mode;
        this.incrementStrategy = incrementStrategy;
        this.script = script;
    }

    public void setScript(String script) {
        this.script = jsToString(script);
    }

    private String jsToString(String pathToJs) {
        String content = null;
        try {
            Path path = Path.of(pathToJs);
            content = Files.readString(path);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return content;
    }
}
