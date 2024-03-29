package com.loadero.model;

import java.util.List;

/**
 * Class used to for deserialization of artifacts paths
 */
public final class ArtifactPaths {
    private final List<String> paths;

    public ArtifactPaths(List<String> paths) {
        this.paths = paths;
    }

    public List<String> getPaths() {
        return paths;
    }

    @Override
    public String toString() {
        return "ArtifactPaths{" +
            "paths=" + paths +
            '}';
    }
}
