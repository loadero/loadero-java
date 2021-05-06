package com.loadero.model;

import com.loadero.Loadero;
import com.loadero.exceptions.ApiException;
import com.loadero.exceptions.ApiPollingException;
import com.loadero.http.ApiResource;
import com.loadero.http.RequestMethod;
import com.loadero.types.IncrementStrategy;
import com.loadero.types.RunStatus;
import com.loadero.types.TestMode;
import java.io.IOException;
import java.time.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Used to read information about test runs, perform polling and
 * call force stop for specific test when needed.
 */
public final class TestRun {
    private final int id;
    private final String created;
    private final String updated;
    private final String processingStarted;
    private final String processingFinished;
    private final String executionStarted;
    private final String executionFinished;
    private final int testId;
    private final RunStatus status;
    private final TestMode testMode;
    private final IncrementStrategy incrementStrategy;
    private final int scriptFileId;
    private final String testName;
    private final Duration startInterval;
    private final Duration participantTimeout;
    private final int launchAccountId;
    private final double successRate;
    private final int groupCount;
    private final int participantCount;

    public TestRun(
        int id,
        String created,
        String updated,
        String processingStarted,
        String processingFinished,
        String executionStarted,
        String executionFinished,
        int testId,
        RunStatus status,
        TestMode testMode,
        IncrementStrategy incrementStrategy,
        int scriptFileId,
        String testName,
        Duration startInterval,
        Duration participantTimeout,
        int launchAccountId,
        double successRate,
        int groupCount,
        int participantCount
    ) {
        this.id = id;
        this.created = created;
        this.updated = updated;
        this.processingStarted = processingStarted;
        this.processingFinished = processingFinished;
        this.executionStarted = executionStarted;
        this.executionFinished = executionFinished;
        this.testId = testId;
        this.status = status;
        this.testMode = testMode;
        this.incrementStrategy = incrementStrategy;
        this.scriptFileId = scriptFileId;
        this.testName = testName;
        this.startInterval = startInterval;
        this.participantTimeout = participantTimeout;
        this.launchAccountId = launchAccountId;
        this.successRate = successRate;
        this.groupCount = groupCount;
        this.participantCount = participantCount;
    }

    /**
     * Read information about test run.
     *
     * @param testId ID of the test.
     * @param runId  ID of the test run.
     * @return {@link TestRun} object.
     * @throws IOException if request failed.
     */
    public static TestRun read(int testId, int runId) throws IOException {
        String route = buildRoute(testId, runId);
        return ApiResource.request(RequestMethod.GET, route, null, TestRun.class);
    }

    /**
     * Stops running test.
     *
     * @param testId ID of the test.
     * @param runId  ID of the test run.
     * @throws IOException if request failed.
     */
    public static void stop(int testId, int runId) throws IOException {
        String route = buildRoute(testId, runId) + "stop/";
        ApiResource.request(RequestMethod.POST, route, null, TestRun.class);
    }

    /**
     * Launches polling to track test run progress for indefinite period of time. Can be useful,
     * when we don't know how much time will it take to run specific test. Returns {@link TestRun}
     * object when test is done. Stops the test run if Loadero returned error during polling.
     *
     * @param testId   ID of the test.
     * @param runId    ID of the test run.
     * @param interval How often poll for information.
     * @return {@link TestRun} object.
     * @throws IOException  if request failed.
     * @throws ApiPollingException if timeout have been exceeded.
     * @throws ApiException if something unexpected happened.
     */
    public static TestRun poll(int testId, int runId, Duration interval)
        throws IOException, ApiPollingException, ApiException {
        return poll(testId, runId, interval, Duration.ofHours(12));
    }

    /**
     * Launches polling to track test run's changes for defined timeout of time. Returns {@link
     * TestRun} object if test run is done. Stops the test run if Loadero returned error during
     * polling.
     *
     * @param testId   ID of the test.
     * @param runId    ID of the test run.
     * @param interval How often should we poll for information.
     * @param timeout  How long polling should last.
     * @return {@link TestRun} object.
     * @throws IOException if request failed.
     * @throws ApiPollingException if timeout have been exceeded.
     * @throws ApiException        if something unexpected happened.
     */
    public static TestRun poll(int testId, int runId, Duration interval, Duration timeout)
        throws IOException, ApiPollingException, ApiException {
        String route = buildRoute(testId, runId);
        Logger log = LogManager.getLogger(TestRun.class);

        TestRun result = null;
        long start = System.currentTimeMillis();
        boolean done = false;
        boolean halt;

        while (!done) {
            halt = (System.currentTimeMillis() - start >= timeout.toMillis());

            if (halt) {
                throw new ApiPollingException("Exceeded timeout.");
            }

            try {
                result = read(testId, runId);
                if (result.getStatus() == RunStatus.DONE) {
                    done = true;
                    log.info("Test run is done.");
                    log.info("Results available at {}/results", route);
                } else {
                    log.info("Run ID: {} - Test run status: {}",
                        result.getId(), result.getStatus()
                    );
                    Thread.sleep(interval.toMillis());
                }
            } catch (InterruptedException | ApiException ex) {
                throw new ApiException(ex.getLocalizedMessage());
            }
        }

        return result;
    }

    private static String buildRoute(int testId, int runId) {
        return String.format("%s/tests/%s/runs/%s", Loadero.getProjectUrl(), testId, runId);
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

    public String getProcessingStarted() {
        return processingStarted;
    }

    public String getProcessingFinished() {
        return processingFinished;
    }

    public String getExecutionStarted() {
        return executionStarted;
    }

    public String getExecutionFinished() {
        return executionFinished;
    }

    public int getTestId() {
        return testId;
    }

    public RunStatus getStatus() {
        return status;
    }

    public TestMode getTestMode() {
        return testMode;
    }

    public IncrementStrategy getIncrementStrategy() {
        return incrementStrategy;
    }

    public int getScriptFileId() {
        return scriptFileId;
    }

    public String getTestName() {
        return testName;
    }

    public Duration getStartInterval() {
        return startInterval;
    }

    public Duration getParticipantTimeout() {
        return participantTimeout;
    }

    public int getLaunchAccountId() {
        return launchAccountId;
    }

    public double getSuccessRate() {
        return successRate;
    }

    public int getGroupCount() {
        return groupCount;
    }

    public int getParticipantCount() {
        return participantCount;
    }

    @Override
    public String toString() {
        return "TestRun{" +
            "id=" + id +
            ", created='" + created + '\'' +
            ", updated='" + updated + '\'' +
            ", processingStarted='" + processingStarted + '\'' +
            ", processingFinished='" + processingFinished + '\'' +
            ", executionStarted='" + executionStarted + '\'' +
            ", executionFinished='" + executionFinished + '\'' +
            ", testId=" + testId +
            ", status='" + status + '\'' +
            ", testMode=" + testMode +
            ", incrementStrategy=" + incrementStrategy +
            ", scriptFileId=" + scriptFileId +
            ", testName='" + testName + '\'' +
            ", startInterval=" + startInterval +
            ", participantTimeout=" + participantTimeout +
            ", launchAccountId=" + launchAccountId +
            ", successRate=" + successRate +
            ", groupCount=" + groupCount +
            ", participantCount=" + participantCount +
            '}';
    }
}
