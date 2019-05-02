package com.myapp.apangcatan.appexam.repository;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.myapp.apangcatan.appexam.repository.local.AppDatabase;
import com.myapp.apangcatan.appexam.model.Track;
import com.myapp.apangcatan.appexam.model.TrackList;
import com.myapp.apangcatan.appexam.repository.local.TrackDao;
import com.myapp.apangcatan.appexam.repository.remote.RetrofitClient;
import com.myapp.apangcatan.appexam.repository.remote.TrackApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class TrackRepository {
    private LiveData<List<Track>> mutableTrackList;
    private TrackDao trackDao;


    public TrackRepository(Context context) {
        trackDao = Room.databaseBuilder(context, AppDatabase.class, "track-db").build().trackDao();
        mutableTrackList = trackDao.loadTracks();
    }

    public LiveData<List<Track>> getMutableTrackList() {
        return mutableTrackList;
    }

    public void loadTracks() {
        Retrofit retrofit = RetrofitClient.getRetrofit();
        TrackApi trackApi = retrofit.create(TrackApi.class);
        Call<TrackList> loadTracks = trackApi.loadTracks();
        loadTracks.enqueue(new Callback<TrackList>() {
            @Override
            public void onResponse(Call<TrackList> call, Response<TrackList> response) {
                new InsertTrackAsyncTask().execute(response.body().getResults());
            }

            @Override
            public void onFailure(Call<TrackList> call, Throwable t) {

            }
        });
    }

    class InsertTrackAsyncTask extends AsyncTask<List<Track>, Void, Void> {

        @Override
        protected Void doInBackground(List<Track>... lists) {
            for (Track track : lists[0]) {
                trackDao.insertTrack(track);
            }
            return null;
        }
    }
}
