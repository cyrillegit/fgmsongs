package com.intelness.fgmsongs;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.intelness.fgmsongs.adapters.ListSongsAdapter;
import com.intelness.fgmsongs.beans.Song;
import com.intelness.fgmsongs.globals.AppManager;
import com.intelness.fgmsongs.utils.FGMSongsUtils;

public class SearchSongActivity extends MainActivity {

    private static final String      TAG = "SearchSongActivity";
    private AutoCompleteTextView     actvSearchSong;
    private ImageButton              ibSearchSong;
    private ListView                 lvSearchedSongs;
    private LinearLayout             llSearchSong;
    private static ArrayList<String> titleSongs;
    private List<Song>               songs;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        View layout = getLayoutInflater().inflate( R.layout.activity_searchsong, frameLayout );
        setTitle( navDrawerItems[1] );
        // get all the global variables, particularly the list of title of songs
        getGlobalVariables();

        actvSearchSong = (AutoCompleteTextView) layout.findViewById( R.id.actvSearchSong );
        ibSearchSong = (ImageButton) layout.findViewById( R.id.ibSearchSong );
        lvSearchedSongs = (ListView) layout.findViewById( R.id.lvSearhedSongs );
        llSearchSong = (LinearLayout) layout.findViewById( R.id.llSearchSong );

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>( this, android.R.layout.simple_list_item_1,
                titleSongs );
        // actvSearchSong.setDropDownWidth( llSearchSong.getWidth() );
        actvSearchSong.setAdapter( arrayAdapter );
        // triggered when search button is clicked
        onClickSearchBtn();

        // ListSongsAdapter adapter = new ListSongsAdapter( this, songs );
        // lvSearchedSongs.setAdapter( adapter );
        // triggered when an item of the listview is clicked
        manageItemsLvSearchedSongs( songs );
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
     * triggered when the search button is clicked
     */
    public void onClickSearchBtn() {
        ibSearchSong.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick( View v ) {
                Editable searchedText = actvSearchSong.getText();
                // Toast.makeText( getApplicationContext(), searchedText,
                // Toast.LENGTH_LONG ).show();
                List<Song> newSongs = FGMSongsUtils.getSongsWithTitleContainingString( searchedText.toString(), songs );
                Log.i( TAG, "new songs : " + newSongs.get( 0 ).getTitle() );
                manageItemsLvSearchedSongs( newSongs );
            }
        } );
    }

    /**
     * get global variables
     */
    public void getGlobalVariables() {
        AppManager app = (AppManager) getApplicationContext();
        songs = app.getSongs();
        titleSongs = app.getTitleSongs();
    }
}
