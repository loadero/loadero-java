package com.loadero.models;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.delete;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.put;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.github.tomakehurst.wiremock.common.Json;
import com.github.tomakehurst.wiremock.stubbing.Scenario;
import com.github.tomakehurst.wiremock.stubbing.StubMapping;
import com.google.gson.Gson;
import com.loadero.AbstractTestLoadero;
import com.loadero.Loadero;
import com.loadero.exceptions.ApiException;
import com.loadero.http.ApiResource;
import com.loadero.http.RequestMethod;
import com.loadero.model.Group;
import com.loadero.model.GroupParams;
import java.io.IOException;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfEnvironmentVariable;

public class TestGroup extends AbstractTestLoadero {
    String groupUrl = String.format(".*/tests/%s/groups/[0-9]+/", TEST_ID);
    private static final String groupJson = "body-projects-5040-tests-6866-groups-48797-m03sm.json";
    private static final Gson GSON = new Gson();

    @BeforeAll
    public void init() {
        Loadero.init(BASE_URL, token, PROJECT_ID);
    }

    @Test
    public void retrieveGroup() throws IOException {
        wmRule.stubFor(get(urlMatching(groupUrl))
            .willReturn(aResponse()
                .withStatus(HttpStatus.SC_OK)
                .withBodyFile(groupJson)));

        com.loadero.model.Group group = com.loadero.model.Group.read(TEST_ID, GROUP_ID);
        Assertions.assertNotNull(group);
        Assertions.assertNotNull(group.getName());
    }

    @Test
    public void negativeRetrieveGroup() {
        wmRule.stubFor(get(urlMatching(groupUrl))
            .willReturn(aResponse()
                .withStatus(HttpStatus.SC_NOT_FOUND)
                .withBodyFile(groupJson))
        );

        Exception ex = assertThrows(ApiException.class, () -> {
            com.loadero.model.Group group = com.loadero.model.Group.read(TEST_ID, 1);
        });
        System.out.println(ex.getMessage()); // Just to see how message looks like
    }

    @Test
    @Disabled
    public void updateGroup() throws IOException {
        wmRule.stubFor(get(urlMatching(groupUrl))
            .willReturn(aResponse()
                .withStatus(HttpStatus.SC_OK)
                .withBodyFile(groupJson))
        );

        Group mockGroup = Group.read(TEST_ID, GROUP_ID);
        GroupParams mockParams = GroupParams.builder()
            .withId(mockGroup.getId())
            .withTestId(mockGroup.getTestId())
            .withName("new name")
            .withCount(mockGroup.getCount())
            .withParticipantCount(mockGroup.getParticipantCount())
            .withTimeUpdated(mockGroup.getUpdated())
            .withTimeCreated(mockGroup.getCreated())
            .build();

        String mockJson = GSON.toJson(mockParams);
        System.out.println(mockJson);

        wmRule.stubFor(put(urlMatching(groupUrl))
            .willReturn(aResponse()
                .withStatus(HttpStatus.SC_OK)
                .withBody(mockJson))
        );

        GroupParams newParams = GroupParams.builder()
            .withName("new name")
            .withId(GROUP_ID)     // For group with id
            .withTestId(TEST_ID)  // in test with id
            .build();             // build params

        com.loadero.model.Group group = com.loadero.model.Group.update(newParams);
        Assertions.assertEquals(3, group.getCount());
        Assertions.assertEquals("new name", group.getName());
    }

    @Test
    public void testCreateAndDeleteGroup() throws IOException {
        GroupParams params =
            GroupParams.builder()
                .withName("Name")
                .withCount(1)
                .withTestId(TEST_ID)
                .build();

        wmRule.stubFor(post(urlMatching(".*/groups/"))
            .inScenario("deleteCreate")
            .whenScenarioStateIs(Scenario.STARTED)
            .willReturn(aResponse()
                .withStatus(HttpStatus.SC_CREATED)
                .withBodyFile(groupJson))
            .willSetStateTo("created"));

        String postUrl = String.format("%s/tests/%s/groups/", Loadero.getProjectUrl(), TEST_ID);

        com.loadero.model.Group group = Group.create(params);
        assertNotNull(group);

        wmRule.stubFor(delete(urlMatching(".*/groups/" + group.getId()))
            .inScenario("deleteCreate")
            .whenScenarioStateIs("created")
            .willReturn(aResponse()
                .withStatus(HttpStatus.SC_NO_CONTENT)));

        // Since delete() doesn't return anything in mocked environment
        // this exception means that everything is okay
        Assertions
            .assertThrows(ApiException.class, () -> Group.delete(TEST_ID, GROUP_ID));
    }
}
