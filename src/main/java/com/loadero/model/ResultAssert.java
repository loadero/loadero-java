package com.loadero.model;

import com.loadero.types.AssertOperator;
import com.loadero.types.AssertPath;
import com.loadero.types.AssertStatus;

/**
 * Class to deserialize information about asserts field in results.
 */
public final class ResultAssert {
    private final int id;
    private final int resultId;
    private final int runAssertId;
    private final String created;
    private final AssertPath path;
    private final AssertOperator operator;
    private final String expected;
    private final String actual;
    private final AssertStatus status;

    public ResultAssert(
        int id,
        int resultId,
        int runAssertId,
        String created,
        AssertPath path,
        AssertOperator operator,
        String expected,
        String actual,
        AssertStatus status
    ) {
        this.id = id;
        this.resultId = resultId;
        this.runAssertId = runAssertId;
        this.created = created;
        this.path = path;
        this.operator = operator;
        this.expected = expected;
        this.actual = actual;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public int getResultId() {
        return resultId;
    }

    public int getRunAssertId() {
        return runAssertId;
    }

    public String getCreated() {
        return created;
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

    public String getActual() {
        return actual;
    }

    public AssertStatus getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "ResultAssert{" +
            "id=" + id +
            ", resultId=" + resultId +
            ", runAssertId=" + runAssertId +
            ", created='" + created + '\'' +
            ", path=" + path +
            ", operator=" + operator +
            ", expected='" + expected + '\'' +
            ", actual='" + actual + '\'' +
            ", status=" + status +
            '}';
    }
}
