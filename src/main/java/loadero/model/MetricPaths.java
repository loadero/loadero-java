package loadero.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Getter
@NoArgsConstructor
public final class MetricPaths implements LoaderoModel {
    private Set<String> machinePaths;
    private Set<String> webrtcPaths;
    
    public MetricPaths(Set<String> machinePaths, Set<String> webrtcPaths) {
        this.machinePaths = machinePaths;
        this.webrtcPaths = webrtcPaths;
    }
}
