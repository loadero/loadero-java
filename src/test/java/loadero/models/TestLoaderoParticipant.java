package loadero.models;

import loadero.model.LoaderoParticipant;
import loadero.types.LoaderoComputeUnitsType;
import loadero.utils.LoaderoClientUtils;
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
        assertEquals(LoaderoComputeUnitsType.G2, updatedPartic.getComputeUnit());
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
