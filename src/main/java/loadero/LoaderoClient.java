package loadero;

import loadero.controller.LoaderoController;
import loadero.model.LoaderoTestOptions;
import loadero.model.LoaderoRunInfo;
import loadero.model.LoaderoType;
import org.apache.http.HttpResponse;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.protocol.HttpContext;

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
    private static final String RUNS_ID = "94633";
    // TODO: create some method to create uris based on given params.
    private static final URI testUri =
            URI.create(BASE_URL+"/projects/"+PROJECT_ID+"/tests/"+TEST_ID+"/");
    private static final URI groupsURI =  URI.create(testUri + "groups/" + GROUP_ID + "/");
    private static final URI particURI = URI.create(groupsURI + "participants/" + PARTICIPANT_ID + "/");
    private static final URI startRunsURI = URI.create(testUri + "runs/");

    public static void main(String[] args) throws InterruptedException {
        // REST controller for tests
        LoaderoController testController = new LoaderoController(
                LOADERO_API_TOKEN,
                PROJECT_ID,
                TEST_ID);
        // Retrieving current test from loadero
        LoaderoTestOptions currentTest = (LoaderoTestOptions) testController.get(
                testUri,
                LoaderoType.LOADERO_TEST);

        // Starting test
        LoaderoRunInfo startTestRun = (LoaderoRunInfo) testController.startTestRun(startRunsURI);
        System.out.println(startTestRun);
        String runsId = String.valueOf(startTestRun.getId());
        URI getRunsURI = URI.create(testUri + "runs/" + runsId + "/");

        System.out.println("Current test: " + currentTest);
        System.out.println();

        boolean done = false;
        int interval = 30000; // ms
        int times = 4;
        int timeout = 300000; // 5 mins
        ConnectionKeepAliveStrategy keepAliveStrategy = new DefaultConnectionKeepAliveStrategy() {
            @Override
            public long getKeepAliveDuration(HttpResponse response,
                                             HttpContext context) {
                long keepAlive = super.getKeepAliveDuration(response, context);
                if (keepAlive == -1) {
                    keepAlive = timeout;
                }
                return keepAlive;
            }
        };
        testController.getClient().setKeepAliveStrategy(keepAliveStrategy);
        while (!done) {
            Thread.sleep(interval);
            LoaderoRunInfo getTestResult = (LoaderoRunInfo) testController
                    .get(getRunsURI,
                            LoaderoType.LOADERO_TEST_RESULT);
            if (getTestResult.getStatus().equals("done")) {
                done = true;
                System.out.println("Done! Test results: " + getTestResult);
            } else {
                System.out.println("Test results are still not done.");
                System.out.println("Test status: " + getTestResult.getStatus());
            }
        }

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
    }
}
