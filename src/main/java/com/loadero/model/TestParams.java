package com.loadero.model;

import com.loadero.exceptions.ApiException;
import com.loadero.util.StringUtil;
import java.io.IOException;
import java.time.Duration;
import com.loadero.types.IncrementStrategy;
import com.loadero.types.TestMode;

public final class TestParams implements ModelParams {
    private final int id;
    private final String created;
    private final String updated;
    private final int projectId;
    private final String name;
    private final int scriptFileId;
    private final String script;
    private final Duration startInterval;
    private final Duration participantTimeout;
    private final TestMode mode;
    private final IncrementStrategy incrementStrategy;
    private final int groupCount;
    private final int participantCount;

    private TestParams(TestParamsBuilder builder) {
        this.id = builder.id;
        this.created = builder.created;
        this.updated = builder.updated;
        this.projectId = builder.projectId;
        this.name = builder.name;
        this.scriptFileId = builder.scriptFileId;
        this.script = builder.script;
        this.startInterval = builder.startInterval;
        this.participantTimeout = builder.participantTimeout;
        this.mode = builder.mode;
        this.incrementStrategy = builder.incrementStrategy;
        this.groupCount = builder.groupCount;
        this.participantCount = builder.participantCount;
    }

    private TestParams(
        int id, String created, String updated,
        int projectId, String name, int scriptFileId, String script,
        Duration startInterval, Duration participantTimeout,
        TestMode mode, IncrementStrategy incrementStrategy,
        int groupCount, int participantCount
    ) {
        this.id = id;
        this.created = created;
        this.updated = updated;
        this.projectId = projectId;
        this.name = name;
        this.scriptFileId = scriptFileId;
        this.script = script;
        this.startInterval = startInterval;
        this.participantTimeout = participantTimeout;
        this.mode = mode;
        this.incrementStrategy = incrementStrategy;
        this.groupCount = groupCount;
        this.participantCount = participantCount;
    }

    /**
     * @return {@link TestParamsBuilder}
     */
    public static TestParamsBuilder builder() {
        return new TestParamsBuilder();
    }

    // Can change: name, mode, strategy, start, timeout, script
    TestParams copyUncommonFields(Test currentParams) throws IOException {
        String name = this.name == null ? currentParams.getName()
            : this.name.equals(currentParams.getName()) ? currentParams.getName() : this.name;
        TestMode mode = this.mode == null ? currentParams.getMode()
            : this.mode == currentParams.getMode() ? currentParams.getMode() : this.mode;
        IncrementStrategy strategy = this.incrementStrategy == null
            ? currentParams.getIncrementStrategy()
            : this.incrementStrategy == currentParams.getIncrementStrategy()
                ? currentParams.getIncrementStrategy() : this.incrementStrategy;
        Duration startInterval = this.startInterval == null ? currentParams.getStartInterval()
            : this.startInterval == currentParams.getStartInterval()
                ? currentParams.getStartInterval() : this.startInterval;
        Duration timeout = this.participantTimeout == null ? currentParams.getParticipantTimeout()
            : this.participantTimeout == currentParams.getParticipantTimeout() ?
                currentParams.getParticipantTimeout() : this.participantTimeout;
        String currentScript = Script.read(currentParams.getScriptFileId()).getContent();
        String script = this.script == null ? currentScript
            : this.script.equals(currentScript) ? currentScript : this.script;

        return new TestParams(
            id, created, updated,
            projectId, name, scriptFileId,
            script, startInterval, timeout,
            mode, strategy,
            groupCount, participantCount
        );
    }

    public static final class TestParamsBuilder {
        private int id;
        private String created;
        private String updated;
        private int projectId;
        private String name;
        private int scriptFileId;
        private Duration startInterval;
        private String script;
        private Duration participantTimeout;
        private TestMode mode;
        private IncrementStrategy incrementStrategy;
        private int groupCount;
        private int participantCount;

        private TestParamsBuilder() {
        }

        /**
         * Sets id.
         *
         * @param id Test ID.
         * @return value of {@link TestParamsBuilder}
         */
        public TestParamsBuilder withId(int id) {
            this.id = id;
            return this;
        }

        /**
         * Sets created.
         *
         * @param created
         * @return value of {@link TestParamsBuilder}
         */
        public TestParamsBuilder withCreated(String created) {
            this.created = created;
            return this;
        }

        /**
         * Sets updated.
         *
         * @param updated
         * @return value of {@link TestParamsBuilder}
         */
        public TestParamsBuilder withUpdated(String updated) {
            this.updated = updated;
            return this;
        }

        /**
         * Sets projectId.
         *
         * @param projectId
         * @return value of {@link TestParamsBuilder}
         */
        public TestParamsBuilder withProjectId(int projectId) {
            this.projectId = projectId;
            return this;
        }

        /**
         * Sets name.
         *
         * @param name
         * @return value of {@link TestParamsBuilder}
         * @throws IllegalArgumentException if name is null or empty string.
         */
        public TestParamsBuilder withName(String name) {
            if (StringUtil.empty(name)) {
                throw new IllegalArgumentException("Name cannot be null or empty.");
            }
            this.name = name;
            return this;
        }

        /**
         * Sets scriptFileId.
         *
         * @param scriptFileId ID of the script that is used for this test.
         * @return value of {@link TestParamsBuilder}
         */
        public TestParamsBuilder withScriptFileId(int scriptFileId) {
            this.scriptFileId = scriptFileId;
            return this;
        }

        /**
         * Sets Java's Test-UI script.
         *
         * @param pathToScript Path to script's location.
         * @param testName     Name of the test method that is going to be executed in Laodero.
         * @return value of {@link TestParamsBuilder}
         * @throws ApiException if testName or script file is empty.
         */
        public TestParamsBuilder withScriptJava(String pathToScript, String testName) {
            String script = ScriptBodyParser.getScriptContentJava(pathToScript, testName);
            if (StringUtil.empty(testName) || StringUtil.empty(script)) {
                throw new ApiException("Test name or script cannot be empty.");
            }
            this.script = script;
            return this;
        }

        /**
         * Sets Nightwatch.js script
         *
         * @param pathToScript Path to Nightwatch.js script.
         * @return value of {@link TestParamsBuilder}
         */
        public TestParamsBuilder withScriptJs(String pathToScript) {
            String script = ScriptBodyParser.getScriptContent(pathToScript);
            if (StringUtil.empty(script)) {
                throw new ApiException("Test name or script cannot be empty.");
            }
            this.script = script;
            return this;
        }

        /**
         * Sets Py-TestUI Python script.
         *
         * @param pathToScript Path to Py-TestUi script.
         * @return value of {@link TestParamsBuilder}
         */
        public TestParamsBuilder withScriptPython(String pathToScript) {
            String script = ScriptBodyParser.getScriptContent(pathToScript);
            if (StringUtil.empty(script)) {
                throw new ApiException("Test name or script cannot be empty.");
            }
            this.script = script;
            return this;
        }

        /**
         * Sets startInterval.
         *
         * @param startInterval Value of {@link Duration} in seconds.
         * @return value of {@link TestParamsBuilder}
         */
        public TestParamsBuilder withStartInterval(Duration startInterval) {
            this.startInterval = startInterval;
            return this;
        }

        /**
         * Sets participantTimeout.
         *
         * @param participantTimeout Value of {@link Duration} in seconds.
         * @return value of {@link TestParamsBuilder}
         */
        public TestParamsBuilder withParticipantTimeout(Duration participantTimeout) {
            this.participantTimeout = participantTimeout;
            return this;
        }

        /**
         * Sets mode.
         *
         * @param mode Values of {@link TestMode} that is going to be used to run test.
         * @return value of {@link TestParamsBuilder}
         */
        public TestParamsBuilder withMode(TestMode mode) {
            this.mode = mode;
            return this;
        }

        /**
         * Sets incrementStrategy.
         *
         * @param incrementStrategy
         * @return value of {@link TestParamsBuilder}
         */
        public TestParamsBuilder withIncrementStrategy(IncrementStrategy incrementStrategy) {
            this.incrementStrategy = incrementStrategy;
            return this;
        }

        /**
         * Sets groupCount.
         *
         * @param groupCount
         * @return value of {@link TestParamsBuilder}
         */
        public TestParamsBuilder withGroupCount(int groupCount) {
            this.groupCount = groupCount;
            return this;
        }

        /**
         * Sets participantCount.
         *
         * @param participantCount
         * @return value of {@link TestParamsBuilder}
         */
        public TestParamsBuilder withParticipantCount(int participantCount) {
            this.participantCount = participantCount;
            return this;
        }

        /**
         * Builds {@link TestParams} object.
         *
         * @return Constructed {@link TestParams} object.
         */
        public TestParams build() {
            return new TestParams(this);
        }
    }

    public int getId() {
        return id;
    }

    public String getCreated() {
        return created;
    }

    public String getUpdated() {
        return updated;
    }

    public int getProjectId() {
        return projectId;
    }

    public String getName() {
        return name;
    }

    public int getScriptFileId() {
        return scriptFileId;
    }

    public String getScript() {
        return script;
    }

    public Duration getStartInterval() {
        return startInterval;
    }

    public Duration getParticipantTimeout() {
        return participantTimeout;
    }

    public TestMode getMode() {
        return mode;
    }

    public IncrementStrategy getIncrementStrategy() {
        return incrementStrategy;
    }

    public int getGroupCount() {
        return groupCount;
    }

    public int getParticipantCount() {
        return participantCount;
    }

    @Override
    public String toString() {
        return "TestParams{" +
            "id=" + id +
            ", created='" + created + '\'' +
            ", updated='" + updated + '\'' +
            ", projectId=" + projectId +
            ", name='" + name + '\'' +
            ", scriptFileId=" + scriptFileId +
            ", script=" + script +
            ", startInterval=" + startInterval +
            ", participantTimeout=" + participantTimeout +
            ", mode=" + mode +
            ", incrementStrategy=" + incrementStrategy +
            ", groupCount=" + groupCount +
            ", participantCount=" + participantCount +
            '}';
    }
}
