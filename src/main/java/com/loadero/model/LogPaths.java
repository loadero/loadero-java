package com.loadero.model;

import com.google.gson.annotations.SerializedName;

/**
 * Class to represent information about log_paths in results.
 */
public final class LogPaths {
    private final int id;
    private final String created;
    @SerializedName("result_id")
    private final int resultId;
    private final String webrtc;
    private final String selenium;
    private final String browser;
    private final String rru;

    public LogPaths(
        int id,
        String created,
        int resultId,
        String webrtc,
        String selenium,
        String browser,
        String rru
    ) {
        this.id = id;
        this.created = created;
        this.resultId = resultId;
        this.webrtc = webrtc;
        this.selenium = selenium;
        this.browser = browser;
        this.rru = rru;
    }

    public int getId() {
        return id;
    }

    public String getCreated() {
        return created;
    }

    public int getResultId() {
        return resultId;
    }

    public String getWebrtc() {
        return webrtc;
    }

    public String getSelenium() {
        return selenium;
    }

    public String getBrowser() {
        return browser;
    }

    public String getRru() {
        return rru;
    }

    @Override
    public String toString() {
        return "LogPaths{" +
            "id=" + id +
            ", created='" + created + '\'' +
            ", resultId=" + resultId +
            ", webrtc='" + webrtc + '\'' +
            ", selenium='" + selenium + '\'' +
            ", browser='" + browser + '\'' +
            ", rru='" + rru + '\'' +
            '}';
    }
}
