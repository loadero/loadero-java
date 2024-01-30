package com.loadero.types;

import com.google.gson.annotations.SerializedName;

/**
 * Contains values that can be used to read compute unit usage payment status.
 */
public enum CUUsageStatus {
    @SerializedName("run-active")
    RUN_ACTIVE("run-active"),
    @SerializedName("reported")
    REPORTED("reported"),
    @SerializedName("payment-failed")
    PAYMENT_FAILED("payment-failed"),
    @SerializedName("paid")
    PAID("paid"),
    @SerializedName("void")
    VOID("void");

    private final String label;
    private static final EnumLookupHelper<CUUsageStatus> helper = new EnumLookupHelper<>(values());

    CUUsageStatus(String label) {
        this.label = label;
    }

    public static CUUsageStatus getConstant(String name) {
        return helper.getConstant(name);
    }

    @Override
    public String toString() {
        return label;
    }
}
