package loadero;

import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import static com.github.tomakehurst.wiremock.client.WireMock.*;

import loadero.controller.LoaderoRestController;
import loadero.model.*;
import loadero.utils.FunctionBodyParser;
import loadero.utils.LoaderoClientUtils;
import loadero.utils.LoaderoHttpClient;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Rule;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.DisabledIfEnvironmentVariable;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;

import java.io.IOException;
import java.net.URI;

import static org.junit.jupiter.api.Assertions.*;

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
    public void testGetTestOptions() throws IOException {
        // Make GET request to url on localhost to get status code
        makeGetRequest(loaderoClient.buildTestURLById(TEST_ID) + "/");
        LoaderoTestOptions test = loaderoClient.getTestOptionsById(TEST_ID);
        String jsonRes = EntityUtils.toString(response.getEntity());

        assertEquals(200,
                response.getStatusLine().getStatusCode());
        assertEquals(TEST_ID, String.valueOf(test.getId()));
        assertTrue(jsonRes.contains(TEST_ID));
    }

    @Test
    public void negativeGetTestOptionsByUrl() {
        String url = loaderoClient.buildTestURLById("0");
        makeGetRequest(url);
        assertEquals(HttpStatus.SC_NOT_FOUND, response.getStatusLine().getStatusCode());
    }

    @Test
    public void testGetGroupById() {
    }

    @Test
    public void testTokenAccessByUrl() {
        String url = loaderoClient.buildProjectURL() + "/";
        makeGetRequest(url);
        assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());
    }

    @Test
    @EnabledIfEnvironmentVariable(named = "LOADERO_BASE_URL", matches = ".*localhost.*")
    public void negativeTokenAccess() throws IOException {
        String url = "/projects/";
        HttpGet request = new HttpGet(BASE_URL + url);
        CloseableHttpClient client = HttpClients.createDefault();
        response = client.execute(request);
        assertEquals(HttpStatus.SC_FORBIDDEN, response.getStatusLine().getStatusCode());
    }

    @Test
    public void testGettingProjectFromSavedMappings() throws IOException {
        makeGetRequest(loaderoClient.buildProjectURL() + "/");
        String jsonRes = EntityUtils.toString(response.getEntity());
        assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());
        assertTrue(jsonRes.contains(loaderoClient.getProjectId()));
    }

    @Test
    @EnabledIfEnvironmentVariable(named = "LOADERO_BASE_URL", matches = ".*localhost.*")
    public void negativeTestGettingProjectFromSavedMappings() {
    }

    @Test
    public void testGetParticipantById() {

    }

    @Test
    public void negativeGetParticipant() {

    }

    @Test
    public void testUpdateParticipant() {

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

        makeGetRequest(loaderoClient.buildRunResultsURL(TEST_ID, RUN_ID) + "/");

        wmRule.verify(getRequestedFor(urlPathMatching(allResultsUrl)));

        assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());
    }

    @Test
    @DisabledIfEnvironmentVariable(named = "LOADERO_BASE_URL", matches = ".*localhost.*")
    public void testGetAllTestResultsLive() {

    }

    @Test
    @DisabledIfEnvironmentVariable(named = "LOADERO_BASE_URL", matches = ".*localhost.*")
    public void testGetSingleRunResults() {

    }

    @Test
    public void negativetestFunctionParser() {
        String str = FunctionBodyParser.getBody("");
        assertNull(str);
    }

    @Test
    public void testJsonToObject() {
        String groupUrl = loaderoClient.buildGroupURL(TEST_ID, GROUP_ID);
        makeGetRequest(groupUrl);
        LoaderoModel model = LoaderoClientUtils.jsonToObject(
                response.getEntity(), LoaderoType.LOADERO_GROUP);
        assertEquals(LoaderoGroup.class, model.getClass());
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
    public void negativeTestJsonToObject() {
        LoaderoModel model = LoaderoClientUtils.jsonToObject(null, null);
        assertNull(model);
    }

    @Test
    public void negativeRESTUpdate() {
        LoaderoRestController restController = new LoaderoRestController(loaderoClient.getLoaderoApiToken());
        LoaderoModel model = restController.update(loaderoClient.buildTestURLById(TEST_ID),
                LoaderoType.LOADERO_TEST_OPTIONS, null);
        assertNull(model);
    }

    @Test
    public void negativePollingFunction() {

    }

}
