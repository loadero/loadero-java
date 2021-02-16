package loadero.model;

import lombok.Data;
import java.util.List;

@Data
public class LoaderoAllTestRunResults implements LoaderoModel {
    private List<LoaderoSingleTestRunResult> results;
}
