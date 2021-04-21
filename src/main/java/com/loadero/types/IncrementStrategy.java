package com.loadero.types;


import com.google.gson.annotations.SerializedName;

/**
 * Enum to define increment strategies types provided for usage in Loadero.
 */
public enum IncrementStrategy {
    @SerializedName("linear")
    LINEAR("linear"),
    @SerializedName("random")
    RANDOM("random"),
    @SerializedName("linear_group")
    LINEAR_GROUP("linear_group"),
    @SerializedName("random_group")
    RANDOM_GROUP("random_group");

    private final String label;
    private static final EnumLookupHelper<IncrementStrategy> helper =
        new EnumLookupHelper<>(values());

    IncrementStrategy(String label) {
        this.label = label;
    }

    public static IncrementStrategy getConstant(String name) {
        return helper.getConstant(name);
    }

    @Override
    public String toString() {
        return label;
    }
}
