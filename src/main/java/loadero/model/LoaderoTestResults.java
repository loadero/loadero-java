package loadero.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoaderoTestResults implements LoaderoModel {
    private long id;
    @SerializedName("test_id")
    private long testId;
    private String status;
    @SerializedName("test_mode")
    private String testMode;
    @SerializedName("increment_strategy")
    private String incrementStrategy;
    @SerializedName("script_file_id")
    private long scriptFileId;
    @SerializedName("test_name")
    private String testName;
    @SerializedName("start_interval")
    private int startInterval;
    @SerializedName("participant_timeout")
    private int participantTimeout;
    @SerializedName("launching_account_id")
    private long launchAccountId;
}
