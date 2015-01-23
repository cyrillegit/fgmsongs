package com.intelness.fgmsongs.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.intelness.fgmsongs.R;
import com.intelness.fgmsongs.beans.Song;

/**
 * adapter for editable songs
 * 
 * @author McCyrille
 * @version 1.0
 */
public class EditableSongAdapter extends BaseAdapter {

    private Context        context;
    private List<Song>     songs;
    private LayoutInflater inflater;

    public EditableSongAdapter( Context context, List<Song> songs ) {
        this.context = context;
        this.songs = songs;
    }

    @Override
    public int getCount() {
        return songs.size();
    }

    @Override
    public Object getItem( int position ) {
        return songs.get( position );
    }

    @Override
    public long getItemId( int position ) {
        return position;
    }

    @Override
    public View getView( int position, View convertView, ViewGroup parent ) {

        if ( inflater == null ) {
            inflater = (LayoutInflater) context.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE );
        }
        if ( convertView == null ) {
            convertView = inflater.inflate( R.layout.editable_song, parent, false );
        }

        TextView tvEditSongNumber = (TextView) convertView.findViewById( R.id.tvEditSongNumber );
        TextView tvEditSongTitle = (TextView) convertView.findViewById( R.id.tvEditSongTitle );
        TextView tvEditSongSecondLine = (TextView) convertView.findViewById( R.id.tvEditSongSecondLine );
        ImageButton ibEditSongDelete = (ImageButton) convertView.findViewById( R.id.ibEditSongDelete );

        Song song = songs.get( position );

        tvEditSongNumber.setText( String.valueOf( song.getNumber() ) );
        tvEditSongTitle.setText( song.getTitle() );
        tvEditSongSecondLine.setText( song.getSecond() );
        ibEditSongDelete.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick( View v ) {
                Toast.makeText( context, "toasted", Toast.LENGTH_LONG ).show();
            }
        } );
        return convertView;
    }
}
