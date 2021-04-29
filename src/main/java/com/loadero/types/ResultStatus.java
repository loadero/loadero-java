package com.loadero.types;

import com.google.gson.annotations.SerializedName;

public enum ResultStatus {
    @SerializedName("timeout")
    TIMEOUT,
    @SerializedName("aborted")
    ABORTED,
    @SerializedName("pass")
    PASS,
    @SerializedName("fail")
    FAIL
}
