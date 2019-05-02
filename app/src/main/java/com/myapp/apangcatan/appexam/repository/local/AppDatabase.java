package com.myapp.apangcatan.appexam.repository.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.myapp.apangcatan.appexam.model.Track;

@Database(entities = {Track.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TrackDao trackDao();

}
