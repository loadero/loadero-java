package com.loadero.http;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.loadero.model.Precondition;
import com.loadero.model.PreconditionParams;
import com.loadero.types.AssertOperator;
import com.loadero.types.Property;
import java.lang.reflect.Type;
import java.util.Locale;

final class PreconditionDeserializer implements JsonDeserializer<Precondition> {
    @Override
    public Precondition deserialize(
        JsonElement json, Type type, JsonDeserializationContext context
    ) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        AssertOperator operator = AssertOperator.getConstant(
            jsonObject.getAsJsonPrimitive("operator").getAsString()
        );
        Property property = Property.valueOf(
            jsonObject.getAsJsonPrimitive("property").getAsString().toUpperCase(Locale.ROOT)
        );

        PreconditionParams params = PreconditionParams
            .builder()
            .withId(jsonObject.getAsJsonPrimitive("id").getAsInt())
            .withCreated(jsonObject.getAsJsonPrimitive("created").getAsString())
            .withUpdated(jsonObject.getAsJsonPrimitive("updated").getAsString())
            .withProperty(property)
            .withOperator(operator)
            .withExpected(jsonObject.getAsJsonPrimitive("expected").getAsString())
            .withAssertId(jsonObject.getAsJsonPrimitive("assert_id").getAsInt())
            .build();

        return new Precondition(params);
    }
}
