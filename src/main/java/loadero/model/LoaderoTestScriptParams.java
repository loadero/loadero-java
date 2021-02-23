package loadero.model;

import lombok.Data;
import lombok.Generated;

import java.util.Random;

/*
    LoaderoTestScriptParams class should only be used when we would like to
    specify/change some parameters in TestOneOnOneCall test class.
    If none of new parameters are set, the default values will be used.
 */
@Data
@Generated
public class LoaderoTestScriptParams {
    private int callDuration    = 10;
    private int elementTimeout  = 10;
    private String appUrl       = "https://voice-webapp-3026-dev.twil.io/index.html?identity=";
    private int participantId;

    public LoaderoTestScriptParams() {
        Random rand = new Random();
        int max = 10;
        int min = 1;
        this.participantId = rand.nextInt((max - min) + 1) + min;
    }
}
