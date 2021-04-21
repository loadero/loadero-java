package com.loadero.http;

import java.io.IOException;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;

final class HttpController {
    private final LoaderoHttpClient httpClient;

    public HttpController() {
        this.httpClient = new LoaderoHttpClient();
    }

    /**
     * Makes request to Loadero API service.
     *
     * @param method  Request method name i.e GET, POST, PUT or DELETE.
     * @param route   Url to make request for.
     * @param content JSON object represented as String.
     * @param clazz   Class that is going to be serialized/deserialized.
     * @return Returns deserialized model from JSON response.
     * @throws IOException if request failed.
     */
    public <T> T request(
        RequestMethod method, String route, String content, Class<T> clazz
    ) throws IOException {
        HttpEntity entityContent = new StringEntity(content);
        HttpUriRequest request = RequestBuilder
            .create(method.toString())
            .setUri(route)
            .setEntity(entityContent)
            .build();
        CustomResponseHandler<T> handler = new CustomResponseHandler<>(method, route, clazz);

        return httpClient.build().execute(request, handler);
    }
}
