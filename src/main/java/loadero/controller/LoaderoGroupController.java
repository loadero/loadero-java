package loadero.controller;

import loadero.model.LoaderoModel;

import java.net.URI;

public class LoaderoGroupController extends LoaderoAbstractController {
    private String uri;

    public LoaderoGroupController(URI uri, String loaderoApiToken,
                                  String projectId, String testId, String groupId) {
        super(uri, loaderoApiToken, projectId, testId);
        this.uri = super.getUri() + "/" + groupId;
    }

    @Override
    public LoaderoModel get() {
        return null;
    }

    @Override
    public LoaderoModel update(LoaderoModel newModel) {
        return null;
    }
}
