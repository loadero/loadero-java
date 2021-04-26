package com.loadero.http;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.loadero.model.AssertParams;
import java.lang.reflect.Type;

final class AssertSerializer implements JsonSerializer<AssertParams> {
    @Override
    public JsonElement serialize(
        AssertParams params, Type type, JsonSerializationContext context
    ) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("path", params.getPath().toString());
        jsonObject.addProperty("operator", params.getOperator().toString());
        jsonObject.addProperty("expected", params.getExpected());

        return jsonObject;
    }
}
