package com.intelness.fgmsongs;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.intelness.fgmsongs.adapters.EditSongAdapter;
import com.intelness.fgmsongs.beans.ApplicationVariables;
import com.intelness.fgmsongs.beans.Song;
import com.intelness.fgmsongs.beans.SongDAO;
import com.intelness.fgmsongs.beans.Verse;
import com.intelness.fgmsongs.managers.ApplicationManager;
import com.intelness.fgmsongs.managers.XMLFileManager;
import com.intelness.fgmsongs.utils.FGMSongsUtils;

/**
 * activity to edit a song
 * 
 * @author McCyrille
 * @version 1.0
 */
public class EditSongActivity extends MainActivity {

    private static final String  TAG = "EditSongActivity";
    private List<Song>           editableSongs;
    private List<Song>           songs;
    private Song                 song;
    private List<Verse>          verses;
    private SparseArray<Verse>   editedSongVerses;
    private int                  position;
    private Button               btnEditSongCancel;
    private Button               btnEditSongValidate;
    private Button               btnEditSongDelete;
    private ListView             lvEditSong;
    private ApplicationVariables appVars;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        View layout = getLayoutInflater().inflate( R.layout.activity_editsong, frameLayout );

        lvEditSong = (ListView) layout.findViewById( R.id.lvEditSong );
        btnEditSongCancel = (Button) layout.findViewById( R.id.btnEditSongCancel );
        btnEditSongDelete = (Button) layout.findViewById( R.id.btnEditSongDelete );
        btnEditSongValidate = (Button) layout.findViewById( R.id.btnEditSongValidate );

        editedSongVerses = new SparseArray<Verse>();

        appVars = super.getAllApplicationVariables();
        // get all songs and all editable songs
        songs = appVars.getSongs();
        editableSongs = FGMSongsUtils.getEditableSongs( songs );
        // get all the editable songs
        // getAllEditableSongs();

        // get clicked item
        Bundle bundle = getIntent().getExtras();
        if ( bundle != null && editableSongs != null ) {
            position = bundle.getInt( POSITION );
        } else {
            Toast.makeText( getApplicationContext(), getResources().getString( R.string.no_verse_to_display ),
                    Toast.LENGTH_LONG ).show();
            Intent intent = new Intent( getApplicationContext(), ListEditableSongsActivity.class );
            startActivity( intent );
        }

        // get the song to be edited
        song = editableSongs.get( position );
        // set the title on the toolbar
        setTitle( song.getNumber() + ". " + song.getTitle() );
        // get the verses of the song
        verses = getVersesOfSong( song.getNumber() );

        if ( verses != null ) {
            EditSongAdapter adapter = new EditSongAdapter( this, verses );
            lvEditSong.setAdapter( adapter );
        } else {
            Toast.makeText( getApplicationContext(), getResources().getString( R.string.no_verse_to_display ),
                    Toast.LENGTH_LONG ).show();
        }

        // button listeners
        onClickBtnEditSongCancel();
        onClickBtnEditSongDelete();
        onClickBtnEditSongValidate();
    }

    /**
     * triggred when button cancel is clicked
     */
    private void onClickBtnEditSongCancel() {
        btnEditSongCancel.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick( View v ) {
                Intent intent = new Intent( getApplicationContext(), ListEditableSongsActivity.class );
                startActivity( intent );
            }
        } );
    }

    /**
     * triggred when button delete is clicked
     */
    private void onClickBtnEditSongDelete() {
        btnEditSongDelete.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick( View v ) {
                onDeleteSong();
            }
        } );
    }

    /**
     * triggred when button validate is clicked
     */
    private void onClickBtnEditSongValidate() {
        btnEditSongValidate.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick( View v ) {
                onValidateNewSong();
            }
        } );
    }

    /**
     * set alert dialog to confirm deletion of a song
     */
    private void onDeleteSong() {
        AlertDialog.Builder builder = new AlertDialog.Builder( this );
        builder.setTitle( R.string.delete );
        builder.setMessage( R.string.confirm_deletion )
                .setCancelable( false )
                .setPositiveButton( R.string.yes, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick( DialogInterface dialog, int which ) {

                        // delete de song, and update the set of songs
                        updateSongsOnDelete( song, appVars.getLanguage() );

                        Toast.makeText( getApplicationContext(), getResources().getString( R.string.song_deleted ),
                                Toast.LENGTH_LONG ).show();

                        Intent intent = new Intent( getApplicationContext(), ListEditableSongsActivity.class );
                        startActivity( intent );
                    }
                } )
                .setNegativeButton( R.string.no, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick( DialogInterface dialog, int which ) {
                        dialog.dismiss();
                    }
                } );

        builder.create().show();
    }

    /**
     * to update the modification of the song
     * 
     * @since 2015-01-26
     */
    private void onValidateNewSong() {
        // update song
        updateSongsOnValidate();

        Toast.makeText( getApplicationContext(), getResources().getString( R.string.song_updated ),
                Toast.LENGTH_LONG ).show();

        Intent intent = new Intent( getApplicationContext(), ListEditableSongsActivity.class );
        startActivity( intent );
    }

    /**
     * get all the verses and de title of the edited song
     */
    private void getNewDataOfEditedSong() {
        // SparseArray<Verse> editedVerses = new SparseArray<Verse>();
        int j = 0;
        for ( int i = 0; i < verses.size(); i++ ) {
            View v = lvEditSong.getChildAt( i );
            TextView tvVerse = (TextView) v.findViewById( R.id.tvEditSongHead );
            EditText etVerse = (EditText) v.findViewById( R.id.etEditSongBody );

            String editedVerse = etVerse.getText().toString();
            String editedTitle = tvVerse.getText().toString();

            if ( !TextUtils.isEmpty( editedVerse ) ) {
                if ( editedTitle.equals( getResources().getString( R.string.title ) ) ) {
                    if ( !TextUtils.isEmpty( editedVerse.trim() ) ) {
                        song.setTitle( editedVerse.trim() );
                    } else {
                        Toast.makeText( getApplicationContext(), "No title", Toast.LENGTH_LONG ).show();
                    }

                } else {
                    Verse verse = new Verse();
                    verse.setStrophe( editedVerse );
                    editedSongVerses.put( j, verse );
                    j++;
                }
            }
        }

        // return editedSongVerses;
    }

    /**
     * get the verses of a song acording to the number and the language
     * 
     * @param number
     *            of the song
     * @param language
     *            of the song
     * @return
     */
    private List<Verse> getVersesOfSong( int number ) {

        List<Verse> versesOfSong = null;
        ArrayList<Verse> versesWithTitle = new ArrayList<Verse>();
        // initialize versesOfSong with title
        Verse titleVerse = new Verse();
        titleVerse.setStrophe( song.getTitle() );

        String filename = FGMSongsUtils.CUSTOM + number + FGMSongsUtils.XML_EXTENSION;

        InputStream is = null;
        try {
            is = openFileInput( filename );

            // parse verses of the song
            XMLFileManager manager = new XMLFileManager( XMLFileManager.VERSE );
            versesOfSong = manager.parseVerse( is );

        } catch ( FileNotFoundException e ) {
            e.printStackTrace();
            Toast.makeText( getApplicationContext(), getResources().getString( R.string.no_file_found ),
                    Toast.LENGTH_LONG ).show();
        }

        if ( versesOfSong != null ) {
            versesWithTitle.add( titleVerse );
            for ( Verse verse : versesOfSong ) {
                versesWithTitle.add( verse );
            }
        }

        return versesWithTitle;
    }

    /**
     * delete the song, both in DB and internal storage, and update the songs
     * 
     * @param song
     *            to be deleted
     * @since 2015-01-26
     * @deprecated
     */
    private void updateSongsOnDelete( Song song ) {
        SongDAO sDao = new SongDAO( getApplicationContext(), appVars.getLanguage() );
        ApplicationManager am = new ApplicationManager( getApplicationContext() );

        String filename = FGMSongsUtils.CUSTOM + song.getNumber() + FGMSongsUtils.XML_EXTENSION;
        deleteFile( filename );

        sDao.deleteSong( song );
        List<Song> newSongs = sDao.getAllSongs();
        ArrayList<String> newTitleSongs = FGMSongsUtils.getAllTitleSongs( newSongs );

        am.setSongs( newSongs );
        am.setTitleSongs( newTitleSongs );
    }

    /**
     * update song, both in DB and internal storage
     * 
     * @since 2015-01-26
     */
    private void updateSongsOnValidate() {
        SongDAO sDao = new SongDAO( getApplicationContext(), appVars.getLanguage() );
        ApplicationManager am = new ApplicationManager( getApplicationContext() );

        // get the data of the edited song
        // SparseArray<Verse> editedSongVerses;
        getNewDataOfEditedSong();
        Log.i( "update : ", editedSongVerses.toString() );
        if ( editedSongVerses.size() <= 0 ) {
            Toast.makeText( getApplicationContext(), "No Verses", Toast.LENGTH_LONG ).show();
        } else {

            // write data in xml format
            XMLFileManager xml = new XMLFileManager( getApplicationContext() );
            String xmlString = xml.format( editedSongVerses );
            // store xml file in internal storage
            int currentNumber = song.getNumber();
            String filename = FGMSongsUtils.CUSTOM + currentNumber + FGMSongsUtils.XML_EXTENSION;
            xml.store( filename, xmlString );

            sDao.updateSong( song );
            List<Song> newSongs = sDao.getAllSongs();
            ArrayList<String> newTitleSongs = FGMSongsUtils.getAllTitleSongs( newSongs );

            am.setSongs( newSongs );
            am.setTitleSongs( newTitleSongs );
        }
    }

}
