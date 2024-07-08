package com.loadero.http;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.loadero.model.File;
import com.loadero.model.FileParams;
import com.loadero.types.FileType;
import java.lang.reflect.Type;

final class FileDeserializer implements JsonDeserializer<File> {
    @Override
    public File deserialize(JsonElement json, Type type, JsonDeserializationContext context
    ) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        if (jsonObject == null) {
            return null;
        }

        FileType fileType = FileType
            .getConstant(jsonObject.getAsJsonPrimitive("file_type").getAsString());

        FileParams params = FileParams
            .builder()
            .withId(jsonObject.getAsJsonPrimitive("id").getAsInt())
            .withCreated(jsonObject.getAsJsonPrimitive("created").getAsString())
            .withUpdated(jsonObject.getAsJsonPrimitive("updated").getAsString())
            .withProjectId(jsonObject.getAsJsonPrimitive("project_id").getAsInt())
            .withFileType(fileType)
            .withContent(jsonObject.getAsJsonPrimitive("content").getAsString())
            .withName(jsonObject.getAsJsonPrimitive("name").getAsString())
            .build();

        return new File(params);
    }
}
