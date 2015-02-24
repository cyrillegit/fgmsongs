package com.intelness.fgmsongs;

import java.util.List;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.intelness.fgmsongs.adapters.ListSongsAdapter;
import com.intelness.fgmsongs.beans.ApplicationVariables;
import com.intelness.fgmsongs.beans.Song;

/**
 * class to display the list of songs
 * 
 * @author McCyrille
 * @version 1.0
 */
public class ListSongsActivity extends MainActivity {

    private static final String  TAG = "ListSongsActivity";
    private List<Song>           songs;
    private ApplicationVariables appVars;
    private View                 layout;
    private ListView             lvListSongs;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        layout = getLayoutInflater().inflate( R.layout.activity_listsongs, frameLayout );
        setTitle( navDrawerItems[0] );

        // get all the global variables, particularly songs
        appVars = super.getAllApplicationVariables();
        songs = appVars.getSongs();

        lvListSongs = (ListView) layout.findViewById( R.id.lvListSongs );
        ListSongsAdapter adapter = new ListSongsAdapter( this, songs );
        lvListSongs.setAdapter( adapter );

        lvListSongs.setOnItemClickListener( new OnItemClickListener() {

            @Override
            public void onItemClick( AdapterView<?> parent, View view, int position, long id ) {
                goToSongActivity( position );
            }
        } );

    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {
        setMenuItemNumber( menu, getSongNumber() );
        return super.onCreateOptionsMenu( menu );
    }

    @Override
    protected void onResume() {
        super.onResume();
        onSwipeScreen( getApplicationContext(), lvListSongs, LIST_SONGS_ACTIVITY_POSITION );
    }

    @Override
    protected void onPause() {
        pushActivity( this );
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        goToActivity( activitiesManager.pop() );
    }

}
