package com.loadero.types;

import com.google.gson.annotations.SerializedName;

/**
 * Contains values that can be used to set location for a {@link com.loadero.model.Participant}.
 */
public enum Location {
    @SerializedName(value = "eu-central-1", alternate = "frankfurt")
    FRANKFURT("eu-central-1"),
    @SerializedName(value = "eu-west-1", alternate = "ireland")
    IRELAND("eu-west-1"),
    @SerializedName(value = "eu-west-3", alternate = "paris")
    PARIS("eu-west-3"),
    @SerializedName(value = "ap-northeast-1", alternate = "tokyo")
    TOKYO("ap-northeast-1"),
    @SerializedName(value = "ap-southeast-2", alternate = "sydney")
    SYDNEY("ap-southeast-2"),
    @SerializedName(value = "ap-east-1", alternate = "hong-kong")
    HONG_KONG("ap-east-1"),
    @SerializedName(value = "ap-south-1", alternate = "mumbai")
    MUMBAI("ap-south-1"),
    @SerializedName(value = "us-east-1", alternate = "north virginia")
    NORTH_VIRGINIA("us-east-1"),
    @SerializedName(value = "us-east-2", alternate = "ohio")
    OHIO("us-east-2"),
    @SerializedName(value = "us-west-2", alternate = "oregon")
    OREGON("us-west-2"),
    @SerializedName(value = "sa-east-1", alternate = "sao-paulo")
    SAO_PAULO("sa-east-1"),
    @SerializedName(value = "ap-northeast-2", alternate = "seoul")
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
