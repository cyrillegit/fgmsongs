package com.intelness.fgmsongs.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * bean for application variables
 * 
 * @author McCyrille
 * @version 1.0
 * @since 2015-01-25
 */
public class ApplicationVariables {

    private List<Song>        songs;
    private ArrayList<String> titleSongs;
    private int               language;
    private int               lastCustomNumberSong;

    public ApplicationVariables( List<Song> songs, ArrayList<String> titleSongs, int language, int lastCustomNumberSong ) {
        this.songs = songs;
        this.titleSongs = titleSongs;
        this.language = language;
        this.lastCustomNumberSong = lastCustomNumberSong;
    }

    public ApplicationVariables() {

    }

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
