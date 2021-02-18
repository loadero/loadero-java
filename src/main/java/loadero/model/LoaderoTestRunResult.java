package loadero.model;

import lombok.Data;
import java.util.List;

/**
 * LoaderoTestRunResult class represents the information retrieved from
 * Loadero /runs/:runID/results/.
 *
 * Basically, just a list of LoaderoTestRunParticipantResult objects with
 * stored fields for our purposes.
 */
@Data
public class LoaderoTestRunResult implements LoaderoModel {
    private List<LoaderoTestRunParticipantResult> results;
}
