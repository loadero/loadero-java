package loadero;

import com.google.gson.Gson;
import loadero.model.LoaderoTestDescription;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.util.EntityUtils;

import java.util.Objects;

public class LoaderoClientUtils {
    private static final Gson gson = new Gson();
    protected static boolean checkNull(Object test) {
        return Objects.isNull(test);
    }
    // Converts JSON from response into LoaderTestDescription object
    protected static LoaderoTestDescription jsonToTestDescr(HttpEntity entity) {
        LoaderoTestDescription test = null;
        try {
            test = gson.fromJson(EntityUtils.toString(entity), LoaderoTestDescription.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return test;
    }

    protected static String testDescrToJson(LoaderoTestDescription test) {
        return gson.toJson(test);
    }

    /**
     * Sets some default headers for Http methods.
     * @param req - Http request for which set default headers
     */
    protected static void setDefaultHeaders(HttpUriRequest req, String loadero_token) {
        req.setHeader(HttpHeaders.ACCEPT, "application/json");
        req.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        req.setHeader(HttpHeaders.AUTHORIZATION, "LoaderoAuth " + loadero_token);
    }
}
