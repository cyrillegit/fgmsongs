package com.intelness.fgmsongs;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.intelness.fgmsongs.beans.ApplicationVariables;
import com.intelness.fgmsongs.beans.Song;
import com.intelness.fgmsongs.beans.SongDAO;
import com.intelness.fgmsongs.beans.Verse;
import com.intelness.fgmsongs.managers.ApplicationManager;
import com.intelness.fgmsongs.managers.XMLFileManager;
import com.intelness.fgmsongs.utils.FGMSongsUtils;

/**
 * activity that manage the adding of a new song
 * 
 * @author McCyrille
 * @version 1.0
 */
public class AddSongActivity extends MainActivity {
    private static final String  TAG          = "AddSongActivity";
    private Button               btnAddSongCancel;
    private Button               btnAddSongValidate;
    private Button               btnAddNewVerse;
    private EditText             etAddSongTitle;
    private ViewGroup            vgAddSong;
    private int                  numberVerses = 0;
    private Song                 song;
    private SparseArray<Verse>   songVerses;
    private final static String  VERSE        = "Verse";
    private int                  lastNumber;
    private ApplicationVariables appVars;
    private View                 layout;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        layout = getLayoutInflater().inflate( R.layout.activity_addsong, frameLayout );
        setTitle( navDrawerItems[2] );

        // get all applications variables
        appVars = super.getAllApplicationVariables();

        btnAddSongCancel = (Button) layout.findViewById(
                R.id.btnAddSongCancel );
        btnAddSongValidate = (Button) layout.findViewById(
                R.id.btnAddSongValidate );
        btnAddNewVerse = (Button) layout.findViewById( R.id.btnAddNewVerse );
        etAddSongTitle = (EditText) layout.findViewById( R.id.etAddSongTitle );
        vgAddSong = (ViewGroup) findViewById( R.id.llNewVerses );

        song = new Song();
        songVerses = new SparseArray<Verse>();

        onClickBtnAddNewVerse();
        onClickBtnAddSongValidate();
        onClickBtnAddSongCancel();
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {
        setMenuItemNumber( menu, getSongNumber() );
        return super.onCreateOptionsMenu( menu );
    }

    @Override
    protected void onResume() {
        super.onResume();
        onSwipeScreen( getApplicationContext(), layout, ADD_SONG_ACTIVITY_POSITION );
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    /**
     * add a new layout to write a new verse for the song
     */
    public void addNewVerseLayout() {

        Log.i( TAG, "nb : " + numberVerses );
        if ( numberVerses > 0 ) {
            EditText etAddNewSong = (EditText) vgAddSong.findViewWithTag( VERSE + "" + ( numberVerses - 1 ) );
            String textVerse = etAddNewSong.getText().toString();

            if ( TextUtils.isEmpty( textVerse ) ) {

                Toast.makeText( getApplicationContext(), getResources().getString( R.string.no_empty_verse ),
                        Toast.LENGTH_LONG ).show();
            } else {
                // add new view
                prepareNewVerseLayout( numberVerses, vgAddSong );
                numberVerses++;
            }
        } else {
            String title = etAddSongTitle.getText().toString();
            if ( TextUtils.isEmpty( title ) ) {
                Toast.makeText( getApplicationContext(), getResources().getString( R.string.no_empty_title ),
                        Toast.LENGTH_LONG ).show();
            } else {
                prepareNewVerseLayout( numberVerses, vgAddSong );
                numberVerses++;
            }
        }
    }

    /**
     * prepare a new view to add a verse
     * 
     * @param index
     *            of the verse
     * @param viewGroup
     *            parent view
     */
    public void prepareNewVerseLayout( int index, ViewGroup viewGroup ) {

        LayoutInflater inflater = (LayoutInflater) getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View verseLayout = inflater.inflate( R.layout.verse_layout, null );

        TextView tvVerse = (TextView) verseLayout.findViewById( R.id.tvAddVerse );
        EditText etVerse = (EditText) verseLayout.findViewById( R.id.etAddNewVerse );

        etVerse.setTag( VERSE + index );
        tvVerse.setText( VERSE + " " + ( index + 1 ) );

        viewGroup.addView( verseLayout, 0 );
    }

    /**
     * triggered when button add new verse is clicked
     */
    public void onClickBtnAddNewVerse() {
        btnAddNewVerse.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick( View v ) {
                addNewVerseLayout();
            }
        } );
    }

    /**
     * triggered when button cancel song is clicked
     */
    public void onClickBtnAddSongCancel() {
        btnAddSongCancel.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick( View v ) {
                numberVerses = 0;
                Intent i = new Intent( getApplicationContext(), AddSongActivity.class );
                startActivity( i );
                finish();
            }
        } );
    }

    /**
     * triggered when btn validate is clicked
     */
    public void onClickBtnAddSongValidate() {
        btnAddSongValidate.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick( View v ) {

                // get the title and verses of the song
                getDataOfSong();
                // write data in xml format
                XMLFileManager xml = new XMLFileManager( getApplicationContext() );
                String xmlString = xml.format( songVerses );
                // store xml file in internal storage
                String filename = FGMSongsUtils.CUSTOM + lastNumber + FGMSongsUtils.XML_EXTENSION;
                xml.store( filename, xmlString );
                // store the song un DB
                // storeSongInDB( song );
                Log.i( TAG, xmlString );
                // update songs
                updateSongs( song );
                // setNewSongInSetOfSongs( song );
                // setLastCustomNumberSong( lastNumber + 1 );

                numberVerses = 0;
                Intent i = new Intent( getApplicationContext(), ListEditableSongsActivity.class );
                startActivity( i );
                finish();
            }
        } );
    }

    /**
     * get all the data corresponding to the song
     */
    public void getDataOfSong() {

        String title = etAddSongTitle.getText().toString();
        String datetime = FGMSongsUtils.getCurrentDateTime();
        lastNumber = appVars.getLastCustomNumberSong();
        // lastNumber = getLastCustomNumberSong();

        song.setNumber( lastNumber );
        song.setTitle( title );
        song.setSecond( title );
        song.setCreatedDate( datetime );

        for ( int i = 0; i < numberVerses; i++ ) {
            EditText etAddNewSong = (EditText) vgAddSong.findViewWithTag( VERSE + i );
            String textVerse = etAddNewSong.getText().toString();

            if ( !TextUtils.isEmpty( textVerse ) ) {

                Verse verse = new Verse();
                verse.setStrophe( textVerse );
                songVerses.put( i, verse );
            }
        }
    }

    /**
     * update songs and last number of songs
     * 
     * @param number
     *            last number of custom song
     * @param song
     *            to add
     */
    private void updateSongs( Song song ) {
        ApplicationManager am = new ApplicationManager( this );
        SongDAO enDao = new SongDAO( this, 0 );
        SongDAO frDao = new SongDAO( this, 1 );

        enDao.addSong( song );
        frDao.addSong( song );

        List<Song> oldSongs = appVars.getSongs();
        oldSongs.add( song );

        am.setLastCustomNumberSong( appVars.getLastCustomNumberSong() + 1 );
        am.setSongs( oldSongs );
    }
}
