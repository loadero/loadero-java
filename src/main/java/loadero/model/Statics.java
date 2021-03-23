package loadero.model;

import com.google.gson.annotations.SerializedName;
import loadero.types.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.Set;

@Data
@NoArgsConstructor
public class Statics implements LoaderoModel {
    private Set<Map<String, String>> browser;
    private Set<Map<String, String>> network;
    @SerializedName("media_type")
    private Set<Map<String, String>> mediaType;
    private Set<Map<String, String>> location;
    @SerializedName("test_mode")
    private Set<Map<String, String>> testMode;
    @SerializedName("compute_unit")
    private Set<Map<String, String>> computeUnit;
    @SerializedName("increment_strategy")
    private Set<Map<String, String>> incrementStrategy;
}
