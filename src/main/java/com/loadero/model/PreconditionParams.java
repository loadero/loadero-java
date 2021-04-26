package com.loadero.model;

import com.loadero.types.AssertOperator;
import com.loadero.types.Property;

public final class PreconditionParams implements ModelParams {
    private final int id;
    private final String created;
    private final String updated;
    private final Property property;
    private final AssertOperator operator;
    private final String expected;
    private final int assertId;
    private final int testId;

    private PreconditionParams(PreconditionParamsBuilder builder) {
        this.id = builder.id;
        this.created = builder.created;
        this.updated = builder.updated;
        this.property = builder.property;
        this.operator = builder.operator;
        this.expected = builder.expected;
        this.assertId = builder.assertId;
        this.testId = builder.testId;
    }

    private PreconditionParams(
        int id, String created, String updated, Property property,
        AssertOperator operator, String expected,
        int assertId, int testId
    ) {
        this.id = id;
        this.created = created;
        this.updated = updated;
        this.property = property;
        this.operator = operator;
        this.expected = expected;
        this.assertId = assertId;
        this.testId = testId;
    }

    /**
     * @return {@link PreconditionParamsBuilder}
     */
    public static PreconditionParamsBuilder builder() {
        return new PreconditionParamsBuilder();
    }

    PreconditionParams copyUncommonFields(Precondition currentParams) {
        String expected = this.expected == null ? currentParams.getExpected() : this.expected;
        Property property = this.property == null ? currentParams.getProperty() : this.property;
        AssertOperator operator = this.operator == null
            ? currentParams.getOperator() : this.operator;

        return new PreconditionParams(
            id, created, updated,
            property, operator, expected,
            assertId, testId
        );
    }

    public static final class PreconditionParamsBuilder {
        private int id;
        private String created;
        private String updated;
        private Property property;
        private AssertOperator operator;
        private String expected;
        private int assertId;
        private int testId;

        private PreconditionParamsBuilder() {
        }

        /**
         * Sets id.
         *
         * @param id
         * @return value of {@link PreconditionParamsBuilder}
         */
        public PreconditionParamsBuilder withId(int id) {
            this.id = id;
            return this;
        }

        /**
         * Sets created.
         *
         * @param created
         * @return value of {@link PreconditionParamsBuilder}
         */
        public PreconditionParamsBuilder withCreated(String created) {
            this.created = created;
            return this;
        }

        /**
         * Sets updated.
         *
         * @param updated
         * @return value of {@link PreconditionParamsBuilder}
         */
        public PreconditionParamsBuilder withUpdated(String updated) {
            this.updated = updated;
            return this;
        }

        /**
         * Sets property.
         *
         * @param property
         * @return value of {@link PreconditionParamsBuilder}
         */
        public PreconditionParamsBuilder withProperty(Property property) {
            this.property = property;
            return this;
        }

        /**
         * Sets operator.
         *
         * @param operator
         * @return value of {@link PreconditionParamsBuilder}
         */
        public PreconditionParamsBuilder withOperator(AssertOperator operator) {
            this.operator = operator;
            return this;
        }

        /**
         * Sets expected.
         *
         * @param expected
         * @return value of {@link PreconditionParamsBuilder}
         */
        public PreconditionParamsBuilder withExpected(String expected) {
            this.expected = expected;
            return this;
        }

        /**
         * Sets assertId.
         *
         * @param assertId
         * @return value of {@link PreconditionParamsBuilder}
         */
        public PreconditionParamsBuilder withAssertId(int assertId) {
            this.assertId = assertId;
            return this;
        }

        /**
         * Sets testId.
         *
         * @param testId
         * @return value of {@link PreconditionParamsBuilder}
         */
        public PreconditionParamsBuilder withTestId(int testId) {
            this.testId = testId;
            return this;
        }

        public PreconditionParams build() {
            return new PreconditionParams(this);
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

    public Property getProperty() {
        return property;
    }

    public AssertOperator getOperator() {
        return operator;
    }

    public String getExpected() {
        return expected;
    }

    public int getAssertId() {
        return assertId;
    }

    public int getTestId() {
        return testId;
    }

    @Override
    public String toString() {
        return "PreconditionParams{" +
            "id=" + id +
            ", created='" + created + '\'' +
            ", updated='" + updated + '\'' +
            ", property=" + property +
            ", operator=" + operator +
            ", expected='" + expected + '\'' +
            ", assertId=" + assertId +
            ", testId=" + testId +
            '}';
    }
}
