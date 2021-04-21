package com.loadero.model;

import com.google.gson.annotations.SerializedName;
import com.loadero.Loadero;
import com.loadero.http.ApiResource;
import com.loadero.http.RequestMethod;
import java.io.IOException;

public final class Script {
    private final int id;
    private final String created;
    private final String updated;
    @SerializedName("project_id")
    private final int projectId;
    @SerializedName("file_type")
    private final String fileType;
    private final String content;

    Script(
        int id, String created, String updated,
        int projectId, String fileType, String content
    ) {
        this.id = id;
        this.created = created;
        this.updated = updated;
        this.projectId = projectId;
        this.fileType = fileType;
        this.content = content;
    }

    /**
     * Retrieves script file from Loadero.
     *
     * @param scriptId ID of the scirpt.
     * @return {@link Script} object.
     * @throws IOException if request failed.
     */
    public static Script read(int scriptId) throws IOException {
        String route = buildRoute(scriptId);
        return ApiResource.request(RequestMethod.GET, route, null, Script.class);
    }

    private static String buildRoute(int scriptId) {
        return String.format("%s/files/%s/", Loadero.getProjectUrl(), scriptId);
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

    public String getFileType() {
        return fileType;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "Script{" +
            "id=" + id +
            ", created='" + created + '\'' +
            ", updated='" + updated + '\'' +
            ", projectId=" + projectId +
            ", fileType='" + fileType + '\'' +
            ", content='" + content + '\'' +
            '}';
    }
}
