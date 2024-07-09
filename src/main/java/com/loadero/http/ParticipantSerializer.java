package com.loadero.http;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.loadero.model.ParticipantParams;
import java.lang.reflect.Type;

final class ParticipantSerializer implements JsonSerializer<ParticipantParams> {
    @Override
    public JsonElement serialize(
        ParticipantParams participant, Type type, JsonSerializationContext context
    ) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", participant.getName());
        jsonObject.addProperty("count", participant.getCount());
        jsonObject.addProperty("compute_unit", participant.getComputeUnit().toString());
        jsonObject.addProperty("browser", participant.getBrowser().getBrowser());
        jsonObject.addProperty("network", participant.getNetwork().toString());
        jsonObject.addProperty("location", participant.getLocation().toString());
        jsonObject.addProperty("record_audio", participant.getRecordAudio());

        return jsonObject;
    }
}
