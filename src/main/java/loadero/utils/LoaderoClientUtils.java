package loadero.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import loadero.model.*;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;

import java.lang.reflect.Field;
import java.util.Objects;

public class LoaderoClientUtils {
    private static final LoaderoModelFactory factory = new LoaderoModelFactory();
    private static final Gson gson = new GsonBuilder().create();

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
                case LOADERO_SCRIPT_FILE_LOC:
                    return gson.fromJson(content, LoaderoScriptFileLoc.class);
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

    // Compares two LoaderModel object's fields values and copies fields from
    // model1 into model2 if there is any difference.
    // Does nothing if no difference.
    // Slightly modified version of code from SO.
    // source: https://stackoverflow.com/a/20366149
    public static LoaderoTestOptions copyUncommonFields(LoaderoTestOptions currentObj, LoaderoTestOptions newObject) {
        LoaderoTestOptions results = new LoaderoTestOptions();
        for(Field field : LoaderoTestOptions.class.getDeclaredFields()){
            // make private fields accessible
            field.setAccessible(true);
            try {
                if(Objects.equals(field.get(currentObj), field.get(newObject)) |
                Objects.equals(field.get(newObject), "") |
                Objects.equals(field.get(newObject), 0)){
                    field.set(results, field.get(currentObj));
                } else {
                    field.set(results, field.get(newObject));
                }
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            } finally {
                // remove access to private fields just in case
                field.setAccessible(false);
            }
        }
        return results;
    }
}
