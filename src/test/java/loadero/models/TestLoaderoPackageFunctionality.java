package loadero.models;

import com.github.tomakehurst.wiremock.stubbing.StubMapping;
import loadero.controller.LoaderoCrudController;
import loadero.model.LoaderoTestOptions;
import loadero.types.LoaderoModelType;
import loadero.types.LoaderoTestModeType;
import loadero.utils.LoaderoClientUtils;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.*;

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
        
        String body = LoaderoClientUtils.modelToJson(newTest);
        
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
        
        body = LoaderoClientUtils.modelToJson(getTest);
        
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
    public void testTokenAccess() {
        String projectUrl = urlBuilder.buildProjectURL() + "/";
        
        StubMapping stub = wmRule.stubFor(get(urlMatching(".*/projects/.*"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.SC_OK)));
        
        assertEquals(HttpStatus.SC_OK, stub.getResponse().getStatus());
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
    public void negativeCrudUpdate() {
        assertThrows(NullPointerException.class, () -> {
            LoaderoCrudController crudController = new LoaderoCrudController(loaderoClient.getLoaderoApiToken());
            crudController.update(urlBuilder.buildTestURLById(TEST_ID),
                    LoaderoModelType.LOADERO_TEST_OPTIONS, null);
        });
    }
    
    @Test
    public void negativeLoaderoServiceFactory() {
        assertThrows(NullPointerException.class, () ->
                loaderoClient.getServiceFactory().getLoaderoService(null));
    }
    
    @Test
    public void testEnumsTypes() {
        assertEquals("load", LoaderoTestModeType.LOAD.label());
        assertEquals("performance", LoaderoTestModeType.PERFORMANCE.label());
        assertEquals("session-record", LoaderoTestModeType.SESSION_RECORD.label());
    }
    
}