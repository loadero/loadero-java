package com.loadero.http;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.loadero.model.ProfileParams;
import com.loadero.types.AudioFeed;
import com.loadero.types.Browser;
import com.loadero.types.Location;
import com.loadero.types.Network;
import com.loadero.types.VideoFeed;
import java.lang.reflect.Type;

final class ProfileParamsDeserializer implements JsonDeserializer<ProfileParams> {
    @Override
    public ProfileParams deserialize(
        JsonElement json, Type type, JsonDeserializationContext context
    ) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        Browser browser = new Browser(
            jsonObject.getAsJsonPrimitive("browser").getAsString()
        );
        Network network = Network.getConstant(
            jsonObject.getAsJsonPrimitive("network").getAsString());
        Location location = Location.getConstant(
            jsonObject.getAsJsonPrimitive("location").getAsString()
        );
        VideoFeed videoFeed = VideoFeed.getConstant(
            jsonObject.getAsJsonPrimitive("video_feed").getAsString()
        );
        AudioFeed audioFeed = AudioFeed.getConstant(
            jsonObject.getAsJsonPrimitive("audio_feed").getAsString()
        );

        return new ProfileParams(
            browser, network, location, videoFeed, audioFeed
        );
    }
}
