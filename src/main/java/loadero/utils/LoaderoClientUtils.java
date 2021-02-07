package loadero.utils;

import com.google.gson.Gson;
import loadero.model.*;
import org.apache.http.HttpEntity;
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
                case LOADERO_TEST_OPTIONS:
                    return gson.fromJson(content, LoaderoTestOptions.class);
                case LOADERO_GROUP:
                    return gson.fromJson(content, LoaderoGroup.class);
                case LOADERO_PARTICIPANT:
                    return gson.fromJson(content, LoaderoParticipant.class);
                case LOADERO_RUN_INFO:
                    return gson.fromJson(content, LoaderoRunInfo.class);
                case LOADERO_TEST_RESULT:
                    return gson.fromJson(content, LoaderoTestResult.class);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    // Serializes LoaderModel into JSON
    public static String modelToJson(LoaderoModel model) {
        return gson.toJson(model);
    }

}
