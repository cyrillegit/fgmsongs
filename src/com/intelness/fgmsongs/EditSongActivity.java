package com.intelness.fgmsongs;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.intelness.fgmsongs.adapters.EditableSongAdapter;
import com.intelness.fgmsongs.beans.Song;
import com.intelness.fgmsongs.globals.AppManager;
import com.intelness.fgmsongs.utils.FGMSongsUtils;

public class EditSongActivity extends MainActivity {

    protected static final String TAG = "EditSongActvity";
    private List<Song>            songs;
    private ArrayList<Song>       editableSongs;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        View layout = getLayoutInflater().inflate( R.layout.activity_editsong, frameLayout );
        setTitle( navDrawerItems[3] );
        // get all the songs
        getAllSongs();
        // get all the editable songs
        editableSongs = FGMSongsUtils.getEditableSongs( songs );

        for ( Song s : songs ) {
            Log.i( TAG, "songs   " + s.getNumber() + " : " + s.getTitle() );
        }

        for ( Song ss : editableSongs ) {
            Log.i( TAG, "editsongs   " + ss.getNumber() + " : " + ss.getTitle() );
        }

        ListView lvEditSongs = (ListView) layout.findViewById( R.id.lvEditSongs );
        EditableSongAdapter adapter = new EditableSongAdapter( this, editableSongs );
        lvEditSongs.setAdapter( adapter );

        lvEditSongs.setOnItemClickListener( new OnItemClickListener() {

            @Override
            public void onItemClick( AdapterView<?> parent, View view, int position, long id ) {
                Log.i( TAG, "position : " + position );

            }
        } );
    }

    /**
     * get global variables
     */
    private void getAllSongs() {
        AppManager app = (AppManager) getApplicationContext();
        songs = app.getSongs();
    }
}
