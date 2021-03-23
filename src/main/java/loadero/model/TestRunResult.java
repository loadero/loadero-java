package loadero.model;

import lombok.Data;
import lombok.Generated;

import java.util.List;

/**
 * TestRunResult class represents the information retrieved from
 * Loadero /runs/:runID/results/.
 *
 * Basically, just a list of TestRunParticipantResult objects with
 * stored fields for our purposes.
 */
@Data
@Generated // Need this for JaCoco to ignore getters and setters
public final class TestRunResult implements LoaderoModel {
    private List<TestRunParticipantResult> results;
}
