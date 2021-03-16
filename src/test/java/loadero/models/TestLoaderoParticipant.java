package loadero.models;

import com.github.tomakehurst.wiremock.stubbing.StubMapping;
import loadero.model.LoaderoParticipant;
import loadero.types.*;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.*;

public class TestLoaderoParticipant extends AbstractTestLoadero {
    
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
        assertEquals(PARTICIPANT_ID, participant.getId());
    }
    
    @Test
    public void negativeGetParticipantWrongTestId() {
        LoaderoParticipant participant = loaderoClient.getParticipantById(234, GROUP_ID, PARTICIPANT_ID);
        assertNull(participant);
    }
    
    @Test
    public void negativeGetParticipantWrongParticipantId() {
        LoaderoParticipant participant = loaderoClient.getParticipantById(TEST_ID, GROUP_ID, 2312);
        assertNull(participant);
    }
    
    @Test
    public void testUpdateParticipant() {
        String url = String.format(".*/tests/%s/groups/%s/participants/%s/", TEST_ID, GROUP_ID, PARTICIPANT_ID);
        LoaderoParticipant newParticipant = new LoaderoParticipant();
        newParticipant.setName("unit test partic");
        newParticipant.setCount(2);
        newParticipant.setComputeUnit(LoaderoComputeUnitsType.G2);
        String updateRes = gson.toJson(newParticipant);
        
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
        assertEquals(LoaderoComputeUnitsType.G2, updatedPartic.getComputeUnit());
    }
    
    @Test
    public void testCreateAndDeleteParticipant() {
        LoaderoParticipant participant = new LoaderoParticipant();
        participant.setName("new participant");
        participant.setCount(1);
        participant.setComputeUnit(LoaderoComputeUnitsType.G2);
        participant.setBrowser(LoaderoBrowserType.CHROME_LATEST);
        participant.setLocation(LoaderoLocationType.EU_WEST_1);
        participant.setNetwork(LoaderoNetworkType.DEFAULT);
        participant.setMediaType(LoaderoMediaType.DEFAULT);
        
        String json = gson.toJson(participant);
        wmRule.stubFor(post(urlMatching(".*/participants/"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.SC_CREATED)
                        .withBody(json)));
        
        LoaderoParticipant newParticipant = loaderoClient.createParticipantById(TEST_ID, GROUP_ID, participant);
        assertNotNull(newParticipant);
        
        StubMapping deleteStub = wmRule.stubFor(delete(urlMatching(".*/participant/*."))
                .willReturn(aResponse().withStatus(HttpStatus.SC_NO_CONTENT)));
        
        loaderoClient.deleteParticipantById(TEST_ID, GROUP_ID, newParticipant.getId());
        assertEquals(HttpStatus.SC_NO_CONTENT, deleteStub.getResponse().getStatus());
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
                        updateTestParticipantById(TEST_ID, GROUP_ID, 0, null)
        );
    }
    
    @Test
    public void negativeUpdateParticipantTestIdWrong() {
        // Should throw null pointer exception
        assertThrows(NullPointerException.class, () ->
                loaderoClient.
                        updateTestParticipantById(1, GROUP_ID, PARTICIPANT_ID, null)
        );
    }
}
