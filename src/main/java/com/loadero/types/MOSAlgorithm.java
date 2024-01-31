package com.loadero.types;

import com.google.gson.annotations.SerializedName;

/**
 * Contains values that can be used to read result MOS algorithms.
 */
public enum MOSAlgorithm {
    @SerializedName("e-model")
    E_MODEL("e-model"),
    @SerializedName("visqol")
    VISQOL("visqol");

    private final String label;
    private static final EnumLookupHelper<MOSAlgorithm> helper = new EnumLookupHelper<>(values());

    MOSAlgorithm(String label) {
        this.label = label;
    }

    public static MOSAlgorithm getConstant(String name) {
        return helper.getConstant(name);
    }

    @Override
    public String toString() {
        return label;
    }
}
