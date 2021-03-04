package loadero.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

/**
 * LoaderoRunInfo class is responsible for representing retrieved information during and after
 * test runs.
 */
@Data
@NoArgsConstructor
@Generated // Need this for JaCoco to ignore getters and setters
public class LoaderoRunInfo implements LoaderoModel {
    private int id;
    @SerializedName("test_id")
    private int testId;
    private String status;
    @SerializedName("test_mode")
    private String testMode;
    @SerializedName("increment_strategy")
    private String incrementStrategy;
    @SerializedName("script_file_id")
    private int scriptFileId;
    @SerializedName("test_name")
    private String testName;
    @SerializedName("start_interval")
    private int startInterval;
    @SerializedName("participant_timeout")
    private int participantTimeout;
    @SerializedName("launching_account_id")
    private int launchAccountId;
    @SerializedName("success_rate")
    private double successRate;
}
