package loadero.models;

import com.github.tomakehurst.wiremock.stubbing.StubMapping;
import loadero.model.Assert;
import loadero.types.AssertOperator;
import loadero.types.MachineAsserts;
import loadero.types.WebRtcAsserts;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class TestAsserts extends AbstractTestLoadero {
    private static final String assertsUrl = String.format(".*/tests/%s/asserts", TEST_ID);
    private static final Assert mockAssert = new Assert(ASSERT_ID, MachineAsserts.MACHINE_CPU_25TH,
            AssertOperator.LESS_THAN, "expected", TEST_ID);
    @Test
    public void testGetAssertById() {
        String json = gson.toJson(mockAssert);
        StubMapping stub = wmRule.stubFor(get(urlMatching(String.format("%s/%s/", assertsUrl, ASSERT_ID)))
                .willReturn(aResponse()
                        .withBody(json)));
        
        Assert assertInfo = loaderoClient.getAssertById(TEST_ID, ASSERT_ID);
        log.info(assertInfo);
        Assertions.assertNotNull(assertInfo);
    }
    
    @Test
    public void testCreateAssert() {
        String json = gson.toJson(mockAssert);
        
        StubMapping stub = wmRule.stubFor(post(urlMatching(assertsUrl + "/"))
                .withRequestBody(containing(json))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.SC_CREATED)
                        .withBody(json)));
        
        
        Assert newAssert = loaderoClient.createNewAssert(mockAssert, TEST_ID);
        Assertions.assertNotNull(newAssert);
    }
    
    @Test
    public void testUpdateAssert() {
        String json = gson.toJson(mockAssert);
        
        StubMapping getCurrentAssert = wmRule.stubFor(get(String.format("%s/%s/", assertsUrl, TEST_ID))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.SC_OK)
                        .withBody(json)));
        
        Assert currentAssert = loaderoClient.getAssertById(TEST_ID, ASSERT_ID);
        currentAssert.setOperator(AssertOperator.EQUAL);
        currentAssert.setPath(WebRtcAsserts.WEBRTC_AUDIO_JITTER_25TH);
        String updateAssertJson = gson.toJson(currentAssert);
        StubMapping updateAssert = wmRule.stubFor(put(urlMatching(String.format("%s/%s/", assertsUrl, ASSERT_ID)))
                .withRequestBody(containing(updateAssertJson))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.SC_OK)
                        .withBody(updateAssertJson)));
        
        Assert updatedAssert = loaderoClient.updateAssertById(currentAssert, TEST_ID, ASSERT_ID);
        Assertions.assertNotNull(updatedAssert);
        Assertions.assertEquals(AssertOperator.EQUAL, updatedAssert.getOperator());
    }
    
    @Test
    public void testDeleteAssert() {
        StubMapping delete = wmRule.stubFor(delete(urlMatching(String.format("%s/%s/", assertsUrl, ASSERT_ID)))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.SC_NO_CONTENT)));
        loaderoClient.deleteAssertById(TEST_ID, ASSERT_ID);
        Assertions.assertEquals(HttpStatus.SC_NO_CONTENT, delete.getResponse().getStatus());
    }
}
