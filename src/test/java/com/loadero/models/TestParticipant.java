package com.loadero.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.loadero.AbstractTestLoadero;
import com.loadero.Loadero;
import com.loadero.model.ParticipantParams;
import java.io.IOException;
import loadero.model.Participant;
import loadero.types.Browser;
import loadero.types.BrowserLatest;
import loadero.types.ComputeUnit;
import loadero.types.Location;
import loadero.types.MediaType;
import loadero.types.Network;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfEnvironmentVariable;

public class TestParticipant extends AbstractTestLoadero {

    @Test
    public void testGetParticipantById() throws IOException {
        Loadero.init(BASE_URL, token, PROJECT_ID);
        com.loadero.model.Participant participant = com.loadero.model.Participant
            .read(TEST_ID, GROUP_ID, PARTICIPANT_ID);
        assertNotNull(participant);
    }

    @Test
    public void negativeGetParticipantWrongTestId() {
        Participant participant = loaderoClient.getParticipantById(234, GROUP_ID, PARTICIPANT_ID);
        assertNull(participant);
    }

    @Test
    public void negativeGetParticipantWrongParticipantId() {
        Participant participant = loaderoClient.getParticipantById(TEST_ID, GROUP_ID, 2312);
        assertNull(participant);
    }

    @Test
    @DisabledIfEnvironmentVariable(named = "LOADERO_BASE_URL", matches = ".*localhost.*")
    public void testUpdateParticipant() throws IOException {
        Loadero.init(BASE_URL, token, PROJECT_ID);
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
        assertEquals(read.getLocation(), update.getLocation());
        assertNotEquals(read.getName(), update.getName());
    }

    @Test
    @DisabledIfEnvironmentVariable(named = "LOADERO_BASE_URL", matches = ".*localhost.*")
    public void testCreateAndDeleteParticipant() throws IOException {
        Loadero.init(BASE_URL, token, PROJECT_ID);
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
