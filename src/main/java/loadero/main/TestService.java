package loadero.main;

import loadero.model.LoaderoModel;
import loadero.model.Test;
import loadero.types.LoaderoModelType;

import java.util.Objects;

/**
 * Implementation of AbstractService that is responsible for CRUD operation
 * related to Test object.
 */
final class TestService extends AbstractService
        implements SpecialOperations<Test> {
    private final CrudController crudController = super.getCrudController();
    private final CustomUrlBuilder urlBuilder = super.getUrlBuilder();
    private final ScriptFileService scriptFileService;

    public TestService(CrudController crudController,
                       CustomUrlBuilder urlBuilder) {
        super(crudController, urlBuilder);
        scriptFileService = new ScriptFileService(crudController, urlBuilder);
    }

    @Override
    public Test getById(int... ids) {
        String testUrl = buildUrl(ids[0]);
        ClientUtils.checkArgumentsForNull(testUrl);

        return (Test) crudController.get(testUrl,
                LoaderoModelType.LOADERO_TEST_OPTIONS);
    }

    @Override
    public Test updateById(Test newModel, int... ids) {
        int testId = ids[0];
        ClientUtils.checkArgumentsForNull(newModel, testId);

        String testUrl = urlBuilder.buildTestURLById(testId) + "/";
        Test currentOptions = getById(testId);
        // If new script is not provided
        // We get the old script from Loadero API endpoint /files/fileId
        // And update accordingly.
        if (Objects.equals(newModel.getScript(), "")) {
            String scriptContent = scriptFileService.getById(currentOptions.getScriptFileId())
                    .getContent();
            currentOptions.setScript(scriptContent);
        }

        LoaderoModel updatedOptions = ClientUtils.copyUncommonFields(
                currentOptions,
                newModel);
        
        return (Test) crudController.update(testUrl,
                LoaderoModelType.LOADERO_TEST_OPTIONS, updatedOptions);
    }
    
    @Override
    public Test createNewModel(Test newModel, int... ids) {
        String url = urlBuilder.buildProjectURL() + "/tests/";
        return (Test) crudController
                .post(url, LoaderoModelType.LOADERO_TEST_OPTIONS, newModel);
    }
    
    @Override
    public void deleteById(int...ids) {
        int testId = ids[0];
        crudController.delete(buildUrl(testId));
    }
    
    @Override
    public String buildUrl(int... id) {
        return String.format("%s/", urlBuilder.buildTestURLById(id[0]));
    }
}
