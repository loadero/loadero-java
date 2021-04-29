package com.loadero.model;

/**
 * Class to represent information about artifacts in results.
 */
public final class Artifacts {
    private final ArtifactPaths audio;
    private final ArtifactPaths downloads;
    private final ArtifactPaths screenshots;
    private final ArtifactPaths video;

    public Artifacts(
        ArtifactPaths audio,
        ArtifactPaths downloads,
        ArtifactPaths screenshots,
        ArtifactPaths video
    ) {
        this.audio = audio;
        this.downloads = downloads;
        this.screenshots = screenshots;
        this.video = video;
    }

    public ArtifactPaths getAudio() {
        return audio;
    }

    public ArtifactPaths getDownloads() {
        return downloads;
    }

    public ArtifactPaths getScreenshots() {
        return screenshots;
    }

    public ArtifactPaths getVideo() {
        return video;
    }

    @Override
    public String toString() {
        return "Artifacts{" +
            "audio=" + audio +
            ", downloads=" + downloads +
            ", screenshots=" + screenshots +
            ", video=" + video +
            '}';
    }
}
