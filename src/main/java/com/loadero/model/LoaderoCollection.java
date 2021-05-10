package com.loadero.model;

import java.util.List;

public abstract class LoaderoCollection<T> {
    private List<T> results;

    public LoaderoCollection(List<T> results) {
        this.results = results;
    }

    public List<T> getResults() {
        return results;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "LoaderoCollection{" +
            "results=" + results +
            '}';
    }
}
