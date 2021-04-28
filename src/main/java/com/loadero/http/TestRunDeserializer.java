package com.loadero.http;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.loadero.model.TestRun;
import com.loadero.types.IncrementStrategy;
import com.loadero.types.RunStatus;
import com.loadero.types.TestMode;
import java.lang.reflect.Type;
import java.time.Duration;
import java.util.Locale;

public class TestRunDeserializer implements JsonDeserializer<TestRun> {
    @Override
    public TestRun deserialize(
        JsonElement json, Type type, JsonDeserializationContext context
    ) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        TestMode mode = TestMode.getConstant(
            jsonObject.getAsJsonPrimitive("test_mode").getAsString()
        );
        IncrementStrategy strategy = IncrementStrategy.getConstant(
            jsonObject.getAsJsonPrimitive("increment_strategy").getAsString()
        );
        Duration startInterval = Duration.ofSeconds(
            jsonObject.getAsJsonPrimitive("start_interval").getAsInt()
        );
        Duration timeout = Duration.ofSeconds(
            jsonObject.getAsJsonPrimitive("participant_timeout").getAsInt()
        );
        RunStatus status = RunStatus.valueOf(
            formatStatus(jsonObject.getAsJsonPrimitive("status").getAsString())
        );
        JsonPrimitive groupCountField = jsonObject.getAsJsonPrimitive("group_count");
        JsonPrimitive participantField = jsonObject.getAsJsonPrimitive(
            "participant_count"
        );
        JsonPrimitive successField = jsonObject.getAsJsonPrimitive("success_rate");
        JsonPrimitive processingStartedField = jsonObject.getAsJsonPrimitive(
            "processing_started"
        );
        JsonPrimitive processingFinishedField = jsonObject.getAsJsonPrimitive(
            "processing_finished"
        );
        JsonPrimitive executionStartedField = jsonObject.getAsJsonPrimitive(
            "execution_started"
        );
        JsonPrimitive executionFinishedField = jsonObject.getAsJsonPrimitive(
            "execution_finished"
        );
        int groupCount = groupCountField == null ? 0 : groupCountField.getAsInt();
        int participantCount = participantField == null ? 0 : participantField.getAsInt();
        double successRate = successField == null ? 0 : successField.getAsDouble();
        String processingStarted = processingStartedField == null ? ""
            : processingStartedField.getAsString();
        String processingFinished = processingFinishedField == null ? ""
            : processingFinishedField.getAsString();
        String executionStarted = executionStartedField == null ? ""
            : executionStartedField.getAsString();
        String executionFinished = executionFinishedField == null ? ""
            : executionFinishedField.getAsString();

        return new TestRun(
            jsonObject.getAsJsonPrimitive("id").getAsInt(),
            jsonObject.getAsJsonPrimitive("created").getAsString(),
            jsonObject.getAsJsonPrimitive("updated").getAsString(),
            processingStarted,
            processingFinished,
            executionStarted,
            executionFinished,
            jsonObject.getAsJsonPrimitive("test_id").getAsInt(),
            status,
            mode,
            strategy,
            jsonObject.getAsJsonPrimitive("script_file_id").getAsInt(),
            jsonObject.getAsJsonPrimitive("test_name").getAsString(),
            startInterval,
            timeout,
            jsonObject.getAsJsonPrimitive("launching_account_id").getAsInt(),
            successRate,
            groupCount,
            participantCount
        );
    }

    private String formatStatus(String name) {
        return name.replaceAll("-", "_").toUpperCase(Locale.ROOT);
    }
}
