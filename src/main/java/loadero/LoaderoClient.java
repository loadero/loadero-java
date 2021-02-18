package loadero;

import loadero.controller.LoaderoPollController;
import loadero.controller.LoaderoRestController;
import loadero.model.*;
import loadero.utils.LoaderoClientUtils;
import lombok.Getter;

import java.util.Objects;


// TODO: abstract heavy logic into service
@Getter
public class LoaderoClient {
    private final String baseUrl;
    private final String projectId;
    private final String loaderoApiToken;
    private final LoaderoRestController restController;
    private final LoaderoPollController pollController;

    public LoaderoClient(String baseUrl, String loaderApiToken,
                         String projectId) {
        this.baseUrl = baseUrl;
        this.projectId = projectId;
        this.loaderoApiToken = loaderApiToken;
        restController = new LoaderoRestController(loaderApiToken);
        pollController = new LoaderoPollController(loaderApiToken);
    }

    /**
     * Returns information about test as LoaderoTestOptions.
     * @param testId - ID of desired test.
     * @return       - LoaderoTestOptions object.
     */
    public LoaderoTestOptions getTestOptionsById(String testId) {
        String testUrl = buildTestURLById(testId) + "/";
        return (LoaderoTestOptions) restController.get(testUrl,
                LoaderoType.LOADERO_TEST_OPTIONS);
    }

    /**
     * Updates the existing Loadero test description based on the
     * parameters provided in new LoaderoTestOptions() object.
     * @param testId         - ID of test to be updated.
     * @param newTestOptions - new LoaderoTestOptions object with parameters that you wish to change
     * @return               - Updated LoaderoTestOptions
     */
    public LoaderoTestOptions updateTestOptions(String testId,
                                                LoaderoTestOptions newTestOptions) {
        String testUrl = buildTestURLById(testId) + "/";
        LoaderoTestOptions currentOptions = getTestOptionsById(testId);

        // If new script is not provided
        // We get the old script from Loadero API endpoint /files/fileId
        // And update accordingly.
        if (Objects.equals(newTestOptions.getScript(), "")) {
            String scriptContent = getTestScript(
                    String.valueOf(currentOptions.getScriptFileId()));
            currentOptions.setScript(scriptContent);
        }

        LoaderoModel updatedOptions = LoaderoClientUtils.copyUncommonFields(
                currentOptions,
                newTestOptions,
                LoaderoType.LOADERO_TEST_OPTIONS);

        return (LoaderoTestOptions) restController.update(testUrl,
                LoaderoType.LOADERO_TEST_OPTIONS, updatedOptions);
    }

    /**
     * Retrieves content of the script file from Loadero API
     * @param fileId - ID of the script file
     * @return       - String containing script content.
     */
    public String getTestScript(String fileId) {
        String scriptFileid = String.valueOf(fileId);
        String scriptFileUrl = buildScriptFileURL(scriptFileid) + "/";
        LoaderoScriptFileLoc scriptFile = (LoaderoScriptFileLoc) restController.get(
                    scriptFileUrl,
                    LoaderoType.LOADERO_SCRIPT_FILE_LOC);
        return scriptFile.getContent();
    }

    /**
     * Returns group as LoaderoGroup object from Loadero with specified ID.
     * @param testId - ID of the test.
     * @param groupId - ID of the group.
     * @return   - LoaderoGroup object.
     */
    public LoaderoModel getGroupById(String testId, String groupId) {
        String groupUrl = buildGroupURL(testId, groupId) + "/";
        return restController.get(groupUrl,
                LoaderoType.LOADERO_GROUP);
    }

    /**
     * Returns participant's information from Loadero as LoaderoParticipant object.
     * @param testId        - ID of the test that contains participant.
     * @param participantId - desired participant.
     * @return              - LoaderoParticipant object
     */
    public LoaderoParticipant getParticipantById(String testId, String groupId,
                                                 String participantId) {
        String particUrl = buildParticipantURL(testId, groupId, participantId) + "/";
        return (LoaderoParticipant) restController.get(
                particUrl, LoaderoType.LOADERO_PARTICIPANT
        );
    }

    /**
     * Updates Loadero Participant by it's ID.
     * @param testId              - ID of the test that contains participant.
     * @param groupId             - ID of the group that contains participant.
     * @param participantId       - ID of desired participant.
     * @param newParticipant      - LoaderoParticipant object with new params.
     * @return LoaderoParticipant - updated LoaderoParticipant object.
     */
    public LoaderoParticipant updateTestParticipantById(String testId,
                                                        String groupId,
                                                        String participantId,
                                                        LoaderoParticipant newParticipant) {
        String participnatUrl = buildParticipantURL(testId, groupId, participantId) + "/";
        LoaderoParticipant currentParticInfo = getParticipantById(testId, groupId, participantId);

        LoaderoParticipant updatedParticipant = (LoaderoParticipant) LoaderoClientUtils
                .copyUncommonFields(
                currentParticInfo,
                newParticipant,
                LoaderoType.LOADERO_PARTICIPANT);

        return (LoaderoParticipant) restController
                .update(participnatUrl, LoaderoType.LOADERO_PARTICIPANT, updatedParticipant);
    }

    /**
     * Gets information about all test run results from Loadero API.
     * @param testId - ID of the desired test to get results from.
     * @param runId  - ID of the test run.
     * @return       - LoaderoAllTestRunResults object, that contains list of LoaderoSingleTestRunResult objects.
     */
    public LoaderoTestRunResult getTestRunResult(String testId, String runId) {
        String resultsUrl = buildRunResultsURL(testId, runId);
        return (LoaderoTestRunResult) restController.get(resultsUrl,
                LoaderoType.LOADERO_RUN_RESULT);
    }

    /**
     * Gets information about specific results.
     * @param testId    - ID of the test we wish to get results.
     * @param runId     - ID of the test run.
     * @param resultId  - ID of the result.
     * @return          - LoaderoSingleTestRunResult object containing information such as ID, status,
     *                    selenium_status, log paths, asserts and artifacts.
     */
    public LoaderoTestRunParticipantResult getTestRunParticipantResult(String testId, String runId, String resultId) {
        String resultsUrl = buildRunResultsURL(testId, runId) + resultId + "/";
        return (LoaderoTestRunParticipantResult) restController.get(resultsUrl,
                LoaderoType.LOADERO_TEST_RUN_PARTICIPANT_RESULT);
    }


    /**
     * Start test run by sending POST command underneath to /runs url.
     * After which starts activly polling for information about test run.
     * Returns run info when test is done or time of the polling run out.
     * @param testId   - ID of the test that is going to run.
     * @param interval - how often check for information. In seconds.
     * @param timeout  - how long should polling for information. In seconds.
     * @return         - LoaderoRunInfo containing information about test run.
     */
    public LoaderoRunInfo startTestAndPollInfo(String testId, int interval, int timeout) {
        String startRunsUrl = buildTestURLById(testId) + "/runs/";
        return pollController
                .startTestAndPoll(startRunsUrl, interval, timeout);
    }

    // Public for testing purposes. May make it private later
    public String buildTestURLById(String testId) {
        return baseUrl
                + "/projects/"
                + projectId
                + "/tests/"
                + testId;
    }

    /**
     * Builds URL for Loadero groups based on given ID.
     * @param testId  - ID of the test.
     * @param groupId - ID of the desired group
     * @return        - String of url pointing to group.
     */
    public String buildGroupURL(String testId, String groupId) {
        String testUrl = buildTestURLById(testId);
        return testUrl
                + "/groups/"
                + groupId;
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
        return buildGroupURL(testId, groupId)
                + "/participants/"
                + participantId;
    }

    public String buildScriptFileURL(String fileId) {
        return baseUrl
                + "/projects/"
                + projectId
                + "/files/"
                + fileId;
    }

    public String buildRunResultsURL(String testId, String runId) {
        return buildTestURLById(testId)
                + "/runs/"
                + runId
                + "/results/";
    }

    public String buildProjectURL() {
        return baseUrl
                + "/projects/"
                + projectId;
    }
}

