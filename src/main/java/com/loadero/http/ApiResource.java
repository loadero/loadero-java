package com.loadero.http;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loadero.model.Group;
import com.loadero.model.ModelParams;
import java.io.IOException;

public abstract class ApiResource {
    private static final HttpController controller = new HttpController();
    private static final Gson gson = createGson();

    /**
     * Makes specify request to Loadero API serivce.
     *
     * @param method Request method name i.e GET, POST, PUT or DELETE.
     * @param url    Url to make request for.
     * @param params {@link ModelParams} concrete object.
     * @param clazz  Class that is going to be serialized/deserialized.
     * @return Deserialized model from JSON response.
     * @throws IOException if request failed.
     */
    public static <T> T request(
        RequestMethod method, String url,
        ModelParams params, Class<T> clazz
    )
        throws IOException {
        String content = gson.toJson(params);
        return controller.request(method, url, content, clazz);
    }

    public static Gson getGSON() {
        return gson;
    }

    private static Gson createGson() {
        return new GsonBuilder()
            .registerTypeAdapter(Group.class, new GroupDeserializer())
            .registerTypeAdapter(Group.class, new GroupSerializer())
            .setPrettyPrinting()
            .addSerializationExclusionStrategy(
                new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes fieldAttributes) {
                        return fieldAttributes.getName().equals("id")
                            || fieldAttributes.getName().equals("scriptFileId")
                            || fieldAttributes.getName().equals("testId")
                            || fieldAttributes.getName().equals("groupId");
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> aClass) {
                        return false;
                    }
                })
            .create();
    }
}
