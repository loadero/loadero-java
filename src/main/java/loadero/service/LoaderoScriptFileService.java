package loadero.service;

import loadero.controller.LoaderoCrudController;
import loadero.model.LoaderoScriptFileLoc;
import loadero.model.LoaderoType;
import loadero.utils.LoaderoClientUtils;
import loadero.utils.LoaderoUrlBuilder;

public class LoaderoScriptFileService extends AbstractLoaderoService<LoaderoScriptFileLoc> {
    private final LoaderoCrudController crudController = super.getCrudController();
    private final LoaderoUrlBuilder urlBuilder = super.getUrlBuilder();

    public LoaderoScriptFileService(LoaderoCrudController crudController,
                                    LoaderoUrlBuilder urlBuilder) {
        super(crudController, urlBuilder);
    }

    /**
     * Retrieves content of the script file from Loadero API and returns as LoaderoScriptFileLoc object.
     * Use toString() to convert into actual script.
     * @param id     - ID of the script file
     * @return       - LoaderoScriptFileLoc information about script content.
     */
    @Override
    public LoaderoScriptFileLoc getById(String... id) {
        String fileId = id[0];
        LoaderoClientUtils.checkArgumentsForNull(fileId);

        String scriptFileUrl = buildUrl(fileId);
        return (LoaderoScriptFileLoc) crudController.get(
                scriptFileUrl,
                LoaderoType.LOADERO_SCRIPT_FILE_LOC);
    }

    /**
     * Not defined.
     * @param newModel
     * @param id
     * @return
     */
    @Override
    public LoaderoScriptFileLoc updateById(LoaderoScriptFileLoc newModel, String... id) {
        return null;
    }

    @Override
    protected String buildUrl(String... id) {
        return String.format("%s/", urlBuilder.buildScriptFileURL(id[0]));
    }
}
