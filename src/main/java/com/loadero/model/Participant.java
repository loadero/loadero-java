package com.loadero.model;

import com.loadero.Loadero;
import com.loadero.http.ApiResource;
import com.loadero.http.RequestMethod;
import com.loadero.types.AudioFeed;
import com.loadero.types.Browser;
import com.loadero.types.BrowserLatest;
import com.loadero.types.ComputeUnit;
import com.loadero.types.Location;
import com.loadero.types.MediaType;
import com.loadero.types.Network;
import com.loadero.types.VideoFeed;
import java.io.IOException;

/**
 * Used for performing CRUD operations on participants.
 */
public final class Participant {
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

    public Participant(ParticipantParams params) {
        this.id = params.getId();
        this.created = params.getCreated();
        this.updated = params.getUpdated();
        this.groupId = params.getGroupId();
        this.testId = params.getTestId();
        this.profileId = params.getProfileId();
        this.name = params.getName();
        this.count = params.getCount();
        this.computeUnit = params.getComputeUnit();
        this.browser = params.getBrowser();
        this.network = params.getNetwork();
        this.location = params.getLocation();
        this.mediaType = params.getMediaType();
        this.recordAudio = params.getRecordAudio();
        this.audioFeed = params.getAudioFeed();
        this.videoFeed = params.getVideoFeed();
    }

    /**
     * Retrieves existing participant.
     *
     * @param testId        ID of the test.
     * @param groupId       ID of the group.
     * @param participantId ID of the participant.
     * @return {@link Participant} object.
     * @throws IOException if request failed.
     */
    public static Participant read(int testId, int groupId, int participantId) throws IOException {
        String route = buildRoute(testId, groupId, participantId);
        return ApiResource.request(RequestMethod.GET, route, null, Participant.class);
    }

    /**
     * Creates new participant.
     *
     * @param params Parameters defined in {@link ParticipantParams}
     * @return {@link Participant} object.
     * @throws IOException if request failed.
     */
    public static Participant create(ParticipantParams params) throws IOException {
        String route = buildRoute(params.getTestId(), params.getGroupId());
        return ApiResource.request(RequestMethod.POST, route, params, Participant.class);
    }

    /**
     * Updates existing participant.
     *
     * @param newParams Parameters defined in {@link ParticipantParams}
     * @return {@link Participant} object.
     * @throws IOException if request failed.
     */
    public static Participant update(ParticipantParams newParams) throws IOException {
        Participant currentPart = read(
            newParams.getTestId(), newParams.getGroupId(), newParams.getId()
        );
        ParticipantParams updatedParams = newParams.copyUncommonFields(currentPart);
        String route = buildRoute(newParams.getTestId(), newParams.getGroupId(), newParams.getId());
        return ApiResource.request(RequestMethod.PUT, route, updatedParams, Participant.class);
    }

    /**
     * Deletes existing participant.
     *
     * @param testId        ID of the test.
     * @param groupId       ID of the group.
     * @param participantId ID of the participant.
     * @throws IOException if request failed.
     */
    public static void delete(int testId, int groupId, int participantId) throws IOException {
        String route = buildRoute(testId, groupId, participantId);
        ApiResource.request(RequestMethod.DELETE, route, null, Participant.class);
    }

    /**
     * Duplicates participant with new name.
     *
     * @param testId ID of the test.
     * @param groupId ID of the group.
     * @param participantId ID of the participant.
     * @param name Name of the copy.
     * @return Copy of {@link Participant} with new name and ID.
     * @throws IOException if request failed.
     */
    public static Participant copy(int testId, int groupId, int participantId, String name)
        throws IOException {
        String route = buildRoute(testId, groupId, participantId) + "copy/";
        return ApiResource.request(RequestMethod.POST, route, Participant.class, name);
    }

    private static String buildRoute(int testId, int groupId) {
        return String
            .format("%s/tests/%s/groups/%s/participants/",
                Loadero.getProjectUrl(), testId, groupId
            );
    }

    private static String buildRoute(int testId, int groupId, int participantId) {
        return String
            .format("%s/tests/%s/groups/%s/participants/%s/",
                Loadero.getProjectUrl(), testId, groupId, participantId
            );
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
        return "Participant{" +
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
