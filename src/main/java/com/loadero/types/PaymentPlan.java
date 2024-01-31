package com.loadero.types;

import com.google.gson.annotations.SerializedName;

/**
 * Contains values that can represent subscription payment plan.
 */
public enum PaymentPlan {
    @SerializedName("enterprise")
    ENTERPRISE("enterprise"),
    @SerializedName("single_basic")
    SINGLE_BASIC("single_basic"),
    @SerializedName("single_pro")
    SINGLE_PRO("single_pro"),
    @SerializedName("monthly")
    MONTHLY("monthly"),
    @SerializedName("yearly")
    YEARLY("yearly");

    private final String label;
    private static final EnumLookupHelper<PaymentPlan> helper = new EnumLookupHelper<>(values());

    PaymentPlan(String label) {
        this.label = label;
    }

    public static PaymentPlan getConstant(String name) {
        return helper.getConstant(name);
    }

    @Override
    public String toString() {
        return label;
    }
}
