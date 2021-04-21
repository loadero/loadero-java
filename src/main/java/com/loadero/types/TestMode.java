package com.loadero.types;

import com.google.gson.annotations.SerializedName;

/**
 * Enum to define test mode types provided for usage in Loadero.
 */
public enum TestMode {
    @SerializedName("load")
    LOAD("load"),
    @SerializedName("performance")
    PERFORMANCE("performance"),
    @SerializedName("session-record")
    SESSION_RECORD("session-record");

    private final String label;
    private static final EnumLookupHelper<TestMode> helper = new EnumLookupHelper<>(values());

    TestMode(String label) {
        this.label = label;
    }

    public static TestMode getConstant(String name) {
        return helper.getConstant(name);
    }

    @Override
    public String toString() {
        return label;
    }
}
