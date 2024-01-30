package com.loadero.types;

import com.google.gson.annotations.SerializedName;

/**
 * Contains values that can be used to set media type for a {@link com.loadero.model.Participant}.
 */
public enum MediaType {
    @SerializedName("custom")
    CUSTOM("custom"),
    @SerializedName("default")
    DEFAULT("default"),
    @SerializedName("240pAV")
    CUSTOM_240P_AV("240pAV"),
    @SerializedName("360pAV")
    CUSTOM_360P_AV("360pAV"),
    @SerializedName("480pAV")
    CUSTOM_480P_AV("480pAV"),
    @SerializedName("720pAV")
    CUSTOM_720P_AV("720pAV"),
    @SerializedName("1080pAV")
    CUSTOM_1080P_AV("1080pAV"),
    @SerializedName("1080p-20db")
    CUSTOM_1080P_20DB("1080p-20db"),
    @SerializedName("1080p-30db")
    CUSTOM_1080P_30DB("1080p-30db"),
    @SerializedName("1080p-50db")
    CUSTOM_1080P_50DB("1080p-50db"),
    @SerializedName("720p-marked")
    CUSTOM_720P_MARKED("720p-marked");

    private final String label;
    private static final EnumLookupHelper<MediaType> helper = new EnumLookupHelper<>(values());

    MediaType(String label) {
        this.label = label;
    }

    public static MediaType getConstant(String name) {
        return helper.getConstant(name);
    }

    @Override
    public String toString() {
        return label;
    }
}
