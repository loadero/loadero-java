package loadero.model;

import lombok.Getter;

import java.util.List;

@Getter
public class LoaderoAllTestRunResults implements LoaderoModel {
    private List<LoaderoSingleTestRunResult> results;
}
