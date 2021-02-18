package loadero.model;

import lombok.Data;
import java.util.List;

@Data
public class LoaderoTestRunResult implements LoaderoModel {
    private List<LoaderoSingleTestRunResult> results;
}
