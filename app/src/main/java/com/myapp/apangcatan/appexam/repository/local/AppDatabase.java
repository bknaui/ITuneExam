package com.myapp.apangcatan.appexam.repository.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.myapp.apangcatan.appexam.model.Track;

/**
 * Room Database:
 * Benefits:
 * 1. Dao use case - only add methods of what that entity needs compared to greenDAO.
 * Disadvantage
 * 1. You have to implement the DAO method(s) that you needed
 * **/
@Database(entities = {Track.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TrackDao trackDao();
}
