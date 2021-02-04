package loadero;

import loadero.controller.LoaderTestController;
import loadero.controller.LoaderoGroupController;
import loadero.model.LoaderoTestOptions;
import java.net.URI;

// TODO: Parse return value into java object - Done
// TODO: Null checks
public class LoaderoClient {
    private static final String BASE_URL = "https://api.loadero.com/v2";
    private static final String LOADERO_API_TOKEN = System.getenv("LOADERO_API_TOKEN");
    private static final String PROJECT_ID = "5040";
    private static final String TEST_ID = "6866";
    private static final String GROUP_ID = "49033";
    // TODO: create some generic method to create uris.
    private static final URI uri =
            URI.create(BASE_URL+"/projects/"+PROJECT_ID+"/tests/"+TEST_ID+"/");

    public static void main(String[] args) {
        // REST controller for tests
        LoaderTestController testController = new LoaderTestController(
                uri,
                LOADERO_API_TOKEN,
                PROJECT_ID,
                TEST_ID);
        LoaderoGroupController groupController = new LoaderoGroupController(
                uri,
                LOADERO_API_TOKEN,
                PROJECT_ID,
                TEST_ID,
                GROUP_ID);
        LoaderoTestOptions test = new LoaderoTestOptions(
                "new name 5", 30,
                10, "performance",
                "random", "src/main/resources/test.js");

        // Retrieving current test from loadero
        LoaderoTestOptions currentTest = testController.get();
        System.out.println("Current test: " + currentTest);
        System.out.println("Setting new name and script");
        // Setting new params
        currentTest.setName("new test 6");
        currentTest.setScript("src/main/resources/test1.js");
//        // Sending updated test options to Loadero
        LoaderoTestOptions updatedTest = testController.update(currentTest);
        System.out.println("Updated test: " + updatedTest);
    }
}
