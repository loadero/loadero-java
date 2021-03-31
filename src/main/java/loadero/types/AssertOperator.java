package loadero.types;

import com.google.gson.annotations.SerializedName;

public enum AssertOperator {
    @SerializedName("eq")
    EQUAL("eq"),
    @SerializedName("gt")
    GREATER_THAN("gt"),
    @SerializedName("gte")
    GREATER_OR_EQUAL("gte"),
    @SerializedName("lt")
    LESS_THAN("lt"),
    @SerializedName("lte")
    LESS_OR_EQUAL("lte"),
    @SerializedName("neq")
    NOT_EQUAL("neq"),
    @SerializedName("regex")
    REGEX("regex");
    
    private final String label;
    
    AssertOperator(String label) {
        this.label = label;
    }
    
    @Override
    public String toString() {
        return label;
    }
}
