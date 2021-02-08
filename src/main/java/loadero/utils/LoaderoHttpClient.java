package loadero.utils;

import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class LoaderoHttpClient extends HttpClientBuilder {
    private static final Logger logger = LogManager.getLogger(LoaderoHttpClient.class);

    public LoaderoHttpClient(String loaderoApiToken) {
        logger.info("Initializing LoaderoHttpClient...");
        List<Header> headers = new ArrayList<>(List.of(
                new BasicHeader(HttpHeaders.ACCEPT, "application/json"),
                new BasicHeader(HttpHeaders.CONTENT_TYPE, "application/json"),
                new BasicHeader(HttpHeaders.AUTHORIZATION, "LoaderoAuth " + loaderoApiToken)));
        setDefaultHeaders(headers);
    }
}
