package com.intelness.fgmsongs.globals;

import java.util.ArrayList;
import java.util.List;

import android.app.Application;

import com.intelness.fgmsongs.beans.Song;

/**
 * store application variables scope
 * 
 * @author McCyrille
 * @version 1.0
 * @since
 */
public class AppManager extends Application {

    private List<Song>        songs;
    private ArrayList<String> titleSongs;
    private int               language;
    private int               lastCustomNumberSong;

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs( List<Song> songs ) {
        this.songs = songs;
    }

    public ArrayList<String> getTitleSongs() {
        return titleSongs;
    }

    public void setTitleSongs( ArrayList<String> titleSongs ) {
        this.titleSongs = titleSongs;
    }

    public int getLanguage() {
        return language;
    }

    public void setLanguage( int language ) {
        this.language = language;
    }

    public int getLastCustomNumberSong() {
        return lastCustomNumberSong;
    }

    public void setLastCustomNumberSong( int lastCustomNumberSong ) {
        this.lastCustomNumberSong = lastCustomNumberSong;
    }

}
