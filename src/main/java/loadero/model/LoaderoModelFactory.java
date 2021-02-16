package loadero.model;

public class LoaderoModelFactory implements LoaderoModel {

    public LoaderoModel getLoaderoModel(LoaderoType type) {
        switch (type) {
            case LOADERO_TEST_OPTIONS:      return new LoaderoTestOptions();
            case LOADERO_GROUP:             return new LoaderoGroup();
            case LOADERO_PARTICIPANT:       return new LoaderoParticipant();
            case LOADERO_RUN_INFO:          return new LoaderoRunInfo();
            case LOADERO_TEST_RESULT:       return new LoaderoSingleTestRunResult();
            case LOADERO_SCRIPT_FILE_LOC:   return new LoaderoScriptFileLoc();
            case LOADERO_ALL_RUN_RESULTS:   return new LoaderoAllTestRunResults();
            case LOADERO_SINGLE_RUN_RESULT: return new LoaderoSingleTestRunResult();
            default:                        return null;
        }
    }
}
