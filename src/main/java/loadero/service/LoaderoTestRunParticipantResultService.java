package loadero.service;

import loadero.controller.LoaderoCrudController;
import loadero.model.LoaderoTestRunParticipantResult;
import loadero.types.LoaderoModelType;
import loadero.utils.LoaderoClientUtils;
import loadero.utils.LoaderoUrlBuilder;

/**
 * Implementation of AbstractLoaderoService that is responsible for CRUD operation
 * related to LoaderoTestRunParticipantResult object.
 */
public class LoaderoTestRunParticipantResultService extends
        AbstractLoaderoService<LoaderoTestRunParticipantResult> {

    private final LoaderoCrudController crudController = super.getCrudController();
    private final LoaderoUrlBuilder urlBuilder = super.getUrlBuilder();

    public LoaderoTestRunParticipantResultService(LoaderoCrudController crudController,
                                                  LoaderoUrlBuilder urlBuilder) {
        super(crudController, urlBuilder);
    }

    @Override
    public LoaderoTestRunParticipantResult getById(int... id) {
        int testId = id[0];
        int runId = id[1];
        int resultId = id[2];
        LoaderoClientUtils.checkArgumentsForNull(testId, runId, resultId);

        String resultsUrl = buildUrl(testId, runId, resultId);
        return (LoaderoTestRunParticipantResult) crudController.get(resultsUrl,
                LoaderoModelType.LOADERO_TEST_RUN_PARTICIPANT_RESULT);
    }

    // Not supported.
    @Override
    public LoaderoTestRunParticipantResult updateById(LoaderoTestRunParticipantResult newModel,
                                                      int... id) {
        return null;
    }
    
    // Not supported yet.
    @Override
    public void deleteById(int... id) {
    }
    
    @Override
    protected String buildUrl(int... id) {
        return  String.format("%s/%s/", urlBuilder.buildRunResultsURL(id[0], id[1]), id[2]);
    }
}
