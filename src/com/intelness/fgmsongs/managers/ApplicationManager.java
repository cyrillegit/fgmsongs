package com.intelness.fgmsongs.managers;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

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
    private Context              context;

    public ApplicationManager( Context context ) {
        this.context = context;
        appManager = (AppManager) context.getApplicationContext();
    }

    public ApplicationManager() {
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

    /**
     * set a locale
     * 
     * @param languageCode
     *            to set
     */
    public void setLocaleResources( final String languageCode ) {

        Resources res = context.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        android.content.res.Configuration conf = res.getConfiguration();
        conf.locale = new Locale( languageCode.toLowerCase( Locale.ENGLISH ) );
        res.updateConfiguration( conf, dm );
    }

}
