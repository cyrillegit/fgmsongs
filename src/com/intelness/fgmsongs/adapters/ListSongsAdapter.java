package com.intelness.fgmsongs.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.intelness.fgmsongs.R;
import com.intelness.fgmsongs.beans.Song;

/**
 * class that manage the items of listview of listSongsActivity
 * 
 * @author McCyrille
 * @version 1.0
 */
public class ListSongsAdapter extends BaseAdapter {

    private Context        context;
    private List<Song>     songs;
    private LayoutInflater inflater;

    public ListSongsAdapter( Context context, List<Song> songs ) {
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
            convertView = inflater.inflate( R.layout.list_song, parent, false );
        }

        TextView tvNumberSong = (TextView) convertView.findViewById( R.id.tvNumberSong );
        TextView tvTitleSong = (TextView) convertView.findViewById( R.id.tvTitleSong );
        TextView tvSecondLine = (TextView) convertView.findViewById( R.id.tvSecondLine );

        Song song = songs.get( position );

        tvNumberSong.setText( String.valueOf( song.getNumber() ) );
        tvTitleSong.setText( song.getTitle() );
        tvSecondLine.setText( song.getSecond() );

        return convertView;
    }
}
