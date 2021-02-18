package loadero.model;

public class LoaderoModelFactory implements LoaderoModel {

    public LoaderoModel getLoaderoModel(LoaderoType type) {
        switch (type) {
            case LOADERO_TEST_OPTIONS:                  return new LoaderoTestOptions();
            case LOADERO_GROUP:                         return new LoaderoGroup();
            case LOADERO_PARTICIPANT:                   return new LoaderoParticipant();
            case LOADERO_RUN_INFO:                      return new LoaderoRunInfo();
            case LOADERO_TEST_RUN_PARTICIPANT_RESULT:   return new LoaderoTestRunParticipantResult();
            case LOADERO_SCRIPT_FILE_LOC:               return new LoaderoScriptFileLoc();
            case LOADERO_RUN_RESULT:                    return new LoaderoTestRunResult();
            default:                                    return null;
        }
    }
}
