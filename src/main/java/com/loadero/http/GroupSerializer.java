package com.loadero.http;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.loadero.model.GroupParams;
import java.lang.reflect.Type;

final class GroupSerializer implements JsonSerializer<GroupParams> {
    @Override
    public JsonElement serialize(GroupParams params, Type type, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", params.getName());
        jsonObject.addProperty("count", params.getCount());

        return jsonObject;
    }
}
