package loadero.main;

import loadero.model.Assert;
import loadero.model.LoaderoModel;
import loadero.types.LoaderoModelType;

public class AssertService extends AbstractService implements SpecialOperations<Assert> {
    private final CrudController crudController = super.getCrudController();
    private final CustomUrlBuilder urlBuilder = super.getUrlBuilder();
    
    public AssertService(CrudController crudController, CustomUrlBuilder urlBuilder) {
        super(crudController, urlBuilder);
    }
    
    @Override
    public Assert getById(int... ids) {
        int testId = ids[0];
        int assertId = ids[1];
        
        String assertUrl = buildUrl(testId, assertId);
        return (Assert) crudController.get(assertUrl, LoaderoModelType.LOADERO_ASSERT);
    }
    
    @Override
    public Assert updateById(Assert updatedModel, int... ids) {
        int testId = ids[0];
        int assertId = ids[1];
        ClientUtils.checkArgumentsForNull(updatedModel);
        ClientUtils.checkIfIntIsNegative(testId, assertId);
        
        String assertUrl = buildUrl(testId, assertId);
        Assert currentAssertInfo = getById(testId, assertId);
        LoaderoModel updatedAssertInfo = ClientUtils.copyUncommonFields(
                currentAssertInfo,
                updatedModel
        );
        
        return (Assert) crudController.update(assertUrl, LoaderoModelType.LOADERO_ASSERT,
                updatedAssertInfo);
    }
    
    @Override
    public Assert createNewModel(Assert newModel, int... ids) {
        int testId = ids[0];
        ClientUtils.checkArgumentsForNull(newModel);
        ClientUtils.checkIfIntIsNegative(testId);
        
        String assertUrl = urlBuilder.buildTestURLById(testId) + "/asserts/";
        return (Assert) crudController.post(assertUrl, LoaderoModelType.LOADERO_ASSERT,
                newModel);
    }
    
    @Override
    public void deleteById(int... ids) {
        int testId = ids[0];
        int assertId = ids[1];
        crudController.delete(buildUrl(testId, assertId));
    }
    
    @Override
    public String buildUrl(int... ids) {
        return String.format("%s/asserts/%s/", urlBuilder.buildTestURLById(ids[0]), ids[1]);
    }
}
