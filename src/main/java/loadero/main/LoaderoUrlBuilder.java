package loadero.main;

/**
 * This class is responsible for building url endpoints for Loadero API.
 */
final class LoaderoUrlBuilder {
    private final String baseUrl;
    private final int projectId;

    public LoaderoUrlBuilder(String baseUrl, int projectId) {
        this.baseUrl = baseUrl;
        this.projectId = projectId;
    }

    // Public for testing purposes. May make it private later
    public String buildTestURLById(int testId) {
        return String.format(
                "%s/projects/%s/tests/%d",
                baseUrl,
                projectId,
                testId);
    }

    /**
     * Builds URL for Loadero groups based on given ID.
     * @param testId  - ID of the test.
     * @param groupId - ID of the desired group
     * @return        - String of url pointing to group.
     */
    public String buildGroupURL(int testId, int groupId) {
        String testUrl = buildTestURLById(testId);
        return String.format("%s/groups/%d", testUrl, groupId);
    }

    /**
     * Builds URL to for specific participant of specific group.
     * @param testId        - ID of the test.
     * @param groupId       - ID of the group that contains participant.
     * @param participantId - ID of desired participant.
     * @return              - String url pointing to participant.
     */
    public String buildParticipantURL(int testId, int groupId, int participantId) {
        return String.format("%s/participants/%d",
                buildGroupURL(testId, groupId),
                participantId);
    }

    public String buildScriptFileURL(int fileId) {
        return String.format("%s/projects/%d/files/%d",
                baseUrl,
                projectId,
                fileId );
    }

    public String buildRunResultsURL(int testId, int runId) {
        return String.format("%s/runs/%d/results",
                buildTestURLById(testId),
                runId);
    }

    public String buildProjectURL() {
        return String.format("%s/projects/%d", baseUrl, projectId);
    }
}