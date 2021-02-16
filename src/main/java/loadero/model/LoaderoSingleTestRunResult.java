package loadero.model;


import lombok.Data;

@Data
public class LoaderoSingleTestRunResult implements LoaderoModel {
    private long id;
    private String status;
}
