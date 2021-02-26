package loadero.service;

import loadero.model.LoaderoGroup;
import loadero.model.LoaderoType;

public class LoaderoGroupService extends AbstractLoaderoService<LoaderoGroup> {

    public LoaderoGroupService(String apiToken) {
        super(apiToken);
    }

    @Override
    public LoaderoGroup getById(String...id) {
        String testId = id[0];
        String groupId = id[1];
        String groupUrl = String.format("%s/");
        return (LoaderoGroup) super.getCrudController().get(groupUrl,
                LoaderoType.LOADERO_GROUP);
    }

    @Override
    public LoaderoGroup updateById(LoaderoGroup newModel, String... id) {
        return null;
    }
}
