package loadero;

import loadero.controller.LoaderoController;
import loadero.model.LoaderoGroup;
import loadero.model.LoaderoParticipant;
import loadero.model.LoaderoTestOptions;
import loadero.model.LoaderoType;
import lombok.Getter;

// TODO: get rid of the casting
// TODO: proper logging system
// TODO: decide how to pass Ids and other params
@Getter
public class LoaderoClient {
    private final String BASE_URL = "https://api.loadero.com/v2";
    private final String projectId;// = "5040";
    private final String testId;// = "6866";
    private String groupId;// = "48797";
    private String participantId;// = "94633";
    private String RUNS_ID;// = "94633";
    private final LoaderoController controller;
    // TODO: create some method to create uris based on given params.
//    private final URI projectURI = URI.create(BASE_URL + "/projects/" + projectId);
//    private static final URI startRunsURI = URI.create(testUri + "runs/");


    public LoaderoClient(String loaderApiToken, String projectId, String testId) {
        this.projectId = projectId;
        this.testId = testId;
        controller = new LoaderoController(loaderApiToken);
    }

    /**
     * Returns information about test as LoaderoTestOptions.
     * @return
     */
    public LoaderoTestOptions getTestOptions() {
        String testUrl = buildTestURL();
        return (LoaderoTestOptions) controller.get(testUrl,
                LoaderoType.LOADERO_TEST_OPTIONS);
    }

    /**
     * Returns group as LoaderoGroup object from Loadero with specified ID.
     * @param id - ID of the group.
     * @return
     */
    public LoaderoGroup getGroupById(String id) {
        String groupUrl = buildGroupURL(id);
        LoaderoGroup group = (LoaderoGroup) controller.get(groupUrl,
                LoaderoType.LOADERO_GROUP);
        // Save group ID for later usage
        return group;
    }

    /**
     * Returns participant's information from Loadero as LoaderoParticipant object.
     * Note: You can't get participant before you know group ID.
     * So, yeah...¯\_(ツ)_/¯
     * @param participantId - desired participant.
     * @param groupId - group ID you want get participant from.
     * @return
     */
    public LoaderoParticipant getParticipantById(String participantId,
                                                 String groupId) {
        String particUrl = buildParticipantURL(participantId, groupId);
        System.out.println(particUrl);
        LoaderoParticipant participant = (LoaderoParticipant) controller.get(
                particUrl, LoaderoType.LOADERO_PARTICIPANT
        );
        return participant;
    }

    public String buildTestURL() {
        return BASE_URL
                + "/projects/"
                + projectId
                + "/tests/"
                + testId + "/";
    }

    /**
     *
     * @param groupId
     * @return
     */
    public String buildGroupURL(String groupId) {
        String testUrl = buildTestURL();
        return testUrl
                + "groups/"
                + groupId
                + "/";
    }

    public String buildParticipantURL(String participantId,
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
