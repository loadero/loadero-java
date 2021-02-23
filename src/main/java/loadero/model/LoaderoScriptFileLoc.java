package loadero.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

/**
 * LoaderoScriptFileLoc purpose is to store information about Loadero test script,
 * that is used to run tests.
 */
@Data
@NoArgsConstructor
@Generated // Need this for JaCoco to ignore getters and setters
public class LoaderoScriptFileLoc implements LoaderoModel{
    private long id;
    @SerializedName("project_id")
    private long projectId;
    @SerializedName("file_type")
    private String fileType;
    private String content;
}
