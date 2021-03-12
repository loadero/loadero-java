package loadero.service;

import loadero.controller.LoaderoCrudController;
import loadero.model.LoaderoScriptFileLoc;
import loadero.types.LoaderoModelType;
import loadero.utils.LoaderoClientUtils;
import loadero.utils.LoaderoUrlBuilder;

/**
 * Implementation of AbstractLoaderoService that is responsible for CRUD operation
 * related to LoaderoScriptFileLoc object.
 */
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
    public LoaderoScriptFileLoc getById(int... id) {
        int fileId = id[0];
        LoaderoClientUtils.checkArgumentsForNull(fileId);

        String scriptFileUrl = buildUrl(fileId);
        return (LoaderoScriptFileLoc) crudController.get(
                scriptFileUrl,
                LoaderoModelType.LOADERO_SCRIPT_FILE_LOC);
    }

    /**
     * Not needed.
     * @param newModel
     * @param id
     * @return
     */
    @Override
    public LoaderoScriptFileLoc updateById(LoaderoScriptFileLoc newModel, int... id) {
        return null;
    }
    
    // Not supported yet.
    @Override
    public void deleteById(int... id) {
    }
    
    @Override
    protected String buildUrl(int... id) {
        return String.format("%s/", urlBuilder.buildScriptFileURL(id[0]));
    }
}
