package com.loadero.model;

import com.loadero.Loadero;
import com.loadero.http.ApiResource;
import com.loadero.http.RequestMethod;
import com.loadero.types.IncrementStrategy;
import com.loadero.types.TestMode;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

/**
 * Used for performing CRUD operations on tests.
 */
public final class Test implements ModelParams {
    private final int id;
    private final String created;
    private final String updated;
    private final int projectId;
    private final String name;
    private final int scriptFileId;
    private final String script;
    private final Duration startInterval;
    private final Duration participantTimeout;
    private final TestMode mode;
    private final IncrementStrategy incrementStrategy;
    private final int groupCount;
    private final int participantCount;

    public Test(TestParams params) {
        this.id = params.getId();
        this.projectId = params.getProjectId();
        this.name = params.getName();
        this.scriptFileId = params.getScriptFileId();
        this.script = params.getScript();
        this.startInterval = params.getStartInterval();
        this.participantTimeout = params.getParticipantTimeout();
        this.mode = params.getMode();
        this.incrementStrategy = params.getIncrementStrategy();
        this.groupCount = params.getGroupCount();
        this.participantCount = params.getParticipantCount();
        this.created = params.getCreated();
        this.updated = params.getUpdated();
    }

    /**
     * Retrieves test.
     *
     * @param testId ID of the test.
     * @return {@link Test} object.
     * @throws IOException if request failed.
     */
    public static Test read(int testId) throws IOException {
        String route = buildRoute(testId);
        return ApiResource.request(RequestMethod.GET, route, null, Test.class);
    }

    /**
     * Retrieves all existing tests.
     *
     * @return List containing {@link Test}s.
     * @throws IOException if request failed.
     */
    public static List<Test> readAll() throws IOException {
        String route = buildRoute();
        return ApiResource.request(RequestMethod.GET, route, null, TestCollection.class);
    }

    /**
     * Creates new test.
     *
     * @param params Parameters defined in {@link TestParams}.
     * @return Created new {@link Test} object.
     * @throws IOException if request failed.
     */
    public static Test create(TestParams params) throws IOException {
        String route = buildRoute();
        Test newTest = new Test(params);
        return ApiResource.request(RequestMethod.POST, route, newTest, Test.class);
    }

    /**
     * Updates test.
     *
     * @param params Parameters defined in {@link TestParams}.
     * @return {@link Test} object with updated params.
     * @throws IOException if request failed.
     */
    public static Test update(TestParams params) throws IOException {
        Test currentTest = read(params.getId());
        TestParams updateParams = params.copyUncommonFields(currentTest);
        String route = buildRoute(params.getId());
        Test updatedTest = new Test(updateParams);
        return ApiResource.request(RequestMethod.PUT, route, updatedTest, Test.class);
    }

    /**
     * Deletes test.
     *
     * @param testId ID of the test.
     * @throws IOException if request failed.
     */
    public static void delete(int testId) throws IOException {
        String route = buildRoute(testId);
        ApiResource.request(RequestMethod.DELETE, route, null, Test.class);
    }

    /**
     * Launches test script.
     *
     * @return {@link TestRun} object with information about test run.
     * @throws IOException if request failed.
     */
    public static TestRun launch(int testId) throws IOException {
        String route = buildRoute(testId) + "runs/";
        return ApiResource.request(RequestMethod.POST, route, null, TestRun.class);
    }

    /**
     * Duplicates test with new name.
     * @param testId ID of the test.
     * @param name Name of the copy.
     * @return Copy of {@link Test} with new name and ID.
     * @throws IOException if request failed.
     */
    public static Test copy(int testId, String name) throws IOException {
        String route = buildRoute(testId) + "copy/";
        return ApiResource.request(RequestMethod.POST, route, Test.class, name);
    }

    private static String buildRoute() {
        return String.format("%s/tests/", Loadero.getProjectUrl());
    }

    private static String buildRoute(int testId) {
        return String.format("%s/tests/%s/", Loadero.getProjectUrl(), testId);
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

    public int getProjectId() {
        return projectId;
    }

    public String getName() {
        return name;
    }

    public int getScriptFileId() {
        return scriptFileId;
    }

    public String getScript() {
        return script;
    }

    public Duration getStartInterval() {
        return startInterval;
    }

    public Duration getParticipantTimeout() {
        return participantTimeout;
    }

    public TestMode getMode() {
        return mode;
    }

    public IncrementStrategy getIncrementStrategy() {
        return incrementStrategy;
    }

    public int getGroupCount() {
        return groupCount;
    }

    public int getParticipantCount() {
        return participantCount;
    }

    @Override
    public String toString() {
        return "Test{" +
            "id=" + id +
            ", created='" + created + '\'' +
            ", updated='" + updated + '\'' +
            ", projectId=" + projectId +
            ", name='" + name + '\'' +
            ", scriptFileId=" + scriptFileId +
            ", script=" + script +
            ", startInterval=" + startInterval +
            ", participantTimeout=" + participantTimeout +
            ", mode=" + mode +
            ", incrementStrategy=" + incrementStrategy +
            ", groupCount=" + groupCount +
            ", participantCount=" + participantCount +
            '}';
    }
}
