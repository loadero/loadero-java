package com.loadero.http;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.loadero.model.Participant;
import com.loadero.model.ParticipantParams;
import com.loadero.types.AudioFeed;
import com.loadero.types.Browser;
import com.loadero.types.ComputeUnit;
import com.loadero.types.Location;
import com.loadero.types.Network;
import com.loadero.types.VideoFeed;
import java.lang.reflect.Type;

final class ParticipantDeserializer implements JsonDeserializer<Participant> {
    @Override
    public Participant deserialize(JsonElement json, Type type, JsonDeserializationContext context
    ) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        if (jsonObject == null) {
            return null;
        }

        ComputeUnit computeUnit = ComputeUnit
            .getConstant(jsonObject.getAsJsonPrimitive("compute_unit").getAsString());
        Browser browser = new Browser(
            jsonObject.getAsJsonPrimitive("browser").getAsString());
        Network network = Network.getConstant(
            jsonObject.getAsJsonPrimitive("network").getAsString());
        Location location = Location.getConstant(
            jsonObject.getAsJsonPrimitive("location").getAsString());
        AudioFeed audioFeed = AudioFeed.getConstant(
            jsonObject.getAsJsonPrimitive("audio_feed").getAsString());
        VideoFeed videoFeed = VideoFeed.getConstant(
            jsonObject.getAsJsonPrimitive("video_feed").getAsString());

        ParticipantParams params = ParticipantParams
            .builder()
            .withId(jsonObject.getAsJsonPrimitive("id").getAsInt())
            .withTestId(jsonObject.getAsJsonPrimitive("test_id").getAsInt())
            .withGroupId(jsonObject.getAsJsonPrimitive("group_id").getAsInt())
            .withProfileId(jsonObject.getAsJsonPrimitive("profile_id").getAsInt())
            .withName(jsonObject.getAsJsonPrimitive("name").getAsString())
            .withCount(jsonObject.getAsJsonPrimitive("count").getAsInt())
            .withComputeUnit(computeUnit)
            .withBrowser(browser)
            .withNetwork(network)
            .withLocation(location)
            .withRecordAudio(jsonObject.getAsJsonPrimitive("record_audio").getAsBoolean())
            .withAudioFeed(audioFeed)
            .withVideoFeed(videoFeed)
            .withCreated(jsonObject.getAsJsonPrimitive("created").getAsString())
            .withUpdated(jsonObject.getAsJsonPrimitive("updated").getAsString())
            .build();

        return new Participant(params);
    }
}
