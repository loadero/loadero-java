package loadero.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import cucumber.api.java.eo.Se;
import loadero.types.LoaderoIncrementStrategyType;
import loadero.types.LoaderoTestModeType;
import loadero.utils.FunctionBodyParser;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

import java.net.URI;
import java.util.Map;

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
    private int id = 0;
    private String name = "";
    @SerializedName("start_interval")
    private int startInterval = 0;
    @SerializedName("participant_timeout")
    private int participantTimeout = 0;
    private LoaderoTestModeType mode;
    @SerializedName("increment_strategy")
    private LoaderoIncrementStrategyType incrementStrategy;
    private String script = ""; // Path to script
    @SerializedName("script_file_id")
    @Expose(serialize = false)
    private int scriptFileId = 0;

    /**
     * When given only URI as argument, threats this argument as path
     * in where to look for a script, then parses it to string.
     * Assumed, that script is already fully functional.
     * @param scriptPath - URI pointing to script location.
     */
    public void setScript(URI scriptPath) {
        this.script = FunctionBodyParser.getScriptContent(scriptPath.toString());
    }

    /**
     * When provided String path and LoaderoTestScriptParams, then it uses params
     * from the LoaderoTestScriptParams object to apply them to script in the given path.
     * @param path         - Location of script.
     * @param scriptParams - LoaderoTestScriptParams to be applied.
     */
    public void setScript(String path, Map<String, String> scriptParams) {
        this.script = FunctionBodyParser.applyParamsToScript(path, scriptParams);
    }

    /**
     * When given String as argument, threats it as script.
     * Assumed that script is already fully functional.
     * @param script - Script given as string.
     */
    public void setScript(String script) {
        this.script = script;
    }
}
