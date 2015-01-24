package com.intelness.fgmsongs;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.intelness.fgmsongs.adapters.ListEditableSongAdapter;
import com.intelness.fgmsongs.beans.Song;
import com.intelness.fgmsongs.globals.AppManager;
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

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        View layout = getLayoutInflater().inflate( R.layout.activity_listeditablesongs, frameLayout );
        setTitle( navDrawerItems[3] );
        // get all the songs
        getAllSongs();
        // get all the editable songs
        editableSongs = FGMSongsUtils.getEditableSongs( songs );

        if ( editableSongs == null ) {
            alertDialogNoSong();
        } else {

            ListView lvEditSongs = (ListView) layout.findViewById( R.id.lvEditableSongs );
            ListEditableSongAdapter adapter = new ListEditableSongAdapter( this, editableSongs );
            lvEditSongs.setAdapter( adapter );

            lvEditSongs.setOnItemClickListener( new OnItemClickListener() {

                @Override
                public void onItemClick( AdapterView<?> parent, View view, int position, long id ) {

                    Bundle bundle = new Bundle();
                    bundle.putInt( POSITION, position );
                    Intent intent = new Intent( getApplicationContext(), EditSongActivity.class );
                    intent.putExtras( bundle );
                    startActivity( intent );
                    finish();
                }
            } );

            lvEditSongs.setOnItemLongClickListener( new OnItemLongClickListener() {

                @Override
                public boolean onItemLongClick( AdapterView<?> parent, View view, int position, long id ) {
                    Toast.makeText( getBaseContext(), "long click", Toast.LENGTH_LONG ).show();
                    return false;
                }
            } );
        }
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
     * get global variables
     */
    private void getAllSongs() {
        AppManager app = (AppManager) getApplicationContext();
        songs = app.getSongs();
    }
}
