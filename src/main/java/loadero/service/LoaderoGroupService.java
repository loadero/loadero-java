package loadero.service;

import loadero.controller.LoaderoCrudController;
import loadero.model.LoaderoGroup;
import loadero.model.LoaderoModel;
import loadero.model.LoaderoType;
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
    public LoaderoGroup getById(String...id) {
        String testId = id[0];
        String groupId = id[1];
        LoaderoClientUtils.checkArgumentsForNull(testId, groupId);
        String groupUrl = buildUrl(testId, groupId);
        return (LoaderoGroup) crudController.get(groupUrl,
                LoaderoType.LOADERO_GROUP);
    }

    @Override
    public LoaderoGroup updateById(LoaderoGroup newModel, String... id) {
        String testId = id[0];
        String groupId = id[1];
        LoaderoClientUtils.checkArgumentsForNull(newModel, testId, groupId);

        String groupUrl = buildUrl(testId, groupId);
        LoaderoGroup currentGroup = getById(testId, groupId);
        LoaderoModel updatedGroup = LoaderoClientUtils.copyUncommonFields(
                currentGroup,
                newModel,
                LoaderoType.LOADERO_GROUP
        );
        return (LoaderoGroup) crudController.update(groupUrl, LoaderoType.LOADERO_GROUP, updatedGroup);
    }

    @Override
    protected String buildUrl(String...id) {
        return String.format("%s/", urlBuilder.buildGroupURL(id[0], id[1]));
    }
}
