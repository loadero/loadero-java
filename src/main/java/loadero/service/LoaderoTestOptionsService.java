package loadero.service;

import loadero.controller.LoaderoCrudController;
import loadero.model.LoaderoModel;
import loadero.model.LoaderoTestOptions;
import loadero.types.LoaderoModelType;
import loadero.utils.LoaderoClientUtils;
import loadero.utils.LoaderoUrlBuilder;

import java.util.Objects;

/**
 * Implementation of AbstractLoaderoService that is responsible for CRUD operation
 * related to LoaderoTestOptions object.
 */
public class LoaderoTestOptionsService extends AbstractLoaderoService<LoaderoTestOptions> {
    private final LoaderoCrudController crudController = super.getCrudController();
    private final LoaderoUrlBuilder urlBuilder = super.getUrlBuilder();
    private final LoaderoScriptFileService scriptFileService;

    public LoaderoTestOptionsService(LoaderoCrudController crudController,
                                     LoaderoUrlBuilder urlBuilder) {
        super(crudController, urlBuilder);
        scriptFileService = new LoaderoScriptFileService(crudController, urlBuilder);
    }

    @Override
    public LoaderoTestOptions getById(int... id) {
        String testUrl = buildUrl(id[0]);
        LoaderoClientUtils.checkArgumentsForNull(testUrl);

        return (LoaderoTestOptions) crudController.get(testUrl,
                LoaderoModelType.LOADERO_TEST_OPTIONS);
    }

    @Override
    public LoaderoTestOptions updateById(LoaderoTestOptions newModel, int... id) {
        int testId = id[0];
        LoaderoClientUtils.checkArgumentsForNull(newModel, testId);

        String testUrl = urlBuilder.buildTestURLById(testId) + "/";
        LoaderoTestOptions currentOptions = getById(testId);
        // If new script is not provided
        // We get the old script from Loadero API endpoint /files/fileId
        // And update accordingly.
        if (Objects.equals(newModel.getScript(), "")) {
            String scriptContent = scriptFileService.getById(currentOptions.getScriptFileId())
                    .getContent();
            currentOptions.setScript(scriptContent);
        }

        LoaderoModel updatedOptions = LoaderoClientUtils.copyUncommonFields(
                currentOptions,
                newModel,
                LoaderoModelType.LOADERO_TEST_OPTIONS);
        
        return (LoaderoTestOptions) crudController.update(testUrl,
                LoaderoModelType.LOADERO_TEST_OPTIONS, updatedOptions);
    }

//    @Override
    public LoaderoTestOptions createNew(LoaderoTestOptions model) {
        String url = urlBuilder.buildProjectURL() + "/tests/";
        return (LoaderoTestOptions) crudController
                .post(url, LoaderoModelType.LOADERO_TEST_OPTIONS, model);
    }
    
    @Override
    public void deleteById(int...id) {
        int testId = id[0];
        crudController.delete(buildUrl(testId));
    }
    
    @Override
    protected String buildUrl(int... id) {
        return String.format("%s/", urlBuilder.buildTestURLById(id[0]));
    }
}
