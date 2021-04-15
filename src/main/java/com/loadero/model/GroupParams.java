package com.loadero.model;

import com.loadero.util.StringUtil;

public class GroupParams implements ModelParams {
    private final int id;
    private final String created;
    private final String updated;
    private final int testId;
    private final String name;
    private final int count;
    private final int participantCount;

    private GroupParams(GroupParamsBuilder builder) {
        this.id = builder.id;
        this.created = builder.created;
        this.updated = builder.updated;
        this.testId = builder.testId;
        this.name = builder.name;
        this.count = builder.count;
        this.participantCount = builder.participantCount;
    }

    private GroupParams(
        int id, String created, String updated,
        int testId, String name, int count, int participantCount
    ) {
        this.id = id;
        this.created = created;
        this.updated = updated;
        this.testId = testId;
        this.name = name;
        this.count = count;
        this.participantCount = participantCount;
    }

    /**
     * Returns {@link GroupParamsBuilder}.
     *
     * @return {@link GroupParamsBuilder}
     */
    public static GroupParamsBuilder builder() {
        return new GroupParamsBuilder();
    }

    // Helper method for internal purposes.
    // Helps us update remote Loadero objects.
    GroupParams copyUncommonFields(Group currentParams) {
        String name =
            this.name == null
                ? currentParams.getName()
                : this.name.equals(currentParams.getName()) ? currentParams.getName() : this.name;
        int count =
            this.count == 0
                ? currentParams.getCount()
                : this.count == currentParams.getCount() ? currentParams.getCount() : this.count;
        return new GroupParams(id, created, updated, testId, name, count, participantCount);
    }

    public static final class GroupParamsBuilder {
        private int id;
        private String created;
        private String updated;
        private int testId;
        private String name;
        private int count;
        private int participantCount;

        private GroupParamsBuilder() {
        }

        /**
         * Sets ID for a group.
         *
         * @param id Numeric ID value.
         * @return {@link GroupParamsBuilder}
         */
        public GroupParamsBuilder withId(int id) {
            this.id = id;
            return this;
        }

        /**
         * Sets time when object was created in Loadero. Used for deserialization only.
         *
         * @param created
         * @return
         */
        public GroupParamsBuilder withTimeCreated(String created) {
            this.created = created;
            return this;
        }

        /**
         * Sets time when object was updated in Loadero. Used for deserialization only.
         *
         * @param updated
         * @return
         */
        public GroupParamsBuilder withTimeUpdated(String updated) {
            this.updated = updated;
            return this;
        }

        /**
         * Sets ID for a test.
         *
         * @param testId Numeric ID value
         * @return {@link GroupParamsBuilder}
         */
        public GroupParamsBuilder withTestId(int testId) {
            this.testId = testId;
            return this;
        }

        /**
         * Sets name for a group.
         *
         * @param name Group name.
         * @return {@link GroupParamsBuilder}
         * @throws IllegalArgumentException if name is null.
         */
        public GroupParamsBuilder withName(String name) {
            if (StringUtil.empty(name)) {
                throw new IllegalArgumentException("Name cannot be null or empty.");
            }
            this.name = name;
            return this;
        }

        /**
         * Sets count for a group.
         *
         * @param count Numeric value.
         * @return {@link GroupParamsBuilder}
         */
        public GroupParamsBuilder withCount(int count) {
            this.count = count;
            return this;
        }

        /**
         * Sets count of participants in group. Used for serialization/deserialization.
         *
         * @param count Participant count.
         * @return {@link GroupParamsBuilder}
         */
        public GroupParamsBuilder withParticipantCount(int count) {
            this.participantCount = count;
            return this;
        }

        /**
         * Builds {@link GroupParams} object.
         *
         * @return Constructed {@link GroupParams} object.
         */
        public GroupParams build() {
            return new GroupParams(this);
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

    public int getTestId() {
        return testId;
    }

    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }

    public int getParticipantCount() {
        return participantCount;
    }

    @Override
    public String toString() {
        return "GroupParams{" +
            "id=" + id +
            ", created=" + created +
            ", updated=" + updated +
            ", testId=" + testId +
            ", name='" + name + '\'' +
            ", count=" + count +
            ", participantCount=" + participantCount +
            '}';
    }
}
