package loadero;

import loadero.controller.LoaderTestController;
import loadero.controller.LoaderoGroupController;
import loadero.model.LoaderoGroup;
import loadero.model.LoaderoTestOptions;
import loadero.model.LoaderoType;

import java.net.URI;

// TODO: get rid of the casting
public class LoaderoClient {
    private static final String BASE_URL = "https://api.loadero.com/v2";
    private static final String LOADERO_API_TOKEN = System.getenv("LOADERO_API_TOKEN");
    private static final String PROJECT_ID = "5040";
    private static final String TEST_ID = "6866";
    private static final String GROUP_ID = "49033";
    // TODO: create some method to create uris based on some params.
    private static final URI testUri =
            URI.create(BASE_URL+"/projects/"+PROJECT_ID+"/tests/"+TEST_ID+"/");
    private static final URI groupsURI =  URI.create(testUri + "groups/" + GROUP_ID + "/");
    private static final URI particURI = URI.create("");

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
        LoaderoTestOptions test = new LoaderoTestOptions(
                "new name 5", 30,
                10, "performance",
                "random", "src/main/resources/test.js");

        // Retrieving current test from loadero
        LoaderoTestOptions currentTest = (LoaderoTestOptions) testController.get(
                testUri,
                LoaderoType.LOADERO_TEST);
        LoaderoGroup currentGroup = (LoaderoGroup) groupController.get(
                groupsURI,
                LoaderoType.LOADERO_GROUP);
        System.out.println("Current test: " + currentTest);
        System.out.println("Current group: " + currentGroup);
        System.out.println("Setting new name and script");
        // Setting new params
        currentTest.setName("new test 6");
        currentTest.setScript("src/main/resources/test1.js");
//        currentGroup.setName("new Group name from Java");
//        // Sending updated test options to Loadero
        LoaderoTestOptions updatedTest = (LoaderoTestOptions) testController.update(
                testUri,
                LoaderoType.LOADERO_TEST,
                currentTest);
//        LoaderoGroup updatedGroup = groupController.update(currentGroup);
        System.out.println("Updated test: " + updatedTest);
//        System.out.println("Updated group: " + updatedGroup);
    }
}
