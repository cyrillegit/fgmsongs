package com.intelness.fgmsongs;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.intelness.fgmsongs.adapters.ListSongsAdapter;
import com.intelness.fgmsongs.beans.ApplicationVariables;
import com.intelness.fgmsongs.beans.Song;
import com.intelness.fgmsongs.globals.AppManager;

/**
 * class to display the list of songs
 * 
 * @author McCyrille
 * @version 1.0
 */
public class ListSongsActivity extends MainActivity {

    private List<Song>           songs;
    private ApplicationVariables appVars;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        View layout = getLayoutInflater().inflate( R.layout.activity_listsongs, frameLayout );
        setTitle( navDrawerItems[0] );

        // get all the global variables, particularly songs
        // getGlobalVariables();
        appVars = super.getAllApplicationVariables();
        songs = appVars.getSongs();

        ListView lvListSongs = (ListView) layout.findViewById( R.id.lvListSongs );
        ListSongsAdapter adapter = new ListSongsAdapter( this, songs );
        lvListSongs.setAdapter( adapter );

        lvListSongs.setOnItemClickListener( new OnItemClickListener() {

            @Override
            public void onItemClick( AdapterView<?> parent, View view, int position, long id ) {

                Bundle bundle = new Bundle();
                bundle.putInt( POSITION, position );
                Intent intent = new Intent( getApplicationContext(), SongActivity.class );
                intent.putExtras( bundle );
                startActivity( intent );
                finish();
            }
        } );

    }

    /**
     * get global variables
     * 
     * @deprecated
     */
    public void getGlobalVariables() {
        AppManager app = (AppManager) getApplicationContext();
        songs = app.getSongs();
    }
}
