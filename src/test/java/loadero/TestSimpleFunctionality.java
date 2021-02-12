package loadero;

import loadero.model.LoaderoGroup;
import loadero.model.LoaderoModel;
import loadero.model.LoaderoParticipant;
import loadero.model.LoaderoTestOptions;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpUriRequest;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.core5.http.HttpResponse;
import org.apache.hc.core5.http.HttpStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class TestSimpleFunctionality {
    private static final String token = System.getenv("LOADERO_API_TOKEN");
    private static final String baseUrl = "https://api.loadero.com/v2";
    private static final String GROUP_ID = "48797";
    private static final String PROJECT_ID = "5040";
    private static final String TEST_ID = "6866";
    private static final String PARTICIPANT_ID = "94633";
    private static final String RUNS_ID = "94633";
    private static final LoaderoClient client = new LoaderoClient(baseUrl, token, PROJECT_ID);

    private static final URI testUri = URI.create(client.buildTestURLById(TEST_ID));

    @BeforeAll
    public static void testIfApiIsUp() {
        try {
            HttpUriRequest request = new HttpGet(client.getBaseUrl() + "/docs");
            HttpResponse response = HttpClientBuilder.create()
                    .build().execute( request );
            assertEquals(HttpStatus.SC_OK, response.getCode());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @BeforeAll
    public static void checkIfLoaderoApiTokenPresent() {
        assertEquals(token.getClass(), String.class);
    }

    @Test
    public void testSpecificTestIdUrl() {
        assertEquals(testUri.toString(),
                client.buildTestURLById(TEST_ID));
    }

    @Test
    public void testGetTestOptions() {
        LoaderoModel options = client.getTestOptionsById(TEST_ID);
        assertEquals(options.getClass().getSimpleName(), LoaderoTestOptions.class.getSimpleName());
    }

    @Test
    public void testGetGroupById() {
        LoaderoModel group = client.getGroupById(TEST_ID, GROUP_ID);
        assertEquals(group.getClass().getSimpleName(),
                LoaderoGroup.class.getSimpleName());
    }

    @Test
    public void testGetParticipantById() {
        LoaderoModel participant = client.getParticipantById(TEST_ID,
                PARTICIPANT_ID);
        assertEquals(participant.getClass().getSimpleName(),
                LoaderoParticipant.class.getSimpleName());
    }
}
