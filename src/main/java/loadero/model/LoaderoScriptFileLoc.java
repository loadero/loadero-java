package loadero.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoaderoScriptFileLoc implements LoaderoModel{
    private long id;
    @SerializedName("project_id")
    private long projectId;
    @SerializedName("file_type")
    private String fileType;
    private String content;
}
