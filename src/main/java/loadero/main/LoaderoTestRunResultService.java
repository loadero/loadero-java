package loadero.main;

import loadero.model.LoaderoTestRunResult;
import loadero.types.LoaderoModelType;

/**
 * Implementation of AbstractLoaderoService that is responsible for CRUD operation
 * related to LoaderoTestRunResult object.
 */
final class LoaderoTestRunResultService extends AbstractLoaderoService {
    private final LoaderoCrudController crudController = super.getCrudController();
    private final LoaderoUrlBuilder urlBuilder = super.getUrlBuilder();

    public LoaderoTestRunResultService(LoaderoCrudController crudController,
                                       LoaderoUrlBuilder urlBuilder) {
        super(crudController, urlBuilder);
    }

    @Override
    public LoaderoTestRunResult getById(int... id) {
        int testId = id[0];
        int runId = id[1];
        LoaderoClientUtils.checkIfIntIsNegative(testId, runId);

        String resultsUrl = buildUrl(testId, runId);
        return (LoaderoTestRunResult) crudController.get(resultsUrl,
                LoaderoModelType.LOADERO_RUN_RESULT);
    }
    
    @Override
    public String buildUrl(int... id) {
        return String.format("%s/",urlBuilder.buildRunResultsURL(id[0], id[1]));
    }
}
