package com.loadero.types;

import com.google.gson.annotations.SerializedName;

/**
 * Contains values that can represent subscription payment status.
 */
public enum PaymentStatus {
    @SerializedName("draft")
    DRAFT("draft"),
    @SerializedName("success")
    SUCCESS("success"),
    @SerializedName("declined")
    DECLINED("declined"),
    @SerializedName("skip_renewal")
    SKIP_RENEWAL("skip_renewal"),
    @SerializedName("cancelled")
    CANCELLED("cancelled"),
    @SerializedName("processing")
    PROCESSING("processing"),
    @SerializedName("vat_invalid")
    VAT_INVALID("vat_invalid");

    private final String label;
    private static final EnumLookupHelper<PaymentStatus> helper = new EnumLookupHelper<>(values());

    PaymentStatus(String label) {
        this.label = label;
    }

    public static PaymentStatus getConstant(String name) {
        return helper.getConstant(name);
    }

    @Override
    public String toString() {
        return label;
    }
}
