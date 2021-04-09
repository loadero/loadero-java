package com.loadero.models;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.delete;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.put;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.github.tomakehurst.wiremock.stubbing.Scenario;
import com.github.tomakehurst.wiremock.stubbing.StubMapping;
import com.google.gson.Gson;
import com.loadero.AbstractTestLoadero;
import com.loadero.Loadero;
import com.loadero.exceptions.ApiException;
import com.loadero.http.ApiResource;
import com.loadero.http.RequestMethod;
import com.loadero.model.GroupParams;
import java.io.IOException;
import loadero.model.Group;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfEnvironmentVariable;

public class TestGroup extends AbstractTestLoadero {
    String groupUrl = String.format(".*/tests/%s/groups/[0-9]+/", TEST_ID);
    private static final String groupJson = "body-projects-5040-tests-6866-groups-48797-m03sm.json";
    private static final Gson GSON = new Gson();

    @Test
    public void retrieveGroupTest() throws IOException {
        Loadero.init(BASE_URL, token, PROJECT_ID);
        wmRule.stubFor(get(urlMatching(groupUrl))
            .willReturn(aResponse()
                .withStatus(HttpStatus.SC_OK)
                .withBodyFile(groupJson)));

        com.loadero.model.Group group = com.loadero.model.Group.read(TEST_ID, GROUP_ID);
        Assertions.assertNotNull(group);
    }

    @Test
    public void negativeRetrieveTest() {
        Loadero.init(BASE_URL, token, PROJECT_ID);
        wmRule.stubFor(get(urlMatching(groupUrl))
            .willReturn(aResponse()
                .withStatus(HttpStatus.SC_OK)
                .withBodyFile(groupJson)));

        Exception ex = assertThrows(ApiException.class, () -> {
            com.loadero.model.Group group = com.loadero.model.Group.read(TEST_ID, 1);
        });
        System.out.println(ex.getMessage()); // Just to see how message looks like
    }

    @Test
    @DisabledIfEnvironmentVariable(named = "LOADERO_BASE_URL", matches = ".*localhost.*")
    public void updateGroupTest() throws IOException {
        Loadero.init(BASE_URL, token, PROJECT_ID);
        GroupParams newParams =
            GroupParams.builder()
                .withName("new name")
                .withId(GROUP_ID)        // For group with id
                .withTestId(TEST_ID)  // in test with id
                .build();             // build params

        String json = GSON.toJson(newParams);
        wmRule.stubFor(get(urlMatching(groupUrl))
            .willReturn(aResponse()
                .withStatus(HttpStatus.SC_OK)
                .withBodyFile(groupJson)));

        wmRule.stubFor(put(urlMatching(String.format(".*/tests/%s/groups/%s/",
            TEST_ID, GROUP_ID
        )))
            .willReturn(aResponse()
                .withStatus(HttpStatus.SC_OK)
                .withBody(json)));

        com.loadero.model.Group group = com.loadero.model.Group.update(newParams);
        Assertions.assertEquals(1, group.getCount());
        Assertions.assertEquals("new name", group.getName());
    }

    @Test
    @DisabledIfEnvironmentVariable(named = "LOADERO_BASE_URL", matches = ".*localhost.*")
    public void testCreateAndDeleteGroup() throws IOException {
        Loadero.init(BASE_URL, token, PROJECT_ID);
        GroupParams builder =
                GroupParams.builder()
                        .withName("Name")
                        .withCount(1)
                        .withTestId(TEST_ID)
                        .build();
        
        String json = ApiResource.getGSON().toJson(builder);

        wmRule.stubFor(post(urlMatching(".*/groups/"))
            .inScenario("deleteCreate")
            .whenScenarioStateIs(Scenario.STARTED)
            .willReturn(aResponse()
                .withStatus(HttpStatus.SC_CREATED)
                .withBody(json))
            .willSetStateTo("created"));

        String postUrl = String.format("%s/tests/%s/groups/", Loadero.getProjectUrl(), TEST_ID);

        com.loadero.model.Group group = ApiResource.request(RequestMethod.POST, postUrl,
            builder, com.loadero.model.Group.class
        );
        assertNotNull(group);

        wmRule.stubFor(delete(urlMatching(".*/groups/" + group.getId()))
            .inScenario("deleteCreate")
            .whenScenarioStateIs("created")
            .willReturn(aResponse()
                .withStatus(HttpStatus.SC_NO_CONTENT)));

        ApiResource.request(RequestMethod.DELETE, postUrl + group.getId() + "/", null, null);
    }

    @Test
    public void negativeUpdateTest() {
        Loadero.init(BASE_URL, token, PROJECT_ID);
        GroupParams params =
            GroupParams.builder()
                .withTestId(TEST_ID)
                .build();

        Exception ex = assertThrows(ApiException.class, () -> {
            com.loadero.model.Group group = com.loadero.model.Group.create(params);
        });
        System.out.println(ex.getMessage()); // Just see how message looks like
    }

    @Test
    public void testUpdateGroup() {
        Group newGroup = new Group();
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

        Group updatedGroup = loaderoClient.updateGroupById(TEST_ID, GROUP_ID, newGroup);
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
        wmRule.stubFor(get(urlMatching(".*/groups/.*"))
            .willReturn(aResponse()
                .withBody("")));
        Group group = loaderoClient.getGroupById(TEST_ID, 2342);
        assertNull(group);
    }
}
