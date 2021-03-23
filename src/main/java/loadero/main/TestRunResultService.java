package loadero.main;

import loadero.model.TestRunResult;
import loadero.types.LoaderoModelType;

/**
 * Implementation of AbstractService that is responsible for CRUD operation
 * related to TestRunResult object.
 */
final class TestRunResultService extends AbstractService {
    private final CrudController crudController = super.getCrudController();
    private final CustomUrlBuilder urlBuilder = super.getUrlBuilder();

    public TestRunResultService(CrudController crudController,
                                CustomUrlBuilder urlBuilder) {
        super(crudController, urlBuilder);
    }

    @Override
    public TestRunResult getById(int... id) {
        int testId = id[0];
        int runId = id[1];
        ClientUtils.checkIfIntIsNegative(testId, runId);

        String resultsUrl = buildUrl(testId, runId);
        return (TestRunResult) crudController.get(resultsUrl,
                LoaderoModelType.LOADERO_RUN_RESULT);
    }
    
    @Override
    public String buildUrl(int... id) {
        return String.format("%s/",urlBuilder.buildRunResultsURL(id[0], id[1]));
    }
}
