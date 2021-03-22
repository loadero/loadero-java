package loadero.models;

import com.github.tomakehurst.wiremock.stubbing.StubMapping;
import loadero.model.LoaderoTestOptions;
import loadero.types.LoaderoTestModeType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestLoaderoPackageFunctionality extends AbstractTestLoadero {
    
    @Test
    public void testFullFunctionality() {
        LoaderoTestOptions newTest = new LoaderoTestOptions();
        Map<String, String> scriptParams = Map.of(
                "callDuration", "60",
                "elementTimeout", "10",
                "appUrl", "url"
        );
        newTest.setId(TEST_ID);
        newTest.setName("new test");
        newTest.setParticipantTimeout(300);
        newTest.setMode(LoaderoTestModeType.LOAD);
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
        LoaderoTestOptions createdTest = loaderoClient.createNewTest(newTest);
        assertNotNull(createdTest);
        
        wmRule.stubFor(get(urlMatching(".*/tests/" + TEST_ID + "/"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.SC_OK)
                        .withBody(body)));
        // Retreiving test
        LoaderoTestOptions getTest = loaderoClient.getTestOptionsById(TEST_ID);
        assertNotNull(getTest);
        assertEquals(LoaderoTestModeType.LOAD, getTest.getMode());
        
        // Updating
        getTest.setMode(LoaderoTestModeType.PERFORMANCE);
        getTest.setName("Performance test");
        
        body = gson.toJson(getTest);
        
        wmRule.stubFor(put(urlMatching(".*/tests/" + TEST_ID + "/"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.SC_OK)
                        .withBody(body)));
        
        LoaderoTestOptions updateTest = loaderoClient.updateTestOptionsById(TEST_ID, getTest);
        assertNotNull(updateTest);
        assertEquals(LoaderoTestModeType.PERFORMANCE, updateTest.getMode());
        // Deleting test
        StubMapping deleteStub = wmRule.stubFor(delete(urlMatching(".*/tests/" + TEST_ID + "/"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.SC_NO_CONTENT)));
        loaderoClient.deleteTestById(TEST_ID);
        assertEquals(HttpStatus.SC_NO_CONTENT, deleteStub.getResponse().getStatus());
    }
    
    @Test
    public void testEnumsTypes() {
        assertEquals("load", LoaderoTestModeType.LOAD.toString());
        assertEquals("performance", LoaderoTestModeType.PERFORMANCE.toString());
        assertEquals("session-record", LoaderoTestModeType.SESSION_RECORD.toString());
    }
    
}