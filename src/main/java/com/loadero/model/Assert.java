package com.loadero.model;

import com.loadero.Loadero;
import com.loadero.http.ApiResource;
import com.loadero.http.RequestMethod;
import com.loadero.types.AssertOperator;
import com.loadero.types.AssertPath;
import java.io.IOException;
import java.util.List;

/**
 * Used for performing CRUD operations on asserts.
 */
public final class Assert {
    private final int id;
    private final String created;
    private final String updated;
    private final int testId;
    private final AssertPath path;
    private final AssertOperator operator;
    private final String expected;
    private final List<Precondition> preconditions;

    public Assert(AssertParams params) {
        this.id = params.getId();
        this.created = params.getCreated();
        this.updated = params.getUpdated();
        this.testId = params.getTestId();
        this.path = params.getPath();
        this.operator = params.getOperator();
        this.expected = params.getExpected();
        this.preconditions = params.getPreconditions();
    }

    /**
     * Retrieves assert.
     *
     * @param testId   ID of the test.
     * @param assertId ID of an assert.
     * @return {@link Assert} object.
     * @throws IOException if request failed.
     */
    public static Assert read(int testId, int assertId) throws IOException {
        String route = buildRoute(testId, assertId);
        return ApiResource.request(RequestMethod.GET, route, null, Assert.class);
    }

    /**
     * Retrieves all existing asserts.
     *
     * @param testId ID of the test.
     * @return List containing {@link Assert}s.
     * @throws IOException if request failed.
     */
    public static List<Assert> readAll(int testId) throws IOException {
        String route = buildRoute(testId);
        return ApiResource.request(RequestMethod.GET, route, null, AssertCollection.class);
    }

    /**
     * Creates assert.
     *
     * @param params Values defined in {@link AssertParams} object.
     * @return {@link Assert} object.
     * @throws IOException if request failed.
     */
    public static Assert create(AssertParams params) throws IOException {
        String route = buildRoute(params.getTestId());
        return ApiResource.request(RequestMethod.POST, route, params, Assert.class);
    }

    /**
     * Modifies existing assert.
     *
     * @param params Values defined in {@link AssertParams} object.
     * @return {@link Assert} object.
     * @throws IOException if request failed.
     */
    public static Assert update(AssertParams params) throws IOException {
        Assert currentAssert = read(params.getTestId(), params.getId());
        AssertParams updatedParams = params.copyUncommonFields(currentAssert);
        String route = buildRoute(params.getTestId(), params.getId());
        return ApiResource.request(RequestMethod.PUT, route, updatedParams, Assert.class);
    }

    /**
     * Deletes assert.
     *
     * @param testId   ID of the test.
     * @param assertId ID of an assert.
     * @throws IOException if request failed.
     */
    public static void delete(int testId, int assertId) throws IOException {
        String route = buildRoute(testId, assertId);
        ApiResource.request(RequestMethod.DELETE, route, null, Assert.class);
    }

    /**
     * Duplicates assert.
     *
     * @param testId ID of the test.
     * @param assertId ID of an assert.
     * @return Copy of {@link Assert} with new ID.
     * @throws IOException if request failed.
     */
    public static Assert copy(int testId, int assertId) throws IOException {
        String route = buildRoute(testId, assertId) + "copy/";
        return ApiResource.request(RequestMethod.POST, route, null, Assert.class);
    }

    private static String buildRoute(int testId) {
        return String.format("%s/tests/%s/asserts/", Loadero.getProjectUrl(), testId);
    }

    private static String buildRoute(int testId, int assertId) {
        return String.format("%s/tests/%s/asserts/%s/", Loadero.getProjectUrl(), testId, assertId);
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

    public int getTestId() {
        return testId;
    }

    public AssertPath getPath() {
        return path;
    }

    public AssertOperator getOperator() {
        return operator;
    }

    public String getExpected() {
        return expected;
    }

    public List<Precondition> getPreconditions() {
        return preconditions;
    }

    @Override
    public String toString() {
        return "Assert{" +
            "id=" + id +
            ", created='" + created + '\'' +
            ", updated='" + updated + '\'' +
            ", testId=" + testId +
            ", path=" + path +
            ", operator=" + operator +
            ", expected='" + expected + '\'' +
            ", preconditions=" + preconditions +
            '}';
    }
}
