package loadero.service;

import loadero.controller.LoaderoCrudController;
import loadero.model.LoaderoModel;
import loadero.model.LoaderoType;
import loadero.utils.LoaderoUrlBuilder;

/**
 * Factory class that contains method that returns required service layer based on the
 * provided LoaderoType.
 */
public class LoaderoServiceFactory {
    private final LoaderoCrudController crudController;
    private final LoaderoUrlBuilder urlBuilder;

    public LoaderoServiceFactory(String apiToken, String baseUrl, String projectId) {
        this.crudController = new LoaderoCrudController(apiToken);
        this.urlBuilder = new LoaderoUrlBuilder(baseUrl, projectId);
    }

    public AbstractLoaderoService<? extends LoaderoModel> getLoaderoService(LoaderoType type) {
        switch (type) {
            case LOADERO_GROUP:         return new LoaderoGroupService(crudController, urlBuilder);
            case LOADERO_TEST_OPTIONS:  return new LoaderoTestOptionsService(crudController, urlBuilder);
            case LOADERO_PARTICIPANT:   return new LoaderoParticipantService(crudController, urlBuilder);
            case LOADERO_RUN_RESULT:    return new LoaderoTestRunResultService(crudController, urlBuilder);
            case LOADERO_TEST_RUN_PARTICIPANT_RESULT:
                                        return new LoaderoTestRunParticipantResultService(crudController, urlBuilder);
            case LOADERO_SCRIPT_FILE_LOC:
                                        return new LoaderoScriptFileService(crudController, urlBuilder);
            case LOADERO_RUN_INFO:      return new LoaderoPollingService(crudController, urlBuilder);
            default:                    throw new IllegalArgumentException("Not supported type");
        }
    }
}
