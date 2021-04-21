package com.loadero.model;

import com.loadero.types.AudioFeed;
import com.loadero.types.Browser;
import com.loadero.types.ComputeUnit;
import com.loadero.types.Location;
import com.loadero.types.MediaType;
import com.loadero.types.Network;
import com.loadero.types.VideoFeed;
import com.loadero.util.StringUtil;

public final class ParticipantParams implements ModelParams {
    private final int id;
    private final String created;
    private final String updated;
    private final int groupId;
    private final int testId;
    private final int profileId;
    private final String name;
    private final int count;
    private final ComputeUnit computeUnit;
    private final Browser browser;
    private final Network network;
    private final Location location;
    private final MediaType mediaType;
    private final boolean recordAudio;
    private final AudioFeed audioFeed;
    private final VideoFeed videoFeed;

    private ParticipantParams(ParticipantParamsBuilder builder) {
        this.id = builder.id;
        this.created = builder.created;
        this.updated = builder.updated;
        this.groupId = builder.groupId;
        this.testId = builder.testId;
        this.profileId = builder.profileId;
        this.name = builder.name;
        this.count = builder.count;
        this.computeUnit = builder.computeUnit;
        this.browser = builder.browser;
        this.network = builder.network;
        this.location = builder.location;
        this.mediaType = builder.mediaType;
        this.recordAudio = builder.recordAudio;
        this.audioFeed = builder.audioFeed;
        this.videoFeed = builder.videoFeed;
    }

    private ParticipantParams(
        int id, String created, String updated,
        int groupId, int testId, int profileId, String name, int count,
        ComputeUnit cu, Browser browser, Network network,
        Location location, MediaType mediaType, boolean recordAudio,
        AudioFeed audioFeed, VideoFeed videoFeed
    ) {
        this.id = id;
        this.created = created;
        this.updated = updated;
        this.groupId = groupId;
        this.testId = testId;
        this.profileId = profileId;
        this.name = name;
        this.count = count;
        this.computeUnit = cu;
        this.browser = browser;
        this.network = network;
        this.location = location;
        this.mediaType = mediaType;
        this.recordAudio = recordAudio;
        this.audioFeed = audioFeed;
        this.videoFeed = videoFeed;
    }

    /**
     * Returns {@link ParticipantParamsBuilder} instance.
     *
     * @return {@link ParticipantParamsBuilder}
     */
    public static ParticipantParamsBuilder builder() {
        return new ParticipantParamsBuilder();
    }

    // Can change: name, count, location, network, browser, record_audio, computeUnit
    // media_type
    ParticipantParams copyUncommonFields(Participant currentParams) {
        String name =
            this.name == null
                ? currentParams.getName()
                : this.name.equals(currentParams.getName()) ? currentParams.getName() : this.name;
        int count =
            this.count == 0
                ? currentParams.getCount()
                : this.count == currentParams.getCount() ? currentParams.getCount() : this.count;
        Location location =
            this.location == null
                ? currentParams.getLocation()
                : this.location == currentParams.getLocation()
                    ? currentParams.getLocation() : this.location;
        Network network =
            this.network == null
                ? currentParams.getNetwork()
                : this.network == currentParams.getNetwork()
                    ? currentParams.getNetwork() : this.network;
        Browser browser =
            this.browser == null
                ? currentParams.getBrowser()
                : this.browser.getBrowser().equals(currentParams.getBrowser().getBrowser())
                    ? currentParams.getBrowser() : this.browser;
        boolean recordAudio =
            this.recordAudio == currentParams.getRecordAudio()
                ? currentParams.getRecordAudio() : this.recordAudio;
        ComputeUnit cu =
            this.computeUnit == null
                ? currentParams.getComputeUnit()
                : this.computeUnit == currentParams.getComputeUnit()
                    ? currentParams.getComputeUnit() : this.computeUnit;
        MediaType mediaType =
            this.mediaType == null
                ? currentParams.getMediaType()
                : this.mediaType == currentParams.getMediaType()
                    ? currentParams.getMediaType() : this.mediaType;
        return new ParticipantParams(
            id, created, updated,
            testId, groupId, profileId,
            name, count, cu, browser, network,
            location, mediaType, recordAudio,
            audioFeed, videoFeed
        );
    }

    public static final class ParticipantParamsBuilder {
        private int id;
        private String created;
        private String updated;
        private int groupId;
        private int testId;
        private int profileId;
        private String name;
        private int count;
        private ComputeUnit computeUnit;
        private Browser browser;
        private Network network;
        private Location location;
        private MediaType mediaType;
        private boolean recordAudio;
        private AudioFeed audioFeed;
        private VideoFeed videoFeed;

        private ParticipantParamsBuilder() {
        }

        /**
         * Sets participant's id.
         *
         * @param id Integer value.
         * @return {@link ParticipantParamsBuilder}
         */
        public ParticipantParamsBuilder withId(int id) {
            this.id = id;
            return this;
        }

        /**
         * Sets time when object was created. Used for deserialization only.
         */
        public ParticipantParamsBuilder withCreated(String created) {
            this.created = created;
            return this;
        }

        /**
         * Sets time when object was updated. Used for deserialization only.
         */
        public ParticipantParamsBuilder withUpdated(String updated) {
            this.updated = updated;
            return this;
        }

        /**
         * Sets group's id.
         *
         * @param groupId Integer value.
         * @return {@link ParticipantParamsBuilder}
         */
        public ParticipantParamsBuilder withGroupId(int groupId) {
            this.groupId = groupId;
            return this;
        }

        /**
         * Sets test's id.
         *
         * @param testId Integer value.
         * @return {@link ParticipantParamsBuilder}
         */
        public ParticipantParamsBuilder withTestId(int testId) {
            this.testId = testId;
            return this;
        }

        /**
         * Sets profile id.
         *
         * @param profileId Integer value.
         * @return {@link ParticipantParamsBuilder}
         */
        public ParticipantParamsBuilder withProfileId(int profileId) {
            this.profileId = profileId;
            return this;
        }

        /**
         * Sets participant's name.
         *
         * @param name String value.
         * @return {@link ParticipantParamsBuilder}
         * @throws IllegalArgumentException if name is null or empty.
         */
        public ParticipantParamsBuilder withName(String name) {
            if (StringUtil.empty(name)) {
                throw new IllegalArgumentException("Name cannot be empty");
            }
            this.name = name;
            return this;
        }

        /**
         * Sets participant's count.
         *
         * @param count Integer value.
         * @return {@link ParticipantParamsBuilder}
         */
        public ParticipantParamsBuilder withCount(int count) {
            this.count = count;
            return this;
        }

        /**
         * Sets compute unit.
         *
         * @param computeUnit Value of {@link ComputeUnit}
         * @return {@link ParticipantParamsBuilder}
         */
        public ParticipantParamsBuilder withComputeUnit(ComputeUnit computeUnit) {
            this.computeUnit = computeUnit;
            return this;
        }

        /**
         * Sets browser.
         *
         * @param browser Value of {@link Browser}.
         * @return {@link ParticipantParamsBuilder}
         */
        public ParticipantParamsBuilder withBrowser(Browser browser) {
            this.browser = browser;
            return this;
        }

        /**
         * Sets network.
         *
         * @param network Value of {@link Network}.
         * @return {@link ParticipantParamsBuilder}
         */
        public ParticipantParamsBuilder withNetwork(Network network) {
            this.network = network;
            return this;
        }

        /**
         * Sets location.
         *
         * @param location Value of {@link Location}.
         * @return {@link ParticipantParamsBuilder}
         */
        public ParticipantParamsBuilder withLocation(Location location) {
            this.location = location;
            return this;
        }

        /**
         * Sets media type.
         *
         * @param mediaType Value of {@link MediaType}.
         * @return {@link ParticipantParamsBuilder}
         */
        public ParticipantParamsBuilder withMediaType(MediaType mediaType) {
            this.mediaType = mediaType;
            return this;
        }

        /**
         * Sets whether or not record audio.
         *
         * @param recordAudio True/False value.
         * @return {@link ParticipantParamsBuilder}
         */
        public ParticipantParamsBuilder withRecordAudio(boolean recordAudio) {
            this.recordAudio = recordAudio;
            return this;
        }

        /**
         * Sets audio feed for a participant.
         *
         * @param audioFeed Value of {@link AudioFeed}.
         * @return {@link ParticipantParamsBuilder}
         */
        public ParticipantParamsBuilder withAudioFeed(AudioFeed audioFeed) {
            this.audioFeed = audioFeed;
            return this;
        }

        /**
         * Sets video feed for a participant.
         *
         * @param videoFeed Value of {@link VideoFeed}.
         * @return {@link ParticipantParamsBuilder}
         */
        public ParticipantParamsBuilder withVideoFeed(VideoFeed videoFeed) {
            this.videoFeed = videoFeed;
            return this;
        }

        /**
         * Builds {@link ParticipantParams} object.
         *
         * @return Built {@link ParticipantParams} object.
         */
        public ParticipantParams build() {
            return new ParticipantParams(this);
        }
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

    public int getGroupId() {
        return groupId;
    }

    public int getTestId() {
        return testId;
    }

    public int getProfileId() {
        return profileId;
    }

    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }

    public ComputeUnit getComputeUnit() {
        return computeUnit;
    }

    public Browser getBrowser() {
        return browser;
    }

    public Network getNetwork() {
        return network;
    }

    public Location getLocation() {
        return location;
    }

    public MediaType getMediaType() {
        return mediaType;
    }

    public boolean getRecordAudio() {
        return recordAudio;
    }

    public AudioFeed getAudioFeed() {
        return audioFeed;
    }

    public VideoFeed getVideoFeed() {
        return videoFeed;
    }

    @Override
    public String toString() {
        return "ParticipantParams{" +
            "id=" + id +
            ", created='" + created + '\'' +
            ", updated='" + updated + '\'' +
            ", groupId=" + groupId +
            ", testId=" + testId +
            ", profileId=" + profileId +
            ", name='" + name + '\'' +
            ", count=" + count +
            ", computeUnit=" + computeUnit +
            ", browser=" + browser +
            ", network=" + network +
            ", location=" + location +
            ", mediaType=" + mediaType +
            ", recordAudio=" + recordAudio +
            ", audioFeed=" + audioFeed +
            ", videoFeed=" + videoFeed +
            '}';
    }
}
