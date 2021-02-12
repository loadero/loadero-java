package loadero;

import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.http.ResponseDefinition;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.github.tomakehurst.wiremock.stubbing.StubMapping;
import loadero.controller.LoaderoRestController;
import loadero.model.LoaderoParticipant;
import loadero.model.LoaderoRunInfo;
import loadero.model.LoaderoTestOptions;
import loadero.model.LoaderoType;
import loadero.utils.LoaderoClientUtils;
import loadero.utils.LoaderoHttpClient;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Rule;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.net.URI;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestWithWireMock {
    private static final String token           = System.getenv("LOADERO_API_TOKEN");
    private static final String PROJECT_ID      = "5040";
    private static final String TEST_ID         = "6866";
    private static String localhost             = "http://localhost:";
    private static final String loaderoTokenStr = "LoaderoAuth " + token;
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


    @Rule
    public static WireMockRule wmRule = new WireMockRule(
            WireMockConfiguration.wireMockConfig().dynamicPort()
    );

    @BeforeAll
    public static void setup() {
        wmRule.start();
        localhost = localhost + wmRule.port();
        loaderoClient = new LoaderoClient(localhost, token, PROJECT_ID);
    }

    @AfterAll
    public static void teardown() {
        wmRule.stop();
    }

    @Test
    @Order(1)
    public void testGetTestOptions() throws IOException {
        // Make GET request to url on localhost to get status code
        makeGetRequest(loaderoClient.buildTestURLById(TEST_ID) + "/");
        LoaderoTestOptions test = loaderoClient.getTestOptionsById(TEST_ID);
        String jsonRes = EntityUtils.toString(response.getEntity());

        assertEquals(200,
                response.getStatusLine().getStatusCode());
        assertEquals("application/json;charset=utf-8",
                response.getEntity().getContentType().getValue());
        assertEquals(TEST_ID, String.valueOf(test.getId()));
        assertTrue(jsonRes.contains(TEST_ID));
    }

    @Test
    @Order(2)
    public void negativeGetTestOptionsByUrl() {
        String url = loaderoClient.buildTestURLById("0");
        makeGetRequest(url);
        assertEquals(HttpStatus.SC_NOT_FOUND, response.getStatusLine().getStatusCode());
    }

    @Test
    @Order(3)
    public void testTokenAccessByUrl() {
        String url = loaderoClient.buildProjectURL() + "/";

        makeGetRequest(url);
        assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());
    }

    @Test
    @Order(4)
    public void negativeTokenAccess() throws IOException {
        String url = "/projects/";
        HttpGet request = new HttpGet(localhost + url);
        CloseableHttpClient client = HttpClients.createDefault();
        response = client.execute(request);
        assertEquals(HttpStatus.SC_FORBIDDEN, response.getStatusLine().getStatusCode());
    }

    @Test
    @Order(5)
    public void testGettingProjectFromSavedMappings() throws IOException {
        makeGetRequest(loaderoClient.buildProjectURL() + "/");
        String jsonRes = EntityUtils.toString(response.getEntity());
        assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());
        assertTrue(jsonRes.contains(loaderoClient.getProjectId()));
    }

    @Test
    @Order(6)
    public void negativeTestGettingProjectFromSavedMappings() {
        makeGetRequest(localhost + "/projects/" + "0" + "/");
        assertEquals(HttpStatus.SC_NOT_FOUND, response.getStatusLine().getStatusCode());
    }

    @Test
    @Order(7)
    public void testGetParticipantById() {
        String participantId = "94633";
        String url = loaderoClient.buildParticipantURL(TEST_ID, participantId) + "/";

        LoaderoParticipant participant = loaderoClient.getParticipantById(TEST_ID, participantId);
        makeGetRequest(url);

        assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());
        assertEquals(participantId, String.valueOf(participant.getId()));
        assertEquals(TEST_ID, String.valueOf(participant.getTestId()));
    }

    // TODO
    @Test
    @Order(8)
    @Disabled
    public void testUpdateParticipantById() throws IOException {
        String participantId = "94633";
    }

    // Add new tests before this comment
    // Don't forget to change order!

    @Test
    @Disabled
    @Order(9)
    public void testFullFunctionalityFlow() {
        String baseUrl = "https://api.loadero.com/v2";
        LoaderoClient localClient = new LoaderoClient(
                baseUrl, token,
                PROJECT_ID);
        String testClientInitUrl = localClient.buildProjectURL() + "/";

        // Checking that client can establish connection and make requests
        makeGetRequest(testClientInitUrl);
        assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());

        // Checking that client is able to update test
        LoaderoTestOptions currentTestOptions = localClient.getTestOptionsById(TEST_ID);
        LoaderoTestOptions newTestOptions = new LoaderoTestOptions();
        newTestOptions.setName("New test name from testFullFunctionalityFlow");
        // Setting script through path using URI
        newTestOptions.setScript(
                URI.create("src/main/resources/loadero/scripts/testui/LoaderoScriptJava.java"));
        newTestOptions.setMode("performance");
        newTestOptions.setStartInterval(30);
        LoaderoTestOptions updatedTestOptions = localClient.updateTestOptions(TEST_ID, newTestOptions);

        logger.info("Before update: {}", currentTestOptions);
        logger.info("After update: {}", updatedTestOptions);
        // asserting that update did happen
        assertEquals("New test name from testFullFunctionalityFlow",
                updatedTestOptions.getName());

        // Checking polling function
        LoaderoRunInfo startAndPollTest = localClient.startTestAndPollInfo(TEST_ID, 20, 40);
        assertEquals("done", startAndPollTest.getStatus());
    }

    @Test
    @Disabled
    @Order(10)
    public void testFullFunctionalityFlowWithAnotherScript() {
        String baseUrl = "https://api.loadero.com/v2";
        LoaderoClient localClient = new LoaderoClient(
                baseUrl, token,
                PROJECT_ID);
        String testClientInitUrl = localClient.buildProjectURL() + "/";

        // Checking that client can establish connection and make requests
        makeGetRequest(testClientInitUrl);
        assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());

        // Checking that client is able to update test
        LoaderoTestOptions currentTestOptions = localClient.getTestOptionsById(TEST_ID);
        LoaderoTestOptions newTestOptions = new LoaderoTestOptions();
        newTestOptions.setName("New Test from testFunctionalityFlowWithAnotherScript");
        // Setting script through path using URI
        // Another script
        newTestOptions.setScript(
                URI.create("src/main/resources/loadero/scripts/testui/LoaderoScript.java"));
        newTestOptions.setMode("load");
        newTestOptions.setStartInterval(30);
        LoaderoTestOptions updatedTestOptions = localClient.updateTestOptions(TEST_ID, newTestOptions);

        logger.info("Before update: {}", currentTestOptions);
        logger.info("After update: {}", updatedTestOptions);
        // asserting that update did happen
        assertEquals("New Test from testFunctionalityFlowWithAnotherScript",
                updatedTestOptions.getName());

        // Checking polling function
        LoaderoRunInfo startAndPollTest = localClient.startTestAndPollInfo(TEST_ID, 20, 40);
        assertEquals("done", startAndPollTest.getStatus());
    }
}
