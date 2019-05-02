package com.myapp.apangcatan.appexam.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.myapp.apangcatan.appexam.model.Track;
import com.myapp.apangcatan.appexam.repository.TrackRepository;

import java.util.List;

public class TrackViewModel extends AndroidViewModel {
    private LiveData<List<Track>> mutableTrackList;
    private TrackRepository trackRepository;


    public TrackViewModel(@NonNull Application application) {
        super(application);
        trackRepository = new TrackRepository(application);
        mutableTrackList = trackRepository.getMutableTrackList();

    }

    public LiveData<List<Track>> getMutableLiveData() {
        return mutableTrackList;
    }

    public void loadTracksFromRemote() {
        trackRepository.loadTracks();
    }
}
