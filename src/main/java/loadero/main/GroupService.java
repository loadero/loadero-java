package loadero.main;

import loadero.model.Group;
import loadero.model.LoaderoModel;
import loadero.types.LoaderoModelType;

/**
 * Implementation of AbstractService that is responsible for CRUD operation
 * related to Group object.
 */
final class GroupService
        extends AbstractService implements SpecialOperations<Group> {
    private final CustomUrlBuilder urlBuilder = super.getUrlBuilder();
    private final CrudController crudController = super.getCrudController();

    public GroupService(CrudController crudController,
                        CustomUrlBuilder urlBuilder) {
        super(crudController, urlBuilder);
    }

    @Override
    public Group getById(int...ids) {
        int testId = ids[0];
        int groupId = ids[1];
        ClientUtils.checkIfIntIsNegative(testId, groupId);
        String groupUrl = buildUrl(testId, groupId);
        return (Group) crudController.get(groupUrl,
                LoaderoModelType.LOADERO_GROUP);
    }

    @Override
    public Group updateById(Group newModel, int... ids) {
        int testId = ids[0];
        int groupId = ids[1];
        ClientUtils.checkArgumentsForNull(newModel);
        ClientUtils.checkIfIntIsNegative(testId, groupId);

        String groupUrl = buildUrl(testId, groupId);
        Group currentGroup = getById(testId, groupId);
        LoaderoModel updatedGroup = ClientUtils.copyUncommonFields(
                currentGroup,
                newModel);
        return (Group) crudController.update(groupUrl, LoaderoModelType.LOADERO_GROUP, updatedGroup);
    }
    
    @Override
    public Group createNewModel(Group newModel, int... ids) {
        int testId = ids[0];
        ClientUtils.checkArgumentsForNull(newModel);
        ClientUtils.checkIfIntIsNegative(testId);
        
        String url = urlBuilder.buildTestURLById(testId) + "/groups/";
        return (Group) crudController
                .post(url, LoaderoModelType.LOADERO_GROUP, newModel);
    }
    
    @Override
    public void deleteById(int... ids) {
        int testId = ids[0];
        int groupId = ids[1];
        ClientUtils.checkIfIntIsNegative(testId, groupId);
        crudController.delete(buildUrl(testId, groupId));
    }
    
    @Override
    public String buildUrl(int...ids) {
        return String.format("%s/", urlBuilder.buildGroupURL(ids[0], ids[1]));
    }
}
