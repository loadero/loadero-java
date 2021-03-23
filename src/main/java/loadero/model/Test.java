package loadero.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import loadero.exceptions.ClientInternalException;
import loadero.types.IncrementStrategyType;
import loadero.types.TestModeType;
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
 * need to be serialized when sending Test object as JSON to update test description
 * on Loadero.
 */
@Data
@NoArgsConstructor
@Generated // Need this for JaCoco to ignore getters and setters
public final class Test implements LoaderoModel {
    @Expose(serialize = false)
    private int id = 0;
    private String name = "";
    @SerializedName("start_interval")
    private int startInterval = 0;
    @SerializedName("participant_timeout")
    private int participantTimeout = 0;
    private TestModeType mode;
    @SerializedName("increment_strategy")
    private IncrementStrategyType incrementStrategy;
    private String script = ""; // Path to script
    @SerializedName("script_file_id")
    @Expose(serialize = false)
    private int scriptFileId = 0;
    
    /**
     * Constructor for Loadero test.
     * @param name               Name of the test.
     * @param startInterval      Starting interval of the participant. In seconds.
     * @param participantTimeout Timeout for each participant. In seconds.
     * @param mode               How to perform test. Performance, load or session-record
     * @param incrementStrategy  How Loadero should add participants during test run.
     * @param script             Test script.
     * @throws ClientInternalException if name is blank or null.
     * @throws ClientInternalException if startInterval is negative.
     * @throws ClientInternalException if participantTimeout is negative.
     */
    public Test(String name, int startInterval, int participantTimeout,
                TestModeType mode, IncrementStrategyType incrementStrategy,
                String script) {
        if (name.isBlank())
            throw new ClientInternalException("Name should not be blank.");
        if (startInterval < 0)
            throw new ClientInternalException("Start interval should not be negative.");
        if (participantTimeout < 0)
            throw new ClientInternalException("Participant timeout should not be negative.");
        
        this.name = name;
        this.startInterval = startInterval;
        this.participantTimeout = participantTimeout;
        this.mode = mode;
        this.incrementStrategy = incrementStrategy;
        this.script = script;
    }
    
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
