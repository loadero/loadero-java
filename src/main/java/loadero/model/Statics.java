package loadero.model;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.Set;

@Getter
@NoArgsConstructor
public final class Statics implements LoaderoModel {
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
