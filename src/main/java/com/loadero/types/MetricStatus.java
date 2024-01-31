package com.loadero.types;

import com.google.gson.annotations.SerializedName;

/**
 * Contains values that can represent run or result metric status.
 */
public enum MetricStatus {
    @SerializedName("none")
    NONE("none"),
    @SerializedName("requested")
    REQUESTED("requested"),
    @SerializedName("calculating")
    CALCULATING("calculating"),
    @SerializedName("available")
    AVAILABLE("available"),
    @SerializedName("calculation-error")
    CALCULATION_ERROR("calculation-error"),
    @SerializedName("calculation-timeout")
    CALCULATION_TIMEOUT("calculation-timeout");

    private final String label;
    private static final EnumLookupHelper<MetricStatus> helper = new EnumLookupHelper<>(values());

    MetricStatus(String label) {
        this.label = label;
    }

    public static MetricStatus getConstant(String name) {
        return helper.getConstant(name);
    }

    @Override
    public String toString() {
        return label;
    }
}
