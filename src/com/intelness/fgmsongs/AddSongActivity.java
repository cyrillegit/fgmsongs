package com.intelness.fgmsongs;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.intelness.fgmsongs.beans.Song;
import com.intelness.fgmsongs.beans.SongDAO;
import com.intelness.fgmsongs.beans.Verse;
import com.intelness.fgmsongs.globals.AppManager;
import com.intelness.fgmsongs.utils.FGMSongsUtils;
import com.intelness.fgmsongs.utils.WriteXMLFile;

/**
 * activity that manage the adding of a new song
 * 
 * @author McCyrille
 * @version 1.0
 */
public class AddSongActivity extends MainActivity {
    private static final String TAG          = "AddSongActivity";
    private Button              btnAddSongCancel;
    private Button              btnAddSongValidate;
    private Button              btnAddNewVerse;
    private EditText            etAddSongTitle;
    private ViewGroup           vgAddSong;
    private int                 numberVerses = 0;
    private Song                song;
    private SparseArray<Verse>  songVerses;
    private final static String VERSE        = "Verse";
    private int                 lastNumber;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        View layout = getLayoutInflater().inflate( R.layout.activity_addsong, frameLayout );
        setTitle( navDrawerItems[2] );

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
            prepareNewVerseLayout( numberVerses, vgAddSong );
            numberVerses++;
        }

        // }
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
                WriteXMLFile xml = new WriteXMLFile( songVerses );
                String xmlString = xml.write();
                // store xml file in internal storage
                String filename = FGMSongsUtils.CUSTOM + lastNumber + ".xml";
                storeXMLFile( filename, xmlString );
                Log.i( TAG, xmlString );

                // setLastCustomNumberSong( lastNumber + 1 );

                numberVerses = 0;
                Intent i = new Intent( getApplicationContext(), EditSongActivity.class );
                startActivity( i );
                finish();

            }
        } );
    }

    public void getDataOfSong() {

        String title = etAddSongTitle.getText().toString();
        String datetime = FGMSongsUtils.getCurrentDateTime();
        lastNumber = getLastCustomNumberSong();

        song.setNumber( lastNumber );
        song.setTitle( title );
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
     * store xml file in internal storage
     * 
     * @param filename
     *            name of the file
     * @param xmlString
     *            content of the file
     */
    public void storeXMLFile( String filename, String xmlString ) {
        try {
            FileOutputStream fileout = openFileOutput( filename, MODE_PRIVATE );
            OutputStreamWriter outputWriter = new OutputStreamWriter( fileout );
            outputWriter.write( xmlString );
            outputWriter.close();

        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    /**
     * store the custom song in db
     * 
     * @param song
     *            to be stored
     */
    public void storeSongInDB( Song song ) {
        SongDAO sDao = new SongDAO( this );
        sDao.addSong( song );
    }

    /**
     * get the last custom song number
     * 
     * @return number of the song
     */
    public int getLastCustomNumberSong() {
        final AppManager app = (AppManager) getApplicationContext();
        return app.getLastCustomNumberSong();
    }

    /**
     * store the current custom song number
     * 
     * @param number
     *            current number
     */
    public void setLastCustomNumberSong( int number ) {
        final AppManager app = (AppManager) getApplicationContext();
        app.setLastCustomNumberSong( number );
    }
}
