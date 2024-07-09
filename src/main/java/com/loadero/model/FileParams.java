package com.loadero.model;

import com.google.gson.annotations.SerializedName;
import com.loadero.types.FileType;
import com.loadero.util.StringUtil;

/**
 * Builder class that is used to define parameters for a {@link File}.
 */
public final class FileParams implements ModelParams {
    private final int id;
    private final String created;
    private final String updated;
    @SerializedName("project_id")
    private final int projectId;
    private final FileType fileType;
    private final String content;
    private final String name;
    private final String password;

    private FileParams(FileParamsBuilder builder) {
        this.id = builder.id;
        this.created = builder.created;
        this.updated = builder.updated;
        this.projectId = builder.projectId;
        this.fileType = builder.fileType;
        this.content = builder.content;
        this.name = builder.name;
        this.password = builder.password;
    }

    private FileParams(
            int id, String created, String updated,
            int projectId, FileType fileType,
            String content, String name, String password
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
     * Returns {@link FileParamsBuilder} instance.
     *
     * @return {@link FileParamsBuilder}
     */
    public static FileParamsBuilder builder() {
        return new FileParamsBuilder();
    }

    // Can change: fileType, name, content, password.
    FileParams copyUncommonFields(File currentParams) {
        FileType fileType = this.fileType == null ? currentParams.getFileType() : this.fileType;
        String name = this.name == null ? currentParams.getName() : this.name;
        String content = this.content == "" ? currentParams.getContent() : this.content;
        String password = this.password == "" ? currentParams.getPassword() : this.password;

        return new FileParams(
            id, created, updated,
            projectId, fileType, content,
            name, password
        );
    }

    public static final class FileParamsBuilder {
        private int id;
        private String created;
        private String updated;
        private int projectId;
        private FileType fileType;
        private String content;
        private String name;
        private String password;

        private FileParamsBuilder() {
        }

        /**
         * Sets file's id.
         *
         * @param id Integer value.
         * @return {@link FileParamsBuilder}
         */
        public FileParamsBuilder withId(int id) {
            this.id = id;
            return this;
        }

        /**
         * Sets time when object was created. Used for deserialization only.
         */
        public FileParamsBuilder withCreated(String created) {
            this.created = created;
            return this;
        }

        /**
         * Sets time when object was updated. Used for deserialization only.
         */
        public FileParamsBuilder withUpdated(String updated) {
            this.updated = updated;
            return this;
        }

        /**
         * Sets project's id.
         *
         * @param projectId Integer value.
         * @return {@link FileParamsBuilder}
         */
        public FileParamsBuilder withProjectId(int projectId) {
            this.projectId = projectId;
            return this;
        }

        /**
         * Sets file type.
         *
         * @param fileType FileType.
         * @return {@link FileParamsBuilder}
         */
        public FileParamsBuilder withFileType(FileType fileType) {
            this.fileType = fileType;
            return this;
        }

        /**
         * Sets file content.
         *
         * @param content String value.
         * @return {@link FileParamsBuilder}
         */
        public FileParamsBuilder withContent(String content) {
            this.content = content;
            return this;
        }

        /**
         * Sets file's name.
         *
         * @param name String value.
         * @return {@link FileParamsBuilder}
         * @throws IllegalArgumentException if name is null or empty.
         */
        public FileParamsBuilder withName(String name) {
            if (StringUtil.empty(name)) {
                throw new IllegalArgumentException("Name cannot be empty");
            }
            this.name = name;
            return this;
        }

        /**
         * Sets file's password.
         *
         * @param password String value.
         * @return {@link FileParamsBuilder}
         */
        public FileParamsBuilder withPassword(String password) {
            this.password = password;
            return this;
        }

        /**
         * Builds {@link FileParams} object.
         *
         * @return Built {@link FileParams} object.
         */
        public FileParams build() {
            return new FileParams(this);
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

    public FileType getFileType() {
        return fileType;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "FileParams{" +
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
