package com.loadero.http;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.loadero.model.Group;
import com.loadero.model.GroupParams;
import java.lang.reflect.Type;

class GroupDeserializer implements JsonDeserializer<Group> {
    @Override
    public Group deserialize(
        JsonElement json, Type type, JsonDeserializationContext context
    ) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        if (jsonObject == null) {
            return null;
        }

        JsonPrimitive jsonPrimitive = jsonObject
            .getAsJsonPrimitive("participant_count");
        int participantCount = jsonPrimitive == null ? 0 : jsonPrimitive.getAsInt();
        GroupParams params = GroupParams
            .builder()
            .withId(jsonObject.getAsJsonPrimitive("id").getAsInt())
            .withTestId(jsonObject.getAsJsonPrimitive("test_id").getAsInt())
            .withName(jsonObject.getAsJsonPrimitive("name").getAsString())
            .withCount(jsonObject.getAsJsonPrimitive("count").getAsInt())
            .withParticipantCount(participantCount)
            .withTimeCreated(jsonObject.getAsJsonPrimitive("created").getAsString())
            .withTimeUpdated(jsonObject.getAsJsonPrimitive("updated").getAsString())
            .build();

        return new Group(params);
    }
}
