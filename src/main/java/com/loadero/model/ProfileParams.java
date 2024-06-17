package com.loadero.model;

import com.loadero.types.AudioFeed;
import com.loadero.types.Browser;
import com.loadero.types.Location;
import com.loadero.types.Network;
import com.loadero.types.VideoFeed;

/**
 * Class to deserialize information about profile_params field in results.
 */
public final class ProfileParams {
    private final Browser browser;
    private final Network network;
    private final Location location;
    private final VideoFeed videoFeed;
    private final AudioFeed audioFeed;

    public ProfileParams(
        Browser browser,
        Network network,
        Location location,
        VideoFeed videoFeed,
        AudioFeed audioFeed
    ) {
        this.browser = browser;
        this.network = network;
        this.location = location;
        this.videoFeed = videoFeed;
        this.audioFeed = audioFeed;
    }

    public Browser getBrowser() {
        return browser;
    }

    public Network getNetwork() {
        return network;
    }

    public Location getLocation() {
        return location;
    }

    public VideoFeed getVideoFeed() {
        return videoFeed;
    }

    public AudioFeed getAudioFeed() {
        return audioFeed;
    }

    @Override
    public String toString() {
        return "ProfileParams{" +
            "browser='" + browser + '\'' +
            ", network=" + network +
            ", location=" + location +
            ", videoFeed=" + videoFeed +
            ", audioFeed=" + audioFeed +
            '}';
    }
}
