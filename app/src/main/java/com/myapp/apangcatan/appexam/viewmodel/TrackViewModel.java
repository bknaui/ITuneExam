package com.myapp.apangcatan.appexam.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.myapp.apangcatan.appexam.model.Track;
import com.myapp.apangcatan.appexam.repository.TrackRepository;

import java.util.List;

/**
 * LiveData & ViewModel Library - MVVM Pattern Architecture
 * Benefits:
 * 1. Retains data kept by ViewModel during Configuration Changes
 * 2. LiveData allows observer to be attached and perform UI Changes when data LiveData is modified
 */
public class TrackViewModel extends AndroidViewModel {
    private LiveData<List<Track>> liveDataTrackList;
    private MutableLiveData<String> mutableLiveDataMessage;
    private TrackRepository trackRepository;

    public TrackViewModel(@NonNull Application application) {
        super(application);
        trackRepository = new TrackRepository(application);
        liveDataTrackList = trackRepository.getMutableTrackList();
        mutableLiveDataMessage = trackRepository.getMutableLiveDataMessage();

    }

    public LiveData<List<Track>> getMutableLiveData() {
        return liveDataTrackList;
    }

    public MutableLiveData<String> getMutableLiveDataMessage() {
        return mutableLiveDataMessage;
    }


    public void loadTracksFromRemote() {
        trackRepository.loadTracks();
    }
}
