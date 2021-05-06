package com.loadero.model;

import com.loadero.types.AssertOperator;
import com.loadero.types.AssertPath;
import java.util.List;

/**
 * Builder class that is used to define parameters for an {@link Assert}.
 */
public final class AssertParams implements ModelParams {
    private final int id;
    private final String created;
    private final String updated;
    private final int testId;
    private final AssertPath path;
    private final AssertOperator operator;
    private final String expected;
    private final List<Precondition> preconditions;

    private AssertParams(AssertsParamsBuilder builder) {
        this.id = builder.id;
        this.created = builder.created;
        this.updated = builder.updated;
        this.testId = builder.testId;
        this.path = builder.path;
        this.operator = builder.operator;
        this.expected = builder.expected;
        this.preconditions = builder.preconditions;
    }

    private AssertParams(
        int id,
        String created,
        String updated,
        int testId,
        AssertPath path,
        AssertOperator operator,
        String expected,
        List<Precondition> preconditions
    ) {
        this.id = id;
        this.created = created;
        this.updated = updated;
        this.testId = testId;
        this.path = path;
        this.operator = operator;
        this.expected = expected;
        this.preconditions = preconditions;
    }

    /**
     * @return {@link AssertsParamsBuilder}
     */
    public static AssertsParamsBuilder builder() {
        return new AssertsParamsBuilder();
    }

    AssertParams copyUncommonFields(Assert currentParams) {
        String expected = this.expected == null ? currentParams.getExpected() : this.expected;
        AssertOperator operator = this.operator == null ? currentParams.getOperator() : this.operator;
        AssertPath path = this.path == null ? currentParams.getPath() : this.path;

        return new AssertParams(
            id,
            created,
            updated,
            currentParams.getTestId(),
            path,
            operator,
            expected,
            currentParams.getPreconditions()
        );
    }

    public static final class AssertsParamsBuilder {
        private int id;
        private String created;
        private String updated;
        private int testId;
        private AssertPath path;
        private AssertOperator operator;
        private String expected;
        private List<Precondition> preconditions;

        private AssertsParamsBuilder() {
        }

        /**
         * Sets id.
         *
         * @param id
         * @return value of {@link AssertsParamsBuilder}
         */
        public AssertsParamsBuilder withId(int id) {
            this.id = id;
            return this;
        }

        /**
         * Sets created.
         *
         * @param created
         * @return value of {@link AssertsParamsBuilder}
         */
        public AssertsParamsBuilder withCreated(String created) {
            this.created = created;
            return this;
        }

        /**
         * Sets updated.
         *
         * @param updated
         * @return value of {@link AssertsParamsBuilder}
         */
        public AssertsParamsBuilder withUpdated(String updated) {
            this.updated = updated;
            return this;
        }

        /**
         * Sets testId.
         *
         * @param testId
         * @return value of {@link AssertsParamsBuilder}
         */
        public AssertsParamsBuilder withTestId(int testId) {
            this.testId = testId;
            return this;
        }

        /**
         * Sets path.
         *
         * @param path
         * @return value of {@link AssertsParamsBuilder}
         */
        public AssertsParamsBuilder withPath(AssertPath path) {
            this.path = path;
            return this;
        }

        /**
         * Sets operator.
         *
         * @param operator
         * @return value of {@link AssertsParamsBuilder}
         */
        public AssertsParamsBuilder withOperator(AssertOperator operator) {
            this.operator = operator;
            return this;
        }

        /**
         * Sets expected.
         *
         * @param expected
         * @return value of {@link AssertsParamsBuilder}
         */
        public AssertsParamsBuilder withExpected(String expected) {
            this.expected = expected;
            return this;
        }

        /**
         * Sets preconditions.
         *
         * @param preconditions
         * @return value of {@link AssertsParamsBuilder}
         */
        public AssertsParamsBuilder withPreconditions(
            List<Precondition> preconditions
        ) {
            this.preconditions = preconditions;
            return this;
        }

        public AssertParams build() {
            return new AssertParams(this);
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

    public AssertPath getPath() {
        return path;
    }

    public AssertOperator getOperator() {
        return operator;
    }

    public String getExpected() {
        return expected;
    }

    public List<Precondition> getPreconditions() {
        return preconditions;
    }

    @Override
    public String toString() {
        return "AssertParams{" +
            "id=" + id +
            ", created='" + created + '\'' +
            ", updated='" + updated + '\'' +
            ", testId=" + testId +
            ", path=" + path +
            ", operator=" + operator +
            ", expected='" + expected + '\'' +
            ", preconditions=" + preconditions +
            '}';
    }
}
