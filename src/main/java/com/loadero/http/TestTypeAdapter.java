package com.loadero.http;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.loadero.model.Test;
import com.loadero.model.TestParams;
import com.loadero.model.TestParams.TestParamsBuilder;
import com.loadero.types.IncrementStrategy;
import com.loadero.types.TestMode;
import java.io.IOException;
import java.time.Duration;

public class TestTypeAdapter extends TypeAdapter<Test> {
    private final String ID = "id";
    private final String PROJECT_ID = "project_id";
    private final String NAME = "name";
    private final String SCRIPT = "script";
    private final String CREATED = "created";
    private final String UPDATED = "updated";
    private final String SCRIPT_FILE_ID = "script_file_id";
    private final String START_INTERVAL = "start_interval";
    private final String PARTICIPANT_TIMEOUT = "participant_timeout";
    private final String INCREMENT_STRATEGY = "increment_strategy";
    private final String MODE = "mode";
    private final String GROUP_COUNT = "group_count";
    private final String PARTICIPANT_COUNT = "participant_count";

    @Override
    public Test read(JsonReader jsonReader) throws IOException {
        TestParamsBuilder paramsBuilder = TestParams.builder();
        jsonReader.beginObject();

        while (jsonReader.hasNext()) {
            String key = jsonReader.nextName();

            switch (key) {
                case ID:
                    paramsBuilder.withId(jsonReader.nextInt());
                    break;
                case PROJECT_ID:
                    paramsBuilder.withProjectId(jsonReader.nextInt());
                    break;
                case NAME:
                    paramsBuilder.withName(jsonReader.nextString());
                    break;
                case CREATED:
                    paramsBuilder.withCreated(jsonReader.nextString());
                    break;
                case UPDATED:
                    paramsBuilder.withUpdated(jsonReader.nextString());
                    break;
                case SCRIPT_FILE_ID:
                    paramsBuilder.withScriptFileId(jsonReader.nextInt());
                    break;
                case START_INTERVAL:
                    paramsBuilder.withStartInterval(Duration.ofSeconds(jsonReader.nextInt()));
                    break;
                case PARTICIPANT_TIMEOUT:
                    paramsBuilder.withParticipantTimeout(Duration.ofSeconds(jsonReader.nextInt()));
                    break;
                case MODE:
                    paramsBuilder.withMode(TestMode.getConstant(jsonReader.nextString()));
                    break;
                case INCREMENT_STRATEGY:
                    paramsBuilder.withIncrementStrategy(
                        IncrementStrategy.getConstant(jsonReader.nextString()));
                    break;
                case GROUP_COUNT:
                    paramsBuilder.withGroupCount(jsonReader.nextInt());
                    break;
                case PARTICIPANT_COUNT:
                    paramsBuilder.withParticipantCount(jsonReader.nextInt());
                    break;
                default:
                    break;
            }
        }

        jsonReader.endObject();
        TestParams params = paramsBuilder.build();

        return new Test(params);
    }

    @Override
    public void write(JsonWriter jsonWriter, Test test) throws IOException {
        if (test == null) {
            jsonWriter.nullValue();
        } else {
            jsonWriter.beginObject();

            jsonWriter.name(NAME).value(test.getName());
            jsonWriter.name(SCRIPT).value(test.getScript());
            jsonWriter.name(START_INTERVAL).value(test.getStartInterval().toString());
            jsonWriter.name(PARTICIPANT_TIMEOUT).value(test.getParticipantTimeout().toString());
            jsonWriter.name(MODE).value(test.getMode().toString());
            jsonWriter.name(INCREMENT_STRATEGY).value(test.getIncrementStrategy().toString());

            jsonWriter.endObject();
        }
    }
}
