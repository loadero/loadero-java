package com.loadero.types;

import com.google.gson.annotations.SerializedName;

/**
 * Contains values that can be used to set compute unit for a {@link com.loadero.model.Participant}.
 */
public enum ComputeUnit {
    @SerializedName("g0.5")
    G05("g0.5"),
    @SerializedName("g1")
    G1("g1"),
    @SerializedName("g2")
    G2("g2"),
    @SerializedName("g6")
    G6("g6"),
    @SerializedName("g4")
    G4("g4");

    private final String label;
    private static final EnumLookupHelper<ComputeUnit> helper = new EnumLookupHelper<>(values());

    ComputeUnit(String label) {
        this.label = label;
    }

    public static ComputeUnit getConstant(String name) {
        return helper.getConstant(name);
    }

    @Override
    public String toString() {
        return label;
    }
}
