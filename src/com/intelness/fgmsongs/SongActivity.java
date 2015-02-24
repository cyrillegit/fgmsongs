package com.intelness.fgmsongs;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.intelness.fgmsongs.adapters.ASongAdapter;
import com.intelness.fgmsongs.beans.ApplicationVariables;
import com.intelness.fgmsongs.beans.Song;
import com.intelness.fgmsongs.beans.Verse;
import com.intelness.fgmsongs.managers.XMLFileManager;
import com.intelness.fgmsongs.utils.FGMSongsUtils;

/**
 * 
 * @author McCyrille
 * @version 1.0
 */
public class SongActivity extends MainActivity {

    private static final String  TAG = "SongActivity";
    private List<Song>           songs;
    private List<Verse>          verses;
    private int                  position;
    private ApplicationVariables appVars;
    private View                 layout;
    private ArrayList<Integer>   indexes;
    private ListView             lvASong;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        layout = getLayoutInflater().inflate( R.layout.activity_song, frameLayout );

        // get global variables, all songs
        // getGlobalVariables();
        appVars = super.getAllApplicationVariables();
        songs = appVars.getSongs();
        indexes = FGMSongsUtils.getAllIndexSongs( songs );

        // get clicked item
        Bundle bundle = getIntent().getExtras();
        if ( bundle != null ) {
            position = bundle.getInt( POSITION );
        } else {
            position = 0;
        }
        setSongNumber( String.valueOf( songs.get( position ).getNumber() ) );

        setTitle( songs.get( position ).getNumber() + ". " + songs.get( position ).getTitle() );
        verses = getVersesOfSong( songs.get( position ).getNumber(), FGMSongsUtils.LOCALE[appVars.getLanguage()] );
        if ( verses != null ) {
            lvASong = (ListView) layout.findViewById( R.id.lvASong );
            ASongAdapter adapter = new ASongAdapter( this, verses );
            lvASong.setAdapter( adapter );
        } else {
            Toast.makeText( getApplicationContext(), getResources().getString( R.string.no_verse_to_display ),
                    Toast.LENGTH_LONG ).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {
        setMenuItemNumber( menu, String.valueOf( songs.get( position ).getNumber() ) );
        return super.onCreateOptionsMenu( menu );
    }

    @Override
    protected void onResume() {
        super.onResume();
        onSwipeScreenSong( getApplicationContext(), lvASong, position, indexes );
    }

    @Override
    public void onBackPressed() {
        goToActivity( activitiesManager.pop() );
    }

    @Override
    protected void onPause() {
        pushActivity( this );
        super.onPause();
    }

    /**
     * get the verses of a song acording to the number and the language
     * 
     * @param number
     *            of the song
     * @param language
     *            of the song
     * @return list of verses
     */
    public List<Verse> getVersesOfSong( int number, String language ) {

        List<Verse> versesOfSong = null;
        XMLFileManager manager = new XMLFileManager( XMLFileManager.VERSE );

        if ( number < FGMSongsUtils.FIRST_CUSTOM_NUMBER_SONG ) {
            int fileResource = FGMSongsUtils.getFileByNumber( number, language );
            if ( fileResource <= 0 ) {
                return versesOfSong;
            }

            // get verses from xml file resources
            versesOfSong = manager.parseVerse( getResources().openRawResource( fileResource ) );
        } else {
            InputStream is = null;
            String filename = FGMSongsUtils.CUSTOM + number + FGMSongsUtils.XML_EXTENSION;
            try {
                is = openFileInput( filename );

                // parse verses of the song
                versesOfSong = manager.parseVerse( is );

            } catch ( FileNotFoundException e ) {
                e.printStackTrace();
                Toast.makeText( getApplicationContext(), getResources().getString( R.string.no_file_found ),
                        Toast.LENGTH_LONG ).show();
            }

        }
        return versesOfSong;
    }

}
