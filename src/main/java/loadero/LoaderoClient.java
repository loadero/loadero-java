package loadero;

import loadero.controller.LoaderoRestController;
import loadero.controller.LoaderoPollController;
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
//    private static final URI startRunsURI = URI.create(testUri + "runs/");


    public LoaderoClient(String loaderApiToken, String projectId, String testId) {
        this.projectId = projectId;
        this.testId = testId;
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

    public LoaderoRunInfo startTestAndPollInfo(String url, int interval, int timeout) {
        LoaderoRunInfo info = (LoaderoRunInfo) pollController
                .startTestAndPoll(url, interval, timeout);
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

    // LoaderoClient client = new LoaderoClient(apiToken, projectId, testId)
    // client.startTestAndPoll(interval, timeout)
    // client.get(testId/groupId/participantId/etc)
    // client.update(testId/groupId/participantId/etc, new LoaderModel(params))
    // client.createTest(new LoaderoModel(params))
    // client.createGroup(new LoaderoModel(params))
    // client.delete()

    /*
    URIBuilder builder = new URIBuilder()
    .setScheme("http")
    .setHost("apache.org")
    .setPath("/shindig")
    .addParameter("helloWorld", "foo&bar")
    .setFragment("foo");
builder.toString();
     */

//
//    public static void main(String[] args) throws InterruptedException {
//        // REST controller for tests
//        LoaderoController testController = new LoaderoController(
//                LOADERO_API_TOKEN,
//                PROJECT_ID,
//                TEST_ID);
//
        // Retrieving current test from loadero
//        LoaderoTestOptions currentTest = (LoaderoTestOptions) testController.get(
//                testUri,
//                LoaderoType.LOADERO_TEST);

        // Starting test and collect run's info
//        LoaderoRunInfo runInfo = (LoaderoRunInfo) testController.startTestAndPoll(
//                startRunsURI, 10, 60);
//        System.out.println(runInfo);
//        System.out.println(currentTest);
//        System.out.println(LoaderoClientUtils.modelToJson(currentTest));

//        String newScript = FunctionBodyParser.getBody("src/test/java/TestParser.java");

        // Setting new params
//        System.out.println("Setting new params...");
        // test
//        currentTest.setName("new test 8, with new script from Java");
//        currentTest.setScript(newScript);
        // group
        // Sending updated test options to Loadero
//        LoaderoTestOptions updatedTest = (LoaderoTestOptions) testController.update(
//                testUri,
//                LoaderoType.LOADERO_TEST,
//                currentTest);
//        System.out.println("Updated test: " + updatedTest);
//    }
}
