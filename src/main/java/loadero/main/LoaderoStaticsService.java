package loadero.main;

import loadero.model.LoaderoStatics;
import loadero.types.LoaderoModelType;

final class LoaderoStaticsService extends AbstractLoaderoService {
    private final LoaderoCrudController crudController = super.getCrudController();
    private final LoaderoUrlBuilder urlBuilder = super.getUrlBuilder();
    
    public LoaderoStaticsService(LoaderoCrudController crudController,
                                 LoaderoUrlBuilder urlBuilder) {
        super(crudController, urlBuilder);
    }
    
    @Override
    public LoaderoStatics getById(int... ids) {
        return (LoaderoStatics) crudController.get(buildUrl(), LoaderoModelType.LOADERO_STATICS);
    }
    
    @Override
    public String buildUrl(int... ids) {
        return urlBuilder.getBaseUrl() + "/statics/";
    }
}
