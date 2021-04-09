package com.loadero.util;

public class StringUtil {

    /**
     * Checks string for null and emptiness.
     *
     * @return boolean
     */
    public static boolean empty(String value) {
        return value == null || value.trim().isEmpty();
    }
}
