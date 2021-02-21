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

//
//import org.apache.http.client.utils.URIBuilder;
//
//public class LoaderoUrlBuilder {
//    private final String baseBath;
//    private final String projectId;
//    private final String testId;
//    private final String groupId;
//    private final String participantId;
//    private final String runId;
//    private final String resultId;
//
//    public static class Builder {
//        private final String basePath;
//        private final String projectId;
//
//        private String testId = "";
//        private String groupId = "";
//        private String participantId = "";
//        private String runId = "";
//        private String resultId = "";
//
//        public Builder(String basePath, String projectId) {
//            this.basePath = basePath;
//            this.projectId = projectId;
//        }
//
//        public Builder testId(String id) {
//            this.testId = id;
//            return this;
//        }
//
//        public Builder groupId(String id) {
//            this.groupId = id;
//            return this;
//        }
//
//        public Builder participantId(String id) {
//            this.participantId = id;
//            return this;
//        }
//
//        public Builder runId(String id) {
//            this.runId = id;
//            return this;
//        }
//
//        public Builder resultId(String id) {
//            this.resultId = id;
//            return this;
//        }
//
//        public LoaderoUrlBuilder build() {
//            return new LoaderoUrlBuilder(this);
//        }
//    }
//
//    private LoaderoUrlBuilder(Builder builder) {
////        URIBuilder
//        baseBath = builder.basePath;
//        projectId = builder.projectId;
//        testId = builder.testId;
//        groupId = builder.groupId;
//        participantId = builder.participantId;
//        runId = builder.runId;
//        resultId = builder.resultId;
//    }
//
//    private String buildUrl() {
//        StringBuilder builder = new StringBuilder();
//        builder
//                .append(baseBath)
//                .append("/projects/")
//                .append(projectId);
//        return builder.toString();
//    }
//}
