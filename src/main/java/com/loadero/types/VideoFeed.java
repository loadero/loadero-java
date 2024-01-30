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
    @SerializedName("1080p-meeting")
    CUSTOM_1080P_MEETING("1080p-meeting"),
    @SerializedName("1080p-meeting-clean")
    CUSTOM_1080P_MEETING_CLEAN("1080p-meeting-clean"),
    @SerializedName("1080p-30fps")
    CUSTOM_1080P_30FPS("1080p-30fps"),
    @SerializedName("1080p-15fps")
    CUSTOM_1080P_15FPS("1080p-15fps"),
    @SerializedName("1080p-5fps")
    CUSTOM_1080P_5FPS("1080p-5fps"),
    @SerializedName("240p-meeting")
    CUSTOM_240P_MEETING("240p-meeting"),
    @SerializedName("240p-30fps")
    CUSTOM_240P_30FPS("240p-30fps"),
    @SerializedName("240p-15fps")
    CUSTOM_240P_15FPS("240p-15fps"),
    @SerializedName("240p-5fps")
    CUSTOM_240P_5FPS("240p-5fps"),
    @SerializedName("360p-meeting")
    CUSTOM_360P_MEETING("360p-meeting"),
    @SerializedName("360p-30fps")
    CUSTOM_360P_30FPS("360p-30fps"),
    @SerializedName("360p-15fps")
    CUSTOM_360P_15FPS("360p-15fps"),
    @SerializedName("360p-5fps")
    CUSTOM_360P_5FPS("360p-5fps"),
    @SerializedName("480p-meeting")
    CUSTOM_480P_MEETING("480p-meeting"),
    @SerializedName("480p-30fps")
    CUSTOM_480P_30FPS("480p-30fps"),
    @SerializedName("480p-15fps")
    CUSTOM_480P_15FPS("480p-15fps"),
    @SerializedName("480p-5fps")
    CUSTOM_480P_5FPS("480p-5fps"),
    @SerializedName("720p-meeting")
    CUSTOM_720P_MEETING("720p-meeting"),
    @SerializedName("720p-30fps")
    CUSTOM_720P_30FPS("720p-30fps"),
    @SerializedName("720p-15fps")
    CUSTOM_720P_15FPS("720p-15fps"),
    @SerializedName("720p-5fps")
    CUSTOM_720P_5FPS("720p-5fps");

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
