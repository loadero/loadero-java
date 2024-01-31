package com.loadero.types;

import com.google.gson.annotations.SerializedName;

/**
 * Contains values that can be used to set property for a {@link com.loadero.model.Precondition}.
 */
public enum Property {
    @SerializedName("browser")
    BROWSER("browser"),
    @SerializedName("media_type")
    MEDIA_TYPE("media_type"),
    @SerializedName("network")
    NETWORK("network"),
    @SerializedName("location")
    LOCATION("location"),
    @SerializedName("compute_unit")
    COMPUTE_UNIT("compute_unit"),
    @SerializedName("participant_name")
    PARTICIPANT_NAME("participant_name"),
    @SerializedName("participant_num")
    PARTICIPANT_NUM("participant_num"),
    @SerializedName("group_name")
    GROUP_NAME("group_name"),
    @SerializedName("group_num")
    GROUP_NUM("group_num"),
    @SerializedName("audio_feed")
    AUDIO_FEED("audio_feed"),
    @SerializedName("video_feed")
    VIDEO_FEED("video_feed");

    private final String label;
    private static final EnumLookupHelper<Property> helper = new EnumLookupHelper<>(values());

    Property(String label) {
        this.label = label;
    }

    public static Property getConstant(String name) {
        return helper.getConstant(name);
    }

    @Override
    public String toString() {
        return label;
    }
}
