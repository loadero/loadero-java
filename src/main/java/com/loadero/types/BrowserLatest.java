package com.loadero.types;

import com.google.gson.annotations.SerializedName;

/**
 * Contains values that can be used to set latest browser
 * for a {@link com.loadero.model.Participant}.
 */
public enum BrowserLatest {
    @SerializedName("firefoxLatest")
    FIREFOX_LATEST("firefoxLatest"),
    @SerializedName("chromeLatest")
    CHROME_LATEST("chromeLatest");

    private final String label;

    BrowserLatest(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
