package loadero.utils;

public class LoaderoUrlBuilder {
    private final String baseUrl;
    private final String projectId;

    public LoaderoUrlBuilder(String baseUrl, String projectId) {
        this.baseUrl = baseUrl;
        this.projectId = projectId;
    }

    // Public for testing purposes. May make it private later
    public String buildTestURLById(String testId) {
        return String.format(
                "%s/projects/%s/tests/%s",
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
    public String buildGroupURL(String testId, String groupId) {
        String testUrl = buildTestURLById(testId);
        return String.format("%s/groups/%s", testUrl, groupId);
    }

    /**
     * Builds URL to for specific participant of specific group.
     * @param testId        - ID of the test.
     * @param groupId       - ID of the group that contains participant.
     * @param participantId - ID of desired participant.
     * @return              - String url pointing to participant.
     */
    public String buildParticipantURL(String testId, String groupId,
                                      String participantId) {
        return String.format("%s/participants/%s",
                buildGroupURL(testId, groupId),
                participantId);
    }

    public String buildScriptFileURL(String fileId) {
        return String.format("%s/projects/%s/files/%s",
                baseUrl,
                projectId,
                fileId );
    }

    public String buildRunResultsURL(String testId, String runId) {
        return String.format("%s/runs/%s/results",
                buildTestURLById(testId),
                runId);
    }

    public String buildProjectURL() {
        return String.format("%s/projects/%s", baseUrl, projectId);
    }
}