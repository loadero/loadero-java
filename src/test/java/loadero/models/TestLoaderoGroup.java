package loadero.models;

import com.github.tomakehurst.wiremock.stubbing.Scenario;
import com.github.tomakehurst.wiremock.stubbing.StubMapping;
import loadero.model.LoaderoGroup;
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
    public void testCreateAndDeleteGroup() {
        LoaderoGroup newGroup = new LoaderoGroup();
        newGroup.setName("name");
        newGroup.setCount(3);
        String json = gson.toJson(newGroup);
       
        wmRule.stubFor(post(urlMatching( ".*/groups/"))
                .inScenario("deleteCreate")
                .whenScenarioStateIs(Scenario.STARTED)
                .willReturn(aResponse()
                        .withStatus(HttpStatus.SC_CREATED)
                        .withBody(json))
                .willSetStateTo("created"));
            
        
        LoaderoGroup group = loaderoClient.createNewGroup(newGroup, TEST_ID);
        assertNotNull(group);
    
        StubMapping deleteStub = wmRule.stubFor(delete(urlMatching(".*/groups/" + group.getId()))
                .inScenario("deleteCreate")
                .whenScenarioStateIs("created")
                .willReturn(aResponse()
                        .withStatus(HttpStatus.SC_NO_CONTENT)));
    
        loaderoClient.deleteGroupById(TEST_ID, group.getId());
        assertEquals(HttpStatus.SC_NO_CONTENT, deleteStub.getResponse().getStatus());
    }
    
    @Test
    public void testUpdateGroup() {
        LoaderoGroup newGroup = new LoaderoGroup();
        newGroup.setCount(3);
        String jsonRes = gson.toJson(newGroup);
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
