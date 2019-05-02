package com.myapp.apangcatan.appexam.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;
/*
* SerializedName specify the JSON Properties to be accessed
* ColumnInfo specify the table's column name
* */
@Entity(tableName = "track_table")
public class Track {
    @PrimaryKey
    @NonNull
    @SerializedName("trackId")
    private int id=0;

    @SerializedName("trackName")
    @ColumnInfo(name = "track_name")
    private String trackName = "Untitled";

    @SerializedName("artworkUrl100")
    @ColumnInfo(name = "art_work")
    private String artWork;

    @SerializedName("primaryGenreName")
    @ColumnInfo(name = "genre")
    private String genre;

    @SerializedName("trackPrice")
    @ColumnInfo(name = "price")
    private double price;

    @SerializedName("longDescription")
    @ColumnInfo(name = "long_description")
    private String longDescription = "No available description";

    public Track() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public String getArtWork() {
        return artWork;
    }

    public void setArtWork(String artWork) {
        this.artWork = artWork;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }
}
