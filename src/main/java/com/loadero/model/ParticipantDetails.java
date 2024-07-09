package com.loadero.model;

import com.google.gson.annotations.SerializedName;
import com.loadero.types.AudioFeed;
import com.loadero.types.Browser;
import com.loadero.types.ComputeUnit;
import com.loadero.types.Location;
import com.loadero.types.Network;
import com.loadero.types.VideoFeed;

/*
 * Class to deserialize information about participant_details field in results.
 */
public final class ParticipantDetails {
    private final int id;
    private final String created;
    private final String updated;
    @SerializedName("participant_num")
    private final int participantNum;
    @SerializedName("participant_name")
    private final String participantName;
    @SerializedName("group_num")
    private final int groupNum;
    @SerializedName("group_name")
    private final String groupName;
    @SerializedName("profile_id")
    private final int profileId;
    @SerializedName("compute_unit")
    private final ComputeUnit computeUnit;
    @SerializedName("run_id")
    private final int runId;
    @SerializedName("record_audio")
    private final boolean recordAudio;

    @SerializedName("browser")
    private final String browser;
    @SerializedName("network")
    private final Network network;
    @SerializedName("location")
    private final Location location;
    @SerializedName("video_feed")
    private final VideoFeed videoFeed;
    @SerializedName("audio_feed")
    private final AudioFeed audioFeed;

    public ParticipantDetails(
        int id,
        String created,
        String updated,
        int participantNum,
        String participantName,
        int groupNum,
        String groupName,
        int profileId,
        ComputeUnit computeUnit,
        int runId,
        boolean recordAudio,
        String browser,
        Network network,
        Location location,
        VideoFeed videoFeed,
        AudioFeed audioFeed
    ) {
        this.id = id;
        this.created = created;
        this.updated = updated;
        this.participantNum = participantNum;
        this.participantName = participantName;
        this.groupNum = groupNum;
        this.groupName = groupName;
        this.profileId = profileId;
        this.computeUnit = computeUnit;
        this.runId = runId;
        this.recordAudio = recordAudio;
        this.browser = browser;
        this.network = network;
        this.location = location;
        this.videoFeed = videoFeed;
        this.audioFeed = audioFeed;
    }

    public int getId() {
        return id;
    }

    public String getCreated() {
        return created;
    }

    public String getUpdated() {
        return updated;
    }

    public int getParticipantNum() {
        return participantNum;
    }

    public String getParticipantName() {
        return participantName;
    }

    public int getGroupNum() {
        return groupNum;
    }

    public String getGroupName() {
        return groupName;
    }

    public int getProfileId() {
        return profileId;
    }

    public ComputeUnit getComputeUnit() {
        return computeUnit;
    }

    public int getRunId() {
        return runId;
    }

    public boolean isRecordAudio() {
        return recordAudio;
    }

    public Browser getBrowser() {
        return new Browser(browser);
    }

    public Network getNetwork() { return network; }

    public Location getLocation() { return location; }

    public VideoFeed getVideoFeed() { return videoFeed; }

    public AudioFeed getAudioFeed() { return audioFeed; }

    @Override
    public String toString() {
        return "ParticipantDetails{" +
            "id=" + id +
            ", created='" + created + '\'' +
            ", updated='" + updated + '\'' +
            ", participantNum=" + participantNum +
            ", participantName='" + participantName + '\'' +
            ", groupNum=" + groupNum +
            ", groupName='" + groupName + '\'' +
            ", profileId=" + profileId +
            ", computeUnit=" + computeUnit +
            ", runId=" + runId +
            ", browser=" + browser +
            ", network=" + network +
            ", location=" + location +
            ", videoFeed=" + videoFeed +
            ", audioFeed=" + audioFeed +
            '}';
    }
}
