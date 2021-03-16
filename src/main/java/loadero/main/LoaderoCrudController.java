package loadero.main;

import loadero.model.LoaderoModel;
import loadero.types.LoaderoModelType;
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
 * Class for defining low-level CRUD operations for Loadero API.
 * Provides only getter. Cannot be extended.
 */
@Getter
final class LoaderoCrudController {
    private final String loaderoApiToken;
    private final LoaderoHttpClient client;
    private static final Logger log = LogManager.getLogger(LoaderoCrudController.class);
    
    /**
     * Main constructor. Needs Loadero Api token as String to be initiated.
     * @param loaderoApiToken Loadero API token.
     * @throws NullPointerException is loaderoApiToken parameter is null or an empty string.
     */
    public LoaderoCrudController(String loaderoApiToken) {
        LoaderoClientUtils.checkArgumentsForNull(loaderoApiToken);
        
        this.loaderoApiToken = loaderoApiToken;
        this.client = new LoaderoHttpClient(loaderoApiToken);
    }

    /**
     * Send GET request to Loadero endpoint to retrieve information.
     * @param url  GET endpoint.
     * @param type type of the returned data.
     * @return LoaderoModel object with values from JSON response.
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
        } catch (IOException ex) {
            logError(get.getMethod(), ex);
        }
        return result;
    }

    /**
     * Send PUT request on existing Loadero test, group or participant.
     *
     * @param url      API endpoint containing object to be updated.
     * @param type     type of the model to be created by factory.
     * @param newModel new LoaderoModel that will replace old one.
     * @throws NullPointerException if any of the arguments are null or an empty String.
     * @return Returns new LoaderoModel with updated parameters.
     */
    public LoaderoModel update(String url, LoaderoModelType type, LoaderoModel newModel) {
        LoaderoClientUtils.checkArgumentsForNull(url, type, newModel);
    
        LoaderoModel result = null;
        String methodName = "";
        try {
            String modelToJson = LoaderoClientUtils.modelToJson(newModel);
            HttpEntity entity = new StringEntity(modelToJson);
            HttpUriRequest put = RequestBuilder.put(url).setEntity(entity).build();
            CloseableHttpResponse res = client.build().execute(put);
            methodName = put.getMethod();
            StatusLine statusLine = res.getStatusLine();
            
            if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
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
     * Sends POST request to the Loadero endpoint.
     * @param url   POST endpoint.
     * @param type  LoaderoModelType to be created.
     * @param model LoaderModel object to be created on the Loadero endpoint.
     * @throws NullPointerException if any of the arguments are null of an empty String.
     * @return New LoaderoModel object created on Loadero site.
     */
    public LoaderoModel post(String url, LoaderoModelType type, LoaderoModel model) {
        LoaderoClientUtils.checkArgumentsForNull(url, type, model);
    
        LoaderoModel result = null;
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
    
    /**
     * Send DELETE request to the Loadero endpoint.
     * @param url DELETE endpoint.
     */
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
