package com.intelness.fgmsongs;

import java.util.List;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.intelness.fgmsongs.beans.Song;
import com.intelness.fgmsongs.beans.SongDAO;
import com.intelness.fgmsongs.globals.AppManager;
import com.intelness.fgmsongs.utils.XMLParser;

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

    private class loadSongs extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground( Void... params ) {

            try {
                Thread.sleep( SPLASH_TIME_OUT );
            } catch ( InterruptedException e ) {
                Thread.interrupted();
            }
            // loadDB();
            songs = getAllSongs();
            // load global variables
            setGlobalVariables();
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

    public List<Song> getAllSongs() {

        SongDAO sDao = new SongDAO( this );
        List<Song> allSongs = sDao.getAllSongs();
        for ( int i = 0; i < allSongs.size(); i++ ) {
            Log.i( TAG, "song : " + allSongs.get( i ).getTitle() );
        }
        return allSongs;
    }

    public void loadDB() {

        SongDAO sDao = new SongDAO( this );
        int rows = sDao.getNumberOfSongs();

        List<Song> songs;

        XMLParser parser = new XMLParser( XMLParser.SONG );
        songs = parser.parseSong( getResources().openRawResource( R.raw.fr_songs ) );
        // Log.i( TAG, "songs : " + songs.toString() );
        for ( int i = 0; i < songs.size(); i++ ) {
            int number = songs.get( i ).getNumber();
            // if db is empty, store every songs that is in xml file in db
            if ( rows <= 0 ) {
                sDao.addSong( songs.get( i ) );
                // else, check to not store two same song
            } else if ( sDao.getSongByNumber( number ).getNumber() != number ) {
                sDao.addSong( songs.get( i ) );
            }
        }
    }

    private void setGlobalVariables() {
        // calling the application class
        final AppManager app = (AppManager) getApplicationContext();
        app.setSongs( songs );
    }
}
