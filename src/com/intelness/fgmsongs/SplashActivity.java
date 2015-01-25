package com.intelness.fgmsongs;

import java.util.List;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.intelness.fgmsongs.beans.ApplicationVariables;
import com.intelness.fgmsongs.beans.PreferencesVariables;
import com.intelness.fgmsongs.beans.Song;
import com.intelness.fgmsongs.beans.SongDAO;
import com.intelness.fgmsongs.globals.AppManager;
import com.intelness.fgmsongs.managers.ApplicationManager;
import com.intelness.fgmsongs.managers.SharedPreferencesManager;
import com.intelness.fgmsongs.managers.XMLFileManager;
import com.intelness.fgmsongs.utils.FGMSongsUtils;

/**
 * splash activity
 * 
 * @author McCyrille
 * @version 1.0
 */
public class SplashActivity extends ActionBarActivity {

    private static final int    SPLASH_TIME_OUT = 3000;
    private static final String TAG             = "SplashActivity";
    private List<Song>          songs;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_splash );
        getSupportActionBar().hide();

        // if ( getIntent().getBooleanExtra( "EXIT", false ) ) {
        // finish();
        // System.exit( 0 );
        // }

        Resources res = getResources();
        String current = res.getConfiguration().locale.getCountry();
        Log.i( TAG, "current locale : " + current );

        new loadSongs().execute();
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate( R.menu.splash, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item ) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if ( id == R.id.action_settings ) {
            return true;
        }
        return super.onOptionsItemSelected( item );
    }

    @Override
    public void onBackPressed() {

    }

    /**
     * asyncTask to load the songs and initialize the App
     * 
     * @author McCyrille
     * @version 1.0
     */
    private class loadSongs extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground( Void... params ) {

            try {
                Thread.sleep( SPLASH_TIME_OUT );
            } catch ( InterruptedException e ) {
                Thread.interrupted();
            }
            populateDatabase();
            songs = getAllSongs();

            // load global variables
            // setGlobalVariables();
            // initialize
            initialize();
            return null;
        }

        @Override
        protected void onCancelled() {
        }

        @Override
        protected void onPostExecute( Void result ) {

            Intent i = new Intent( getApplicationContext(), HomeActivity.class );
            startActivity( i );
            finish();
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onProgressUpdate( Void... values ) {
        }
    }

    /**
     * get all the title and second line of songs stored in db
     * 
     * @return list of songs
     */
    public List<Song> getAllSongs() {

        SongDAO sDao = new SongDAO( this );
        List<Song> allSongs = sDao.getAllSongs();
        return allSongs;
    }

    /**
     * load xml file songs in DB
     * 
     * @since 2015-01-25
     */
    public void populateDatabase() {

        SongDAO sDao = new SongDAO( this );
        int rows = sDao.getNumberOfSongs();

        List<Song> songs;

        // old way
        // XMLParser parser = new XMLParser( XMLParser.SONG );
        // songs = parser.parseSong( getResources().openRawResource(
        // R.raw.fr_songs ) );

        // with XMLFileManager
        XMLFileManager manager = new XMLFileManager( XMLFileManager.SONG );
        songs = manager.parseSong( getResources().openRawResource( R.raw.fr_songs ) );

        // Log.i( TAG, "songs : " + songs.toString() );
        for ( Song song : songs ) {
            int number = song.getNumber();
            // if db is empty, store every songs that is in xml file in db
            if ( rows <= 0 ) {
                sDao.addSong( song );
                // else, check to not store two same song
            } else if ( sDao.getSongByNumber( number ).getNumber() != number ) {
                sDao.addSong( song );
            }
        }
    }

    /**
     * set global variable
     * 
     * @deprecated use setApplicationVariables instead
     * @see setApplicationVariables
     */
    private void setGlobalVariables() {
        // calling the application class
        final AppManager app = (AppManager) getApplicationContext();
        SharedPreferences prefs = getSharedPreferences( FGMSongsUtils.PREFERENCES, MODE_PRIVATE );

        app.setSongs( songs );
        app.setTitleSongs( FGMSongsUtils.getAllTitleSongs( songs ) );
        app.setLanguage( prefs.getInt( FGMSongsUtils.LANGUAGE, -1 ) );
        app.setLastCustomNumberSong( prefs.getInt( FGMSongsUtils.LAST_CUSTOM_NUMBER_SONG,
                FGMSongsUtils.FIRST_CUSTOM_NUMBER_SONG ) );
    }

    /**
     * set the application variables scope
     * 
     * @since 2015-01-25
     */
    private void setAllApplicationVariables( ApplicationVariables variables ) {

        ApplicationManager am = new ApplicationManager( this );
        am.setApplicationVariables( variables );
    }

    /**
     * get the preferences variables
     * 
     * @since 2015-01-25
     */
    private PreferencesVariables getAllPreferencesVariables() {
        SharedPreferencesManager spm = new SharedPreferencesManager( this );
        return spm.getPreferencesVariables();
    }

    /**
     * initialize all the application variables
     * 
     * @since 2015-01-25
     */
    private void initialize() {
        ApplicationVariables appVars = new ApplicationVariables();
        PreferencesVariables prefVars = getAllPreferencesVariables();

        appVars.setSongs( songs );
        appVars.setTitleSongs( FGMSongsUtils.getAllTitleSongs( songs ) );
        appVars.setLanguage( prefVars.getLocaleLanguage() );
        appVars.setLastCustomNumberSong( prefVars.getLastCustomNumberSong() );

        setAllApplicationVariables( appVars );

        Log.i( TAG, "language : " + appVars.getLanguage() );
    }
}
