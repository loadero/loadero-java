package com.loadero.types;

import com.google.gson.annotations.SerializedName;

/**
 * Contains values that are used to deserialize result artifact types.
 */
public enum Artifact {
    @SerializedName("screenshot")
    SCREENSHOT("screenshot"),
    @SerializedName("video")
    VIDEO("video"),
    @SerializedName("audio")
    AUDIO("audio"),
    @SerializedName("downloads")
    DOWNLOADS("downloads");

    private final String label;
    private static final EnumLookupHelper<Artifact> helper = new EnumLookupHelper<>(values());

    Artifact(String label) {
        this.label = label;
    }

    public static Artifact getConstant(String name) {
        return helper.getConstant(name);
    }

    @Override
    public String toString() {
        return label;
    }
}
