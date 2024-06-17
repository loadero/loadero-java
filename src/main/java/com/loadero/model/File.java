package com.loadero.model;

import com.google.gson.annotations.SerializedName;
import com.loadero.Loadero;
import com.loadero.http.ApiResource;
import com.loadero.http.RequestMethod;
import java.io.IOException;

/**
 * Used to read information about a project file.
 */
public final class File {
    private final int id;
    private final String created;
    private final String updated;
    @SerializedName("project_id")
    private final int projectId;
    @SerializedName("file_type")
    private final String fileType;
    private final String content;
    private final String name;
    private final String password;

    File(
        int id, String created, String updated,
        int projectId, String fileType, String content, 
        String name, String password
    ) {
        this.id = id;
        this.created = created;
        this.updated = updated;
        this.projectId = projectId;
        this.fileType = fileType;
        this.content = content;
        this.name = name;
        this.password = password;
    }

    /**
     * Retrieves file from Loadero.
     *
     * @param fileId ID of the file.
     * @return {@link File} object.
     * @throws IOException if request failed.
     */
    public static File read(int fileId) throws IOException {
        String route = buildRoute(fileId);
        return ApiResource.request(RequestMethod.GET, route, null, File.class);
    }

    private static String buildRoute(int fileId) {
        return String.format("%s/files/%s/", Loadero.getProjectUrl(), fileId);
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

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }


    @Override
    public String toString() {
        return "File{" +
            "id=" + id +
            ", created='" + created + '\'' +
            ", updated='" + updated + '\'' +
            ", projectId=" + projectId +
            ", fileType='" + fileType + '\'' +
            ", content='" + content + '\'' +
            ", name='" + name + '\'' +
            ", password='" + password + '\'' +
            '}';
    }
}
