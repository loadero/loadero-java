package com.loadero.types;

import com.google.gson.annotations.SerializedName;

/**
 * Contains values that are used to deserialize video feed for a {@link com.loadero.model.Participant}.
 */
public enum VideoFeed {
    @SerializedName("default")
    DEFAULT("default"),
    @SerializedName("1080p-marked-top-left")
    CUSTOM_1080P_MARKED_TOP_LEFT("1080p-marked-top-left"),
    @SerializedName("1080p")
    CUSTOM_1080P("1080p"),
    @SerializedName("720p")
    CUSTOM_720P("720p"),
    @SerializedName("360p")
    CUSTOM_360P("360p"),
    @SerializedName("240p")
    CUSTOM_240P("240p"),
    @SerializedName("720p-marked")
    CUSTOM_720P_MARKED("720p-marked"),
    @SerializedName("1080p-marked-center")
    CUSTOM_1080P_MARKED_CENTER("1080p-marked-center"),
    @SerializedName("480p")
    CUSTOM_480P("480p");

    private final String label;
    private static final EnumLookupHelper<VideoFeed> helper = new EnumLookupHelper<>(values());

    VideoFeed(String label) {
        this.label = label;
    }

    public static VideoFeed getConstant(String name) {
        return helper.getConstant(name);
    }

    @Override
    public String toString() {
        return label;
    }
}
