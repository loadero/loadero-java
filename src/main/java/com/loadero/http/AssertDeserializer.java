package com.loadero.http;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.loadero.exceptions.ApiException;
import com.loadero.model.Assert;
import com.loadero.model.AssertParams;
import com.loadero.model.Precondition;
import com.loadero.types.AssertOperator;
import com.loadero.types.AssertPath;
import com.loadero.types.MachineAsserts;
import com.loadero.types.WebRtcAsserts;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

final class AssertDeserializer implements JsonDeserializer<Assert> {
    @Override
    public Assert deserialize(
        JsonElement json, Type type, JsonDeserializationContext context
    ) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        AssertPath path = getAssertPath(
            jsonObject.getAsJsonPrimitive("path").getAsString());
        AssertOperator operator = AssertOperator
            .getConstant(jsonObject.getAsJsonPrimitive("operator").getAsString());
        List<Precondition> preconditions = new ArrayList<>();

        if (!jsonObject.get("preconditions").isJsonNull()) {
            jsonObject.get("preconditions").getAsJsonArray()
                .forEach(p -> preconditions.add(context.deserialize(p, Precondition.class)));
        }

        AssertParams params = AssertParams
            .builder()
            .withId(jsonObject.getAsJsonPrimitive("id").getAsInt())
            .withCreated(jsonObject.getAsJsonPrimitive("created").getAsString())
            .withUpdated(jsonObject.getAsJsonPrimitive("updated").getAsString())
            .withPath(path)
            .withOperator(operator)
            .withExpected(jsonObject.getAsJsonPrimitive("expected").getAsString())
            .withTestId(jsonObject.getAsJsonPrimitive("test_id").getAsInt())
            .withPreconditions(preconditions)
            .build();

        return new Assert(params);
    }

    private AssertPath getAssertPath(String name) {
        AssertPath path;

        if (name.startsWith("machine")) {
            path = MachineAsserts.getConstant(name);
        } else if (name.startsWith("webrtc")){
            path = WebRtcAsserts.getConstant(name);
        } else {
            throw new ApiException("Unknown assert path.");
        }

        return path;
    }
}
