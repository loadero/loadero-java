package loadero.types;


import com.google.gson.annotations.SerializedName;

/**
 * Enum to define increment strategies types provided for usage in Loadero.
 */
public enum LoaderoIncrementStrategyType implements Labeled {
    @SerializedName("linear")
    LINEAR("linear"),
    @SerializedName("random")
    RANDOM("random"),
    @SerializedName("linear_group")
    LINEAR_GROUP("linear_group"),
    @SerializedName("random_group")
    RANDOM_GROUP("random_group");
    
    private final String label;
    
    LoaderoIncrementStrategyType(String label) {
        this.label = label;
    }
    
    @Override
    public String label() { return label; }
}
