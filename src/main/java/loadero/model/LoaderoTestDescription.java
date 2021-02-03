package loadero.model;

/**
 * LoaderoTestDescription object is a configuration profile to specify
 * test parameters for Loadero tests.
 * Class will be created using Builder pattern so we can add
 * optional parameters to it. Params that are not specified is just empty String.
 */
public class LoaderoTestDescription {
    private final String name;
    private final String runMode;
    private final String incrementStrategy;
    private final String testTimeout;
    private final String participantTimeout;
    private final String script;
    private final String startInterval;
    private final String userTimeout;

    public static class LoaderoTestBuilder {
        private String name = "";
        private String runMode =  "";
        private String incrementStrategy = "";
        private String testTimeout = "";
        private String participantTimeout = "";
        private String script = "";
        private String startInterval = "";
        private String userTimeout = "";

        public LoaderoTestBuilder withName(String val) {
            name = val;
            return this;
        }

        public LoaderoTestBuilder withRunMode(String val) {
            runMode = val;
            return this;
        }

        public LoaderoTestBuilder withIncrementStrategy(String val) {
            incrementStrategy = val;
            return this;
        }

        public LoaderoTestBuilder withTestTimeout(String val) {
            testTimeout = val;
            return this;
        }

        public LoaderoTestBuilder withParticipantTimeout(String val) {
            participantTimeout = val;
            return this;
        }

        public LoaderoTestBuilder withScript(String val) {
            script = val;
            return this;
        }

        public LoaderoTestBuilder withStartInterval(String val) {
            startInterval = val;
            return this;
        }

        public LoaderoTestBuilder withUserTimeout(String val) {
            userTimeout = val;
            return this;
        }

        public LoaderoTestDescription build() {
            return new LoaderoTestDescription(this);
        }
    }

    private LoaderoTestDescription(LoaderoTestBuilder builder) {
        name = builder.name;
        runMode = builder.runMode;
        incrementStrategy = builder.incrementStrategy;
        testTimeout = builder.testTimeout;
        participantTimeout = builder.participantTimeout;
        script = builder.script;
        startInterval = builder.startInterval;
        userTimeout = builder.userTimeout;
    }
}
