package loadero.model;

import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

/*
    LoaderoTestScriptParams class should only be used when we would like to
    specify/change some parameters in TestOneOnOneCall test class.
    If none of new parameters are set, the default values will be used.
 */
@Data
@NoArgsConstructor
@Generated
public class LoaderoTestScriptParams {
    private int callDuration     = 10;
    private int elementTimeout   = 10;
    private String appUrl        = "https://voice-webapp-3026-dev.twil.io/index.html?identity=";
    private String participantId = "globalConfig.getParticipant().getId()";
}
