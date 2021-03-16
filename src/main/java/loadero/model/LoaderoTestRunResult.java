package loadero.model;

import lombok.Data;
import lombok.Generated;

import java.util.List;

/**
 * LoaderoTestRunResult class represents the information retrieved from
 * Loadero /runs/:runID/results/.
 *
 * Basically, just a list of LoaderoTestRunParticipantResult objects with
 * stored fields for our purposes.
 */
@Data
@Generated // Need this for JaCoco to ignore getters and setters
public final class LoaderoTestRunResult implements LoaderoModel {
    private List<LoaderoTestRunParticipantResult> results;
}
