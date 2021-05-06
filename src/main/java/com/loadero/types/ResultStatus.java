package com.loadero.types;

import com.google.gson.annotations.SerializedName;

/**
 * Contains values that is used for status and selenium_result fields.
 */
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
