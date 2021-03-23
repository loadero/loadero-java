package loadero.main;

import loadero.model.Statics;
import loadero.types.LoaderoModelType;

final class StaticsService extends AbstractService {
    private final CrudController crudController = super.getCrudController();
    private final CustomUrlBuilder urlBuilder = super.getUrlBuilder();
    
    public StaticsService(CrudController crudController,
                          CustomUrlBuilder urlBuilder) {
        super(crudController, urlBuilder);
    }
    
    @Override
    public Statics getById(int... ids) {
        return (Statics) crudController.get(buildUrl(), LoaderoModelType.LOADERO_STATICS);
    }
    
    @Override
    public String buildUrl(int... ids) {
        return urlBuilder.getBaseUrl() + "/statics/";
    }
}
