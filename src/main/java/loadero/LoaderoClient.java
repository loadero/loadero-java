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
    private final String BASE_URL = "https://api.loadero.com/v2";
    private final String projectId;// = "5040";
    private final String testId;// = "6866";
    private final String loaderoApiToken;
    private final LoaderoRestController restController;
    private final LoaderoPollController pollController;

    public LoaderoClient(String loaderApiToken, String projectId, String testId) {
        this.projectId = projectId;
        this.testId    = testId;
        this.loaderoApiToken = loaderApiToken;
        restController = new LoaderoRestController(loaderApiToken);
        pollController = new LoaderoPollController(loaderApiToken);
    }

    /**
     * Returns information about test as LoaderoTestOptions.
     * @return - LoaderoTestOptions object.
     */
    public LoaderoTestOptions getTestOptions() {
        String testUrl = buildTestURL();
        return (LoaderoTestOptions) restController.get(testUrl,
                LoaderoType.LOADERO_TEST_OPTIONS);
    }

    /**
     * Updates the existing Loadero test description based on the
     * parameters provided in new LoaderoTestOptions() object.
     *
     * @param newTestOptions - new LoaderoTestOptions object with parameters that you wish to change
     * @return               - Updated LoaderoTestOptions
     */
    public LoaderoTestOptions updateTestOptions(LoaderoTestOptions newTestOptions) {
        String testUrl = buildTestURL();
        LoaderoTestOptions currentOptions = getTestOptions();

        // If new script is not provided
        // We get the old script from Loadero API endpoint /files/fileId
        // And update accordingly.
        if (Objects.equals(newTestOptions.getScript(), "")) {
            String scriptContent = getFileScriptConent(
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
     * @param id - ID of the script file
     * @return   - String containing script content.
     */
    public String getFileScriptConent(String id) {
        String scriptFileid = String.valueOf(id);
        String scriptFileUrl = buildScriptFileURL(scriptFileid);
        LoaderoScriptFileLoc scriptFile = (LoaderoScriptFileLoc) restController.get(
                    scriptFileUrl,
                    LoaderoType.LOADERO_SCRIPT_FILE_LOC);
        return scriptFile.getContent();
    }

    /**
     * Returns group as LoaderoGroup object from Loadero with specified ID.
     * @param id - ID of the group.
     * @return   - LoaderoGroup object.
     */
    public LoaderoModel getGroupById(String id) {
        String groupUrl = buildGroupURL(id);
        return restController.get(groupUrl,
                LoaderoType.LOADERO_GROUP);
    }

    /**
     * Returns participant's information from Loadero as LoaderoParticipant object.
     * @param participantId - desired participant.
     * @return              - LoaderoParticipant object
     */
    public LoaderoParticipant getParticipantById(String participantId) {
        String particUrl = buildParticipantURL(participantId);
        return (LoaderoParticipant) restController.get(
                particUrl, LoaderoType.LOADERO_PARTICIPANT
        );
    }

    /**
     * Updates Loadero Participant by it's ID.
     * @param participantId       - ID of desired participant.
     * @param newParticipant      - LoaderoParticipant object with new params.
     * @return LoaderoParticipant - updated LoaderoParticipant object.
     */
    public LoaderoParticipant updateTestParticipantById(String participantId,
                                                    LoaderoParticipant newParticipant) {
        String participnatUrl = buildParticipantURL(participantId);
        LoaderoParticipant currentParticInfo = getParticipantById(participantId);

        LoaderoParticipant updatedParticipant = (LoaderoParticipant) LoaderoClientUtils
                .copyUncommonFields(
                currentParticInfo,
                newParticipant,
                LoaderoType.LOADERO_PARTICIPANT);

        return (LoaderoParticipant) restController
                .update(participnatUrl, LoaderoType.LOADERO_PARTICIPANT, updatedParticipant);
    }

    /**
     * Start test run by sending POST command underneath to /runs url.
     * After which starts activly polling for information about test run.
     * Returns run info when test is done or time of the polling run out.
     * @param interval - how often check for information. In seconds.
     * @param timeout  - how long should polling for information. In seconds.
     * @return
     */
    public LoaderoModel startTestAndPollInfo(int interval, int timeout) {
        String startRunsUrl = buildTestURL() + "runs/";
        return pollController
                .startTestAndPoll(startRunsUrl, interval, timeout);
    }

    // Public for testing purposes. May make it private later
    public String buildTestURL() {
        return BASE_URL
                + "/projects/"
                + projectId
                + "/tests/"
                + testId + "/";
    }


    // Private methods

    /**
     * Builds URL for Loadero groups based on given ID.
     * @param groupId - ID of the desired group
     * @return        - String of url pointing to group.
     */
    private String buildGroupURL(String groupId) {
        String testUrl = buildTestURL();
        return testUrl
                + "groups/"
                + groupId
                + "/";
    }

    /**
     * Builds URL to for specific participant of specific group.
     * @param participantId - ID of desired participant.
     * @return              - String url pointing to participant.
     */
    public String buildParticipantURL(String participantId) {
        return buildTestURL()
                + "participants/"
                + participantId
                + "/";
    }

    public String buildScriptFileURL(String fileId) {
        return BASE_URL
                + "/projects/"
                + projectId
                + "/files/"
                + fileId
                + "/";
    }

    public String buildRunResultsURL(String runId) {
        return BASE_URL
                + "/projects/"
                + projectId
                + "/runs/"
                + runId
                + "/results/";
    }

    public String buildProjectURL() {
        return BASE_URL
                + "/projects/"
                + projectId;
    }
}

