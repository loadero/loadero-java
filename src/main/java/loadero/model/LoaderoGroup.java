package loadero.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class LoaderoGroup implements LoaderoModel {
    private String name;
    private int count;
    private List<LoaderoParticipant> loaderoParticipantList;

    public LoaderoGroup(String name, int count) {
        this.name = name;
        this.count = count;
    }
}
