package com.loadero.http;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.loadero.model.TestRun;
import com.loadero.types.IncrementStrategy;
import com.loadero.types.RunStatus;
import com.loadero.types.TestMode;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class TestRunTypeAdapter extends TypeAdapter<TestRun> {
    private final String ID = "id";
    private final String CREATED = "created";
    private final String UPDATED = "updated";
    private final String TEST_ID = "test_id";
    private final String STATUS = "status";
    private final String TEST_MODE = "test_mode";
    private final String INCREMENT_STRATEGY = "increment_strategy";
    private final String PROCESSING_STARTED = "processing_started";
    private final String PROCESSING_FINISHED = "processing_finished";
    private final String EXECUTION_STARTED = "execution_started";
    private final String EXECUTION_FINISHED = "execution_finished";
    private final String SCRIPT_FILE_ID = "script_file_id";
    private final String TEST_NAME = "test_name";
    private final String START_INTERVAL = "start_interval";
    private final String PARTICIPANT_TIMEOUT = "participant_timeout";
    private final String LAUNCHING_ACCOUNT_ID = "launching_account_id";
    private final String SUCCESS_RATE = "success_rate";
    private final String GROUP_COUNT = "group_count";
    private final String PARTICIPANT_COUNT = "participant_count";

    @Override
    public TestRun read(JsonReader jsonReader) throws IOException {
        Map<String, Object> testRunMap = new HashMap<>();
        jsonReader.beginObject();

        while (jsonReader.hasNext()) {
            String key = jsonReader.nextName();
            String value;

            switch (key) {
                case ID:
                    testRunMap.put(ID, jsonReader.nextInt());
                    break;
                case CREATED:
                    testRunMap.put(CREATED, jsonReader.nextString());
                    break;
                case UPDATED:
                    testRunMap.put(UPDATED, jsonReader.nextString());
                    break;
                case TEST_ID:
                    testRunMap.put(TEST_ID, jsonReader.nextInt());
                    break;
                case STATUS:
                    String formattedStatus = formatStatus(
                        jsonReader.nextString().toUpperCase(Locale.ROOT));
                    RunStatus status = RunStatus.valueOf(formattedStatus);
                    testRunMap.put(STATUS, status);
                    break;
                case TEST_MODE:
                    TestMode mode = TestMode.getConstant(jsonReader.nextString());
                    testRunMap.put(TEST_MODE, mode);
                    break;
                case INCREMENT_STRATEGY:
                    IncrementStrategy strategy = IncrementStrategy.getConstant(
                        jsonReader.nextString());
                    testRunMap.put(INCREMENT_STRATEGY, strategy);
                    break;
                case PROCESSING_STARTED:
                    value = jsonReader.nextString();
                    String proccesingStarted = Objects.isNull(value) ? "" : value;
                    testRunMap.put(PROCESSING_STARTED, proccesingStarted);
                    break;
                case PROCESSING_FINISHED:
                    value = jsonReader.nextString();
                    String proccesingFinished = Objects.isNull(value) ? "" : value;
                    testRunMap.put(PROCESSING_FINISHED, proccesingFinished);
                    break;
                case EXECUTION_STARTED:
                    value = jsonReader.nextString();
                    String executionStarted = Objects.isNull(value) ? "" : value;
                    testRunMap.put(EXECUTION_STARTED, executionStarted);
                    break;
                case EXECUTION_FINISHED:
                    value = jsonReader.nextString();
                    String executionFinished = Objects.isNull(value) ? "" : value;
                    testRunMap.put(EXECUTION_FINISHED, executionFinished);
                    break;
                case SCRIPT_FILE_ID:
                    testRunMap.put(SCRIPT_FILE_ID, jsonReader.nextInt());
                    break;
                case TEST_NAME:
                    testRunMap.put(TEST_NAME, jsonReader.nextString());
                    break;
                case START_INTERVAL:
                    Duration startInterval = Duration.ofSeconds(jsonReader.nextInt());
                    testRunMap.put(START_INTERVAL, startInterval);
                    break;
                case PARTICIPANT_TIMEOUT:
                    Duration timeout = Duration.ofSeconds(jsonReader.nextInt());
                    testRunMap.put(PARTICIPANT_TIMEOUT, timeout);
                    break;
                case LAUNCHING_ACCOUNT_ID:
                    testRunMap.put(LAUNCHING_ACCOUNT_ID, jsonReader.nextInt());
                    break;
                case SUCCESS_RATE:
                    testRunMap.put(SUCCESS_RATE, jsonReader.nextDouble());
                    break;
                case GROUP_COUNT:
                    testRunMap.put(GROUP_COUNT, jsonReader.nextInt());
                    break;
                case PARTICIPANT_COUNT:
                    testRunMap.put(PARTICIPANT_TIMEOUT, jsonReader.nextInt());
                    break;
                default:
                    break;
            }
        }

        jsonReader.endObject();

        double successRate = testRunMap.get(SUCCESS_RATE) == null ? 0.0 :
            (double) testRunMap.get(SUCCESS_RATE);
        int groupCount = testRunMap.get(GROUP_COUNT) == null ? 0 :
            (int) testRunMap.get(GROUP_COUNT);
        int participantCount = testRunMap.get(PARTICIPANT_COUNT) == null ? 0 :
            (int) testRunMap.get(PARTICIPANT_COUNT);
        String processingStarted = testRunMap.get(PROCESSING_STARTED) == null ? ""
            : (String) testRunMap.get(PROCESSING_STARTED);
        String processingFinished = testRunMap.get(PROCESSING_FINISHED) == null ? ""
            : (String) testRunMap.get(PROCESSING_FINISHED);
        String executionStarted = testRunMap.get(EXECUTION_STARTED) == null ? ""
            : (String) testRunMap.get(EXECUTION_STARTED);
        String executionFinished = testRunMap.get(EXECUTION_FINISHED) == null ? ""
            : (String) testRunMap.get(EXECUTION_FINISHED);

        return new TestRun(
            (int) testRunMap.get(ID),
            (String) testRunMap.get(CREATED),
            (String) testRunMap.get(UPDATED),
            processingStarted,
            processingFinished,
            executionStarted,
            executionFinished,
            (int) testRunMap.get(TEST_ID),
            (RunStatus) testRunMap.get(STATUS),
            (TestMode) testRunMap.get(TEST_MODE),
            (IncrementStrategy) testRunMap.get(INCREMENT_STRATEGY),
            (int) testRunMap.get(SCRIPT_FILE_ID),
            (String) testRunMap.get(TEST_NAME),
            (Duration) testRunMap.get(START_INTERVAL),
            (Duration) testRunMap.get(PARTICIPANT_TIMEOUT),
            (int) testRunMap.get(LAUNCHING_ACCOUNT_ID),
            successRate,
            groupCount,
            participantCount
        );
    }

    @Override
    public void write(JsonWriter jsonWriter, TestRun testRun) throws IOException {
        // we don't need to serialize TestRun into JSON
        // but we need this method here to comfort TypeAdapter<T> class
    }

    private String formatStatus(String name) {
        return name.replaceAll("-", "_").toUpperCase(Locale.ROOT);
    }
}
