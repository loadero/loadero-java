package com.loadero.models;

import com.github.tomakehurst.wiremock.stubbing.StubMapping;
import com.loadero.AbstractTestLoadero;
import loadero.model.Test;
import loadero.types.TestModeType;
import org.apache.http.HttpStatus;

import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestPackageFunctionality extends AbstractTestLoadero {
    
    @org.junit.jupiter.api.Test
    public void testFullFunctionality() {
        Test newTest = new Test();
        Map<String, String> scriptParams = Map.of(
                "callDuration", "60",
                "elementTimeout", "10",
                "appUrl", "url"
        );
        newTest.setId(TEST_ID);
        newTest.setName("new test");
        newTest.setParticipantTimeout(300);
        newTest.setMode(TestModeType.LOAD);
        newTest.setStartInterval(20);
        newTest.setScript(
                "src/test/java/loadero/scripts/testui/TestOneOnOneCall.java",
                scriptParams);
        
        String body = gson.toJson(newTest);
        
        wmRule.stubFor(post(urlPathMatching(".*/tests/"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.SC_CREATED)
                        .withBody(body)));
        // Creating test
        Test createdTest = loaderoClient.createNewTest(newTest);
        assertNotNull(createdTest);
        
        wmRule.stubFor(get(urlMatching(".*/tests/" + TEST_ID + "/"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.SC_OK)
                        .withBody(body)));
        // Retreiving test
        Test getTest = loaderoClient.getTestById(TEST_ID);
        assertNotNull(getTest);
        assertEquals(TestModeType.LOAD, getTest.getMode());
        
        // Updating
        getTest.setMode(TestModeType.PERFORMANCE);
        getTest.setName("Performance test");
        
        body = gson.toJson(getTest);
        
        wmRule.stubFor(put(urlMatching(".*/tests/" + TEST_ID + "/"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.SC_OK)
                        .withBody(body)));
        
        Test updateTest = loaderoClient.updateTestById(TEST_ID, getTest);
        assertNotNull(updateTest);
        assertEquals(TestModeType.PERFORMANCE, updateTest.getMode());
        // Deleting test
        StubMapping deleteStub = wmRule.stubFor(delete(urlMatching(".*/tests/" + TEST_ID + "/"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.SC_NO_CONTENT)));
        loaderoClient.deleteTestById(TEST_ID);
        assertEquals(HttpStatus.SC_NO_CONTENT, deleteStub.getResponse().getStatus());
    }
    
    @org.junit.jupiter.api.Test
    public void testEnumsTypes() {
        assertEquals("load", TestModeType.LOAD.toString());
        assertEquals("performance", TestModeType.PERFORMANCE.toString());
        assertEquals("session-record", TestModeType.SESSION_RECORD.toString());
    }
    
}