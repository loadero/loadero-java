package loadero;

import loadero.model.*;
import loadero.service.*;
import lombok.Getter;


/**
 * Main entry point of the interaction with the Loadero API.
 */
@Getter
public class LoaderoClient {
    private final String baseUrl;
    private final String projectId;
    private final String loaderoApiToken;
    private final LoaderoServiceFactory serviceFactory;

    public LoaderoClient(String baseUrl, String loaderApiToken,
                         String projectId) {
        this.baseUrl = baseUrl;
        this.projectId = projectId;
        this.loaderoApiToken = loaderApiToken;
        this.serviceFactory = new LoaderoServiceFactory(loaderApiToken, baseUrl, projectId);
    }

    /**
     * Returns information about test as LoaderoTestOptions.
     * @param testId - ID of desired test.
     * @return       - LoaderoTestOptions object.
     */
    public LoaderoTestOptions getTestOptionsById(String testId) {
        return (LoaderoTestOptions) serviceFactory
                .getLoaderoService(LoaderoType.LOADERO_TEST_OPTIONS)
                .getById(testId);
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
        LoaderoTestOptionsService testOptionsService = (LoaderoTestOptionsService)
                serviceFactory.getLoaderoService(LoaderoType.LOADERO_TEST_OPTIONS);
        return testOptionsService.updateById(newTestOptions, testId);
    }

    /**
     * Retrieves content of the script file from Loadero API and returns as LoaderoScriptFileLoc object.
     * Use toString() to convert into actual script.
     * @param fileId - ID of the script file
     * @return       - LoaderoScriptFileLoc information about script content.
     */
    public LoaderoScriptFileLoc getTestScript(String fileId) {
        return (LoaderoScriptFileLoc) serviceFactory
                .getLoaderoService(LoaderoType.LOADERO_SCRIPT_FILE_LOC)
                .getById(fileId);
    }

    /**
     * Returns group as LoaderoGroup object from Loadero with specified ID.
     * @param testId - ID of the test.
     * @param groupId - ID of the group.
     * @return   - LoaderoGroup object.
     */
    public LoaderoGroup getGroupById(String testId, String groupId) {
        return (LoaderoGroup) serviceFactory
                .getLoaderoService(LoaderoType.LOADERO_GROUP)
                .getById(testId, groupId);
    }

    /**
     * Updates Loadero group parameters by given group ID.
     * @param testId    - ID of the test containing the group.
     * @param groupId   - ID of the group to be updated.
     * @param newGroup  - LoaderoGroup object with new parameters.
     * @return          - LoaderoGroup object with updated parameters.
     */
    public LoaderoGroup updateGroupById(String testId, String groupId, LoaderoGroup newGroup) {
        LoaderoGroupService groupService = (LoaderoGroupService)
                serviceFactory.getLoaderoService(LoaderoType.LOADERO_GROUP);
        return groupService.updateById(newGroup, testId, groupId);
    }

    /**
     * Returns participant's information from Loadero as LoaderoParticipant object.
     * @param testId        - ID of the test that contains participant.
     * @param participantId - desired participant.
     * @return              - LoaderoParticipant object
     */
    public LoaderoParticipant getParticipantById(String testId, String groupId,
                                                 String participantId) {
        return (LoaderoParticipant) serviceFactory
                .getLoaderoService(LoaderoType.LOADERO_PARTICIPANT)
                .getById(testId, groupId, participantId);
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

        LoaderoParticipantService participantService = (LoaderoParticipantService)
                serviceFactory.getLoaderoService(LoaderoType.LOADERO_PARTICIPANT);
        return participantService.updateById(newParticipant, testId, groupId, participantId);
    }

    /**
     * Gets information about all test run results from Loadero API.
     * @param testId - ID of the desired test to get results from.
     * @param runId  - ID of the test run.
     * @return       - LoaderoAllTestRunResults object, that contains list of LoaderoSingleTestRunResult objects.
     */
    public LoaderoTestRunResult getTestRunResult(String testId, String runId) {
        return (LoaderoTestRunResult) serviceFactory
                .getLoaderoService(LoaderoType.LOADERO_RUN_RESULT)
                .getById(testId, runId);
    }

    /**
     * Gets information about specific results.
     * @param testId    - ID of the test we wish to get results.
     * @param runId     - ID of the test run.
     * @param resultId  - ID of the result.
     * @return          - LoaderoSingleTestRunResult object containing information such as ID, status,
     *                    selenium_status, log paths, asserts and artifacts.
     */
    public LoaderoTestRunParticipantResult getTestRunParticipantResult(String testId,
                                                                       String runId,
                                                                       String resultId) {
        return (LoaderoTestRunParticipantResult) serviceFactory
                .getLoaderoService(LoaderoType.LOADERO_TEST_RUN_PARTICIPANT_RESULT)
                .getById(testId, runId, resultId);
    }

    /**
     * Start test run by sending POST command underneath to tests/testId/runs endpoint.
     * After which actively starts polling for information about test run.
     * Returns run info when test is done or time of the polling runs out.
     * @param testId   - ID of the test that is going to run.
     * @param interval - how often check for information. In seconds.
     * @param timeout  - how long should polling for information. In seconds.
     * @return         - LoaderoRunInfo containing information about test run.
     */
    public LoaderoRunInfo startTestAndPollInfo(String testId, int interval, int timeout) {
        LoaderoPollingService pollingService = (LoaderoPollingService) serviceFactory
                .getLoaderoService(LoaderoType.LOADERO_RUN_INFO);
        return pollingService.startTestAndPoll(testId, interval, timeout);
    }
}

