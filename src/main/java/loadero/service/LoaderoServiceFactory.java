package loadero.service;

import loadero.controller.LoaderoCrudController;
import loadero.model.LoaderoType;
import loadero.utils.LoaderoUrlBuilder;

public class LoaderoServiceFactory {
    private final LoaderoCrudController crudController;
    private final LoaderoUrlBuilder urlBuilder;

    public LoaderoServiceFactory(String apiToken, String baseUrl, String projectId) {
        this.crudController = new LoaderoCrudController(apiToken);
        this.urlBuilder = new LoaderoUrlBuilder(baseUrl, projectId);
    }

    public AbstractLoaderoService<?> getLoaderoService(LoaderoType type) {
        switch (type) {
            case LOADERO_GROUP: return new LoaderoGroupService(crudController, urlBuilder);
            default: throw new IllegalArgumentException("Not supported type");
        }
    }
}
