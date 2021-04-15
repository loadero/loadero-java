package com.loadero.models;

import com.github.tomakehurst.wiremock.stubbing.StubMapping;
import com.loadero.AbstractTestLoadero;
import loadero.model.Test;
import loadero.types.IncrementStrategy;
import loadero.types.TestMode;
import org.apache.http.HttpStatus;

import java.net.URI;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.*;

public class TestTestMethods extends AbstractTestLoadero {
    
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
    
    @org.junit.jupiter.api.Test
    public void testGetTestOptionsById() {
        wmRule.stubFor(get(urlMatching(".*/tests/" + TEST_ID + "/"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.SC_OK)
                        .withBodyFile("body-projects-5040-tests-6866-uaor7.json")));
        
        Test test = loaderoClient.getTestById(TEST_ID);
        assertNotNull(test);
        assertEquals(TEST_ID, test.getId());
    }
    
    @org.junit.jupiter.api.Test
    public void negativeGetTestOptionsById() {
        Test test = loaderoClient.getTestById(0);
        assertNull(test);
    }
    
    @org.junit.jupiter.api.Test
    public void testCreateNewTest() {
        Test testOptions = new Test();
        testOptions.setName("new test 5");
        testOptions.setMode(TestMode.LOAD);
        testOptions.setIncrementStrategy(IncrementStrategy.LINEAR_GROUP);
        testOptions.setStartInterval(10);
        testOptions.setParticipantTimeout(200);
        testOptions.setScript(URI.create("/Users/mihhail.matisinets/Desktop/Projects/loadero-rest-api-wrapper" +
                "/src/main/resources/loadero/scripts/testui/LoaderoScript.java"));
        
        String body = gson.toJson(testOptions);
        
        StubMapping stub = wmRule.stubFor(post(urlPathMatching(".*/tests/"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.SC_CREATED)
                        .withBody(body)));
        
        
        Test newTest = loaderoClient.createNewTest(testOptions);
        assertEquals(HttpStatus.SC_CREATED, stub.getResponse().getStatus());
        assertNotNull(newTest);
    }
    
    @org.junit.jupiter.api.Test
    public void testDeleteTestById() {
        int testId = 7443;
        StubMapping stub = wmRule.stubFor(delete(urlPathMatching(".*/tests/" + testId + "/"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.SC_NO_CONTENT)));
        
        loaderoClient.deleteTestById(testId);
        // There should not be test with such id after deletion
        assertEquals(HttpStatus.SC_NO_CONTENT, stub.getResponse().getStatus());
    }
    
    @org.junit.jupiter.api.Test
    public void testUpdateTestOptions() {
        Test newTest = new Test();
        newTest.setMode(TestMode.LOAD);
        newTest.setIncrementStrategy(IncrementStrategy.LINEAR_GROUP);
        newTest.setParticipantTimeout(150);
        newTest.setStartInterval(5);
        String jsonRes = gson.toJson(newTest);
        
        testOptionsHelper(jsonRes);
        
        Test updatedTest = loaderoClient.updateTestById(TEST_ID, newTest);
        assertNotNull(updatedTest);
        assertEquals(150, updatedTest.getParticipantTimeout());
        assertEquals(TestMode.LOAD, updatedTest.getMode());
    }
    
    @org.junit.jupiter.api.Test
    public void testUpdateTestOptionsWithEnums() {
        Test newTest = new Test();
        newTest.setMode(TestMode.PERFORMANCE);
        newTest.setIncrementStrategy(IncrementStrategy.LINEAR_GROUP);
        newTest.setParticipantTimeout(150);
        newTest.setStartInterval(5);
        String jsonRes = gson.toJson(newTest);
        testOptionsHelper(jsonRes);
        
        Test updatedTest = loaderoClient.updateTestById(TEST_ID, newTest);
        assertNotNull(updatedTest);
        assertEquals(150, updatedTest.getParticipantTimeout());
        assertEquals(TestMode.PERFORMANCE, updatedTest.getMode());
        assertEquals(IncrementStrategy.LINEAR_GROUP, updatedTest.getIncrementStrategy());
    }
    
    @org.junit.jupiter.api.Test
    public void negativeUpdateTestOptions() {
        int testId = 23423;
        Test test = new Test();
        test.setMode(TestMode.PERFORMANCE);
        test.setName("negative test");
        String json = gson.toJson(test);
        
        StubMapping stub = wmRule.stubFor(put(urlMatching(".*/tests/" + testId + "/"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.SC_NOT_FOUND)
                        .withBody(json)));
        
        Test nullTest = loaderoClient.updateTestById(testId, test);
        assertNull(nullTest);
    }
    
}
