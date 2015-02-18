package com.intelness.fgmsongs;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.intelness.fgmsongs.adapters.ListSongsAdapter;
import com.intelness.fgmsongs.beans.ApplicationVariables;
import com.intelness.fgmsongs.beans.Song;
import com.intelness.fgmsongs.utils.FGMSongsUtils;

public class SearchSongActivity extends MainActivity {

    private static final String      TAG = "SearchSongActivity";
    private AutoCompleteTextView     actvSearchSong;
    private ImageButton              ibSearchSong;
    private ListView                 lvSearchedSongs;
    private static ArrayList<String> titleSongs;
    private List<Song>               songs;
    private ApplicationVariables     appVars;
    private View                     layout;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        layout = getLayoutInflater().inflate( R.layout.activity_searchsong, frameLayout );
        setTitle( navDrawerItems[1] );
        // get all the global variables, particularly the list of title of songs
        // getGlobalVariables();
        appVars = super.getAllApplicationVariables();
        songs = appVars.getSongs();
        titleSongs = appVars.getTitleSongs();

        actvSearchSong = (AutoCompleteTextView) layout.findViewById( R.id.actvSearchSong );
        ibSearchSong = (ImageButton) layout.findViewById( R.id.ibSearchSong );
        lvSearchedSongs = (ListView) layout.findViewById( R.id.lvSearhedSongs );

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>( this, android.R.layout.simple_list_item_1,
                titleSongs );
        // actvSearchSong.setDropDownWidth( llSearchSong.getWidth() );
        actvSearchSong.setAdapter( arrayAdapter );
        actvSearchSong.setOnClickListener( new OnClickListener() {

            @Override
            public void onClick( View v ) {
                manageSearch();
            }
        } );
        // triggered when search button is clicked
        onClickSearchBtn();

        // ListSongsAdapter adapter = new ListSongsAdapter( this, songs );
        // lvSearchedSongs.setAdapter( adapter );
        // triggered when an item of the listview is clicked
        manageItemsLvSearchedSongs( songs );
    }

    @Override
    protected void onResume() {
        super.onResume();
        onSwipeScreen( getApplicationContext(), lvSearchedSongs, SEARCH_SONG_ACTIVITY_POSITION );
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    /**
     * triggered when an item of the listveiw is clicked
     */
    public void manageItemsLvSearchedSongs( final List<Song> songsToDisplay ) {
        ListSongsAdapter adapter = new ListSongsAdapter( this, songsToDisplay );
        lvSearchedSongs.setAdapter( adapter );
        lvSearchedSongs.setOnItemClickListener( new OnItemClickListener() {

            @Override
            public void onItemClick( AdapterView<?> parent, View view, int position, long id ) {
                int realPosition = FGMSongsUtils.getIndexByNumber( songsToDisplay.get( position ).getNumber(), songs );
                goToSongActivity( realPosition );
                // Bundle bundle = new Bundle();
                // bundle.putInt( POSITION, realPosition );
                // Intent intent = new Intent( getApplicationContext(),
                // SongActivity.class );
                // intent.putExtras( bundle );
                // startActivity( intent );
                // finish();
            }
        } );
    }

    /**
     * triggered when the search button is clicked
     */
    public void onClickSearchBtn() {
        ibSearchSong.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick( View v ) {
                manageSearch();
            }
        } );
    }

    /**
     * manage the search
     */
    private void manageSearch() {
        Editable searchedText = actvSearchSong.getText();
        List<Song> newSongs = FGMSongsUtils.getSongsWithTitleContainingString( searchedText.toString(), songs );
        // Log.i( TAG, "new songs : " + newSongs.get( 0 ).getTitle() );
        manageItemsLvSearchedSongs( newSongs );
    }
}
