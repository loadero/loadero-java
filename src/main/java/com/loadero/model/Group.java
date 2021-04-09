package com.loadero.model;

import com.loadero.Loadero;
import com.loadero.http.ApiResource;
import com.loadero.http.RequestMethod;
import java.io.IOException;

public final class Group {
    private final int id;
    private final int testId;
    private final String name;
    private final int count;
    private final int participantCount;
    private final String created;
    private final String updated;

    public Group(GroupParams params) {
        this.id = params.getId();
        this.testId = params.getTestId();
        this.name = params.getName();
        this.count = params.getCount();
        this.participantCount = params.getParticipantCount();
        this.created = params.getCreated();
        this.updated = params.getUpdated();
    }

    /**
     * Retrieves existing group.
     *
     * @param testId  ID of test containing group.
     * @param groupId ID of the group we want.
     * @return {@link Group} object.
     * @throws IOException if request failed.
     */
    public static Group read(int testId, int groupId) throws IOException {
        String route = buildRoute(testId, groupId);
        return ApiResource.request(RequestMethod.GET, route, null, Group.class);
    }

    /**
     * Creates group.
     *
     * @param groupParams {@link GroupParams} class.
     * @return {@link Group} class.
     * @throws IOException if request failed.
     */
    public static Group create(GroupParams groupParams) throws IOException {
        String route = buildRoute(groupParams.getTestId());
        return ApiResource.request(RequestMethod.POST, route, groupParams, Group.class);
    }

    /**
     * Modifies existing group.
     *
     * @param newParams {@link GroupParams} class with parameters we wish to change.
     * @return {@link Group} class with updated params.
     * @throws IOException if request failed.
     */
    public static Group update(GroupParams newParams) throws IOException {
        Group currentGroup = read(newParams.getTestId(), newParams.getId());
        GroupParams updatedParams = newParams.copyUncommonFields(currentGroup);
        String route = buildRoute(newParams.getTestId(), newParams.getId());
        return ApiResource.request(RequestMethod.PUT, route, updatedParams, Group.class);
    }

    /**
     * Deletes existing group.
     *
     * @param testId  ID of the test.
     * @param groupId ID of the group.
     * @throws IOException if request failed.
     */
    public static void delete(int testId, int groupId) throws IOException {
        String route = buildRoute(testId, groupId);
        ApiResource.request(RequestMethod.DELETE, route, null, null);
    }

    private static String buildRoute(int testId) {
        return String.format("%s/tests/%s/groups/", Loadero.getProjectUrl(), testId);
    }

    private static String buildRoute(int testId, int groupId) {
        return String.format("%s/tests/%s/groups/%s/", Loadero.getProjectUrl(), testId, groupId);
    }

    public int getId() {
        return id;
    }

    public int getTestId() {
        return testId;
    }

    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }

    public int getParticipantCount() {
        return participantCount;
    }

    public String getCreated() {
        return created;
    }

    public String getUpdated() {
        return updated;
    }

    @Override
    public String toString() {
        return "Group{"
            + "id="
            + id
            + ", testId="
            + testId
            + ", name='"
            + name
            + '\''
            + ", count="
            + count
            + ", participantCount="
            + participantCount
            + ", created="
            + created
            + ", updated="
            + updated
            + '}';
    }
}
