package loadero.main;

import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of HttpClientBuilder for interacting with Loadero API.
 * Basically, applies some common headers/traits that is later used to send requests.
 */
final class LoaderoHttpClient extends HttpClientBuilder {

    public LoaderoHttpClient(String loaderoApiToken) {
        List<Header> headers = new ArrayList<>(List.of(
                new BasicHeader(HttpHeaders.ACCEPT, "application/json"),
                new BasicHeader(HttpHeaders.CONTENT_TYPE, "application/json"),
                new BasicHeader(HttpHeaders.AUTHORIZATION, "LoaderoAuth " + loaderoApiToken)));
        setDefaultHeaders(headers);
    }
}
