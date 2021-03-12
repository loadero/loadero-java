package loadero.service;

import loadero.controller.LoaderoCrudController;
import loadero.model.LoaderoGroup;
import loadero.model.LoaderoModel;
import loadero.types.LoaderoModelType;
import loadero.utils.LoaderoClientUtils;
import loadero.utils.LoaderoUrlBuilder;

/**
 * Implementation of AbstractLoaderoService that is responsible for CRUD operation
 * related to LoaderoGroup object.
 */
public class LoaderoGroupService
        extends AbstractLoaderoService implements LoaderoSpecialOperation<LoaderoGroup> {
    private final LoaderoUrlBuilder urlBuilder = super.getUrlBuilder();
    private final LoaderoCrudController crudController = super.getCrudController();

    public LoaderoGroupService(LoaderoCrudController crudController,
                               LoaderoUrlBuilder urlBuilder) {
        super(crudController, urlBuilder);
    }

    @Override
    public LoaderoGroup getById(int...ids) {
        int testId = ids[0];
        int groupId = ids[1];
        LoaderoClientUtils.checkIfIntIsNegative(testId, groupId);
        String groupUrl = buildUrl(testId, groupId);
        return (LoaderoGroup) crudController.get(groupUrl,
                LoaderoModelType.LOADERO_GROUP);
    }

    @Override
    public LoaderoGroup updateById(LoaderoGroup newModel, int... ids) {
        int testId = ids[0];
        int groupId = ids[1];
        LoaderoClientUtils.checkArgumentsForNull(newModel);
        LoaderoClientUtils.checkIfIntIsNegative(testId, groupId);

        String groupUrl = buildUrl(testId, groupId);
        LoaderoGroup currentGroup = getById(testId, groupId);
        LoaderoModel updatedGroup = LoaderoClientUtils.copyUncommonFields(
                currentGroup,
                newModel,
                LoaderoModelType.LOADERO_GROUP
        );
        return (LoaderoGroup) crudController.update(groupUrl, LoaderoModelType.LOADERO_GROUP, updatedGroup);
    }
    
    @Override
    public LoaderoGroup createNewModel(LoaderoGroup newModel, int... ids) {
        int testId = ids[0];
        LoaderoClientUtils.checkArgumentsForNull(newModel);
        LoaderoClientUtils.checkIfIntIsNegative(testId);
        
        String url = urlBuilder.buildTestURLById(testId) + "/groups/";
        return (LoaderoGroup) crudController
                .post(url, LoaderoModelType.LOADERO_GROUP, newModel);
    }
    
    @Override
    public void deleteById(int... ids) {
        int testId = ids[0];
        int groupId = ids[1];
        LoaderoClientUtils.checkIfIntIsNegative(testId, groupId);
        crudController.delete(buildUrl(testId, groupId));
    }
    
    @Override
    public String buildUrl(int...ids) {
        return String.format("%s/", urlBuilder.buildGroupURL(ids[0], ids[1]));
    }
}
