package loadero;

import loadero.model.LoaderoModel;
import loadero.model.LoaderoTestOptions;

// Class mainly for some manual testing purposes
// Will be deleted later.
public class ManTests {
    private static final String token = System.getenv("LOADERO_API_TOKEN");
    private static final String PROJECT_ID = "5040";
    private static final String TEST_ID = "6866";

    public static void main(String[] args) {
        LoaderoClient client = new LoaderoClient(token, PROJECT_ID, TEST_ID);
        LoaderoTestOptions newOptions = new LoaderoTestOptions();
        newOptions.setName("New name 20, if script file not provided, script should stay the same");
        LoaderoTestOptions updatedOptions = (LoaderoTestOptions)
                client.updateTestOptions(newOptions);
        LoaderoModel testPoll = client.startTestAndPollInfo(20, 10);

    }
}
