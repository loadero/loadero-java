package loadero.service;

import loadero.controller.LoaderoCrudController;
import loadero.model.LoaderoModel;
import loadero.utils.LoaderoUrlBuilder;
import lombok.Getter;

@Getter
public abstract class AbstractLoaderoService<T extends LoaderoModel> {
    private final LoaderoCrudController crudController;
    private final LoaderoUrlBuilder urlBuilder;

    public AbstractLoaderoService(LoaderoCrudController crudController,
                                  LoaderoUrlBuilder urlBuilder) {
        this.crudController = crudController;
        this.urlBuilder = urlBuilder;
    }

    public abstract T getById(String...id);
    public abstract T updateById(T newModel, String...id);
    protected abstract String buildUrl(String...id);
}
