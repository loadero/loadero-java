package com.loadero.http;

import com.loadero.Loadero;
import java.util.Arrays;
import java.util.List;
import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;

final class LoaderoHttpClient extends HttpClientBuilder {
    LoaderoHttpClient() {
        List<Header> headers = Arrays.asList(
            new BasicHeader(HttpHeaders.ACCEPT, "application/json"),
            new BasicHeader(HttpHeaders.CONTENT_TYPE, "application/json"),
            new BasicHeader(HttpHeaders.AUTHORIZATION, "LoaderoAuth " + Loadero.getToken())
        );
        setDefaultHeaders(headers);
    }
}
