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
 * 
 * @author McCyrille
 * @version 1.0
 * @since 22.01.2015
 */
public class SummaryAdapter extends BaseAdapter {

    private Context        context;
    private List<Song>     songs;
    private LayoutInflater inflater;
    private static int     ZERO = 0;

    public SummaryAdapter( Context context, List<Song> songs ) {
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
            convertView = inflater.inflate( R.layout.alphabetic_list_song, parent, false );
        }

        TextView tvAlphaNumberSong = (TextView) convertView.findViewById( R.id.tvAlphaNumber );
        TextView tvAlphaTitleSong = (TextView) convertView.findViewById( R.id.tvAlphaTitle );
        TextView tvAlphaSecondLine = (TextView) convertView.findViewById( R.id.tvAlphaSecondLine );
        TextView tvAlpha = (TextView) convertView.findViewById( R.id.tvAlpha );

        Song song = songs.get( position );

        if ( song.getNumber() == ZERO ) {
            tvAlphaNumberSong.setVisibility( View.INVISIBLE );
            tvAlphaSecondLine.setVisibility( View.INVISIBLE );
            tvAlphaTitleSong.setVisibility( View.INVISIBLE );
            tvAlpha.setVisibility( View.VISIBLE );
            tvAlpha.setText( song.getTitle() );
            convertView.setEnabled( false );
            convertView.setOnClickListener( null );
        } else {
            tvAlphaNumberSong.setVisibility( View.VISIBLE );
            tvAlphaSecondLine.setVisibility( View.VISIBLE );
            tvAlphaTitleSong.setVisibility( View.VISIBLE );
            tvAlpha.setVisibility( View.INVISIBLE );
            tvAlphaNumberSong.setText( String.valueOf( song.getNumber() ) );
            tvAlphaTitleSong.setText( song.getTitle() );
            tvAlphaSecondLine.setText( song.getSecond() );
        }

        return convertView;
    }
}
