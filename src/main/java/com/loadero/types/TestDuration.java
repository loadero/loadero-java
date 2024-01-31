package com.loadero.types;

import com.google.gson.annotations.SerializedName;

/**
 * Contains values represent project subscription max test duration.
 */
public enum TestDuration {
    @SerializedName("15m")
    DURATION_15M("15m"),
    @SerializedName("45m")
    DURATION_45M("45m"),
    @SerializedName("1h")
    DURATION_1H("1h"),
    @SerializedName("2h")
    DURATION_2H("2h"),
    @SerializedName("4h")
    DURATION_4H("4h"),
    @SerializedName("8h")
    DURATION_8H("8h"),
    @SerializedName("24h")
    DURATION_24H("24h");

    private final String label;
    private static final EnumLookupHelper<TestDuration> helper = new EnumLookupHelper<>(values());

    TestDuration(String label) {
        this.label = label;
    }

    public static TestDuration getConstant(String name) {
        return helper.getConstant(name);
    }

    @Override
    public String toString() {
        return label;
    }
}
