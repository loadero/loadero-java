package loadero.service;

import loadero.controller.LoaderoCrudController;
import loadero.model.LoaderoModel;
import lombok.Getter;

@Getter
public abstract class AbstractLoaderoService<T extends LoaderoModel> {
    private final LoaderoCrudController crudController;

    public AbstractLoaderoService(String apiToken) {
        this.crudController = new LoaderoCrudController(apiToken);
    }

    public abstract T getById(String...id);
    public abstract T updateById(T newModel, String...id);
}
