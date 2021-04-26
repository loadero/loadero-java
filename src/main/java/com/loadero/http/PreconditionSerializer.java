package com.loadero.http;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.loadero.model.PreconditionParams;
import java.lang.reflect.Type;
import java.util.Locale;

final class PreconditionSerializer implements JsonSerializer<PreconditionParams> {
    @Override
    public JsonElement serialize(
        PreconditionParams params, Type type, JsonSerializationContext jsonSerializationContext
    ) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(
            "property",
            params.getProperty().toString().toLowerCase(Locale.ROOT)
        );
        jsonObject.addProperty("operator", params.getOperator().toString());
        jsonObject.addProperty("expected", params.getExpected());

        return jsonObject;
    }
}
