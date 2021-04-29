package com.loadero.http;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.loadero.exceptions.ApiException;
import com.loadero.model.ResultAssert;
import com.loadero.types.AssertOperator;
import com.loadero.types.AssertPath;
import com.loadero.types.AssertStatus;
import com.loadero.types.MachineAsserts;
import com.loadero.types.WebRtcAsserts;
import java.lang.reflect.Type;
import java.util.Locale;

final class ResultAssertDeserializer implements JsonDeserializer<ResultAssert> {
    @Override
    public ResultAssert deserialize(
        JsonElement json, Type type, JsonDeserializationContext context
    ) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        AssertPath assertPath = getAssertPath(
            jsonObject.getAsJsonPrimitive("path").getAsString()
        );
        AssertOperator operator = AssertOperator.getConstant(
            jsonObject.getAsJsonPrimitive("operator").getAsString()
        );
        AssertStatus status = AssertStatus.valueOf(
            jsonObject.getAsJsonPrimitive("status")
                .getAsString()
                .toUpperCase(Locale.ROOT)
        );
        return new ResultAssert(
            jsonObject.getAsJsonPrimitive("id").getAsInt(),
            jsonObject.getAsJsonPrimitive("result_id").getAsInt(),
            jsonObject.getAsJsonPrimitive("run_assert_id").getAsInt(),
            jsonObject.getAsJsonPrimitive("created").getAsString(),
            assertPath,
            operator,
            jsonObject.getAsJsonPrimitive("expected").getAsString(),
            jsonObject.getAsJsonPrimitive("actual").getAsString(),
            status
        );
    }

    private AssertPath getAssertPath(String name) {
        AssertPath path;

        if (name.startsWith("machine")) {
            path = MachineAsserts.getConstant(name);
        } else if (name.startsWith("webrtc")) {
            path = WebRtcAsserts.getConstant(name);
        } else {
            throw new ApiException("Unknown assert path.");
        }

        return path;
    }
}
