package com.loadero.models;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.delete;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static com.github.tomakehurst.wiremock.client.WireMock.get;

import com.loadero.AbstractTestLoadero;
import com.loadero.Loadero;
import com.loadero.model.Participant;
import com.loadero.model.ParticipantParams;
import java.io.IOException;
import com.loadero.types.*;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfEnvironmentVariable;

public class TestParticipant extends AbstractTestLoadero {
    private static final String participantJson =
        "body-projects-5040-tests-6866-participants-94633-aZHuT.json";

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
    @DisabledIfEnvironmentVariable(named = "LOADERO_BASE_URL", matches = ".*localhost.*")
    public void testUpdateParticipant() throws IOException {
        ParticipantParams newParams = ParticipantParams
            .builder()
            .withName("new name")
            .withId(PARTICIPANT_ID)
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
        assertNotEquals(read.getName(), update.getName());
    }

    @Test
    @DisabledIfEnvironmentVariable(named = "LOADERO_BASE_URL", matches = ".*localhost.*")
    public void testCreateAndDeleteParticipant() throws IOException {
        ParticipantParams params = ParticipantParams
            .builder()
            .withName("participant1")
            .withCount(1)
            .withLocation(Location.EU_WEST_1)
            .withNetwork(Network.DEFAULT)
            .withBrowser(new Browser(BrowserLatest.CHROME_LATEST))
            .withComputeUnit(ComputeUnit.G2)
            .withMediaType(MediaType.DEFAULT)
            .withGroupId(GROUP_ID)
            .withTestId(TEST_ID)
            .withRecordAudio(false)
            .build();
        com.loadero.model.Participant create = com.loadero.model.Participant.create(params);
        assertNotNull(create);
        com.loadero.model.Participant
            .delete(create.getTestId(), create.getGroupId(), create.getId());
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
}
