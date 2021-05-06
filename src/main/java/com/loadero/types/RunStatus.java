package com.loadero.types;

/**
 * Contains values that are used to deserialize status for a {@link com.loadero.model.TestRun}.
 */
public enum RunStatus {
    PENDING,
    INITIALIZING,
    RUNNING,
    WAITING_RESULTS,
    COLLECTING_RESULTS,
    STOPPING,
    DONE,
    ABORTED,
    SERVER_ERROR,
    DB_ERROR,
    NO_USERS,
    AWS_ERROR,
    INSUFFICIENT_RESOURCES,
    TIMEOUT_EXCEEDED
}
