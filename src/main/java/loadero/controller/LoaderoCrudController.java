package loadero.controller;

import loadero.model.LoaderoModel;
import loadero.types.LoaderoModelType;
import loadero.utils.LoaderoClientUtils;
import loadero.utils.LoaderoHttpClient;
import lombok.Getter;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.lang.reflect.Field;

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
     * @param url  - GET endpoint of data
     * @param type - type of the returned data
     */
    public LoaderoModel get(String url, LoaderoModelType type) {
        LoaderoModel result = null;
        HttpUriRequest get = RequestBuilder.get(url).build();
        // Try-catch with resources statement that will close
        // everything for us after we are done.
        try (CloseableHttpResponse res = client.build().execute(get)) {
            if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = res.getEntity();
                result = LoaderoClientUtils.httpEntityToModel(entity, type);
                logInfo(get.getMethod(), res.getStatusLine(), url);
            } else {
                logError(get.getMethod(), res.getStatusLine(), url);
            }
        } catch (NullPointerException | IOException ex) {
            logError(get.getMethod(), ex);
        }
        return result;
    }

    /**
     * Update operation on existing LoaderoModel by supplying new LoaderoModel
     *
     * @param url      - URI of the API pointing to which LoaderoModel(test, group or participant) to update
     * @param type     - type of the model to be created by factory
     * @param newModel - new model that will replace old one
     * @return - Returns new LoaderoModel with updated parameters.
     */
    public LoaderoModel update(String url, LoaderoModelType type, LoaderoModel newModel) {
        LoaderoModel result = null;
        LoaderoClientUtils.checkArgumentsForNull(url, type, newModel);
        String methodName = "";
        try {
            String modelToJson = LoaderoClientUtils.modelToJson(newModel);
            HttpEntity entity = new StringEntity(modelToJson);
            HttpUriRequest put = RequestBuilder.put(url).setEntity(entity).build();
            CloseableHttpResponse res = client.build().execute(put);
            methodName = put.getMethod();
            StatusLine statusLine = res.getStatusLine();
            
            if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK &&
                    !(LoaderoClientUtils.isNull(res.getEntity()))) {
                result = LoaderoClientUtils.httpEntityToModel(
                        res.getEntity(),
                        type);
                logInfo(methodName, statusLine, url);
            } else {
                logError(methodName, statusLine, url);
                log.error("Json sent: {}", modelToJson);
            }
            res.close();
        } catch (IOException ex) {
            logError(methodName, ex);
        }
        return result;
    }
    
    /**
     * Creates an instance of the LoaderoModel type in Loadero.
     * @param url   - POST endpoint.
     * @param type  - LoaderoModelType to be created.
     * @param model - LoaderModel object to be created on the Loadero endpoint.
     * @return      - New LaoderoModel object created on Loadero site.
     */
    public LoaderoModel post(String url, LoaderoModelType type, LoaderoModel model) {
        LoaderoModel result = null;
        LoaderoClientUtils.checkArgumentsForNull(url, type, model);
        String modelToJson = LoaderoClientUtils.modelToJson(model);
        String methodName = "";
        try {
            HttpEntity entity = new StringEntity(modelToJson);
            HttpUriRequest post = RequestBuilder.post(url).setEntity(entity).build();
            CloseableHttpResponse res = client.build().execute(post);
            methodName = post.getMethod();
            StatusLine statusLine = res.getStatusLine();
            
            if (res.getStatusLine().getStatusCode() == HttpStatus.SC_CREATED) {
                result = LoaderoClientUtils.httpEntityToModel(res.getEntity(), type);
                Field id = result.getClass().getDeclaredField("id");
                id.setAccessible(true);
                int testId = id.getInt(result); // Retrieving id from freshly created Test object
                logInfo(methodName, statusLine, url + testId + "/");
                id.setAccessible(false); // Just in case
            } else {
                logError(methodName, statusLine, url);
                log.warn("Json sent: {}", modelToJson);
            }
        } catch (IOException | NoSuchFieldException | IllegalAccessException ex) {
            logError(methodName, ex);
        }
        return result;
    }
    
    public void delete(String url) {
        HttpUriRequest delete = RequestBuilder.delete(url).build();
        try (CloseableHttpResponse res = client.build().execute(delete)) {
            if (res.getStatusLine().getStatusCode() == HttpStatus.SC_NO_CONTENT) {
                logInfo(delete.getMethod(), res.getStatusLine(), url);
            }
        } catch (NullPointerException | IOException e) {
            logError(delete.getMethod(), e);
        }
    }
    
    private void logError(String methodName, Exception ex) {
        log.error("{} has failed - {}", methodName, ex.getMessage());
    }
    
    private void logError(String methodName, StatusLine statusLine, String url) {
        log.error("{} has failed - {} - {}", methodName, statusLine, url);
    }
    
    private void logInfo(String methodName, StatusLine statusLine, String url) {
        log.info("{} has succeeded - {} - {}", methodName, statusLine, url);
    }
    

}
