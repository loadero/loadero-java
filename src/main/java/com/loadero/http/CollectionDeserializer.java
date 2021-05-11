package com.loadero.http;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.loadero.exceptions.ApiException;
import com.loadero.model.AssertCollection;
import com.loadero.model.LoaderoCollection;
import java.lang.reflect.Type;
import java.util.List;

final class CollectionDeserializer<T> implements JsonDeserializer<LoaderoCollection<?>> {
    private final Class<T> clazz;

    public CollectionDeserializer(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public LoaderoCollection<?> deserialize(
        JsonElement json, Type type, JsonDeserializationContext context
    ) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        JsonArray arr = jsonObject.getAsJsonArray("results");
        return deserializeHelper(context, arr);
    }

    private LoaderoCollection<?> deserializeHelper(
        JsonDeserializationContext context, JsonArray arr
    ) {
        String[] split = clazz.getTypeName().split("\\.");
        String name = split[split.length - 1];
        Type valType = TypeToken.getParameterized(List.class, clazz).getType();

        switch (name) {
            case "Assert":
                return new AssertCollection(context.deserialize(arr, valType));
            // other cases eg Test, Group etc.
            default:
                throw new ApiException("Unsupported collection.");
        }
    }
}
