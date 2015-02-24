package com.intelness.fgmsongs;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.intelness.fgmsongs.adapters.SummaryAdapter;
import com.intelness.fgmsongs.beans.ApplicationVariables;
import com.intelness.fgmsongs.beans.Song;
import com.intelness.fgmsongs.utils.FGMSongsUtils;

public class SummaryActivity extends MainActivity {

    private static final String  TAG = "SummaryActivity";
    private List<Song>           songs;
    private ArrayList<Song>      sortedSongs;
    private ApplicationVariables appVars;
    private View                 layout;
    private ListView             lvSummarySongs;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        layout = getLayoutInflater().inflate( R.layout.activity_summary, frameLayout );
        setTitle( navDrawerItems[4] );

        // get all the global variables, particularly songs
        // getGlobalVariables();
        appVars = super.getAllApplicationVariables();
        songs = appVars.getSongs();

        sortedSongs = FGMSongsUtils.sortSongsAlphabetically( songs );

        lvSummarySongs = (ListView) layout.findViewById( R.id.lvSummarySongs );
        SummaryAdapter adapter = new SummaryAdapter( this, sortedSongs );
        lvSummarySongs.setAdapter( adapter );

        lvSummarySongs.setOnItemClickListener( new OnItemClickListener() {

            @Override
            public void onItemClick( AdapterView<?> parent, View view, int position, long id ) {
                int realPosition = FGMSongsUtils.getIndexByNumber( sortedSongs.get( position ).getNumber(), songs );
                goToSongActivity( realPosition );
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
        onSwipeScreen( getApplicationContext(), lvSummarySongs, SUMMARY_ACTIVITY_POSITION );
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

}
