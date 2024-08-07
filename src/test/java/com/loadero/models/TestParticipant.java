package com.loadero.models;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.delete;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.put;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static com.github.tomakehurst.wiremock.client.WireMock.get;

import com.google.gson.JsonObject;
import com.loadero.AbstractTestLoadero;
import com.loadero.Loadero;
import com.loadero.exceptions.ApiException;
import com.loadero.http.ApiResource;
import com.loadero.model.Participant;
import com.loadero.model.ParticipantParams;
import java.io.IOException;
import com.loadero.types.*;
import java.util.List;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class TestParticipant extends AbstractTestLoadero {
    private static final String participantJson =
        "body-projects-5040-tests-6866-participants-94633-aZHuT.json";
    private static final String allParticipantsFile = "body-all-participants.json";
    private static final String participantUrl = ".*/participants/[0-9]*/";

    @BeforeAll
    public void init() {
        Loadero.init(BASE_URL, token, PROJECT_ID);
    }

    @Test
    public void readParticipant() throws IOException {
        wmRule.stubFor(get(urlMatching(".*/participants/[0-9]*/"))
            .willReturn(aResponse()
                .withStatus(200)
                .withBodyFile(participantJson)));

        com.loadero.model.Participant participant = com.loadero.model.Participant
            .read(TEST_ID, GROUP_ID, PARTICIPANT_ID);
        assertNotNull(participant);
    }

    @Test
    public void negativeRead() {
        wmRule.stubFor(get(urlMatching(".*/participants/[0-9]*/"))
            .willReturn(aResponse()
                .withStatus(HttpStatus.SC_NOT_FOUND))
        );
        Assertions.assertThrows(ApiException.class, () -> {
            Participant participant = Participant.read(TEST_ID, GROUP_ID, 1);
        });
        Assertions.assertThrows(ApiException.class, () -> {
            Participant participant = Participant.read(TEST_ID, 1, PARTICIPANT_ID);
        });
        Assertions.assertThrows(ApiException.class, () -> {
            Participant participant = Participant.read(1, GROUP_ID, PARTICIPANT_ID);
        });
    }

    @Test
    public void testUpdateParticipant() throws IOException {
        ParticipantParams mockParams = ParticipantParams.builder()
            .withId(PARTICIPANT_ID)
            .withTestId(TEST_ID)
            .withGroupId(GROUP_ID)
            .withProfileId(1)
            .withName("Participant 1")
            .withLocation(Location.FRANKFURT)
            .withComputeUnit(ComputeUnit.G1)
            .withBrowser(new Browser(BrowserLatest.CHROME_LATEST))
            .withNetwork(Network.DEFAULT)
            .withAudioFeed(AudioFeed.DEFAULT)
            .withVideoFeed(VideoFeed.DEFAULT)
            .withCreated("now")
            .withUpdated("now")
            .withCount(4)
            .build();

        JsonObject mockJson = ApiResource.getGSON()
            .toJsonTree(mockParams, Participant.class)
            .getAsJsonObject();
        mockJson.addProperty("id", PARTICIPANT_ID);
        mockJson.addProperty("test_id", TEST_ID);
        mockJson.addProperty("group_id", GROUP_ID);
        mockJson.addProperty("profile_id", 1);
        mockJson.addProperty("video_feed", VideoFeed.DEFAULT.toString());
        mockJson.addProperty("audio_feed", AudioFeed.DEFAULT.toString());
        mockJson.addProperty("created", "date");
        mockJson.addProperty("updated", "date");

        wmRule.stubFor(get(urlMatching(participantUrl))
            .willReturn(aResponse()
                .withStatus(HttpStatus.SC_OK)
                .withBodyFile(participantJson))
        );

        wmRule.stubFor(put(urlMatching(participantUrl))
            .willReturn(aResponse()
                .withStatus(HttpStatus.SC_OK)
                .withBody(mockJson.toString()))
        );

        ParticipantParams newParams = ParticipantParams
            .builder()
            .withName("Participant 1")
            .withId(PARTICIPANT_ID)
            .withComputeUnit(ComputeUnit.G1)
            .withGroupId(GROUP_ID)
            .withTestId(TEST_ID)
            .build();
        // current participant
        com.loadero.model.Participant read = com.loadero.model.Participant
            .read(TEST_ID, GROUP_ID, PARTICIPANT_ID);
        assertNotNull(read);
        // updated
        com.loadero.model.Participant update = com.loadero.model.Participant.update(newParams);
        assertNotNull(update);
        assertEquals(read.getCount(), update.getCount());
        assertEquals("Participant 1", update.getName());
        assertEquals(ComputeUnit.G1, update.getComputeUnit());
    }

    @Test
    public void negativeUpdate() {
        wmRule.stubFor(get(urlMatching(participantUrl))
            .willReturn(aResponse()
                .withStatus(HttpStatus.SC_OK)
                .withBodyFile(participantJson))
        );

        wmRule.stubFor(put(urlMatching(participantUrl))
            .willReturn(aResponse()
                .withStatus(HttpStatus.SC_UNPROCESSABLE_ENTITY))
        );

        Assertions.assertThrows(ApiException.class, () -> {
            ParticipantParams newParams = ParticipantParams
                .builder()
                .withId(PARTICIPANT_ID)
                .withGroupId(GROUP_ID)
                .withTestId(TEST_ID)
                .build();
            Participant update = Participant.update(newParams);
        });
    }

    @Test
    public void testCreateAndDeleteParticipant() throws IOException {
        ParticipantParams params = ParticipantParams
            .builder()
            .withName("participant1")
            .withCount(1)
            .withLocation(Location.OREGON)
            .withNetwork(Network.DEFAULT)
            .withBrowser(new Browser(BrowserLatest.CHROME_LATEST))
            .withComputeUnit(ComputeUnit.G2)
            .withGroupId(GROUP_ID)
            .withTestId(TEST_ID)
            .withRecordAudio(false)
            .build();

        JsonObject mockJson = ApiResource.getGSON()
            .toJsonTree(params, Participant.class)
            .getAsJsonObject();
        mockJson.addProperty("test_id", TEST_ID);
        mockJson.addProperty("group_id", GROUP_ID);
        mockJson.addProperty("profile_id", 1);
        mockJson.addProperty("id", PARTICIPANT_ID);
        mockJson.addProperty("count", 1);
        mockJson.addProperty("video_feed", VideoFeed.DEFAULT.toString());
        mockJson.addProperty("audio_feed", AudioFeed.DEFAULT.toString());
        mockJson.addProperty("created", "date");
        mockJson.addProperty("updated", "date");

        wmRule.stubFor(post(urlMatching(".*/participants/"))
            .willReturn(aResponse()
                .withStatus(HttpStatus.SC_CREATED)
                .withBody(mockJson.toString())
            )
        );

        wmRule.stubFor(delete(urlMatching(participantUrl))
            .willReturn(aResponse().withStatus(HttpStatus.SC_NO_CONTENT))
        );
        com.loadero.model.Participant create = com.loadero.model.Participant.create(params);
        assertNotNull(create);
        com.loadero.model.Participant
            .delete(create.getTestId(), create.getGroupId(), create.getId());
    }

    @Test
    public void negativeCreate() {
        wmRule.stubFor(post(urlMatching(".*/participants/"))
            .willReturn(aResponse()
                .withStatus(HttpStatus.SC_UNPROCESSABLE_ENTITY))
        );

        Assertions.assertThrows(NullPointerException.class, () -> {
            ParticipantParams params = ParticipantParams.builder()
                .withNetwork(Network.DEFAULT)
                .withTestId(TEST_ID)
                .withGroupId(GROUP_ID)
                .build();
            Participant create = Participant.create(params);
        });
    }

    @Test
    public void testCopyParticipant() throws IOException {
        wmRule.stubFor(get(urlMatching(".*/participants/[0-9]*/"))
            .willReturn(aResponse()
                .withStatus(200)
                .withBodyFile(participantJson)));

        wmRule.stubFor(post(urlMatching(".*/participants/[0-9]*/copy/"))
            .willReturn(aResponse()
                .withStatus(200)
                .withBodyFile(participantJson)));

        wmRule.stubFor(delete(urlMatching(".*/participants/[0-9]*/"))
            .willReturn(aResponse()
                .withStatus(HttpStatus.SC_NO_CONTENT)
                .withBody("")
            ));

        Participant original = Participant.read(TEST_ID, GROUP_ID, PARTICIPANT_ID);
        Participant copy = Participant.copy(
            TEST_ID, GROUP_ID, PARTICIPANT_ID, "Copy"
        );

        Assertions.assertNotNull(copy);
        Assertions.assertEquals(original.getCount(), copy.getCount());
        Assertions.assertEquals(original.getComputeUnit(), copy.getComputeUnit());

        Participant.delete(TEST_ID, GROUP_ID, copy.getId());
    }

    @Test
    public void negativeCopy() {
        wmRule.stubFor(post(urlMatching(".*/participants/[0-9]*/copy/"))
            .willReturn(aResponse()
                .withStatus(HttpStatus.SC_NOT_FOUND))
        );

        Assertions.assertThrows(ApiException.class, () -> {
            Participant copy = Participant.copy(
                TEST_ID, GROUP_ID, 1, "Copy"
            );
        });
        Assertions.assertThrows(ApiException.class, () -> {
            Participant copy = Participant.copy(
                TEST_ID, 1, PARTICIPANT_ID, "Copy"
            );
        });
        Assertions.assertThrows(ApiException.class, () -> {
            Participant copy = Participant.copy(
                1, GROUP_ID, PARTICIPANT_ID, "Copy"
            );
        });
    }

    @Test
    public void emptyOrNullNameException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            ParticipantParams params = ParticipantParams.builder()
                .withName("")
                .build();
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            ParticipantParams params = ParticipantParams.builder()
                .withName(null)
                .build();
        });
    }

    @Test
    public void testReadAll() throws IOException {
        wmRule.stubFor(get(urlMatching(".*/participants/"))
            .willReturn(aResponse()
                .withStatus(200)
                .withBodyFile(allParticipantsFile)
            )
        );

        List<Participant> participants = Participant.readAll(TEST_ID, GROUP_ID);
        Assertions.assertNotNull(participants);
    }

    @Test
    public void negativeReadAll() {
        wmRule.stubFor(get(urlMatching(".*/participants/"))
            .willReturn(aResponse()
                .withStatus(404)
            )
        );

        Assertions.assertThrows(ApiException.class, () -> {
            List<Participant> participants = Participant.readAll(1, GROUP_ID);
        });

        Assertions.assertThrows(ApiException.class, () -> {
            List<Participant> participants = Participant.readAll(TEST_ID, 12);
        });
    }
  
    @Test
    public void testLocationDeserialization() {
        String configJson1 = "frankfurt";
        String configJson2 = "sao-paulo";
        String configJson3 = "hong-kong";
        String configJson4 = "north-virginia";
        Location config1 = gson.fromJson(configJson1, Location.class);
        Location config2 = gson.fromJson(configJson2, Location.class);
        Location config3 = gson.fromJson(configJson3, Location.class);
        Location config4 = gson.fromJson(configJson4, Location.class);
        Assertions.assertEquals(Location.FRANKFURT, config1);
        Assertions.assertEquals(Location.SAO_PAULO, config2);
        Assertions.assertEquals(Location.HONG_KONG, config3);
        Assertions.assertEquals(Location.NORTH_VIRGINIA, config4);
    }
}
