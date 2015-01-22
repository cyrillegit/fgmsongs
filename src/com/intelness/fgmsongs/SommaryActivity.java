package com.intelness.fgmsongs;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.intelness.fgmsongs.adapters.SummaryAdapter;
import com.intelness.fgmsongs.beans.Song;
import com.intelness.fgmsongs.globals.AppManager;
import com.intelness.fgmsongs.utils.FGMSongsUtils;

public class SommaryActivity extends MainActivity {

    private List<Song>      songs;
    private ArrayList<Song> sortedSongs;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        View layout = getLayoutInflater().inflate( R.layout.activity_summary, frameLayout );
        setTitle( navDrawerItems[4] );

        // get all the global variables, particularly songs
        getGlobalVariables();

        sortedSongs = FGMSongsUtils.sortSongsAlphabetically( songs );

        ListView lvSummarySongs = (ListView) layout.findViewById( R.id.lvSummarySongs );
        SummaryAdapter adapter = new SummaryAdapter( this, sortedSongs );
        lvSummarySongs.setAdapter( adapter );

        lvSummarySongs.setOnItemClickListener( new OnItemClickListener() {

            @Override
            public void onItemClick( AdapterView<?> parent, View view, int position, long id ) {
                int realPosition = FGMSongsUtils.getIndexByNumber( sortedSongs.get( position ).getNumber(), songs );
                Bundle bundle = new Bundle();
                bundle.putInt( POSITION, realPosition );
                Intent intent = new Intent( getApplicationContext(), SongActivity.class );
                intent.putExtras( bundle );
                startActivity( intent );
                finish();
            }
        } );
    }

    /**
     * get global variables
     */
    public void getGlobalVariables() {
        AppManager app = (AppManager) getApplicationContext();
        songs = app.getSongs();
    }
}
