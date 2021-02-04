package loadero.controller;

import loadero.LoaderoClientUtils;
import loadero.model.LoaderoGroup;
import loadero.model.LoaderoModel;
import loadero.model.LoaderoType;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.net.URI;

public class LoaderoGroupController extends LoaderoAbstractController {
    private String uri;

    public LoaderoGroupController(URI uri, String loaderoApiToken,
                                  String projectId, String testId, String groupId) {
        super(uri, loaderoApiToken, projectId, testId);
        this.uri = super.getUri() + "/groups/" + groupId + "/";
    }

    @Override
    public LoaderoGroup get() {
        LoaderoGroup group = new LoaderoGroup();
        HttpUriRequest get = RequestBuilder.get(uri).build();
        LoaderoClientUtils.setDefaultHeaders(get, super.getLoaderoApiToken());
        try (CloseableHttpClient client = HttpClients.custom().build();
             CloseableHttpResponse res = client.execute(get)) {
            if (res.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = res.getEntity();
                group = (LoaderoGroup) LoaderoClientUtils.jsonToObject(
                        entity,
                        LoaderoType.LOADERO_GROUP);
            } else {
                System.out.println(get.getURI());
                System.out.println(res.getStatusLine());
            }

        } catch (NullPointerException | IOException e) {
            System.out.println(e.getMessage());
        }
        return group;
    }

    @Override
    public LoaderoModel update(LoaderoModel newModel) {
        return null;
    }
}
