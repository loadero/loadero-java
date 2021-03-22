package loadero.main;

import loadero.types.LoaderoModelType;
/**
 * This class provides method that returns required service layer based on the
 * provided LoaderoModelType.
 */
final class LoaderoServiceFactory {
    private final LoaderoCrudController crudController;
    private final LoaderoUrlBuilder urlBuilder;
    
    /**
     * Creates LoaderoServiceFactory with given Loadero API token, base url and project ID.
     * @param apiToken  Loadero APi token, that is provided for Crud controller later.
     * @param baseUrl   Base url that is passed to the LoaderoUrlBuilder.
     * @param projectId ID of the project we going to work with.
     * @throws NullPointerException if apiToken or baseUrl is null or an empty String.
     * @throws loadero.exceptions.LoaderoException if projectId is negative.
     */
    public LoaderoServiceFactory(String apiToken, String baseUrl, int projectId) {
        LoaderoClientUtils.checkArgumentsForNull(apiToken, baseUrl);
        LoaderoClientUtils.checkIfIntIsNegative(projectId);
        
        this.crudController = new LoaderoCrudController(apiToken);
        this.urlBuilder = new LoaderoUrlBuilder(baseUrl, projectId);
    }
    
    /**
     * Factory method for returning concrete service layer based on the given LoaderoModelType.
     * @param type LoaderoModelType we want to create service layer for.
     * @throws IllegalArgumentException if unsupported type is provided.
     * @return concrete AbstractLoaderoService implementation.
     */
    public AbstractLoaderoService getLoaderoService(LoaderoModelType type) {
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
            case LOADERO_STATICS:       return new LoaderoStaticsService(crudController, urlBuilder);
            default:                    throw new IllegalArgumentException("Not supported type");
        }
    }
}
