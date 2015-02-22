package com.intelness.fgmsongs;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.intelness.fgmsongs.adapters.ListEditableSongAdapter;
import com.intelness.fgmsongs.beans.ApplicationVariables;
import com.intelness.fgmsongs.beans.Song;
import com.intelness.fgmsongs.utils.FGMSongsUtils;

/**
 * activity to display editable songs
 * 
 * @author McCyrille
 * @version 1.0
 */
public class ListEditableSongsActivity extends MainActivity {

    protected static final String TAG = "ListEditableSongsActvity";
    private List<Song>            songs;
    private ArrayList<Song>       editableSongs;
    private ApplicationVariables  appVars;
    private ListView              lvEditSongs;
    private Song                  song;
    private View                  layout;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        layout = getLayoutInflater().inflate( R.layout.activity_listeditablesongs, frameLayout );
        setTitle( navDrawerItems[3] );
        // get all the songs
        appVars = super.getAllApplicationVariables();
        songs = appVars.getSongs();
        // get all the editable songs
        editableSongs = FGMSongsUtils.getEditableSongs( songs );
        lvEditSongs = (ListView) layout.findViewById( R.id.lvEditableSongs );

    }

    @Override
    protected void onResume() {
        super.onResume();

        if ( editableSongs == null || editableSongs.isEmpty() ) {
            alertDialogNoSong();
        } else {
            manageEditedSong();
        }

        onSwipeScreen( getApplicationContext(), lvEditSongs, LIST_EDITABLE_SONGS_ACTIVITY_POSITION );
        onSwipeScreen( getApplicationContext(), layout, LIST_EDITABLE_SONGS_ACTIVITY_POSITION );
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {
        setMenuItemNumber( menu, getSongNumber() );
        return super.onCreateOptionsMenu( menu );
    }

    /**
     * set alert dialog to confirm deletion of a song
     */
    private void alertDialogNoSong() {
        AlertDialog.Builder builder = new AlertDialog.Builder( this );
        builder.setTitle( R.string.songs );
        builder.setMessage( R.string.no_editable_songs )
                .setCancelable( false )
                .setNeutralButton( R.string.ok, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick( DialogInterface dialog, int which ) {
                        dialog.dismiss();
                        Intent intent = new Intent( getApplicationContext(), ListSongsActivity.class );
                        startActivity( intent );
                    }
                } );

        builder.create().show();
    }

    /**
     * manage onLongClick on editable song
     * 
     * @since 2015-01-30
     */
    private void alertDialogOnLongClick( final int position ) {

        final String[] choiceItems = this.getResources().getStringArray( R.array.edit_song_choices );
        String choiceMessage = this.getResources().getString( R.string.make_choice );

        AlertDialog.Builder builder = new AlertDialog.Builder( this );
        builder.setTitle( choiceMessage );
        builder.setItems( choiceItems, new DialogInterface.OnClickListener() {
            public void onClick( DialogInterface dialog, int item ) {
                switch ( item ) {
                // copy
                case 0:

                    break;
                // edit
                case 1:
                    forwardToEditSongActivity( position );
                    break;
                // delete
                case 2:
                    onDeleteSong( position );
                    break;

                default:
                    break;
                }
                dialog.dismiss();
            }
        } );
        AlertDialog alert = builder.create();
        alert.show();
    }

    /**
     * manage list of editable songs
     * 
     * @since 2015-01-30
     */
    private void manageEditedSong() {

        ListEditableSongAdapter adapter = new ListEditableSongAdapter( this, editableSongs );
        lvEditSongs.setAdapter( adapter );

        lvEditSongs.setOnItemClickListener( new OnItemClickListener() {

            @Override
            public void onItemClick( AdapterView<?> parent, View view, int position, long id ) {
                forwardToEditSongActivity( position );
            }
        } );

        lvEditSongs.setOnItemLongClickListener( new OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick( AdapterView<?> parent, View view, int position, long id ) {
                alertDialogOnLongClick( position );
                return false;
            }
        } );

    }

    /**
     * forward to EditSong Activity
     * 
     * @param position
     *            of the item
     */
    private void forwardToEditSongActivity( int position ) {
        Bundle bundle = new Bundle();
        bundle.putInt( POSITION, position );
        Intent intent = new Intent( getApplicationContext(), EditSongActivity.class );
        intent.putExtras( bundle );
        startActivity( intent );
        finish();
    }

    /**
     * set alert dialog to confirm deletion of a song
     */
    private void onDeleteSong( int position ) {
        song = editableSongs.get( position );
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
}
