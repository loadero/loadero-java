package loadero.model;

import com.google.gson.annotations.SerializedName;
import loadero.utils.FunctionBodyParser;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * LoaderoTestDescription object is a configuration profile to specify
 * test parameters for creation of Loadero tests.
 * Class will be created using Builder pattern so we can add
 * optional parameters to it. Params that are not specified is just empty String.
 */
@Data
@NoArgsConstructor
public class LoaderoTestOptions implements LoaderoModel {
//    private long id;
    private String name = "";
    @SerializedName("start_interval")
    private int startInterval = 0;
    @SerializedName("participant_timeout")
    private int participantTimeout = 0;
    private String mode = "";
    @SerializedName("increment_strategy")
    private String incrementStrategy = "";
    private String script = ""; // Path to script
    @SerializedName("script_file_id")
    private long scriptFileId;

    public void setScriptPath(String scriptPath) {
        this.script = FunctionBodyParser.getBody(scriptPath);
    }

    public void setScript(String script) {
        this.script = script;
    }
}
