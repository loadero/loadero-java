package com.loadero.http;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.loadero.model.TestParams;
import java.lang.reflect.Type;

final class TestSerializer implements JsonSerializer<TestParams> {
    @Override
    public JsonElement serialize(
        TestParams params, Type type, JsonSerializationContext context
    ) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", params.getName());
        jsonObject.addProperty("script", params.getScript());
        jsonObject.addProperty("increment_strategy", params.getIncrementStrategy().toString());
        jsonObject.addProperty("mode", params.getMode().toString());
        jsonObject.addProperty("participant_timeout", params.getParticipantTimeout().getSeconds());
        jsonObject.addProperty("start_interval", params.getStartInterval().getSeconds());

        return jsonObject;
    }
}
