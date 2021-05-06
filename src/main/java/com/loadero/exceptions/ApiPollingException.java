package com.loadero.exceptions;

/**
 * Used to throw an error during polling operations.
 */
public class ApiPollingException extends LoaderoException {
    public ApiPollingException(String message) {
        super(message);
    }
}
