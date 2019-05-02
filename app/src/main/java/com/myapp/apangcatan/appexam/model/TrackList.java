package com.myapp.apangcatan.appexam.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TrackList {

    @SerializedName("results")
    private List<Track> results;

    public TrackList(List<Track> results) {
        this.results = results;
    }

    public List<Track> getResults() {
        return results;
    }
}
