package loadero.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class LoaderoGroup {
    private String name;
    private int count;
    private List<LoaderoParticipant> loaderoParticipantList;

    public LoaderoGroup(String name, int count) {
        this.name = name;
        this.count = count;
    }
}
