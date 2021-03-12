package loadero.service;

import loadero.model.LoaderoModel;

/**
 * Public interface for specifying common operation for Loadero service layer such as GET request
 * and method for building urls.
 * @param <T> Concrete type of the LoaderModel interface.
 */
public interface LoaderoCommonOperation<T extends LoaderoModel> {
    
    /**
     * Make GET request to the endpoint and retrieves object/objects with specific ID.
     * @param ids Array of IDs.
     * @return Returns concrete object of the LoaderoModel type from Loadero.
     */
    T getById(int... ids);
    
    /**
     * Common method used by every service to build url for itself.
     * @param ids ID of the specific object endpoint.
     * @return   URL pointing to the object.
     */
    String buildUrl(int... ids);
}
