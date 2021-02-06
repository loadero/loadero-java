package loadero;

import loadero.controller.LoaderoPollController;
import loadero.controller.LoaderoRestController;
import loadero.model.*;
import lombok.Getter;

// TODO: get rid of the casting
// TODO: proper logging system
@Getter
public class LoaderoClient {
    private final String BASE_URL = "https://api.loadero.com/v2";
    private final String projectId;// = "5040";
    private final String testId;// = "6866";
    private String groupId;// = "48797";
    private String participantId;// = "94633";
    private String RUNS_ID;// = "94633";
    private final LoaderoRestController restController;
    private final LoaderoPollController pollController;

//    private final URI projectURI = URI.create(BASE_URL + "/projects/" + projectId);

    public LoaderoClient(String loaderApiToken, String projectId, String testId) {
        this.projectId = projectId;
        this.testId    = testId;
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
     * Returns group as LoaderoGroup object from Loadero with specified ID.
     * @param id - ID of the group.
     * @return - LoaderoGroup object.
     */
    public LoaderoGroup getGroupById(String id) {
        String groupUrl = buildGroupURL(id);
        return (LoaderoGroup) restController.get(groupUrl,
                LoaderoType.LOADERO_GROUP);
    }

    /**
     * Returns participant's information from Loadero as LoaderoParticipant object.
     * Note: You can't get participant before you know group ID.
     * So, yeah...¯\_(ツ)_/¯
     * @param participantId - desired participant.
     * @param groupId - group ID you want get participant from.
     * @return - LoaderoParticipant object
     */
    public LoaderoParticipant getParticipantById(String participantId,
                                                 String groupId) {
        String particUrl = buildParticipantURL(participantId, groupId);
        System.out.println(particUrl);
        return (LoaderoParticipant) restController.get(
                particUrl, LoaderoType.LOADERO_PARTICIPANT
        );
    }

    public String buildTestURL() {
        return BASE_URL
                + "/projects/"
                + projectId
                + "/tests/"
                + testId + "/";
    }

    /**
     * Start test run by sending POST command underneath to /runs url.
     * After which starts activly polling for information about test run.
     * Returns run info when test is done or time of the polling run out.
     * @param interval - how often check for information. In seconds.
     * @param timeout  - how long should polling for information. In seconds.
     * @return
     */
    public LoaderoRunInfo startTestAndPollInfo(int interval, int timeout) {
        String startRunsUrl = buildTestURL() + "runs/";
        LoaderoRunInfo info = (LoaderoRunInfo) pollController
                .startTestAndPoll(startRunsUrl, interval, timeout);
        return info;
    }


    // Private methods

    /**
     * Builds URL for Loadero groups based on given ID.
     * @param groupId - ID of the desired group
     * @return - String of url pointing to group.
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
     * @param groupId - ID of the group participant belongs to.
     * @return - String url pointing to participant.
     */
    private String buildParticipantURL(String participantId,
                                      String groupId) {
        String groupUrl = buildGroupURL(groupId);
        return groupUrl
                + "participants/"
                + participantId
                + "/";

    }
}
