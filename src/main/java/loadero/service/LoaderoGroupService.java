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
public class LoaderoGroupService extends AbstractLoaderoService<LoaderoGroup> {
    private final LoaderoUrlBuilder urlBuilder = super.getUrlBuilder();
    private final LoaderoCrudController crudController = super.getCrudController();

    public LoaderoGroupService(LoaderoCrudController crudController,
                               LoaderoUrlBuilder urlBuilder) {
        super(crudController, urlBuilder);
    }

    @Override
    public LoaderoGroup getById(int...id) {
        int testId = id[0];
        int groupId = id[1];
        LoaderoClientUtils.checkArgumentsForNull(testId, groupId);
        String groupUrl = buildUrl(testId, groupId);
        return (LoaderoGroup) crudController.get(groupUrl,
                LoaderoModelType.LOADERO_GROUP);
    }

    @Override
    public LoaderoGroup updateById(LoaderoGroup newModel, int... id) {
        int testId = id[0];
        int groupId = id[1];
        LoaderoClientUtils.checkArgumentsForNull(newModel, testId, groupId);

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
    public void deleteById(int... id) {
        int testId = id[0];
        int groupId = id[1];
        crudController.delete(buildUrl(testId, groupId));
    }
    
    @Override
    protected String buildUrl(int...id) {
        return String.format("%s/", urlBuilder.buildGroupURL(id[0], id[1]));
    }
}
