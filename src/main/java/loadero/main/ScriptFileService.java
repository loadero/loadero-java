package loadero.main;

import loadero.model.ScriptDetails;
import loadero.types.LoaderoModelType;

/**
 * Implementation of AbstractService that is responsible for CRUD operation
 * related to ScriptDetails object.
 */
final class ScriptFileService extends AbstractService {
    private final CrudController crudController = super.getCrudController();
    private final CustomUrlBuilder urlBuilder = super.getUrlBuilder();

    ScriptFileService(CrudController crudController,
                      CustomUrlBuilder urlBuilder) {
        super(crudController, urlBuilder);
    }

    /**
     * Retrieves content of the script file from Loadero API and returns as ScriptDetails object.
     * Use toString() to convert into actual script.
     * @param id     ID of the script file
     * @return       ScriptDetails information about script content.
     */
    @Override
    public ScriptDetails getById(int... id) {
        int fileId = id[0];
        ClientUtils.checkIfIntIsNegative(fileId);

        String scriptFileUrl = buildUrl(fileId);
        return (ScriptDetails) crudController.get(
                scriptFileUrl,
                LoaderoModelType.LOADERO_SCRIPT_FILE_LOC);
    }
    
    @Override
    public String buildUrl(int... id) {
        return String.format("%s/", urlBuilder.buildScriptFileURL(id[0]));
    }
}
