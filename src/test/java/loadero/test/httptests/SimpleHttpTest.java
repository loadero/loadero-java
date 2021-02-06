package loadero.test.httptests;
import static org.junit.jupiter.api.Assertions.assertEquals;

import loadero.LoaderoClient;
import loadero.model.LoaderoTestOptions;
import org.junit.jupiter.api.Test;

import java.net.URI;


public class SimpleHttpTest {
    private static final String token = System.getenv("LOADERO_API_TOKEN");
    private static final LoaderoClient client = new LoaderoClient(token);
    private static final URI testUri = URI.create(
            client.getBASE_URL()
                    +"/projects/"
                    + client.getPROJECT_ID()
                    +"/tests/"
                    +client.getTEST_ID() + "/");

    @Test
    public void testSpecificTestIdUrl() {
        assertEquals(testUri.toString(),
                client.buildTestURL());
    }

    @Test
    public void testGetTestOptions() {
        LoaderoTestOptions options = client.getTestOptions();
        System.out.println(options);
        assertEquals(options.getClass().getSimpleName(), LoaderoTestOptions.class.getSimpleName());
    }
}
