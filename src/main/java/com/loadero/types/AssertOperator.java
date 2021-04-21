package com.loadero.types;

import com.google.gson.annotations.SerializedName;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public enum AssertOperator {
    @SerializedName("eq")
    EQUAL("eq"),
    @SerializedName("gt")
    GREATER_THAN("gt"),
    @SerializedName("gte")
    GREATER_OR_EQUAL("gte"),
    @SerializedName("lt")
    LESS_THAN("lt"),
    @SerializedName("lte")
    LESS_OR_EQUAL("lte"),
    @SerializedName("neq")
    NOT_EQUAL("neq"),
    @SerializedName("regex")
    REGEX("regex");

    private final String label;
    private static final EnumLookupHelper<AssertOperator> helper = new EnumLookupHelper<>(values());

    AssertOperator(String label) {
        this.label = label;
    }

    public static AssertOperator getConstant(String name) {
        return helper.getConstant(name);
    }

    @Override
    public String toString() {
        return label;
    }
}
