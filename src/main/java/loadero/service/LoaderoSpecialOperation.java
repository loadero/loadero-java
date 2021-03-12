package loadero.service;


import loadero.model.LoaderoModel;


/**
 * Public interface to provide not so common operation for each service layer.
 * For example, update, delete and create operations are only specific for tests, groups and participants
 * objects, thus this make more sense to have separate interface for only this operations.
 *
 * @param <T> Concrete type of the LoaderModel interface.
 */
public interface LoaderoSpecialOperation<T extends LoaderoModel> {
    /**
     * Updates information by ID about newModel object on Loadero API endpoint.
     * @param updatedModel LoaderoModel object with new parameters that we wish to update
     * @param ids          Necessary IDs for update.
     * @return             Concrete implementation of LoaderoModel object.
     */
    T updateById(T updatedModel, int... ids);
    
    /**
     * Create new LoaderoModel on Loadero site.
     * @param newModel LoaderoModel to be created.
     * @param ids      Optional - IDs required to create new model.
     * @return         Newly created concrete LoaderModel object.
     */
    T createNewModel(T newModel, int... ids);
    
    /**
     * Deletes Loadero object from site based on provided id/ids.
     * @param ids ID/IDs required for object deletion.
     */
    void deleteById(int... ids);
}
