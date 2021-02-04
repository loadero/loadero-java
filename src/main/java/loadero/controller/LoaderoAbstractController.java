package loadero.controller;

import loadero.model.LoaderoModel;
import lombok.Getter;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;

import java.net.URI;
@Getter
public abstract class LoaderoAbstractController {
    private final URI uri;
    private final String loaderoApiToken;
    private final HttpClientBuilder client = HttpClients.custom();

    public LoaderoAbstractController(URI uri, String loaderoApiToken) {
        this.uri = uri;
        this.loaderoApiToken = loaderoApiToken;
    }

    /**
     * Returns test description for the specific test in specific project.
     * @param projectId
     * @param testId
     */
    public abstract LoaderoModel getById(String projectId, String testId);
}
