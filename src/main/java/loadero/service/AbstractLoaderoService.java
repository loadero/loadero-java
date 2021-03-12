package loadero.service;

import loadero.controller.LoaderoCrudController;
import loadero.model.LoaderoModel;
import loadero.utils.LoaderoUrlBuilder;
import lombok.Generated;
import lombok.Getter;

/**
 * This abstract serves as a template for the service layer, that should be implemented
 * for each Loadero model.
 * @param <T> - Any type that implements/extends LoaderoModel
 */
@Getter
@Generated
public abstract class AbstractLoaderoService<T extends LoaderoModel> {
    private final LoaderoCrudController crudController;
    private final LoaderoUrlBuilder urlBuilder;

    public AbstractLoaderoService(LoaderoCrudController crudController,
                                  LoaderoUrlBuilder urlBuilder) {
        this.crudController = crudController;
        this.urlBuilder = urlBuilder;
    }

    /**
     * Make GET request to the endpoint and retrieves object/objects with specific ID.
     * @param id - Array of IDs.
     * @return
     */
    public abstract T getById(int...id);

    /**
     * Updates information by ID about newModel object on Loadero API endpoint.
     * @param newModel - LoaderoModel object with new parameters that we wish to update
     * @param id       - Necessary IDs for update.
     * @return         - concrete implementation of LoaderoModel object.
     */
    public abstract T updateById(T newModel, int...id);
    
    /**
     * Create new LoaderoModel on Loadero site.
     * @param newModel - LoaderoModel to be created.
     * @return         - Newly created concrete LoaderModel object.
     */
//    public abstract T createNew(T newModel);
    
    /**
     * Deletes Loadero object from site based on provided id/ids.
     * @param id - ID/IDs required for object deletion.
     */
    public abstract void deleteById(int...id);

    /**
     * Common method used by every service to build url for itself.
     * @param id - ID of the specific object endpoint.
     * @return   - URL pointing to the object.
     */
    protected abstract String buildUrl(int...id);
}
