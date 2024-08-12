package com.loadero.types;

/**
 * Contains dynamic values that can be used to set machine paths for an {@link com.loadero.model.Assert}.
 * This provided name is used to create a path for the assert.
 */
public class TimeExecutionAsserts implements AssertPath {
    private static final String ACTIONS_TIMEEXECUTION_NAME = "actions/timeExecution/%s";

    private final String label;

    public static TimeExecutionAsserts getConstant(String name) {
        return new TimeExecutionAsserts(name);
    }

    public TimeExecutionAsserts(String name) {
        this.label = String.format(ACTIONS_TIMEEXECUTION_NAME, name);
    }

    @Override
    public String toString() {
        return label;
    }
}
