package loadero.model;


import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.Map;

/**
 * LoaderoTestRunParticipantResult represents the information about single participant
 * test run result.
 */
@Data
public class LoaderoTestRunParticipantResult implements LoaderoModel {
    private long id;
    private String status;
    @SerializedName("selenium_result")
    private String seleniumResult;
    @SerializedName("log_paths")
    private Map<String, Object> logPaths;
    private Map<String, Object> asserts;
    private Map<String, Object> artifacts;
}
