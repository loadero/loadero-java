package com.loadero.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loadero.model.Assert;
import com.loadero.model.AssertCollection;
import com.loadero.model.File;
import com.loadero.model.Group;
import com.loadero.model.GroupCollection;
import com.loadero.model.ModelParams;
import com.loadero.model.Participant;
import com.loadero.model.ParticipantCollection;
import com.loadero.model.Precondition;
import com.loadero.model.PreconditionCollection;
import com.loadero.model.ProfileParams;
import com.loadero.model.Result;
import com.loadero.model.ResultAssert;
import com.loadero.model.ResultCollection;
import com.loadero.model.Test;
import com.loadero.model.TestCollection;
import com.loadero.model.TestRun;
import com.loadero.model.TestRunCollection;
import java.io.IOException;

/**
 * JSON serialization/deserialization layer. Also used as an abstraction to hide HTTP logic.
 */
public enum ApiResource {
    ;

    private static final HttpController controller = new HttpController();
    private static final Gson gson = createGson();

    /**
     * Makes specific request to Loadero API service.
     *
     * @param method Request method name i.e GET, POST, PUT or DELETE.
     * @param url    Url to make request for.
     * @param params {@link ModelParams} concrete object.
     * @param clazz  Class that is going to be serialized/deserialized.
     * @return Deserialized model from JSON response.
     * @throws IOException if request failed.
     */
    public static <T> T request(
        RequestMethod method, String url, ModelParams params, Class<T> clazz
    ) throws IOException {
        String content = gson.toJson(params, clazz);
        return controller.request(method, url, content, clazz);
    }

    /**
     * Overloads request() method above to avoid unnecessary serialization
     * of *Params object during copy operation.
     */
    public static <T> T request(
        RequestMethod method, String url, Class<T> clazz, String name
    ) throws IOException {
        String content = String.format(
            "{"
            + "\"name\":\"%s\""
            + "}", name
        );
        return controller.request(method, url, content, clazz);
    }

    public static Gson getGSON() {
        return gson;
    }

    private static Gson createGson() {
        return new GsonBuilder()
            .registerTypeAdapter(Test.class, new TestTypeAdapter())
            .registerTypeAdapter(File.class, new FileDeserializer())
            .registerTypeAdapter(File.class, new FileSerializer())
            .registerTypeAdapter(Group.class, new GroupDeserializer())
            .registerTypeAdapter(Group.class, new GroupSerializer())
            .registerTypeAdapter(Participant.class, new ParticipantDeserializer())
            .registerTypeAdapter(Participant.class, new ParticipantSerializer())
            .registerTypeAdapter(TestRun.class, new TestRunTypeAdapter())
            .registerTypeAdapter(Assert.class, new AssertDeserializer())
            .registerTypeAdapter(Assert.class, new AssertSerializer())
            .registerTypeAdapter(Precondition.class, new PreconditionDeserializer())
            .registerTypeAdapter(Precondition.class, new PreconditionSerializer())
            .registerTypeAdapter(ProfileParams.class, new ProfileParamsDeserializer())
            .registerTypeAdapter(ResultAssert.class, new ResultAssertDeserializer())
            .registerTypeAdapter(AssertCollection.class, new CollectionDeserializer<>(Assert.class))
            .registerTypeAdapter(GroupCollection.class, new CollectionDeserializer<>(Group.class))
            .registerTypeAdapter(ParticipantCollection.class, new CollectionDeserializer<>(Participant.class))
            .registerTypeAdapter(PreconditionCollection.class, new CollectionDeserializer<>(Precondition.class))
            .registerTypeAdapter(TestCollection.class, new CollectionDeserializer<>(Test.class))
            .registerTypeAdapter(ResultCollection.class, new CollectionDeserializer<>(Result.class))
            .registerTypeAdapter(TestRunCollection.class, new CollectionDeserializer<>(TestRun.class))
            .setPrettyPrinting()
            .create();
    }
}
