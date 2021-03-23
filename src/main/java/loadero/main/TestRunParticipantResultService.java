package loadero.main;

import loadero.model.TestRunParticipantResult;
import loadero.types.LoaderoModelType;

/**
 * Implementation of AbstractService that is responsible for CRUD operation
 * related to TestRunParticipantResult object.
 */
final class TestRunParticipantResultService extends AbstractService {

    private final CrudController crudController = super.getCrudController();
    private final CustomUrlBuilder urlBuilder = super.getUrlBuilder();

    public TestRunParticipantResultService(CrudController crudController,
                                           CustomUrlBuilder urlBuilder) {
        super(crudController, urlBuilder);
    }

    @Override
    public TestRunParticipantResult getById(int... id) {
        int testId = id[0];
        int runId = id[1];
        int resultId = id[2];
        ClientUtils.checkIfIntIsNegative(testId, runId, resultId);

        String resultsUrl = buildUrl(testId, runId, resultId);
        return (TestRunParticipantResult) crudController.get(resultsUrl,
                LoaderoModelType.LOADERO_TEST_RUN_PARTICIPANT_RESULT);
    }

    @Override
    public String buildUrl(int... id) {
        return  String.format("%s/%s/", urlBuilder.buildRunResultsURL(id[0], id[1]), id[2]);
    }
}
