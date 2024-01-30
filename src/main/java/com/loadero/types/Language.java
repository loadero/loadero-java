package com.loadero.types;

import com.google.gson.annotations.SerializedName;

/**
 * Contains values represent project language.
 */
public enum Language {
    @SerializedName("javascript")
    JAVASCRIPT("javascript"),
    @SerializedName("java")
    JAVA("java"),
    @SerializedName("python")
    PYTHON("python");

    private final String label;
    private static final EnumLookupHelper<Language> helper = new EnumLookupHelper<>(values());

    Language(String label) {
        this.label = label;
    }

    public static Language getConstant(String name) {
        return helper.getConstant(name);
    }

    @Override
    public String toString() {
        return label;
    }
}
