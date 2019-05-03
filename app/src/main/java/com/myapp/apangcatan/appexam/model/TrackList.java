package com.myapp.apangcatan.appexam.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @SerializedName specify the JSON Properties to be accessed
 */
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
