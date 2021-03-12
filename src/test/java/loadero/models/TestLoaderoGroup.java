package loadero.models;

import loadero.model.LoaderoGroup;
import loadero.utils.LoaderoClientUtils;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.*;

public class TestLoaderoGroup extends AbstractTestLoadero {
    
    @Test
    public void testGetGroupById() {
        String groupUrl = String.format(".*/tests/%s/groups/%s/", TEST_ID,
                GROUP_ID);
        
        wmRule.stubFor(get(urlMatching(groupUrl))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.SC_OK)
                        .withBodyFile("body-projects-5040-tests-6866-groups-48797-m03sm.json")));
        
        LoaderoGroup group = loaderoClient.getGroupById(TEST_ID, GROUP_ID);
        assertNotNull(group);
        assertEquals(GROUP_ID, group.getId());
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
    public void negativeGetGroupById() {
        LoaderoGroup group = loaderoClient.getGroupById(TEST_ID, 2342);
        assertNull(group);
    }
}
