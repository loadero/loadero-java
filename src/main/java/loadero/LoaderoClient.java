package loadero;
import com.google.gson.Gson;
import loadero.model.LoaderoTestDescription;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.net.URI;

public class LoaderoClient {
    private static final String BASE_URL = "http://api.loadero.com/v2";
    private static final String LOADERO_API_TOKEN = System.getenv("LOADERO_API_TOKEN");
    private static final String PROJECT_ID = "5040";
    private static final String TEST_ID = "6866";

    public static void main(String[] args) {
        LoaderoTestDescription test = new LoaderoTestDescription(
                "Name",
                "mode",
                "",
                10,
                "",
                10
        );

        Gson gson = new Gson();
        URI uri = URI.create(BASE_URL+"/projects/"+PROJECT_ID+"/tests/"+TEST_ID);

        try {
            CloseableHttpClient client = HttpClients.custom().build();
            HttpUriRequest req = RequestBuilder.get(uri).build();
            req.setHeader(HttpHeaders.ACCEPT, "*/*");
            req.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
            req.setHeader(HttpHeaders.AUTHORIZATION, "LoaderoAuth " + LOADERO_API_TOKEN);
            CloseableHttpResponse res = client.execute(req);
            HttpEntity entity = res.getEntity();
            System.out.println(EntityUtils.toString(entity));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
