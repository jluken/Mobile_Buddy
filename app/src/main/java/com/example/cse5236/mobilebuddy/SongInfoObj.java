package com.example.cse5236.mobilebuddy;

/**
 * Created by Amir on 3/15/2018.
 */

public class SongInfoObj {

    private long ID;
    private String title, artist;

    public SongInfoObj(String title, long ID, String artist){
        this.title = title;
        this.ID = ID;
        this.artist = artist;
    }

    public String getTitle(){
        return title;
    }

    public long getID(){
        return ID;
    }

    public String getArtist(){
        return artist;
    }
}
