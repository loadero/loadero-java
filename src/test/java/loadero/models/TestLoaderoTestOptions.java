package loadero.models;

import com.github.tomakehurst.wiremock.stubbing.StubMapping;
import loadero.model.LoaderoTestOptions;
import loadero.types.LoaderoIncrementStrategyType;
import loadero.types.LoaderoTestModeType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import java.net.URI;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.*;

public class TestLoaderoTestOptions extends AbstractTestLoadero {
    
    // Helper method
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
    
    @Test
    public void testGetTestOptionsById() {
        wmRule.stubFor(get(urlMatching(".*/tests/" + TEST_ID + "/"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.SC_OK)
                        .withBodyFile("body-projects-5040-tests-6866-uaor7.json")));
        
        LoaderoTestOptions test = loaderoClient.getTestOptionsById(TEST_ID);
        assertNotNull(test);
        assertEquals(TEST_ID, test.getId());
    }
    
    @Test
    public void negativeGetTestOptionsById() {
        LoaderoTestOptions test = loaderoClient.getTestOptionsById(0);
        assertNull(test);
    }
    
    @Test
    public void testCreateNewTest() {
        LoaderoTestOptions testOptions = new LoaderoTestOptions();
        testOptions.setName("new test 5");
        testOptions.setMode(LoaderoTestModeType.LOAD);
        testOptions.setIncrementStrategy(LoaderoIncrementStrategyType.LINEAR_GROUP);
        testOptions.setStartInterval(10);
        testOptions.setParticipantTimeout(200);
        testOptions.setScript(URI.create("/Users/mihhail.matisinets/Desktop/Projects/loadero-rest-api-wrapper" +
                "/src/main/resources/loadero/scripts/testui/LoaderoScript.java"));
        
        String body = gson.toJson(testOptions);
        
        StubMapping stub = wmRule.stubFor(post(urlPathMatching(".*/tests/"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.SC_CREATED)
                        .withBody(body)));
        
        
        LoaderoTestOptions newTest = loaderoClient.createNewTest(testOptions);
        assertEquals(HttpStatus.SC_CREATED, stub.getResponse().getStatus());
        assertNotNull(newTest);
    }
    
    @Test
    public void testDeleteTestById() {
        int testId = 7443;
        StubMapping stub = wmRule.stubFor(delete(urlPathMatching(".*/tests/" + testId + "/"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.SC_NO_CONTENT)));
        
        loaderoClient.deleteTestById(testId);
        // There should not be test with such id after deletion
        assertEquals(HttpStatus.SC_NO_CONTENT, stub.getResponse().getStatus());
    }
    
    @Test
    public void testUpdateTestOptions() {
        LoaderoTestOptions newTest = new LoaderoTestOptions();
        newTest.setMode(LoaderoTestModeType.LOAD);
        newTest.setIncrementStrategy(LoaderoIncrementStrategyType.LINEAR_GROUP);
        newTest.setParticipantTimeout(150);
        newTest.setStartInterval(5);
        String jsonRes = gson.toJson(newTest);
        
        testOptionsHelper(jsonRes);
        
        LoaderoTestOptions updatedTest = loaderoClient.updateTestOptionsById(TEST_ID, newTest);
        assertNotNull(updatedTest);
        assertEquals(150, updatedTest.getParticipantTimeout());
        assertEquals(LoaderoTestModeType.LOAD, updatedTest.getMode());
    }
    
    @Test
    public void testUpdateTestOptionsWithEnums() {
        LoaderoTestOptions newTest = new LoaderoTestOptions();
        newTest.setMode(LoaderoTestModeType.PERFORMANCE);
        newTest.setIncrementStrategy(LoaderoIncrementStrategyType.LINEAR_GROUP);
        newTest.setParticipantTimeout(150);
        newTest.setStartInterval(5);
        String jsonRes = gson.toJson(newTest);
        testOptionsHelper(jsonRes);
        
        LoaderoTestOptions updatedTest = loaderoClient.updateTestOptionsById(TEST_ID, newTest);
        assertNotNull(updatedTest);
        assertEquals(150, updatedTest.getParticipantTimeout());
        assertEquals(LoaderoTestModeType.PERFORMANCE, updatedTest.getMode());
        assertEquals(LoaderoIncrementStrategyType.LINEAR_GROUP, updatedTest.getIncrementStrategy());
    }
    
    @Test
    public void negativeUpdateTestOptions() {
        int testId = 23423;
        LoaderoTestOptions test = new LoaderoTestOptions();
        test.setMode(LoaderoTestModeType.PERFORMANCE);
        test.setName("negative test");
        String json = gson.toJson(test);
        
        StubMapping stub = wmRule.stubFor(put(urlMatching(".*/tests/" + testId + "/"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.SC_NOT_FOUND)
                        .withBody(json)));
        
        LoaderoTestOptions nullTest = loaderoClient.updateTestOptionsById(testId, test);
        assertNull(nullTest);
    }
    
}
