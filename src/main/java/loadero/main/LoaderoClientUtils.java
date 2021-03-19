package loadero.main;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import loadero.exceptions.LoaderoClientInternalException;
import loadero.model.*;
import loadero.types.LoaderoModelType;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Objects;

/**
 * This class provides some utility/helper functions for the package.
 */
final class LoaderoClientUtils {
    private static final Logger log = LogManager.getLogger(LoaderoClientUtils.class);
    private static final Gson gson = new GsonBuilder()
            .addSerializationExclusionStrategy(new ExclusionStrategy() {
        @Override
        public boolean shouldSkipField(FieldAttributes fieldAttributes) {
            return fieldAttributes.getName().equals("id") ||
                    fieldAttributes.getName().equals("scriptFileId") ||
                    fieldAttributes.getName().equals("testId") ||
                    fieldAttributes.getName().equals("groupId");
        }

        @Override
        public boolean shouldSkipClass(Class<?> aClass) {
            return false;
        }
    })
            .create();
    
    /**
     * Checks if given arguments are null. String checks for emptiness with isBlank()
     * @throws NullPointerException if argument is null or an empty String.
     * @param args Comma separated arguments.
     */
    public static void checkArgumentsForNull(Object...args) {
        for (Object arg: args) {
            Objects.requireNonNull(arg,
                    String.format("%s cannot be null", arg.getClass().getSimpleName()));
            if (arg instanceof String) {
                boolean isBlank = ((String) arg).isBlank();
                if (isBlank) throw new NullPointerException(arg + "is an empty String or null.");
            }
        }
    }
    
    /**
     * Check given comma separated int values, if they are negative.
     * @param values Comma separated int arguments.
     * @throws LoaderoClientInternalException if value is negative.
     */
    public static void checkIfIntIsNegative(int...values) {
        for (int arg: values) {
            if (arg < 0) {
                throw new LoaderoClientInternalException(arg + "cannot be negative");
            }
        }
    }
    
    /**
     * Converts JSON from HttpEntity response into concrete LoaderModel object according to provided type.
     * @param entity HttpEntity containing JSON body.
     * @param type LoaderoModelType enum for the object we wish to get.
     * @throws NullPointerException if any of the arguments are null.
     * @return concrete class of LoaderoModel object.
     */
    public static LoaderoModel httpEntityToModel(HttpEntity entity, LoaderoModelType type) {
        checkArgumentsForNull(entity, type);
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
                case LOADERO_TEST_RUN_PARTICIPANT_RESULT:
                    return gson.fromJson(content, LoaderoTestRunParticipantResult.class);
                case LOADERO_SCRIPT_FILE_LOC:
                    return gson.fromJson(content, LoaderoScriptFileLoc.class);
                case LOADERO_RUN_RESULT:
                    return gson.fromJson(content, LoaderoTestRunResult.class);
            }
        } catch (IOException e) {
            log.error("{}", e.getMessage());
        }
        return result;
    }
    
    /**
     * Serializes LoaderModel object into JSON.
     * @param model LoaderoModel we want to serialize.
     * @throws NullPointerException if model is null.
     * @return JSON representation of LoaderoModel as String.
     */
    public static String modelToJson(LoaderoModel model) {
        checkArgumentsForNull(model);
        return gson.toJson(model);
    }
    
    /**
     * Compares two LoaderModel objects field by field and copies field's values from
     * copyFrom into copyTo if there are any differences detected. Does nothing if field's values are the same.
     *
     * Slightly modified version of code from SO: https://stackoverflow.com/a/20366149.
     *
     * @param copyFrom LoaderoModel to compare against.
     * @param copyTo   LoaderoModel to be compared.
     * @throws NullPointerException if any of provided arguments are null.
     * @throws IllegalArgumentException if Reflection API couldn't get value from the Field.
     * @throws LoaderoClientInternalException if currentObject and copyTo are not of the same class.
     * @return copyTo object.
     */
    public static LoaderoModel copyUncommonFields(LoaderoModel copyFrom, LoaderoModel copyTo) {
        checkArgumentsForNull(copyFrom, copyTo);
        
        if (!Objects.equals(copyFrom.getClass(), copyTo.getClass())) {
            throw new LoaderoClientInternalException(
                    String.format("%s and %s not of the same class",
                    copyFrom, copyTo));
        }
    
        Field[] fieldsArr = copyFrom.getClass().getDeclaredFields();

        for(Field field : fieldsArr){
            // make private fields accessible
            field.setAccessible(true);
            try {
                /* If field of copyTo object values are equal to copyFrom object values,
                 * empty, 0 or null, then copies values of copyFrom object into copyTo object.
                 * Otherwise, leaves field unchanged.
                */
                if (field.get(copyFrom) == field.get(copyTo)
                        || Objects.equals(field.get(copyTo), "")
                        || Objects.equals(field.get(copyTo), 0)
                        || Objects.equals(field.get(copyTo), 0L)
                        || field.get(copyTo) == null) {
                    field.set(copyTo, field.get(copyFrom));
                }
            } catch (IllegalArgumentException | IllegalAccessException e) {
                log.error("Couldn't get access or an illegal argument was provided to {}",
                        field.toGenericString());
            }
            field.setAccessible(false);
        }
        return copyTo;
    }
}