package loadero.model;


import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.Generated;

import java.util.Map;

/**
 * LoaderoTestRunParticipantResult represents the information about single participant
 * test run result.
 */
@Data
@Generated // Need this for JaCoco to ignore getters and setters
public final class LoaderoTestRunParticipantResult implements LoaderoModel {
    private int id;
    private String status;
    @SerializedName("selenium_result")
    private String seleniumResult;
    @SerializedName("log_paths")
    private Map<String, Object> logPaths;
    private Map<String, Object> asserts;
    private Map<String, Object> artifacts;
}
