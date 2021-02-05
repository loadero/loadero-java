package loadero;

import loadero.controller.LoaderTestController;
import loadero.controller.LoaderoGroupController;
import loadero.controller.LoaderoParticipantController;
import loadero.model.LoaderoGroup;
import loadero.model.LoaderoParticipant;
import loadero.model.LoaderoTestOptions;
import loadero.model.LoaderoType;

import java.net.URI;

// TODO: get rid of the casting
// TODO: proper logging system
// TODO: decide how to pass Ids and other params
public class LoaderoClient {
    private static final String BASE_URL = "https://api.loadero.com/v2";
    private static final String LOADERO_API_TOKEN = System.getenv("LOADERO_API_TOKEN");
    private static final String PROJECT_ID = "5040";
    private static final String TEST_ID = "6866";
    private static final String GROUP_ID = "48797";
    private static final String PARTICIPANT_ID = "94633";
    // TODO: create some method to create uris based on given params.
    private static final URI testUri =
            URI.create(BASE_URL+"/projects/"+PROJECT_ID+"/tests/"+TEST_ID+"/");
    private static final URI groupsURI =  URI.create(testUri + "groups/" + GROUP_ID + "/");
    private static final URI particURI = URI.create(groupsURI + "participants/" + PARTICIPANT_ID + "/");

    public static void main(String[] args) {
        // REST controller for tests
        LoaderTestController testController = new LoaderTestController(
                LOADERO_API_TOKEN,
                PROJECT_ID,
                TEST_ID);
        LoaderoGroupController groupController = new LoaderoGroupController(
                LOADERO_API_TOKEN,
                PROJECT_ID,
                TEST_ID);
        LoaderoParticipantController participantController = new LoaderoParticipantController(
                LOADERO_API_TOKEN,
                PROJECT_ID,
                TEST_ID
        );
        // Retrieving current test from loadero
        LoaderoTestOptions currentTest = (LoaderoTestOptions) testController.get(
                testUri,
                LoaderoType.LOADERO_TEST);
        LoaderoGroup currentGroup = (LoaderoGroup) groupController.get(
                groupsURI,
                LoaderoType.LOADERO_GROUP);
        LoaderoParticipant currentPartic = (LoaderoParticipant) participantController.get(
                particURI, LoaderoType.LOADERO_PARTICIPANT);
        System.out.println("Current test: " + currentTest);
        System.out.println("Current group: " + currentGroup);
        System.out.println("Current group: " + currentPartic);

        // Setting new params
        System.out.println("Setting new params...");
        // test
        currentTest.setName("new test 6");
        currentTest.setScript("src/main/resources/test1.js");
        // group
        currentGroup.setName("new Group name from Java 2");
        // participant
        currentPartic.setName("New Participant name from Java");
//        // Sending updated test options to Loadero
        LoaderoTestOptions updatedTest = (LoaderoTestOptions) testController.update(
                testUri,
                LoaderoType.LOADERO_TEST,
                currentTest);
        LoaderoGroup updatedGroup = (LoaderoGroup) groupController.update(
                groupsURI,
                LoaderoType.LOADERO_GROUP,
                currentGroup);
        LoaderoParticipant updatedPartic = (LoaderoParticipant) participantController.update(
                particURI,
                LoaderoType.LOADERO_PARTICIPANT,
                currentPartic
        );

        System.out.println("Updated test: " + updatedTest);
        System.out.println("Updated group: " + updatedGroup);
        System.out.println("Updated participant: " + updatedPartic);
    }
}
