package loadero.service;

import loadero.controller.LoaderoCrudController;
import loadero.model.LoaderoTestRunResult;
import loadero.types.LoaderoModelType;
import loadero.utils.LoaderoClientUtils;
import loadero.utils.LoaderoUrlBuilder;

/**
 * Implementation of AbstractLoaderoService that is responsible for CRUD operation
 * related to LoaderoTestRunResult object.
 */
public class LoaderoTestRunResultService extends AbstractLoaderoService<LoaderoTestRunResult> {
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
        LoaderoClientUtils.checkArgumentsForNull(testId, runId);

        String resultsUrl = buildUrl(testId, runId);
        return (LoaderoTestRunResult) crudController.get(resultsUrl,
                LoaderoModelType.LOADERO_RUN_RESULT);
    }

    // Not supported.
    @Override
    public LoaderoTestRunResult updateById(LoaderoTestRunResult newModel, int... id) {
        return null;
    }
    
    // Not supported yet.
    @Override
    public void deleteById(int... id) {
    
    }
    
    @Override
    protected String buildUrl(int... id) {
        return String.format("%s/",urlBuilder.buildRunResultsURL(id[0], id[1]));
    }
}
