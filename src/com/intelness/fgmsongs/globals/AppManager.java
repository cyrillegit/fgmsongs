package com.intelness.fgmsongs.globals;

import java.util.List;

import android.app.Application;

import com.intelness.fgmsongs.beans.Song;

public class AppManager extends Application {

    private List<Song> songs;
    private int        number;

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs( List<Song> songs ) {
        this.songs = songs;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber( int number ) {
        this.number = number;
    }

}
