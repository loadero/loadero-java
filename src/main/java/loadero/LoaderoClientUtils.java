package loadero;

import com.google.gson.Gson;
import loadero.model.*;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.util.EntityUtils;

import java.util.Objects;

public class LoaderoClientUtils {
    private static final Gson gson = new Gson();

    public static boolean isNull(Object test) {
        return Objects.isNull(test);
    }

    // Converts JSON from response into according LoaderModel object
    public static LoaderoModel jsonToObject(HttpEntity entity, LoaderoType type) {
        LoaderoModel result = null;
        try {
            String content = EntityUtils.toString(entity);
            switch (type) {
                case LOADERO_TEST:
                    result = gson.fromJson(content, LoaderoTestOptions.class);
                    break;
                case LOADERO_GROUP:
                    result = gson.fromJson(content, LoaderoGroup.class);
                    break;
                case LOADERO_PARTICIPANT:
                    result = gson.fromJson(content, LoaderoParticipant.class);
                    break;
                case LOADERO_RUN_INFO:
                    result = gson.fromJson(content, LoaderoRunInfo.class);
                    break;
                case LOADERO_TEST_RESULT:
                    result = gson.fromJson(content, LoaderoTestResult.class);
                    break;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    // Serializes LoaderModel into JSON
    public static String modelDescrToJson(LoaderoModel model) {
        return gson.toJson(model);
    }

    /**
     * Sets some default headers for Http methods.
     * @param req - Http request for which set default headers
     */
    public static void setDefaultHeaders(HttpUriRequest req, String loadero_token) {
        req.setHeader(HttpHeaders.ACCEPT, "application/json");
        req.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        req.setHeader(HttpHeaders.AUTHORIZATION, "LoaderoAuth " + loadero_token);
    }
}
