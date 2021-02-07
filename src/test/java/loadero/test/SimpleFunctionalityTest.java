package loadero.test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import loadero.LoaderoClient;
import loadero.model.LoaderoGroup;
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


public class SimpleFunctionalityTest {
    private static final String token = System.getenv("LOADERO_API_TOKEN");
    private static final String GROUP_ID = "48797";
    private static final String PROJECT_ID = "5040";
    private static final String TEST_ID = "6866";
    private static final String PARTICIPANT_ID = "94633";
    private static final String RUNS_ID = "94633";
    private static final LoaderoClient client = new LoaderoClient(token, PROJECT_ID, TEST_ID);

    private static final URI testUri = URI.create(
            client.getBASE_URL()
                    + "/projects/"
                    + client.getProjectId()
                    + "/tests/"
                    + client.getTestId() + "/");

    @BeforeAll
    public static void testIfApiIsUp() {
        try {
            HttpUriRequest request = new HttpGet(client.getBASE_URL() + "/docs");
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
                client.buildTestURL());
    }

    @Test
    public void testGetTestOptions() {
        LoaderoTestOptions options = client.getTestOptions();
        assertEquals(options.getClass().getSimpleName(), LoaderoTestOptions.class.getSimpleName());
    }

    @Test
    public void testGetGroupById() {
        LoaderoGroup group = client.getGroupById(GROUP_ID);
        assertEquals(group.getClass().getSimpleName(),
                LoaderoGroup.class.getSimpleName());
    }

    @Test
    public void testGetParticipantById() {
        LoaderoParticipant participant = client.getParticipantById(
                PARTICIPANT_ID, GROUP_ID);
        assertEquals(participant.getClass().getSimpleName(),
                LoaderoParticipant.class.getSimpleName());
    }

    @Test
    public void testPolling() {

    }
}
