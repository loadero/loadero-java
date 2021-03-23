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
public abstract class AbstractService implements CommonOperation<LoaderoModel> {
    private final CrudController crudController;
    private final CustomUrlBuilder urlBuilder;
    
    /**
     * Constructor for an AbstractService.
     * @param crudController CrudController object.
     * @param urlBuilder     CustomUrlBuilder object.
     * @throws NullPointerException if any of given arguments are null.
     */
    public AbstractService(CrudController crudController,
                           CustomUrlBuilder urlBuilder) {
        ClientUtils.checkArgumentsForNull(crudController, urlBuilder);
        this.crudController = crudController;
        this.urlBuilder = urlBuilder;
    }
}
