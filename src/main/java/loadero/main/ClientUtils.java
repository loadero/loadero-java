package loadero.main;

import com.google.gson.*;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import loadero.exceptions.ClientInternalException;
import loadero.model.*;
import loadero.types.*;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

class ParticipantCustomSerializer implements
        JsonSerializer<Participant> {
    
    @Override
    public JsonElement serialize(Participant participant, Type type,
                                 JsonSerializationContext context) {
        JsonObject json = new JsonObject();
        json.addProperty("name", participant.getName());
        json.addProperty("count", participant.getCount());
        json.add("compute_unit",
                context.serialize(participant.getComputeUnit(), ComputeUnitsType.class));
        json.addProperty("browser", participant.getBrowser().getBrowser());
        json.add("network",
                context.serialize(participant.getNetwork(), NetworkType.class));
        json.add("location",
                context.serialize(participant.getLocation(), LocationType.class));
        json.add("media_type",
                context.serialize(participant.getMediaType(), MediaType.class));
        return json;
    }
}

class ParticipantCustomDeserializer implements
        JsonDeserializer<Participant> {
    
    @Override
    public Participant deserialize(JsonElement jsonElement, Type type,
                                          JsonDeserializationContext context)
            throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonElement browserName = jsonObject.get("browser");
        jsonObject.remove("browser");
        if (browserName != null) {
            BrowserType browser = new BrowserType(browserName.getAsString());
            return new Participant(
                    jsonObject.getAsJsonPrimitive("id").getAsInt(),
                    jsonObject.getAsJsonPrimitive("name").getAsString(),
                    jsonObject.getAsJsonPrimitive("count").getAsInt(),
                    context.deserialize(jsonObject.get("compute_unit"), ComputeUnitsType.class),
                    browser,
                    context.deserialize(jsonObject.get("network"), NetworkType.class),
                    context.deserialize(jsonObject.get("location"), LocationType.class),
                    context.deserialize(jsonObject.get("media_type"), MediaType.class)
            );
        }
        return null;
    }
}

class MetricsPathCustomDeserializer implements JsonDeserializer<MetricPaths> {
    
    @Override
    public MetricPaths deserialize(JsonElement jsonElement, Type type,
                                   JsonDeserializationContext context)
            throws JsonParseException {
        JsonElement metricPaths = jsonElement.getAsJsonArray();
        Type setType = new TypeToken<Set<String>>() {}.getType();
        Set<String> metricSet = context.deserialize(metricPaths, setType);
        Set<String> machinePaths = metricSet.stream()
                .filter(path -> path.contains("machine"))
                .collect(Collectors.toSet());
        Set<String> webrtcPaths = metricSet.stream()
                .filter(path -> path.contains("webrtc"))
                .collect(Collectors.toSet());
        
        return new MetricPaths(machinePaths, webrtcPaths);
    }
}

class AssertPathsCustomDeserializer implements JsonDeserializer<AssertPath> {
    
    @Override
    public AssertPath deserialize(JsonElement jsonElement, Type type,
                                  JsonDeserializationContext context)
            throws JsonParseException {
        String path = jsonElement.getAsString();
        if (path.contains("webrtc")) {
            return context.deserialize(jsonElement, WebRtcAsserts.class);
        } else {
            return context.deserialize(jsonElement, MachineAsserts.class);
        }
    }
}

/**
 * This class provides some utility/helper functions for the package.
 */
final class ClientUtils {
    private static final Logger log = LogManager.getLogger(ClientUtils.class);
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(Participant.class, new ParticipantCustomSerializer())
            .registerTypeAdapter(Participant.class, new ParticipantCustomDeserializer())
            .registerTypeAdapter(MetricPaths.class, new MetricsPathCustomDeserializer())
            .registerTypeAdapter(AssertPath.class, new AssertPathsCustomDeserializer())
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
     * @throws ClientInternalException if value is negative.
     */
    public static void checkIfIntIsNegative(int...values) {
        for (int arg: values) {
            if (arg < 0) {
                throw new ClientInternalException(arg + "cannot be negative");
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
                    return gson.fromJson(content, Test.class);
                case LOADERO_GROUP:
                    return gson.fromJson(content, Group.class);
                case LOADERO_PARTICIPANT:
                    return gson.fromJson(content, Participant.class);
                case LOADERO_RUN_INFO:
                    return gson.fromJson(content, RunInfo.class);
                case LOADERO_TEST_RUN_PARTICIPANT_RESULT:
                    return gson.fromJson(content, TestRunParticipantResult.class);
                case LOADERO_SCRIPT_FILE_LOC:
                    return gson.fromJson(content, ScriptDetails.class);
                case LOADERO_RUN_RESULT:
                    return gson.fromJson(content, TestRunResult.class);
                case LOADERO_STATICS:
                    return gson.fromJson(content, Statics.class);
                case LOADERO_ASSERT:
                    return gson.fromJson(content, Assert.class);
                case LOADERO_METRIC_PATHS:
                    return gson.fromJson(content, MetricPaths.class);
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
     * @throws ClientInternalException if currentObject and copyTo are not of the same class.
     * @return copyTo object.
     */
    public static LoaderoModel copyUncommonFields(LoaderoModel copyFrom, LoaderoModel copyTo) {
        checkArgumentsForNull(copyFrom, copyTo);
        
        if (!Objects.equals(copyFrom.getClass(), copyTo.getClass())) {
            throw new ClientInternalException(
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