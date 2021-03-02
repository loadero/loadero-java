package loadero;

import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.extension.responsetemplating.ResponseTemplateTransformer;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.github.tomakehurst.wiremock.stubbing.Scenario;
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
public class TestLoaderoPackage {
    private static final String token           = System.getenv("LOADERO_API_TOKEN");
    private static String BASE_URL              = System.getenv("LOADERO_BASE_URL");
    private static final String PROJECT_ID      = "5040";
    private static final String TEST_ID         = "6866";
    private static final String PARTICIPANT_ID  = "94633";
    private static final String GROUP_ID        = "48797";
    private static final String RUN_ID          = "33328";
    private static final String RESULT_ID       = "930397";
    private static final String DISABLED_ENABLED_IF_NAME = "LOADERO_BASE_URL";
    private static final String DISABLED_ENABLED_IF_MATCH = ".*localhost.*";
    private final LoaderoHttpClient httpClient  = new LoaderoHttpClient(token);
    private static final Logger log = LogManager.getLogger(TestLoaderoPackage.class);
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
            log.error("{}", ex.getMessage());
        }
    }
    
    private void testOptionsHelper(String jsonRes) {
        String testUrl = ".*/tests/.*";
    
        wmRule.stubFor(get(urlMatching(testUrl))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.SC_OK)
                        .withBodyFile("body-projects-5040-tests-6866-uaor7.json")));
    
        wmRule.stubFor(get(urlMatching(".*/files/.*"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.SC_OK)
                        .withBodyFile("body-script-file.json")));
    
        wmRule.stubFor(put(urlMatching(testUrl))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.SC_OK)
                        .withBody(jsonRes)));
    }
    
    @Rule
    private static final WireMockRule wmRule = new WireMockRule(
            WireMockConfiguration
                    .wireMockConfig()
                    .extensions(new ResponseTemplateTransformer(false))
                    .dynamicPort()
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
    public static void teardownWM() {
        wmRule.stop();
    }

    @Test
    @Order(1)
    public void testTokenAccess() {
        String projectUrl = urlBuilder.buildProjectURL() + "/";
        
        wmRule.stubFor(get(urlMatching(".*/projects/.*"))
                .willReturn(aResponse().withStatus(HttpStatus.SC_OK)));
        
        makeGetRequest(projectUrl);
        assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());
    }

    @Test
    public void negativeTokenAccess() {
        wmRule.stubFor(get(urlMatching(".*/projects/.*"))
                .willReturn(aResponse().withStatus(HttpStatus.SC_UNAUTHORIZED)));
        
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
    public void testGetTestOptionsById() {
        // Make GET request to url on localhost to get status code
        
        wmRule.stubFor(get(urlMatching(".*/tests/" + TEST_ID + "/"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.SC_OK)
                        .withBodyFile("body-projects-5040-tests-6866-uaor7.json")));
        
        LoaderoTestOptions test = loaderoClient.getTestOptionsById(TEST_ID);
        assertNotNull(test);
        assertEquals(TEST_ID, String.valueOf(test.getId()));
    }

    @Test
    public void negativeGetTestOptionsById() {
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
        String jsonRes = LoaderoClientUtils.modelToJson(newTest);
        
        testOptionsHelper(jsonRes);
        
        LoaderoTestOptions updatedTest = loaderoClient.updateTestOptions(TEST_ID, newTest);
        assertNotNull(updatedTest);
        assertEquals(150, updatedTest.getParticipantTimeout());
        assertEquals("unit test 1", updatedTest.getName());
        assertEquals("load", updatedTest.getMode());
    }

    @Test
    public void testUpdateTestOptionsWithNewScript() {
        LoaderoTestOptions newTest = new LoaderoTestOptions();
        // New params for test
        newTest.setName("unit test 2");
        // New test script params
        newTest.setScript(URI.create("src/main/resources/loadero/scripts/testui/LoaderoScript.java"));
    
        String jsonRes = LoaderoClientUtils.modelToJson(newTest);
        testOptionsHelper(jsonRes);
        
        // Updating some random test
        LoaderoTestOptions updatedTest = loaderoClient.updateTestOptions("7193", newTest);
        // Retrieving ID pointing to script file
        long scriptId = updatedTest.getScriptFileId();
        // Getting content of the script from Loadero
        String actualScript = loaderoClient.getTestScript(String.valueOf(scriptId))
                .getContent();
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
            loaderoClient.updateTestOptions("23423", test);
        });
        assertThrows(NullPointerException.class, () -> {
            loaderoClient.updateTestOptions("23423", null);
        });
    }
    
    @Test
    public void testGetGroupById() {
        String groupUrl = String.format(".*/tests/%s/groups/%s/", TEST_ID, GROUP_ID);
        
        wmRule.stubFor(get(urlMatching(groupUrl))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.SC_OK)
                        .withBodyFile("body-projects-5040-tests-6866-groups-48797-m03sm.json")));
        
        LoaderoGroup group = loaderoClient.getGroupById(TEST_ID, GROUP_ID);
        assertNotNull(group);
        assertEquals(GROUP_ID, String.valueOf(group.getId()));
    }

    @Test
    public void negativeGetGroupById() {
        LoaderoGroup group = loaderoClient.getGroupById(TEST_ID, "2342");
        assertNull(group);
    }

    @Test
    public void testGetParticipantById() {
        String url = String.format(".*/tests/%s/groups/%s/participants/%s/",
                TEST_ID, GROUP_ID, PARTICIPANT_ID);
        wmRule.stubFor(get(urlMatching(url))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.SC_OK)
                        .withBodyFile("body-projects-5040-tests-6866-participants-94633-aZHuT.json")));
        
        LoaderoParticipant participant = loaderoClient.getParticipantById(TEST_ID, GROUP_ID, PARTICIPANT_ID);
        assertNotNull(participant);
        assertEquals(PARTICIPANT_ID, String.valueOf(participant.getId()));
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
    public void testUpdateParticipant() {
        String url = String.format(".*/tests/%s/groups/%s/participants/%s/",TEST_ID, GROUP_ID, PARTICIPANT_ID);
        LoaderoParticipant newParticipant = new LoaderoParticipant();
        newParticipant.setName("unit test partic");
        newParticipant.setCount(2);
        newParticipant.setComputeUnit("g2");
        String updateRes = LoaderoClientUtils.modelToJson(newParticipant);
        
        wmRule.stubFor(get(urlMatching(url))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.SC_OK)
                        .withBodyFile("body-projects-5040-tests-6866-participants-94633-aZHuT.json")));
        
        wmRule.stubFor(put(urlMatching(url))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.SC_OK)
                        .withBody(updateRes)));
        
        LoaderoParticipant updatedPartic = loaderoClient.
                updateTestParticipantById(TEST_ID, GROUP_ID, PARTICIPANT_ID, newParticipant);
        
        assertEquals("unit test partic", updatedPartic.getName());
        assertEquals(2, updatedPartic.getCount());
        assertEquals("g2", updatedPartic.getComputeUnit());
    }

    @Test
    public void negativeUpdateParticipant() {
        // Should throw null pointer exception
        assertThrows(NullPointerException.class, () ->
            loaderoClient.
                    updateTestParticipantById(TEST_ID, GROUP_ID, PARTICIPANT_ID, null)
        );
    }
    
    @Test
    public void negativeUpdateParticipantIdNull() {
        // Should throw null pointer exception
        assertThrows(NullPointerException.class, () ->
            loaderoClient.
                    updateTestParticipantById(TEST_ID, GROUP_ID, null, null)
        );
    }
    
    @Test
    public void negativeUpdateParticipantTestIdWrong() {
        // Should throw null pointer exception
        assertThrows(NullPointerException.class, () ->
            loaderoClient.
                    updateTestParticipantById("3eqq", GROUP_ID, PARTICIPANT_ID, null)
        );
    }

    @Test
    public void testGetAllResultsFromWireMock() {
        String allResultsUrl = String.format(".*/tests/%s/runs/%s/results/", TEST_ID, RUN_ID);

        wmRule.givenThat(get(urlMatching(allResultsUrl))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.SC_OK)
                        .withBodyFile("body-run-results-NyqBC.json")));
        
        LoaderoTestRunResult testRunResult = loaderoClient.getTestRunResult(TEST_ID, RUN_ID);
        assertNotNull(testRunResult);
    }
    
    @Test
    public void negativeGetAllTestResultsInvalidTestId() {
        LoaderoTestRunResult results = loaderoClient.getTestRunResult("2323", RUN_ID);
        assertNull(results);
    }
    
    @Test
    public void negativeGetAllTestResultsInvalidRunId() {
        LoaderoTestRunResult results = loaderoClient.getTestRunResult(TEST_ID, "2342");
        assertNull(results.getResults());
    }

    @Test
    public void testGetSingleRunResultsFromWireMock() {
        String resultsUrl = String.format(".*/tests/%s/runs/%s/results/%s/",
                TEST_ID, RUN_ID, RESULT_ID);
        
        wmRule.stubFor(get(urlMatching(resultsUrl))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.SC_OK)
                        .withBodyFile("body-projects-5040-tests-6866-participant-results-iHbtF.json")));
        
        LoaderoTestRunParticipantResult result = loaderoClient
                .getTestRunParticipantResult(TEST_ID, RUN_ID, RESULT_ID);
        assertNotNull(result);
        assertNotNull(result.getLogPaths());
        assertNotNull(result.getArtifacts());
        // log_paths fields shouldn't be null
        assertNotNull(result.getLogPaths().get("browser"));
        assertNotNull(result.getLogPaths().get("webrtc"));
        assertNotNull(result.getLogPaths().get("selenium"));
        assertNotNull(result.getLogPaths().get("rru"));
    }

    @Test
    public void negativeGetSingleRunResultsInvalidResultId() {
        wmRule.resetMappings();
        LoaderoTestRunParticipantResult result = loaderoClient
                .getTestRunParticipantResult(TEST_ID, RUN_ID, "2341");
        assertNull(result);
    }
    
    @Test
    public void negativeGetSingleRunResultsInvalidRunId() {
        LoaderoTestRunParticipantResult result = loaderoClient
                .getTestRunParticipantResult(TEST_ID, "RUN_ID", RESULT_ID);
        assertNull(result);
    }

    @Test
    public void negativeCrudUpdate() {
        assertThrows(NullPointerException.class, () -> {
            LoaderoCrudController crudController = new LoaderoCrudController(loaderoClient.getLoaderoApiToken());
            crudController.update(urlBuilder.buildTestURLById(TEST_ID),
                    LoaderoType.LOADERO_TEST_OPTIONS, null);
        });
    }

    @Test
    public void testPolling() {
        String scenarioName = "polling";
        String getRunInfo = ".*/runs/.*";
        
        wmRule.stubFor(post(urlMatching(".*/runs/"))
                .inScenario(scenarioName)
                .whenScenarioStateIs(Scenario.STARTED)
                .willReturn(aResponse()
                        .withStatus(HttpStatus.SC_ACCEPTED)
                        .withBodyFile("body-projects-5040-tests-6866-runs-34778-pending-ua.json"))
        );
    
        wmRule.stubFor(get(urlMatching(getRunInfo))
                .inScenario(scenarioName)
                .whenScenarioStateIs(Scenario.STARTED)
                .willReturn(aResponse()
                        .withStatus(HttpStatus.SC_OK)
                        .withBodyFile("body-projects-5040-tests-6866-runs-34778-pending-ua.json"))
                .willSetStateTo("pending")
        );
        
        wmRule.stubFor(get(urlMatching(getRunInfo))
                .inScenario(scenarioName)
                .whenScenarioStateIs("pending")
                .willReturn(aResponse()
                        .withStatus(HttpStatus.SC_OK)
                        .withBodyFile("body-projects-5040-tests-6866-runs-34778-initializing-ua.json"))
                .willSetStateTo("initializing")
        );
        wmRule.stubFor(get(urlMatching(getRunInfo))
                .inScenario(scenarioName)
                .whenScenarioStateIs("initializing")
                .willReturn(aResponse()
                        .withStatus(HttpStatus.SC_OK)
                        .withBodyFile("body-projects-5040-tests-6866-runs-34778-running-ua.json"))
                .willSetStateTo("running")
        );
    
        wmRule.stubFor(get(urlMatching(getRunInfo))
                .inScenario(scenarioName)
                .whenScenarioStateIs("running")
                .willReturn(aResponse()
                        .withStatus(HttpStatus.SC_OK)
                        .withBodyFile("body-projects-5040-tests-6866-runs-34778-waiting-for-results-ua.json"))
                .willSetStateTo("waiting for results")
        );
        
        wmRule.stubFor(get(urlMatching(getRunInfo))
                .inScenario(scenarioName)
                .whenScenarioStateIs("waiting for results")
                .willReturn(aResponse()
                        .withStatus(HttpStatus.SC_OK)
                        .withBodyFile("body-projects-5040-tests-6866-runs-34778-done-ua.json"))
        );
        
        LoaderoRunInfo poll = loaderoClient.startTestAndPollInfo(TEST_ID, 10, 40);
        assertEquals("done", poll.getStatus());
    }

    @Test
    public void negativePollingTestInvalidTestId() {
        assertThrows(NullPointerException.class, () -> {
            loaderoClient.startTestAndPollInfo("111", 2, 40);
        });
    }

    @Test
    public void testUpdateGroup() {
        LoaderoGroup newGroup = new LoaderoGroup();
        newGroup.setCount(3);
        String jsonRes = LoaderoClientUtils.modelToJson(newGroup);
        String groupUrl = String.format(".*/%s/groups/%s/", TEST_ID, GROUP_ID);
        
        wmRule.stubFor(get(urlMatching(groupUrl))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.SC_OK)
                        .withBodyFile("body-projects-5040-tests-6866-groups-48797-m03sm.json")));
        
        wmRule.stubFor(put(urlMatching(groupUrl))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.SC_OK)
                        .withBody(jsonRes)));
        
        LoaderoGroup updatedGroup = loaderoClient.updateGroupById(TEST_ID, GROUP_ID, newGroup);
        assertEquals(3, updatedGroup.getCount());
    }

    @Test
    public void negativeUpdateGroup() {
        assertThrows(NullPointerException.class, () -> {
            loaderoClient.updateGroupById(TEST_ID, GROUP_ID, null);
        });
    }
    
    @Test
    public void negativeLoaderoServiceFactory() {
        assertThrows(NullPointerException.class, () ->
            loaderoClient.getServiceFactory().getLoaderoService(null));
    }
}