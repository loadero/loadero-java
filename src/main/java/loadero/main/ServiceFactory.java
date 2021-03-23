package loadero.main;

import loadero.types.LoaderoModelType;
/**
 * This class provides method that returns required service layer based on the
 * provided LoaderoModelType.
 */
final class ServiceFactory {
    private final CrudController crudController;
    private final CustomUrlBuilder urlBuilder;
    
    /**
     * Creates ServiceFactory with given Loadero API token, base url and project ID.
     * @param apiToken  Loadero APi token, that is provided for Crud controller later.
     * @param baseUrl   Base url that is passed to the CustomUrlBuilder.
     * @param projectId ID of the project we going to work with.
     * @throws NullPointerException if apiToken or baseUrl is null or an empty String.
     * @throws loadero.exceptions.ClientInternalException if projectId is negative.
     */
    public ServiceFactory(String apiToken, String baseUrl, int projectId) {
        ClientUtils.checkArgumentsForNull(apiToken, baseUrl);
        ClientUtils.checkIfIntIsNegative(projectId);
        
        this.crudController = new CrudController(apiToken);
        this.urlBuilder = new CustomUrlBuilder(baseUrl, projectId);
    }
    
    /**
     * Factory method for returning concrete service layer based on the given LoaderoModelType.
     * @param type LoaderoModelType we want to create service layer for.
     * @throws IllegalArgumentException if unsupported type is provided.
     * @return concrete AbstractService implementation.
     */
    public AbstractService getLoaderoService(LoaderoModelType type) {
        switch (type) {
            case LOADERO_GROUP:         return new GroupService(crudController, urlBuilder);
            case LOADERO_TEST_OPTIONS:  return new TestService(crudController, urlBuilder);
            case LOADERO_PARTICIPANT:   return new ParticipantService(crudController, urlBuilder);
            case LOADERO_RUN_RESULT:    return new TestRunResultService(crudController, urlBuilder);
            case LOADERO_TEST_RUN_PARTICIPANT_RESULT:
                                        return new TestRunParticipantResultService(crudController, urlBuilder);
            case LOADERO_SCRIPT_FILE_LOC:
                                        return new ScriptFileService(crudController, urlBuilder);
            case LOADERO_RUN_INFO:      return new PollingService(crudController, urlBuilder);
            case LOADERO_STATICS:       return new StaticsService(crudController, urlBuilder);
            default:                    throw new IllegalArgumentException("Not supported type");
        }
    }
}
