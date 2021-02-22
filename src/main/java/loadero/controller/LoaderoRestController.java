package loadero.controller;

import loadero.model.LoaderoModel;
import loadero.model.LoaderoModelFactory;
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
public class LoaderoRestController {
    private final String loaderoApiToken;
    private final LoaderoHttpClient client;
    private final LoaderoModelFactory factory = new LoaderoModelFactory();
    private static final Logger logger = LogManager.getLogger(LoaderoRestController.class);

    public LoaderoRestController(String loaderoApiToken) {
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
                result = LoaderoClientUtils.jsonToObject(entity, type);
                logger.info("{} : {}", res.getStatusLine(), uri);
            }
        } catch (NullPointerException | IOException e) {
            logger.error("{}", e.getMessage());
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

        if (LoaderoClientUtils.isNull(newModel) || LoaderoClientUtils.isNull(type)) {
            logger.error("Parameter can't be null.");
            return result;
        }

        try {
            String testToJson = LoaderoClientUtils.modelToJson(newModel);
            HttpEntity entity = new StringEntity(testToJson);
            HttpUriRequest put = RequestBuilder.put(uri).setEntity(entity).build();
            CloseableHttpResponse res = client.build().execute(put);

            if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK &&
                    !(LoaderoClientUtils.isNull(res.getEntity()))) {
                result = LoaderoClientUtils.jsonToObject(
                        res.getEntity(),
                        type);
                logger.info("{}: {}", res.getStatusLine(), "Value updated");
                logger.info("Updated value url: {}", uri);
            } else {
                logger.error("{} : {}", res.getStatusLine(), uri);
                logger.error("{}", testToJson);
            }
            res.close();
        } catch (IOException e) {
            logger.error("{}", e.getMessage());
        }
        return result;
    }
}
