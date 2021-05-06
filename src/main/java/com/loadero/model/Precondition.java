package com.loadero.model;

import com.loadero.Loadero;
import com.loadero.http.ApiResource;
import com.loadero.http.RequestMethod;
import com.loadero.types.AssertOperator;
import com.loadero.types.Property;
import java.io.IOException;

/**
 * Used to perform CRUD operations on assert's preconditions.
 */
public final class Precondition {
    private final int id;
    private final String created;
    private final String updated;
    private final Property property;
    private final AssertOperator operator;
    private final String expected;
    private final int assertId;

    public Precondition(PreconditionParams params) {
        this.id = params.getId();
        this.updated = params.getUpdated();
        this.created = params.getCreated();
        this.property = params.getProperty();
        this.operator = params.getOperator();
        this.expected = params.getExpected();
        this.assertId = params.getAssertId();
    }

    /**
     * Retrieves precondition.
     *
     * @param testId         ID of the test.
     * @param assertId       ID of an assert.
     * @param preconditionId ID of the precondition.
     * @return {@link Precondition} object.
     * @throws IOException if request failed.
     */
    public static Precondition read(
        int testId, int assertId, int preconditionId
    ) throws IOException {
        String route = buildRoute(testId, assertId, preconditionId);
        return ApiResource.request(RequestMethod.GET, route, null, Precondition.class);
    }

    /**
     * Creates precondition for an assert.
     *
     * @param params Values defined in the {@link PreconditionParams} object.
     * @return Created {@link Precondition} object.
     * @throws IOException if request failed.
     */
    public static Precondition create(PreconditionParams params) throws IOException {
        String route = buildRoute(params.getTestId(), params.getAssertId());
        return ApiResource.request(RequestMethod.POST, route, params, Precondition.class);
    }

    /**
     * Modifies existing precondition.
     *
     * @param params Values defined in {@link PreconditionParams} object.
     * @return Modified {@link Precondition} object.
     * @throws IOException if request failed.
     */
    public static Precondition update(PreconditionParams params) throws IOException {
        Precondition currentPrecondition = read(
            params.getTestId(), params.getAssertId(), params.getId()
        );
        PreconditionParams updatedParams = params.copyUncommonFields(currentPrecondition);
        String route = buildRoute(params.getTestId(), params.getAssertId(), params.getId());
        return ApiResource.request(RequestMethod.PUT, route, updatedParams, Precondition.class);
    }

    /**
     * Deletes precondition.
     * @param testId        ID of the test.
     * @param assertId      ID of an asserts.
     * @param preconditionId ID of the precondition.
     * @throws IOException  if request failed.
     */
    public static void delete(int testId, int assertId, int preconditionId) throws IOException {
        String route = buildRoute(testId, assertId, preconditionId);
        ApiResource.request(RequestMethod.DELETE, route, null, Precondition.class);
    }

    private static String buildRoute(int testId, int assertId) {
        return String
            .format("%s/tests/%s/asserts/%s/preconditions/",
                Loadero.getProjectUrl(), testId, assertId
            );
    }

    private static String buildRoute(int testId, int assertId, int preconditionId) {
        return String
            .format("%s/tests/%s/asserts/%s/preconditions/%s/",
                Loadero.getProjectUrl(), testId, assertId, preconditionId
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

    public Property getProperty() {
        return property;
    }

    public AssertOperator getOperator() {
        return operator;
    }

    public String getExpected() {
        return expected;
    }

    public int getAssertId() {
        return assertId;
    }

    @Override
    public String toString() {
        return "Precondition{" +
            "id=" + id +
            ", created='" + created + '\'' +
            ", updated='" + updated + '\'' +
            ", property=" + property +
            ", operator=" + operator +
            ", expected='" + expected + '\'' +
            ", assertId=" + assertId +
            '}';
    }
}
