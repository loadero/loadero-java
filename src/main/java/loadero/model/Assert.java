package loadero.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import loadero.types.AssertOperator;
import loadero.types.AssertPath;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Generated
public final class Assert implements LoaderoModel {
    @Expose(serialize = false)
    private int id = 0;
    private AssertPath path;
    private AssertOperator operator;
    private String expected = "";
    @Expose(serialize = false)
    @SerializedName("test_id")
    private int testId = 0;
    @Expose(serialize = false)
    private List<Precondition> precondition;
    
    /**
     * Loadero asserts class.
     * @param path      Path to asserts like machine/* or webrtc/*
     * @param operator  Relational or equality operator
     * @param expected  Expected output
     */
    public Assert(AssertPath path, AssertOperator operator, String expected) {
        this.path = path;
        this.operator = operator;
        this.expected = expected;
    }
    
    /**
     * For testing purposes.
     */
    public Assert(int id, AssertPath path, AssertOperator operator, String expected, int testId) {
        this.id = id;
        this.path = path;
        this.operator = operator;
        this.expected = expected;
        this.testId = testId;
    }
}
