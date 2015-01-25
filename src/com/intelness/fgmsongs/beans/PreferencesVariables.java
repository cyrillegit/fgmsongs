package com.intelness.fgmsongs.beans;

/**
 * bean for shared preferences variables
 * 
 * @author McCyrille
 * @version 1.0
 * @since 2015-01-25
 */
public class PreferencesVariables {

    private int localeLanguage;
    private int lastCustomNumberSong;

    public PreferencesVariables( int localeLanguage, int lastCustomNumberSong ) {
        this.localeLanguage = localeLanguage;
        this.lastCustomNumberSong = lastCustomNumberSong;
    }

    public PreferencesVariables() {
    }

    public int getLocaleLanguage() {
        return localeLanguage;
    }

    public void setLocaleLanguage( int localeLanguage ) {
        this.localeLanguage = localeLanguage;
    }

    public int getLastCustomNumberSong() {
        return lastCustomNumberSong;
    }

    public void setLastCustomNumberSong( int lastCustomNumberSong ) {
        this.lastCustomNumberSong = lastCustomNumberSong;
    }
}
