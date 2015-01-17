package com.intelness.fgmsongs;

import java.util.List;

import android.os.Bundle;
import android.view.View;

import com.intelness.fgmsongs.beans.Song;
import com.intelness.fgmsongs.globals.AppManager;

public class SongActivity extends MainActivity {

    private List<Song> songs;
    private int        position;

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
    }

    /**
     * get global variables
     */
    public void getGlobalVariables() {
        AppManager app = (AppManager) getApplicationContext();
        songs = app.getSongs();
    }

}
