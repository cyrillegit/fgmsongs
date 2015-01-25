package com.intelness.fgmsongs.managers;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.intelness.fgmsongs.beans.ApplicationVariables;
import com.intelness.fgmsongs.beans.Song;
import com.intelness.fgmsongs.globals.AppManager;
import com.intelness.fgmsongs.utils.FGMSongsUtils;

/**
 * to manager application variables
 * 
 * @author McCyrille
 * @version 1.0
 * @since 2015-01-25
 */
public class ApplicationManager {

    private AppManager           appManager;
    private ApplicationVariables applicationVariables;

    public ApplicationManager( Context context ) {
        appManager = (AppManager) context.getApplicationContext();
    }

    public ApplicationManager() {
    }

    /**
     * initialize global variable
     * 
     * @deprecated
     */
    public void initialize( ApplicationVariables variables ) {
        appManager.setSongs( variables.getSongs() );
        appManager.setTitleSongs( FGMSongsUtils.getAllTitleSongs( variables.getSongs() ) );
        appManager.setLanguage( variables.getLanguage() );
        appManager.setLastCustomNumberSong( variables.getLastCustomNumberSong() );
    }

    public ApplicationVariables getApplicationVariables() {
        applicationVariables = new ApplicationVariables();

        applicationVariables.setSongs( appManager.getSongs() );
        applicationVariables.setTitleSongs( appManager.getTitleSongs() );
        applicationVariables.setLanguage( appManager.getLanguage() );
        applicationVariables.setLastCustomNumberSong( appManager.getLastCustomNumberSong() );

        return applicationVariables;
    }

    public void setApplicationVariables( ApplicationVariables applicationVariables ) {

        appManager.setSongs( applicationVariables.getSongs() );
        appManager.setTitleSongs( FGMSongsUtils.getAllTitleSongs( applicationVariables.getSongs() ) );
        appManager.setLanguage( applicationVariables.getLanguage() );
        appManager.setLastCustomNumberSong( applicationVariables.getLastCustomNumberSong() );

        this.applicationVariables = applicationVariables;
    }

    public List<Song> getSongs() {
        return appManager.getSongs();
    }

    public void setSongs( List<Song> songs ) {
        appManager.setSongs( songs );
    }

    public ArrayList<String> getTitleSongs() {
        return appManager.getTitleSongs();
    }

    public void setTitleSongs( ArrayList<String> titleSongs ) {
        appManager.setTitleSongs( titleSongs );
    }

    public int getLanguage() {
        return appManager.getLanguage();
    }

    public void setLanguage( int language ) {
        appManager.setLanguage( language );
    }

    public int getLastCustomNumberSong() {
        return appManager.getLastCustomNumberSong();
    }

    public void setLastCustomNumberSong( int lastCustomNumberSong ) {
        appManager.setLastCustomNumberSong( lastCustomNumberSong );
    }

}
