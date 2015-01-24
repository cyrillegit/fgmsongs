package com.intelness.fgmsongs;

import java.util.List;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.intelness.fgmsongs.adapters.ASongAdapter;
import com.intelness.fgmsongs.beans.Song;
import com.intelness.fgmsongs.beans.Verse;
import com.intelness.fgmsongs.globals.AppManager;
import com.intelness.fgmsongs.utils.FGMSongsUtils;
import com.intelness.fgmsongs.utils.XMLParser;

/**
 * 
 * @author McCyrille
 * @version 1.0
 */
public class SongActivity extends MainActivity {

    private static final String TAG = "SongActivity";
    private List<Song>          songs;
    private List<Verse>         verses;
    private int                 position;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        View layout = getLayoutInflater().inflate( R.layout.activity_song, frameLayout );

        // get global variables
        getGlobalVariables();

        // get clicked item
        Bundle bundle = getIntent().getExtras();
        if ( bundle != null ) {
            position = bundle.getInt( POSITION );
        } else {
            position = 0;
        }
        setTitle( songs.get( position ).getNumber() + ". " + songs.get( position ).getTitle() );
        verses = getVersesOfSong( songs.get( position ).getNumber(), FGMSongsUtils.FR );
        if ( verses != null ) {
            Log.i( TAG, verses.get( 0 ).getStrophe() );

            ListView lvASong = (ListView) layout.findViewById( R.id.lvASong );
            ASongAdapter adapter = new ASongAdapter( this, verses );
            lvASong.setAdapter( adapter );
        } else {
            Toast.makeText( getApplicationContext(), getResources().getString( R.string.no_verse_to_display ),
                    Toast.LENGTH_LONG ).show();
        }
    }

    /**
     * get the verses of a song acording to the number and the language
     * 
     * @param number
     *            of the song
     * @param language
     *            of the song
     * @return
     */
    public List<Verse> getVersesOfSong( int number, String language ) {

        List<Verse> versesOfSong = null;
        int fileResource = FGMSongsUtils.getFileByNumber( number, language );
        if ( fileResource <= 0 ) {
            return versesOfSong;
        }
        XMLParser parser = new XMLParser( XMLParser.VERSE );
        versesOfSong = parser.parseVerse( ( getResources().openRawResource( fileResource ) ) );
        return versesOfSong;
    }

    /**
     * get global variables
     */
    public void getGlobalVariables() {
        AppManager app = (AppManager) getApplicationContext();
        songs = app.getSongs();
    }

}
