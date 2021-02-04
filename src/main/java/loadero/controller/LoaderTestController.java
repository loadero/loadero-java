package loadero.controller;

import java.net.URI;

/**
 * REST controller class responsible for CRUD actions related to tests.
 * Meaning here is defined logic for creating, updetaing, retrieving and deleting Loadero tests.
 */
public class LoaderTestController extends LoaderoAbstractController {

    public LoaderTestController(String loaderoApiToken,
                                String projectId, String testId) {
        super(loaderoApiToken, projectId, testId);
    }
}
