package com.myapp.apangcatan.appexam.repository.remote;

import com.myapp.apangcatan.appexam.model.TrackList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface TrackApi {

    @GET("/search?term=star&amp;country=au&amp;media=movie")
    Call<TrackList> loadTracks();
}
