package com.loadero.http;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.loadero.model.Test;
import com.loadero.model.TestParams;
import java.lang.reflect.Type;
import java.time.Duration;
import com.loadero.types.IncrementStrategy;
import com.loadero.types.TestMode;

final class TestDeserializer implements JsonDeserializer<Test> {
    @Override
    public Test deserialize(
        JsonElement json, Type type, JsonDeserializationContext context
    ) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        Duration startInterval = Duration.ofSeconds(
            jsonObject.getAsJsonPrimitive("start_interval").getAsInt()
        );
        Duration participantTimeout = Duration.ofSeconds(
            jsonObject.getAsJsonPrimitive("participant_timeout").getAsInt()
        );
        TestMode mode = TestMode.getConstant(
            jsonObject.getAsJsonPrimitive("mode").getAsString()
        );
        IncrementStrategy strategy = IncrementStrategy.getConstant(
            jsonObject.getAsJsonPrimitive("increment_strategy").getAsString()
        );

        JsonPrimitive participantCountField = jsonObject.getAsJsonPrimitive("participant_count");
        int participantCount = participantCountField == null ? 0 : participantCountField.getAsInt();

        JsonPrimitive groupCountField = jsonObject.getAsJsonPrimitive("group_count");
        int groupCount = groupCountField == null ? 0 : groupCountField.getAsInt();

        TestParams params = TestParams.builder()
            .withId(jsonObject.getAsJsonPrimitive("id").getAsInt())
            .withCreated(jsonObject.getAsJsonPrimitive("created").getAsString())
            .withUpdated(jsonObject.getAsJsonPrimitive("updated").getAsString())
            .withProjectId(jsonObject.getAsJsonPrimitive("project_id").getAsInt())
            .withName(jsonObject.getAsJsonPrimitive("name").getAsString())
            .withScriptFileId(jsonObject.getAsJsonPrimitive("script_file_id").getAsInt())
            .withStartInterval(startInterval)
            .withParticipantTimeout(participantTimeout)
            .withMode(mode)
            .withIncrementStrategy(strategy)
            .withGroupCount(groupCount)
            .withParticipantCount(participantCount)
            .build();

        return new Test(params);
    }
}
