package com.loadero.types;

import com.google.gson.annotations.SerializedName;

/**
 * Contains values that can be used to set location for a {@link com.loadero.model.Participant}.
 */
public enum Location {
    @SerializedName("eu-central-1")
    FRANKFURT("eu-central-1"),
    @SerializedName("eu-west-1")
    IRELAND("eu-west-1"),
    @SerializedName("eu-west-3")
    PARIS("eu-west-3"),
    @SerializedName("ap-northeast-1")
    TOKYO("ap-northeast-1"),
    @SerializedName("ap-southeast-2")
    SYDNEY("ap-southeast-2"),
    @SerializedName("ap-east-1")
    HONG_KONG("ap-east-1"),
    @SerializedName("ap-south-1")
    MUMBAI("ap-south-1"),
    @SerializedName("us-east-1")
    NORTH_VIRGINIA("us-east-1"),
    @SerializedName("us-east-2")
    OHIO("us-east-2"),
    @SerializedName("us-west-2")
    OREGON("us-west-2"),
    @SerializedName("sa-east-1")
    SAO_PAULO("sa-east-1"),
    @SerializedName("ap-northeast-2")
    SEOUL("ap-northeast-2");

    private final String label;
    private static final EnumLookupHelper<Location> helper = new EnumLookupHelper<>(values());

    Location(String label) {
        this.label = label;
    }

    public static Location getConstant(String name) {
        return helper.getConstant(name);
    }

    @Override
    public String toString() {
        return label;
    }
}
