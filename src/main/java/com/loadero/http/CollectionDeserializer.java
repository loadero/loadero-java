package com.loadero.http;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;

final class CollectionDeserializer<T> implements JsonDeserializer<List<T>> {
    private final Class<T> clazz;

    public CollectionDeserializer(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public List<T> deserialize(
        JsonElement json, Type type, JsonDeserializationContext context
    ) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        JsonArray arr = jsonObject.get("results").getAsJsonArray();
        Type collectionType = TypeToken.getParameterized(List.class, clazz).getType();
        return context.deserialize(arr, collectionType);
    }
}
