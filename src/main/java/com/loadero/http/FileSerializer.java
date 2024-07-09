package com.loadero.http;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.loadero.model.FileParams;
import java.lang.reflect.Type;

final class FileSerializer implements JsonSerializer<FileParams> {
    @Override
    public JsonElement serialize(
        FileParams file, Type type, JsonSerializationContext context
    ) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("file_type", file.getFileType().toString());
        jsonObject.addProperty("content", file.getContent());
        jsonObject.addProperty("name", file.getName());
        jsonObject.addProperty("password", file.getPassword());

        return jsonObject;
    }
}
