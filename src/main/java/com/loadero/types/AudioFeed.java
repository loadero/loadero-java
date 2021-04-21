package com.loadero.types;

import com.google.gson.annotations.SerializedName;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public enum AudioFeed {
    @SerializedName("dtmf")
    DTMF("dtmf"),
    @SerializedName("default")
    DEFAULT("default"),
    @SerializedName("-50db")
    STEREO_MINUS_50_DB("-50db"),
    @SerializedName("-20db")
    STEREO_MINUS_20_DB("-20db"),
    @SerializedName("-30db")
    STEREO_MINUS_30_DB("-30db"),
    @SerializedName("128kbps")
    STEREO_128_KBPS("128kbps");

    private final String label;
    private static final EnumLookupHelper<AudioFeed> helper = new EnumLookupHelper<>(values());

    AudioFeed(String label) {
        this.label = label;
    }

    public static AudioFeed getConstant(String name) {
        return helper.getConstant(name);
    }

    @Override
    public String toString() {
        return label;
    }
}