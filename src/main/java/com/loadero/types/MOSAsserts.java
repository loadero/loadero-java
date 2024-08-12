package com.loadero.types;

import com.google.gson.annotations.SerializedName;

/**
 * Contains values that can be used to set MOS paths for an {@link com.loadero.model.Assert}.
 */
public enum MOSAsserts implements AssertPath {
    @SerializedName("mos/audio/e-model/avg")
    MOS_AUDIO_EMODEL_AVG("mos/audio/e-model/avg");

    private final String label;
    private static final EnumLookupHelper<MOSAsserts> helper = new EnumLookupHelper<>(values());

    MOSAsserts(String label) {
        this.label = label;
    }

    public static MOSAsserts getConstant(String name) {
        return helper.getConstant(name);
    }

    @Override
    public String toString() {
        return label;
    }
}
