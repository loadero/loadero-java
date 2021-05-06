package com.loadero.types;

import com.google.gson.annotations.SerializedName;

/**
 * Contains values that can be used to set location for a {@link com.loadero.model.Participant}.
 */
public enum Location {
    @SerializedName("eu-central-1")
    EU_CENTRAL_1("eu-central-1"),
    @SerializedName("eu-west-1")
    EU_WEST_1("eu-west-1"),
    @SerializedName("eu-west-3")
    EU_WEST_3("eu-west-3"),
    @SerializedName("ap-northwest-1")
    AP_NORTHEAST_1("ap-northeast-1"),
    @SerializedName("ap-southeast-2")
    AP_SOUTHEAST_2("ap-southeast-2"),
    @SerializedName("ap-east-1")
    AP_EAST_1("ap-east-1"),
    @SerializedName("ap-south-1")
    AP_SOUTH_1("ap-south-1"),
    @SerializedName("us-east-1")
    US_EAST_1("us-east-1"),
    @SerializedName("us-east-2")
    US_EAST_2("us-east-2"),
    @SerializedName("us-west-2")
    US_WEST_2("us-west-2"),
    @SerializedName("sa-east-1")
    SA_EAST_1("sa-east-1"),
    @SerializedName("ap-northeast-2")
    AP_NORTHEAST_2("ap-northeast-2");

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
