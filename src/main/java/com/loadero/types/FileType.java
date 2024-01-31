package com.loadero.types;

import com.google.gson.annotations.SerializedName;

/**
 * Contains values represent various file types.
 */
public enum FileType {
    @SerializedName("test_script")
    TEST_SCRIPT("test_script"),
    @SerializedName("run_script")
    RUN_SCRIPT("run_script"),
    @SerializedName("ssl-certificate")
    SSL_CERTIFICATE("ssl-certificate"),
    @SerializedName("run-ssl-certificate")
    RUN_SSL_CERTIFICATE("run-ssl-certificate");

    private final String label;
    private static final EnumLookupHelper<FileType> helper = new EnumLookupHelper<>(values());

    FileType(String label) {
        this.label = label;
    }

    public static FileType getConstant(String name) {
        return helper.getConstant(name);
    }

    @Override
    public String toString() {
        return label;
    }
}
