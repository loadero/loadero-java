package loadero.controller;

import loadero.model.LoaderoModel;
import loadero.model.LoaderoTestOptions;
import lombok.Getter;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;

import java.net.URI;
import java.util.List;

@Getter
public abstract class LoaderoAbstractController {
    private final URI uri;
    private final String loaderoApiToken;
    private final String projectId;
    private final String testId;
    private final HttpClientBuilder client = HttpClients.custom();

    public LoaderoAbstractController(URI uri, String loaderoApiToken,
                                     String projectId, String testId) {
        this.uri = uri;
        this.loaderoApiToken = loaderoApiToken;
        this.projectId = projectId;
        this.testId = testId;
    }

    /**
     * Retrieves LoaderoModel i.e test, participant or group description
     * for the specific test in specific project.
     */
    public abstract LoaderoModel get();

    /**
     * Update operation on existing LoaderoModel by supplying new LoaderoModel
     * @param newModel
     * @return
     */
    public abstract LoaderoModel update(LoaderoModel newModel);
}
