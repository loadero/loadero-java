package loadero;

import loadero.model.*;
import loadero.utils.FunctionBodyParser;
import loadero.utils.LoaderoHttpClient;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfEnvironmentVariable;

import java.io.IOException;
import java.net.URI;

import static org.junit.jupiter.api.Assertions.*;

public class TestWithLoaderoApi {
    private static final String token           = System.getenv("LOADERO_API_TOKEN");
    private static String BASE_URL              = System.getenv("LOADERO_BASE_URL");
    private static final String PROJECT_ID      = "5040";
    private static final String TEST_ID         = "6866";
    private static final String PARTICIPANT_ID  = "94633";
    private static final String GROUP_ID        = "48797";
    private static final String RUN_ID          = "33328";
    private static final String RESULT_ID       = "930397";
    private final LoaderoHttpClient httpClient  = new LoaderoHttpClient(token);
    private static final Logger logger = LogManager.getLogger(TestWithWireMock.class);
    private CloseableHttpResponse response;
    private static LoaderoClient loaderoClient;

    /**
     * Makes GET request to specified url and saves response to
     * private response variable.
     * @param url - URL to make request to.
     */
    private void makeGetRequest(String url) {
        try {
            HttpGet get = new HttpGet(url);
            response = httpClient.build().execute(get);
        } catch (IOException ex) {
            logger.error("{}", ex.getMessage());
        }
    }

    @BeforeAll
    public static void setup() {
        loaderoClient = new LoaderoClient(BASE_URL, token, PROJECT_ID);
    }


    @Test
    @Order(1)
    public void testTokenAccess() {
        String url = loaderoClient.buildProjectURL() + "/";
        makeGetRequest(url);
        assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());
    }

    @Test
    public void negativeTokenAccess() {
        try {
            String url = loaderoClient.buildProjectURL() + "/";
            CloseableHttpClient client = HttpClientBuilder.create().build();
            HttpUriRequest get = RequestBuilder.get(url).build();
            get.addHeader(new BasicHeader(HttpHeaders.AUTHORIZATION, "random token value"));
            try (CloseableHttpResponse response = client.execute(get)) {
                assertEquals(HttpStatus.SC_UNAUTHORIZED, response.getStatusLine().getStatusCode());
            } catch (IOException ignored) {

            }
        } catch (Exception ignored) {

        }
    }

    @Test
    public void testGetTestOptions() {
        // Make GET request to url on localhost to get status code
        LoaderoTestOptions test = loaderoClient.getTestOptionsById(TEST_ID);
        assertEquals(TEST_ID, String.valueOf(test.getId()));
        assertNotNull(test);
    }

    @Test
    public void negativeGetTestOptionsByUrl() {
        LoaderoTestOptions test = loaderoClient.getTestOptionsById("0");
        assertNull(test);
    }

    @Test
    public void testUpdateTestOptions() {
        LoaderoTestOptions newTest = new LoaderoTestOptions();
        newTest.setMode("load");
        newTest.setName("unit test 1");
        newTest.setParticipantTimeout(150);
        newTest.setStartInterval(5);
        LoaderoTestOptions updatedTest = loaderoClient.updateTestOptions(TEST_ID, newTest);

        assertEquals(150, updatedTest.getParticipantTimeout());
        assertEquals("unit test 1", updatedTest.getName());
        assertEquals("load", updatedTest.getMode());
    }

    @Test
    public void negativeUpdateTestOptions() {
        LoaderoTestOptions test = new LoaderoTestOptions();
        test.setMode("performance");
        test.setName("negative test");
        LoaderoTestOptions updatedTest = loaderoClient.updateTestOptions("23423", test);
        assertNull(updatedTest);
    }

    @Test
    public void testUpdateTestOptionsWithNewScript() {
        LoaderoTestOptions newTest = new LoaderoTestOptions();
        newTest.setName("unit test 2");
        newTest.setScript(URI.create("src/main/resources/loadero/scripts/testui/LoaderoScript.java"));
        // Updating some random test
        LoaderoTestOptions updatedTest = loaderoClient.updateTestOptions("7193", newTest);
        // Retrieving ID pointing to script file
        long scriptId = updatedTest.getScriptFileId();
        // Getting content of the script from Loadero
        String actualScript = loaderoClient.getTestScript(String.valueOf(scriptId));
        String expectedScript = FunctionBodyParser
                .getBody("src/main/resources/loadero/scripts/testui/LoaderoScript.java");

        assertEquals("unit test 2", updatedTest.getName());
        // Comparing script from Loadero and local script
        assertEquals(expectedScript, actualScript);
    }

    @Test
    public void testGetGroupById() {
        LoaderoGroup group = loaderoClient.getGroupById(TEST_ID, GROUP_ID);
        assertEquals(3, group.getCount());
    }

    @Test
    public void negativeGetGroupById() {
        LoaderoGroup group = loaderoClient.getGroupById(TEST_ID, "2342");
        assertNull(group);
    }

    @Test
    public void testGetParticipantById() {
        String url = loaderoClient.buildParticipantURL(TEST_ID, GROUP_ID, PARTICIPANT_ID) + "/";
        LoaderoParticipant participant = loaderoClient.getParticipantById(TEST_ID, GROUP_ID,
                PARTICIPANT_ID);
        makeGetRequest(url);

        assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());
        assertEquals(PARTICIPANT_ID, String.valueOf(participant.getId()));
        assertEquals(TEST_ID, String.valueOf(participant.getTestId()));
    }

    @Test
    public void negativeGetParticipant() {
        LoaderoParticipant participant = loaderoClient.getParticipantById("2311", GROUP_ID, PARTICIPANT_ID);
        assertNull(participant);
    }

    @Test
    public void testUpdateParticipant() {
        LoaderoParticipant newParticipant = new LoaderoParticipant();
        newParticipant.setName("unit test partic");
        newParticipant.setCount(2);
        newParticipant.setComputeUnit("g2");

        LoaderoParticipant updatedPartic = loaderoClient.
                updateTestParticipantById(TEST_ID, GROUP_ID, PARTICIPANT_ID,newParticipant);
        assertEquals("unit test partic", updatedPartic.getName());
        assertEquals(2, updatedPartic.getCount());
        assertEquals("g2", updatedPartic.getComputeUnit());
    }

    @Test
    public void testGetAllTestResults() {
        LoaderoTestRunResult results = loaderoClient.getTestRunResult(TEST_ID, RUN_ID);
        assertNotNull(results.getResults());
    }

    @Test
    public void negativeGetAllTestResults() {
        LoaderoTestRunResult results = loaderoClient.getTestRunResult("2323", RUN_ID);
        assertNull(results);
    }

    @Test
    public void testGetSingleRunResults() {
        LoaderoTestRunParticipantResult result = loaderoClient.getTestRunParticipantResult(TEST_ID, RUN_ID, RESULT_ID);
        // log_paths shouldn't be null
        assertNotNull(result.getLogPaths());
        // log_paths fields shouldn't be null
        assertNotNull(result.getLogPaths().get("browser"));
        assertNotNull(result.getLogPaths().get("webrtc"));
        assertNotNull(result.getLogPaths().get("selenium"));
        assertNotNull(result.getLogPaths().get("rru"));
    }

    @Test
    public void negativeGetSingleRunResults() {
        LoaderoTestRunParticipantResult result = loaderoClient
                .getTestRunParticipantResult(TEST_ID, "34234", "2341");
        assertNull(result);
    }

    @Test
    public void negativePollingFunction() {
        LoaderoRunInfo startAndPollTest = loaderoClient.startTestAndPollInfo("2342", 2, 40);
        assertNull(startAndPollTest);
    }

    @Test
    @Disabled
    public void testFullFunctionalityFlow() {
        String testClientInitUrl = loaderoClient.buildProjectURL() + "/";
        logger.info(token);
        // Checking that client can establish connection and make requests
        makeGetRequest(testClientInitUrl);
        assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());

        // Checking that client is able to update test
        LoaderoTestOptions currentTestOptions = loaderoClient.getTestOptionsById(TEST_ID);
        LoaderoTestOptions newTestOptions = new LoaderoTestOptions();
        newTestOptions.setName("New test name from testFullFunctionalityFlow");
        // Setting script through path using URI
        newTestOptions.setScript(
                URI.create("src/main/resources/loadero/scripts/testui/LoaderoScriptJava.java"));
        newTestOptions.setMode("performance");
        newTestOptions.setStartInterval(30);
        LoaderoTestOptions updatedTestOptions = loaderoClient.updateTestOptions(TEST_ID, newTestOptions);

        logger.info("Before update: {}", currentTestOptions);
        logger.info("After update: {}", updatedTestOptions);
        // asserting that update did happen
        assertEquals("New test name from testFullFunctionalityFlow",
                updatedTestOptions.getName());

        // Checking polling function
        LoaderoRunInfo startAndPollTest = loaderoClient.startTestAndPollInfo(TEST_ID, 2, 40);
        assertEquals("done", startAndPollTest.getStatus());
    }
}
