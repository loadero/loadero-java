package loadero.service;

import loadero.controller.LoaderoCrudController;
import loadero.model.LoaderoModel;
import loadero.utils.LoaderoUrlBuilder;
import lombok.Generated;
import lombok.Getter;

/**
 * This abstract class serves as a template for each service layer, that should be implemented
 * for each Loadero model in order to provide CRUD operations.
 */
@Getter
@Generated
public abstract class AbstractLoaderoService implements LoaderoCommonOperation<LoaderoModel> {
    private final LoaderoCrudController crudController;
    private final LoaderoUrlBuilder urlBuilder;

    public AbstractLoaderoService(LoaderoCrudController crudController,
                                  LoaderoUrlBuilder urlBuilder) {
        this.crudController = crudController;
        this.urlBuilder = urlBuilder;
    }
}
