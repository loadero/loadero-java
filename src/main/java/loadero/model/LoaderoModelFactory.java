package loadero.model;

public class LoaderoModelFactory implements LoaderoModel {

    public LoaderoModel getLoaderoModel(LoaderoType type) {
        switch (type) {
            case LOADERO_TEST: return new LoaderoTestOptions();
            case LOADERO_GROUP: return new LoaderoGroup();
            case LOADERO_PARTICIPANT: return new LoaderoParticipant();
            case LOADERO_RUN_INFO: return new LoaderoRunInfo();
            case LOADERO_TEST_RESULT: return new LoaderoTestResult();
            default: return null;
        }
    }
}
