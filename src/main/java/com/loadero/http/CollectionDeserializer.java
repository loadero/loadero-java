package com.loadero.http;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.loadero.model.Assert;
import com.loadero.model.AssertCollection;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CollectionDeserializer<T> implements JsonDeserializer<AssertCollection> {
    private final Class<T> clazz;

    public CollectionDeserializer(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public AssertCollection deserialize(
        JsonElement json, Type type, JsonDeserializationContext context
    ) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        System.out.println(jsonObject.toString());
        JsonArray arr = jsonObject.getAsJsonArray("results");
        System.out.println(arr);
        List<Assert> asserts = new ArrayList<>();
        arr.forEach(el -> asserts.add(ApiResource.getGSON().fromJson(el, Assert.class)));

        return new AssertCollection(asserts);
    }
}
