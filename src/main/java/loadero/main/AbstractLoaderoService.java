package loadero.main;

import loadero.model.LoaderoModel;
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
    
    /**
     * Constructor for an AbstractLoaderoService.
     * @param crudController LoaderoCrudController object.
     * @param urlBuilder     LoaderoUrlBuilder object.
     * @throws NullPointerException if any of given arguments are null.
     */
    public AbstractLoaderoService(LoaderoCrudController crudController,
                                  LoaderoUrlBuilder urlBuilder) {
        LoaderoClientUtils.checkArgumentsForNull(crudController, urlBuilder);
        this.crudController = crudController;
        this.urlBuilder = urlBuilder;
    }
}
