package com.loadero.types;

import com.google.gson.annotations.SerializedName;

/**
 * Contains values that are used to deserialize project member's role.
 */
public enum MemberRole {
    @SerializedName("visitor")
    VISITOR("visitor"),
    @SerializedName("developer")
    DEVELOPER("developer"),
    @SerializedName("administrator")
    ADMINISTRATOR("admistrator");

    private final String label;
    private static final EnumLookupHelper<MemberRole> helper = new EnumLookupHelper<>(values());

    MemberRole(String label) {
        this.label = label;
    }

    public static MemberRole getConstant(String name) {
        return helper.getConstant(name);
    }

    @Override
    public String toString() {
        return label;
    }
}
