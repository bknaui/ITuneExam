package com.myapp.apangcatan.appexam.repository.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.myapp.apangcatan.appexam.model.Track;

import java.util.List;

@Dao
public interface TrackDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTrack(Track track);

    @Query("SELECT * FROM track_table")
    LiveData<List<Track>> loadTracks();

}
