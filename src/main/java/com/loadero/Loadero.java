package com.loadero;

/**
 * Singleton instance that represents setting for Loadero.
 */
public enum Loadero {
    ;

    private static String BASE_URL;
    private static String token;
    private static int projectId;

    public static void init(String token, int projectId) {
        BASE_URL = "https://api.loadero.com/v2";
        Loadero.setToken(token);
        Loadero.setProjectId(projectId);
    }

    // For testing.
    public static void init(String baseUrl, String token, int projectId) {
        BASE_URL = baseUrl;
        Loadero.setToken(token);
        Loadero.setProjectId(projectId);
    }

    public static String getBaseUrl() {
        return BASE_URL;
    }

    public static String getProjectUrl() {
        return String.format("%s/projects/%s", BASE_URL, projectId);
    }

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        Loadero.token = token;
    }

    public static int getProjectId() {
        return projectId;
    }

    public static void setProjectId(int projectId) {
        Loadero.projectId = projectId;
    }
}
