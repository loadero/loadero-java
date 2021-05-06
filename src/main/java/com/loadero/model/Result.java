package com.loadero.model;

import com.google.gson.annotations.SerializedName;
import com.loadero.Loadero;
import com.loadero.http.ApiResource;
import com.loadero.http.RequestMethod;
import com.loadero.types.ResultStatus;
import java.io.IOException;
import java.util.List;

/**
 * Class for reading the results of a test run.
 */
public final class Result {
    private final int id;
    private final String created;
    private final String updated;
    private final String start;
    private final String end;
    private final ResultStatus status;
    @SerializedName("selenium_result")
    private final ResultStatus seleniumResult;
    @SerializedName("run_participant_id")
    private final int runParticipantId;
    @SerializedName("participant_details")
    private final ParticipantDetails participantDetails;
    @SerializedName("profile_params")
    private final ProfileParams profileParams;
    @SerializedName("log_paths")
    private final LogPaths logPaths;
    private final List<ResultAssert> asserts;
    private final Artifacts artifacts;

    public Result(
        int id,
        String created,
        String updated,
        String start,
        String end,
        ResultStatus status,
        ResultStatus seleniumResult,
        int runParticipantId,
        ParticipantDetails participantDetails,
        ProfileParams profileParams,
        LogPaths logPaths,
        List<ResultAssert> asserts,
        Artifacts artifacts
    ) {
        this.id = id;
        this.created = created;
        this.updated = updated;
        this.start = start;
        this.end = end;
        this.status = status;
        this.seleniumResult = seleniumResult;
        this.runParticipantId = runParticipantId;
        this.participantDetails = participantDetails;
        this.profileParams = profileParams;
        this.logPaths = logPaths;
        this.asserts = asserts;
        this.artifacts = artifacts;
    }

    /**
     * Retrieves result of a single test run.
     *
     * @param testId   ID of the test.
     * @param runId    ID of the test run
     * @param resultId ID of the result
     * @return {@link Result} object.
     * @throws IOException if request failed.
     */
    public static Result read(int testId, int runId, int resultId) throws IOException {
        String route = buildRoute(testId, runId, resultId);
        return ApiResource.request(RequestMethod.GET, route, null, Result.class);
    }

    private static String buildRoute(int testId, int runId, int resultId) {
        return String.format(
            "%s/tests/%s/runs/%s/results/%s/",
            Loadero.getProjectUrl(), testId, runId, resultId
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

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

    public ResultStatus getStatus() {
        return status;
    }

    public ResultStatus getSeleniumResult() {
        return seleniumResult;
    }

    public int getRunParticipantId() {
        return runParticipantId;
    }

    public ParticipantDetails getParticipantDetails() {
        return participantDetails;
    }

    public ProfileParams getProfileParams() {
        return profileParams;
    }

    public LogPaths getLogPaths() {
        return logPaths;
    }

    public List<ResultAssert> getAsserts() {
        return asserts;
    }

    public Artifacts getArtifacts() {
        return artifacts;
    }

    @Override
    public String toString() {
        return "Result{" +
            "id=" + id +
            ", created='" + created + '\'' +
            ", updated='" + updated + '\'' +
            ", start='" + start + '\'' +
            ", end='" + end + '\'' +
            ", status=" + status +
            ", seleniumResult=" + seleniumResult +
            ", runParticipantId=" + runParticipantId +
            ", participantDetails=" + participantDetails +
            ", profileParams=" + profileParams +
            ", logPaths=" + logPaths +
            ", asserts=" + asserts +
            ", artifacts=" + artifacts +
            '}';
    }
}
