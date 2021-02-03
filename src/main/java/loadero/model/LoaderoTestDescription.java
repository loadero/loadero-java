package loadero.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

/**
 * LoaderoTestDescription object is a configuration profile to specify
 * test parameters for creation of Loadero tests.
 * Class will be created using Builder pattern so we can add
 * optional parameters to it. Params that are not specified is just empty String.
 */
@Data
@ToString
@AllArgsConstructor
public class LoaderoTestDescription {
    private final String name;
    private final String runMode;
    private final String incrementStrategy;
    private final int participantTimeout;
    private final String script;
    private final int startInterval;
}
