package loadero.controller;

import loadero.model.LoaderoModel;
import loadero.model.LoaderoType;
import loadero.utils.LoaderoClientUtils;
import loadero.utils.LoaderoHttpClient;
import lombok.Getter;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * REST controller class responsible for CRUD actions related to Loadero tests.
 * Meaning here is defined logic for creating, updetaing, retrieving and deleting Loadero tests.
 */
@Getter
public class LoaderoCrudController {
    private final String loaderoApiToken;
    private final LoaderoHttpClient client;
    private static final Logger log = LogManager.getLogger(LoaderoCrudController.class);

    public LoaderoCrudController(String loaderoApiToken) {
        this.loaderoApiToken = loaderoApiToken;
        this.client = new LoaderoHttpClient(loaderoApiToken);
    }

    /**
     * Retrieves LoaderoModel i.e test, participant or group description
     * from the specific URI.
     *
     * @param uri  - GET endpoint of data
     * @param type - type of the returned data
     */
    public LoaderoModel get(String uri, LoaderoType type) {
        LoaderoModel result = null;
        HttpUriRequest get = RequestBuilder.get(uri).build();

        // Try-catch with resources statement that will close
        // everything for us after we are done.
        try (CloseableHttpResponse res = client.build().execute(get)) {
            if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = res.getEntity();
                result = LoaderoClientUtils.httpEntityToModel(entity, type);
                log.info("{} - {} - {}", get.getMethod(), res.getStatusLine(), uri);
            }
        } catch (NullPointerException | IOException e) {
            log.error("{}", e.getMessage());
        }
        return result;
    }

    /**
     * Update operation on existing LoaderoModel by supplying new LoaderoModel
     *
     * @param uri      - URI of the API pointing to which LoaderoModel(test, group or participant) to update
     * @param type     - type of the model to be created by factory
     * @param newModel - new model that will replace old one
     * @return - Returns new LoaderoModel with updated parameters.
     */
    public LoaderoModel update(String uri, LoaderoType type, LoaderoModel newModel) {
        LoaderoModel result = null;
        LoaderoClientUtils.checkArgumentsForNull(uri, type, newModel);

        try {
            String modelToJson = LoaderoClientUtils.modelToJson(newModel);
            HttpEntity entity = new StringEntity(modelToJson);
            HttpUriRequest put = RequestBuilder.put(uri).setEntity(entity).build();
            CloseableHttpResponse res = client.build().execute(put);

            if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK &&
                    !(LoaderoClientUtils.isNull(res.getEntity()))) {
                result = LoaderoClientUtils.httpEntityToModel(
                        res.getEntity(),
                        type);
                log.info("{} - {} - Updated value: {}", put.getMethod(), res.getStatusLine(), uri);
            } else {
                log.error("{} - {} - {}", put.getMethod(), res.getStatusLine(), uri);
                log.error("Json sent: {}", modelToJson);
            }
            res.close();
        } catch (IOException e) {
            log.error("{}", e.getMessage());
        }
        return result;
    }
}
