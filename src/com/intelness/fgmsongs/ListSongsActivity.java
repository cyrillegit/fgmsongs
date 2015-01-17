package com.intelness.fgmsongs;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.intelness.fgmsongs.adapters.ListSongsAdapter;
import com.intelness.fgmsongs.beans.Song;
import com.intelness.fgmsongs.globals.AppManager;

public class ListSongsActivity extends MainActivity {

    private static final String TAG = "ListSongsActivity";
    private Button              btnLoadDB;
    private Button              btnDisplayDB;
    private List<Song>          songs;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        View layout = getLayoutInflater().inflate( R.layout.activity_listsongs, frameLayout );
        // loadDB();
        setTitle( navDrawerItems[0] );
        // get all the sngs in db and store in in List<Song> songs
        // songs = getAllSongs();
        // get all the global variables, particulaarly songs
        getGlobalVariables();

        Log.i( TAG, "song 0 : " + songs.get( 0 ).getTitle() );

        ListView lvListSongs = (ListView) layout.findViewById( R.id.lvListSongs );

        Log.i( TAG, "listview tostring : " + lvListSongs.getId() );

        ListSongsAdapter adapter = new ListSongsAdapter( this, songs );

        lvListSongs.setAdapter( adapter );

        lvListSongs.setOnItemClickListener( new OnItemClickListener() {

            @Override
            public void onItemClick( AdapterView<?> parent, View view, int position, long id ) {
                Toast.makeText( getApplicationContext(), "position : " + position + "   long : " + id,
                        Toast.LENGTH_LONG ).show();
                Log.i( TAG, "position : " + songs.get( position ).getTitle() + "   long : " + id );

                Bundle bundle = new Bundle();
                bundle.putInt( POSITION, position );
                Intent intent = new Intent( getApplicationContext(), SongActivity.class );
                intent.putExtras( bundle );
                startActivity( intent );
                finish();
            }
        } );

    }

    public void onClickBtnLoad() {
        btnLoadDB.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick( View v ) {
                // loadDB();
            }
        } );
    }

    public void onClickBtnDisplay() {
        btnDisplayDB.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick( View v ) {
                // getAllSongs();
            }
        } );
    }

    public void getGlobalVariables() {
        AppManager app = (AppManager) getApplicationContext();
        songs = app.getSongs();
    }

    // public void loadDB() {
    //
    // SongDAO sDao = new SongDAO( this );
    // int rows = sDao.getNumberOfSongs();
    // Log.i( TAG, "rows : " + rows );
    //
    // List<Song> songs;
    //
    // XMLParser parser = new XMLParser();
    // songs = parser.parse( getResources().openRawResource( R.raw.fr_songs ) );
    //
    // for ( int i = 0; i < songs.size(); i++ ) {
    // int number = songs.get( i ).getNumber();
    // // if db is empty, store every devinettes that is in xml file in db
    // if ( rows <= 0 ) {
    // sDao.addSong( songs.get( i ) );
    // Log.i( TAG, "if : " + number );
    // // else, check to not store two same devinette
    // } else if ( sDao.getSongByNumber( number ).getNumber() != number ) {
    // Log.i( TAG, "else if : " + number );
    // sDao.addSong( songs.get( i ) );
    // }
    // }
    // }
    //
    // public List<Song> getAllSongs() {
    //
    // SongDAO sDao = new SongDAO( this );
    // List<Song> allSongs = sDao.getAllSongs();
    // for ( int i = 0; i < allSongs.size(); i++ ) {
    // Log.i( TAG, "song : " + allSongs.get( i ).getTitle() );
    // }
    // return allSongs;
    // }
}
