package com.loadero.http;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.loadero.model.Participant;
import com.loadero.model.ParticipantParams;
import java.lang.reflect.Type;
import loadero.types.AudioFeed;
import loadero.types.Browser;
import loadero.types.ComputeUnit;
import loadero.types.Location;
import loadero.types.MediaType;
import loadero.types.Network;
import loadero.types.VideoFeed;

final class ParticipantDeserializer implements JsonDeserializer<Participant> {
    @Override
    public Participant deserialize(
        JsonElement json,
        Type type,
        JsonDeserializationContext context
    ) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        if (jsonObject == null) {
            return null;
        }

        ComputeUnit computeUnit = ComputeUnit
            .getValue(jsonObject.getAsJsonPrimitive("compute_unit").getAsString());
        Browser browser = new Browser(
            jsonObject.getAsJsonPrimitive("browser").getAsString());
        Network network = Network.getValue(
            jsonObject.getAsJsonPrimitive("network").getAsString());
        Location location = Location.getValue(
            jsonObject.getAsJsonPrimitive("location").getAsString());
        MediaType mediaType = MediaType.getValue(
            jsonObject.getAsJsonPrimitive("media_type").getAsString());
        AudioFeed audioFeed = AudioFeed.getValue(
            jsonObject.getAsJsonPrimitive("audio_feed").getAsString());
        VideoFeed videoFeed = VideoFeed.getValue(
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
            .withMediaType(mediaType)
            .withRecordAudio(jsonObject
            .withRecordAudio(jsonObject.getAsJsonPrimitive("record_audio").getAsBoolean())
            .withAudioFeed(audioFeed)
            .withVideoFeed(videoFeed)
            .withCreated(jsonObject.getAsJsonPrimitive("created").getAsString())
            .withUpdated(jsonObject.getAsJsonPrimitive("updated").getAsString())
            .build();

        return new Participant(params);
    }
}
