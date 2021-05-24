package com.loadero.http;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.loadero.exceptions.ApiException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ResponseHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

final class CustomResponseHandler<T> implements ResponseHandler<T> {
    private final Type type;
    private final RequestMethod method;
    private final String route;
    private final Logger log = LogManager.getLogger(CustomResponseHandler.class);

    public CustomResponseHandler(RequestMethod method, String route, Class<T> clazz) {
        this.method = method;
        this.route = route;
        this.type = TypeToken.get(clazz).getType();
    }

    @Override
    public T handleResponse(HttpResponse response) throws IOException {
        int code = response.getStatusLine().getStatusCode();
        if (code == HttpStatus.SC_NO_CONTENT) {
            log.info("Successful - {} - {}", method, route);
            return null;
        }

        HttpEntity entity = response.getEntity();
        if (entity == null) {
            throw new ApiException("Returned response is null.");
        }

        InputStream input = entity.getContent();
        Reader reader = new InputStreamReader(input, StandardCharsets.UTF_8);

        if (code < 200 || code > 300) {
            handleError(reader);
        }

        T resource;
        try {
            resource = ApiResource.getGSON().fromJson(reader, type);
            log.info("Successful - {} - {} - {}", method, resource, route);
        } catch (JsonSyntaxException | NullPointerException ex) {
            log.error("{} - {} - {}", ex.getMessage(), ex.fillInStackTrace(), route);
            throw new ApiException(ex.getMessage());
        } finally {
            input.close();
            reader.close();
        }

        return resource;
    }

    private void handleError(Reader reader) {
        JsonElement jsonError = JsonParser.parseReader(reader);
        String prettyError = ApiResource.getGSON().toJson(jsonError);
        throw new ApiException(String.format("%s", prettyError));
    }
}
