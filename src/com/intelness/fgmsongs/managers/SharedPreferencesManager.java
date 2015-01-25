package com.intelness.fgmsongs.managers;

import android.content.Context;
import android.content.SharedPreferences;

import com.intelness.fgmsongs.beans.PreferencesVariables;
import com.intelness.fgmsongs.utils.FGMSongsUtils;

/**
 * manager the shared preferences
 * 
 * @author McCyrille
 * @version 1.0
 * @since 2015-01-25
 */
public class SharedPreferencesManager {

    private static int           DEFAULT_LANGUAGE = 0;
    private Context              context;
    private PreferencesVariables preferencesVariables;

    public SharedPreferencesManager( Context context ) {
        this.context = context;
        preferencesVariables = new PreferencesVariables();
    }

    public SharedPreferencesManager() {

    }

    public PreferencesVariables getPreferencesVariables() {
        SharedPreferences sharedPreferences = context.getSharedPreferences( FGMSongsUtils.PREFERENCES,
                Context.MODE_PRIVATE );

        preferencesVariables.setLocaleLanguage( sharedPreferences.getInt( FGMSongsUtils.LANGUAGE, DEFAULT_LANGUAGE ) );
        preferencesVariables.setLastCustomNumberSong( sharedPreferences.getInt( FGMSongsUtils.LAST_CUSTOM_NUMBER_SONG,
                FGMSongsUtils.FIRST_CUSTOM_NUMBER_SONG ) );

        return preferencesVariables;
    }

    public void setPreferencesVariables( PreferencesVariables preferencesVariables ) {

        SharedPreferences.Editor editor = context
                .getSharedPreferences( FGMSongsUtils.PREFERENCES, Context.MODE_PRIVATE )
                .edit();

        editor.putInt( FGMSongsUtils.LANGUAGE, preferencesVariables.getLocaleLanguage() );
        editor.putInt( FGMSongsUtils.LAST_CUSTOM_NUMBER_SONG, preferencesVariables.getLastCustomNumberSong() );

        editor.commit();
        this.preferencesVariables = preferencesVariables;
    }
}
