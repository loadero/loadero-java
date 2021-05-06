package com.loadero.exceptions;

/**
 * Used to throw any error that might come up during
 * interaction with Loadero's API.
 */
public class ApiException extends LoaderoException {
    public ApiException(String message) {
        super(message);
    }
}
