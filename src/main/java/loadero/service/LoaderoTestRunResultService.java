package loadero.service;

import loadero.controller.LoaderoCrudController;
import loadero.model.LoaderoTestRunResult;
import loadero.model.LoaderoType;
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
                LoaderoType.LOADERO_RUN_RESULT);
    }

    /**
     * Not needed.
     * @param newModel
     * @param id
     * @return
     */
    @Override
    public LoaderoTestRunResult updateById(LoaderoTestRunResult newModel, int... id) {
        return null;
    }

    @Override
    protected String buildUrl(int... id) {
        return String.format("%s/",urlBuilder.buildRunResultsURL(id[0], id[1]));
    }
}
