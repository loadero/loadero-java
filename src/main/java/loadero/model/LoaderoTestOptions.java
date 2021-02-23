package loadero.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import loadero.utils.FunctionBodyParser;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

import java.net.URI;

/**
 * LoaderoTestDescription object is a configuration profile to specify
 * test parameters for creation of Loadero tests.
 *
 * Due to Loadero API design, fields have to have default values and field ID doesn't
 * need to be serialized when sending LoaderoTestOptions object as JSON to update test description
 * on Loadero.
 */
@Data
@NoArgsConstructor
@Generated // Need this for JaCoco to ignore getters and setters
public class LoaderoTestOptions implements LoaderoModel {
    @Expose(serialize = false)
    private long id = 0L;
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
    @Expose(serialize = false)
    private long scriptFileId = 0L;

    /**
     * When given URI as argument, threats this argument as path
     * in where to look for a script, then parses it to string.
     * @param scriptPath - URI pointing to script location.
     */
    public void setScript(URI scriptPath) {
        this.script = FunctionBodyParser.getScriptContent(scriptPath.toString());
    }

    /**
     * When given String as argument, threats it as script.
     * @param script - Script given as string.
     */
    public void setScript(String script) {
        this.script = script;
    }
}
