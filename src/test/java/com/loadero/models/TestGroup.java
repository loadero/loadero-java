package com.loadero.models;

import com.github.tomakehurst.wiremock.stubbing.Scenario;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loadero.AbstractTestLoadero;
import com.loadero.Loadero;
import com.loadero.exceptions.ApiException;
import com.loadero.model.Group;
import com.loadero.model.GroupParams;
import com.loadero.model.ParticipantParams;
import java.io.IOException;
import java.util.List;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfEnvironmentVariable;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.delete;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.put;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestGroup extends AbstractTestLoadero {
    String groupUrl = String.format(".*/tests/%s/groups/[0-9]+/", TEST_ID);
    private static final String groupJson = "body-projects-5040-tests-6866-groups-48797-m03sm.json";
    private static final String allGroupsFile = "body-all-groups.json";
    private static final Gson GSON = new GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .create();

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

        wmRule.stubFor(put(urlMatching(".*/groups/[0-9]*/"))
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
    public void negativeUpdate() {
        wmRule.stubFor(get(urlMatching(groupUrl))
            .willReturn(aResponse()
                .withStatus(HttpStatus.SC_OK)
                .withBodyFile(groupJson))
        );

        wmRule.stubFor(get(urlMatching(groupUrl))
            .willReturn(aResponse()
                .withStatus(HttpStatus.SC_UNPROCESSABLE_ENTITY))
        );

        Assertions.assertThrows(ApiException.class, () -> {
            GroupParams newParams = GroupParams.builder()
                .withId(GROUP_ID)     // For group with id
                .withTestId(TEST_ID)  // in test with id
                .build();             // build params
            Group group = Group.update(newParams);
        });
        Assertions.assertThrows(ApiException.class, () -> {
            wmRule.stubFor(get(urlMatching(".*/groups/.*"))
                .willReturn(aResponse()
                    .withStatus(HttpStatus.SC_NOT_FOUND))
            );

            GroupParams newParams = GroupParams.builder()
                .withTestId(TEST_ID)  // in test with id
                .build();             // build params
            Group group = Group.update(newParams);
        });
        Assertions.assertThrows(ApiException.class, () -> {
            wmRule.stubFor(get(urlMatching(".*/groups/.*"))
                .willReturn(aResponse()
                    .withStatus(HttpStatus.SC_NOT_FOUND))
            );

            GroupParams newParams = GroupParams.builder()
                .withId(GROUP_ID)     // For group with id
                .build();             // build params
            Group group = Group.update(newParams);
        });
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

        com.loadero.model.Group group = Group.create(params);
        assertNotNull(group);

        wmRule.stubFor(delete(urlMatching(".*/groups/[0-9]*/"))
            .inScenario("deleteCreate")
            .whenScenarioStateIs("created")
            .willReturn(aResponse()
                .withStatus(HttpStatus.SC_NO_CONTENT)));

        // Since delete() doesn't return anything in mocked environment
        // this exception means that everything is okay
        Group.delete(TEST_ID, GROUP_ID);
    }

    @Test
    public void negativeCreate() {
        Assertions.assertThrows(ApiException.class, () -> {
            wmRule.stubFor(post(urlMatching(".*/groups/"))
                .willReturn(aResponse()
                    .withStatus(HttpStatus.SC_UNPROCESSABLE_ENTITY))
            );
            GroupParams params =
                GroupParams.builder()
                    .withCount(1)
                    .withTestId(TEST_ID)
                    .build();
            Group create = Group.create(params);
        });

        Assertions.assertThrows(ApiException.class, () -> {
            wmRule.stubFor(post(urlMatching(".*/groups/"))
                .willReturn(aResponse()
                    .withStatus(HttpStatus.SC_NOT_FOUND))
            );
            GroupParams params =
                GroupParams.builder()
                    .withName("name")
                    .withCount(1)
                    .build();
            Group create = Group.create(params);
        });
    }

    @Test
    public void negativeDelete() {
        wmRule.stubFor(delete(urlMatching(".*/groups/[0-9]*/"))
            .willReturn(aResponse()
                .withStatus(HttpStatus.SC_NOT_FOUND))
        );
        Assertions.assertThrows(ApiException.class, () -> {
            Group.delete(TEST_ID, 1);
        });
        Assertions.assertThrows(ApiException.class, () -> {
            Group.delete(1, GROUP_ID);
        });
    }

    @Test
    public void testCopyGroup() throws IOException {
        wmRule.stubFor(get(urlMatching(groupUrl))
            .willReturn(aResponse()
                .withStatus(HttpStatus.SC_OK)
                .withBodyFile(groupJson))
        );

        wmRule.stubFor(post(urlMatching(".*/groups/[0-9]*/copy/"))
            .willReturn(aResponse()
                .withStatus(HttpStatus.SC_CREATED)
                .withBodyFile(groupJson)));

        wmRule.stubFor(delete(urlMatching(".*/groups/[0-9]*/"))
            .willReturn(aResponse()
                .withStatus(HttpStatus.SC_NO_CONTENT)));

        Group original = Group.read(TEST_ID, GROUP_ID);
        Group copy = Group.copy(TEST_ID, GROUP_ID, original.getName() + "Copy");

        Assertions.assertNotNull(copy);
        Assertions.assertEquals(original.getCount(), copy.getCount());
        Group.delete(TEST_ID, copy.getId());
    }

    @Test
    public void negativeCopy() {
        Assertions.assertThrows(ApiException.class, () -> {
            wmRule.stubFor(post(urlMatching(".*/groups/[0-9]*/copy/"))
                .willReturn(aResponse()
                    .withStatus(HttpStatus.SC_NOT_FOUND))
            );
            Group copy = Group.copy(TEST_ID, 1, "Copy");
        });
        Assertions.assertThrows(ApiException.class, () -> {
            wmRule.stubFor(post(urlMatching(".*/groups/[0-9]*/copy/"))
                .willReturn(aResponse()
                    .withStatus(HttpStatus.SC_NOT_FOUND))
            );
            Group copy = Group.copy(1, 1, "Copy");
        });
    }

    @Test
    public void emptyOrNullNameException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            GroupParams params = GroupParams.builder()
                .withName("")
                .build();
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            GroupParams params = GroupParams.builder()
                .withName(null)
                .build();
        });
    }

    @Test
    public void testReadAll() throws IOException {
        wmRule.stubFor(get(urlMatching(".*/groups/"))
            .willReturn(aResponse()
                .withStatus(200)
                .withBodyFile(allGroupsFile)
            )
        );
        List<Group> groupList = Group.readAll(TEST_ID);
        Assertions.assertNotNull(groupList);
    }

    @Test
    public void negativeReadAll() {
        wmRule.stubFor(get(urlMatching(".*/groups/"))
            .willReturn(aResponse()
                .withStatus(404)
            )
        );

        Assertions.assertThrows(ApiException.class, () -> {
            List<Group> groupList = Group.readAll(111);
        });
    }
}
