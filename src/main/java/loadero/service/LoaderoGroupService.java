package loadero.service;

import loadero.controller.LoaderoCrudController;
import loadero.model.LoaderoGroup;
import loadero.model.LoaderoType;
import loadero.utils.LoaderoUrlBuilder;

public class LoaderoGroupService extends AbstractLoaderoService<LoaderoGroup> {
    private final LoaderoUrlBuilder urlBuilder = super.getUrlBuilder();

    public LoaderoGroupService(LoaderoCrudController crudController,
                               LoaderoUrlBuilder urlBuilder) {
        super(crudController, urlBuilder);
    }

    @Override
    public LoaderoGroup getById(String...id) {
        String testId = id[0];
        String groupId = id[1];
        String groupUrl = String.format("%s/", urlBuilder.buildGroupURL(testId, groupId));
        return (LoaderoGroup) super.getCrudController().get(groupUrl,
                LoaderoType.LOADERO_GROUP);
//        return null;
    }

    @Override
    public LoaderoGroup updateById(LoaderoGroup newModel, String... id) {
        return null;
    }
}
