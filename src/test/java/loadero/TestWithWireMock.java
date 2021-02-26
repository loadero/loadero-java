package loadero;

import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import loadero.controller.LoaderoCrudController;
import loadero.model.*;
import loadero.utils.FunctionBodyParser;
import loadero.utils.LoaderoClientUtils;
import loadero.utils.LoaderoHttpClient;
import loadero.utils.LoaderoUrlBuilder;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Rule;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.DisabledIfEnvironmentVariable;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestWithWireMock {
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
    private final LoaderoUrlBuilder urlBuilder = new LoaderoUrlBuilder(BASE_URL, PROJECT_ID);

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
        if (BASE_URL.contains("localhost")) {
            BASE_URL = BASE_URL + ":" + wmRule.port();
        }
        loaderoClient = new LoaderoClient(BASE_URL, token, PROJECT_ID);
    }

    @AfterAll
    public static void teardown() {
        wmRule.stop();
    }

    @Test
    @Order(1)
    public void testTokenAccess() {
        String url = urlBuilder.buildProjectURL() + "/";
        makeGetRequest(url);
        assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());
    }

    @Test
    @DisabledIfEnvironmentVariable(named = "LOADERO_BASE_URL", matches = ".*localhost.*")
    public void negativeTokenAccess() {
        try {
            String url = urlBuilder.buildProjectURL() + "/";
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
    public void testGetTestOptions() throws IOException {
        // Make GET request to url on localhost to get status code
        makeGetRequest(urlBuilder.buildTestURLById(TEST_ID) + "/");
        LoaderoTestOptions test = loaderoClient.getTestOptionsById(TEST_ID);
        String jsonRes = EntityUtils.toString(response.getEntity());

        assertEquals(200,
                response.getStatusLine().getStatusCode());
        assertEquals(TEST_ID, String.valueOf(test.getId()));
        assertTrue(jsonRes.contains(TEST_ID));
    }

    @Test
    public void negativeGetTestOptionsByUrl() {
        LoaderoTestOptions test = loaderoClient.getTestOptionsById("0");
        assertNull(test);
    }

    @Test
    @DisabledIfEnvironmentVariable(named = "LOADERO_BASE_URL", matches = ".*localhost.*")
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
    @DisabledIfEnvironmentVariable(named = "LOADERO_BASE_URL", matches = ".*localhost.*")
    public void testUpdateTestOptionsWithNewScript() {
        LoaderoTestOptions newTest = new LoaderoTestOptions();
        // New params for test
        newTest.setName("unit test 2");
        // New test script params
        newTest.setScript(URI.create("src/main/resources/loadero/scripts/testui/LoaderoScript.java"));
        // Updating some random test
        LoaderoTestOptions updatedTest = loaderoClient.updateTestOptions("7193", newTest);
        // Retrieving ID pointing to script file
        long scriptId = updatedTest.getScriptFileId();
        // Getting content of the script from Loadero
        String actualScript = loaderoClient.getTestScript(String.valueOf(scriptId));
        String expectedScript = FunctionBodyParser
                .getScriptContent("src/main/resources/loadero/scripts/testui/LoaderoScript.java");

        assertEquals("unit test 2", updatedTest.getName());
        // Comparing script from Loadero and local script
        assertEquals(expectedScript, actualScript);
    }

    @Test
    public void negativeUpdateTestOptions() {
        LoaderoTestOptions test = new LoaderoTestOptions();
        test.setMode("performance");
        test.setName("negative test");
        assertThrows(NullPointerException.class, () -> {
            LoaderoTestOptions updatedTest = loaderoClient.updateTestOptions("23423", test);
        });
        assertThrows(NullPointerException.class, () -> {
            LoaderoTestOptions updatedTest = loaderoClient.updateTestOptions("23423", null);
        });
    }

    @Test
    @DisabledIfEnvironmentVariable(named = "LOADERO_BASE_URL", matches = ".*localhost.*")
    public void testUpdateLoaderoTestScriptWithNewParams() {
        LoaderoTestOptions newTestOptions = new LoaderoTestOptions();

        // New parameters for a test script
        // If new value parameter is not set, default provided in template will be used.
        Map<String, String> newScriptParams = new HashMap<>();
        newScriptParams.put("callDuration", "15");
        newScriptParams.put("elementTimeout","15");

        // Setting script with new params
        // Setting path where script "template" is stored and new script parameters for this "template"
        newTestOptions.setScript(
                "src/test/java/loadero/scripts/testui/TestOneOnOneCall.java",
                newScriptParams);
        LoaderoTestOptions updatedTestOptions = loaderoClient.updateTestOptions(TEST_ID, newTestOptions);
        assertNotNull(updatedTestOptions);

        // Comparing script updated script with script on local machine
        String actualScript = loaderoClient.getTestScript(String.valueOf(updatedTestOptions.getScriptFileId()));
        String expectedScript = FunctionBodyParser
                .applyParamsToScript(
                        "src/test/java/loadero/scripts/testui/TestOneOnOneCall.java",
                        newScriptParams);

        assertEquals(expectedScript, actualScript);
    }

    @Test
    public void testGetGroupById() {
        LoaderoGroup group = loaderoClient.getGroupById(TEST_ID, GROUP_ID);
        assertNotNull(group);
    }

    @Test
    public void negativeGetGroupById() {
        LoaderoGroup group = loaderoClient.getGroupById(TEST_ID, "2342");
        assertNull(group);
    }

    @Test
    public void testGettingProjectFromSavedMappings() throws IOException {
        makeGetRequest(urlBuilder.buildProjectURL() + "/");
        String jsonRes = EntityUtils.toString(response.getEntity());
        assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());
        assertTrue(jsonRes.contains(loaderoClient.getProjectId()));
    }

    @Test
    public void testGetParticipantById() {
        LoaderoParticipant participant = loaderoClient.getParticipantById(TEST_ID, "48797", PARTICIPANT_ID);
        assertNotNull(participant);
    }

    @Test
    public void negativeGetParticipantWrongTestId() {
        LoaderoParticipant participant = loaderoClient.getParticipantById("234", GROUP_ID, PARTICIPANT_ID);
        assertNull(participant);
    }

    @Test
    public void negativeGetParticipantWrongParticipantId() {
        LoaderoParticipant participant = loaderoClient.getParticipantById(TEST_ID, GROUP_ID, "2312");
        assertNull(participant);
    }

    @Test
    @DisabledIfEnvironmentVariable(named = "LOADERO_BASE_URL", matches = ".*localhost.*")
    public void testUpdateParticipant() {
        LoaderoParticipant newParticipant = new LoaderoParticipant();
        newParticipant.setName("unit test partic");
        newParticipant.setCount(2);
        newParticipant.setComputeUnit("g2");

        LoaderoParticipant updatedPartic = loaderoClient.
                updateTestParticipantById(TEST_ID, GROUP_ID, PARTICIPANT_ID, newParticipant);
        assertEquals("unit test partic", updatedPartic.getName());
        assertEquals(2, updatedPartic.getCount());
        assertEquals("g2", updatedPartic.getComputeUnit());
    }

    @Test
    public void negativeUpdateParticipant() {
        // Should throw null pointer exception
        assertThrows(NullPointerException.class, () -> {
            LoaderoParticipant updatedPartic = loaderoClient.
                    updateTestParticipantById(TEST_ID, GROUP_ID, PARTICIPANT_ID, null);
        });
    }

    @Test
    @EnabledIfEnvironmentVariable(named = "LOADERO_BASE_URL", matches = ".*localhost.*")
    public void testGetAllResults() {
        String allResultsUrl = "/projects/"
                + PROJECT_ID
                + "/tests/"
                + TEST_ID
                + "/runs/"
                + RUN_ID
                + "/results/";

        wmRule.givenThat(get(allResultsUrl)
                .willReturn(aResponse()
                .withStatus(HttpStatus.SC_OK)));

        makeGetRequest(urlBuilder.buildRunResultsURL(TEST_ID, RUN_ID) + "/");

        wmRule.verify(getRequestedFor(urlPathMatching(allResultsUrl)));

        assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());
    }

    @Test
    @DisabledIfEnvironmentVariable(named = "LOADERO_BASE_URL", matches = ".*localhost.*")
    public void testGetAllTestResultsFromLoadero() {
        LoaderoTestRunResult results = loaderoClient.getTestRunResult(TEST_ID, RUN_ID);
        assertNotNull(results.getResults());
    }

    @Test
    public void negativeGetAllTestResults() {
        LoaderoTestRunResult results = loaderoClient.getTestRunResult("2323", RUN_ID);
        assertNull(results);
    }

    @Test
    @DisabledIfEnvironmentVariable(named = "LOADERO_BASE_URL", matches = ".*localhost.*")
    public void testGetSingleRunResults() {
        LoaderoTestRunParticipantResult result = loaderoClient
                .getTestRunParticipantResult(TEST_ID, RUN_ID, RESULT_ID);
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
    public void negativetestFunctionParser() {
        String str = FunctionBodyParser.getScriptContent("");
        assertNull(str);
    }

    @Test
    public void testJsonToObject() {
        String testUrl = urlBuilder.buildTestURLById(TEST_ID) + "/";
        makeGetRequest(testUrl);
        LoaderoModel model = LoaderoClientUtils.jsonToObject(
                response.getEntity(), LoaderoType.LOADERO_TEST_OPTIONS);
        assertEquals(LoaderoTestOptions.class, model.getClass());
    }

    @Test
    public void testModelToJson() {
        LoaderoGroup group = loaderoClient.getGroupById(TEST_ID, GROUP_ID);
        String json = LoaderoClientUtils.modelToJson(group);
        assertTrue(json.contains(group.getName()));
    }

    @Test
    public void testCopyCommonFields() {
        LoaderoTestOptions currentTest = loaderoClient.getTestOptionsById(TEST_ID);
        LoaderoTestOptions newTest     = new LoaderoTestOptions();

        LoaderoTestOptions updatedTest = (LoaderoTestOptions) LoaderoClientUtils.copyUncommonFields(
                currentTest,
                newTest,
                LoaderoType.LOADERO_TEST_OPTIONS
        );
        // Comparing different fields against each other. Should be the same
        assertAll("Test currentTest with updatedTest. Should be the same.",
                () -> assertEquals(currentTest.getId(), updatedTest.getId()),
                () -> assertEquals(currentTest.getName(), updatedTest.getName()),
                () -> assertEquals(currentTest.getScriptFileId(), updatedTest.getScriptFileId()));
    }

    @Test
    public void testApplyNewParamsToScript() {
        Map<String, String> scriptParams = new HashMap<>();
        String appUrl = "https://voice-webapp-3026-dev.twil.io/index.html?identity=";

        scriptParams.put("callDuration", "20");
        scriptParams.put("particId", "globalConfig.getParticipant().getId()");
        scriptParams.put("appUrl", appUrl);

        String scriptLoc = "src/test/java/loadero/scripts/testui/TestOneOnOneCall.java";
        String scriptWithNewParams = FunctionBodyParser.applyParamsToScript(scriptLoc, scriptParams);
        assertTrue(scriptWithNewParams.contains("callDuration = 20"));
        assertTrue(scriptWithNewParams.contains(appUrl));
    }

    @Test
    public void negativeApplyNewParamsToScript() {
        assertThrows(NullPointerException.class, () -> {
            String scriptLoc = "src/test/java/loadero/scripts/testui/TestOneOnOneCall.java";
            String scriptWithNewParams = FunctionBodyParser.applyParamsToScript(scriptLoc, null);
        });
        assertThrows(NullPointerException.class, () -> {
            Map<String, String> scriptParams = new HashMap<>();
            String scriptWithNewParams = FunctionBodyParser.applyParamsToScript(null, scriptParams);
        });
    }

    @Test
    public void negativeTestJsonToObject() {
        LoaderoModel model = LoaderoClientUtils.jsonToObject(null, null);
        assertNull(model);
    }

    @Test
    public void negativeRESTUpdate() {
        LoaderoCrudController restController = new LoaderoCrudController(loaderoClient.getLoaderoApiToken());
        LoaderoModel model = restController.update(urlBuilder.buildTestURLById(TEST_ID),
                LoaderoType.LOADERO_TEST_OPTIONS, null);
        assertNull(model);
    }

    @Test
    public void negativePollingTest() {
        LoaderoRunInfo info = loaderoClient.startTestAndPollInfo("111", 2, 40);
        assertNull(info);
    }

    @Test
    @DisabledIfEnvironmentVariable(named = "LOADERO_BASE_URL", matches = ".*localhost.*")
    @Disabled
    public void testFullFunctionalityFlow() {
        String testClientInitUrl = urlBuilder.buildProjectURL() + "/";
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
                URI.create("src/main/resources/loadero/scripts/testui/CallOneOnOne.java"));
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

    @Test
    @DisabledIfEnvironmentVariable(named = "LOADERO_BASE_URL", matches = ".*localhost.*")
    public void testUpdateGroup() {
        LoaderoGroup newGroup = new LoaderoGroup();
        newGroup.setCount(3);
        LoaderoGroup updatedGroup = loaderoClient.updateGroupById(TEST_ID, GROUP_ID, newGroup);
        assertEquals(3, updatedGroup.getCount());
    }

    @Test
    public void negativeUpdateGroup() {
        assertThrows(NullPointerException.class, () -> {
            LoaderoGroup updatedGroup = loaderoClient.updateGroupById(TEST_ID, GROUP_ID, null);
        });
    }
}
