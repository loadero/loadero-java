package loadero.service;

import loadero.controller.LoaderoCrudController;
import loadero.model.LoaderoModel;
import loadero.model.LoaderoTestOptions;
import loadero.model.LoaderoType;
import loadero.utils.LoaderoClientUtils;
import loadero.utils.LoaderoUrlBuilder;

import java.util.Objects;

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
    public LoaderoTestOptions getById(String... id) {
        String testUrl = buildUrl(id[0]);
        LoaderoClientUtils.checkArgumentsForNull(testUrl);

        return (LoaderoTestOptions) crudController.get(testUrl,
                LoaderoType.LOADERO_TEST_OPTIONS);
    }

    @Override
    public LoaderoTestOptions updateById(LoaderoTestOptions newModel, String... id) {
        String testId = id[0];
        LoaderoClientUtils.checkArgumentsForNull(newModel, testId);

        String testUrl = urlBuilder.buildTestURLById(testId) + "/";
        LoaderoTestOptions currentOptions = getById(testId);
        // If new script is not provided
        // We get the old script from Loadero API endpoint /files/fileId
        // And update accordingly.
        if (Objects.equals(newModel.getScript(), "")) {
            String scriptContent = scriptFileService.getById(
                    String.valueOf(currentOptions.getScriptFileId()))
                    .toString();
            currentOptions.setScript(scriptContent);
        }

        LoaderoModel updatedOptions = LoaderoClientUtils.copyUncommonFields(
                currentOptions,
                newModel,
                LoaderoType.LOADERO_TEST_OPTIONS);

        return (LoaderoTestOptions) crudController.update(testUrl,
                LoaderoType.LOADERO_TEST_OPTIONS, updatedOptions);    }

    @Override
    protected String buildUrl(String... id) {
        return String.format("%s/", urlBuilder.buildTestURLById(id[0]));
    }
}
