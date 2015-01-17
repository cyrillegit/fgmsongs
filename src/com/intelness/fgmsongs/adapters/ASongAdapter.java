package com.intelness.fgmsongs.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.intelness.fgmsongs.R;

/**
 * manage the display of a song
 * 
 * @author McCyrille
 * @version 1.0
 */
public class ASongAdapter extends BaseAdapter {

    private Context        context;
    private List<String>   song;
    private LayoutInflater inflater;

    public ASongAdapter( Context context, List<String> song ) {
        this.context = context;
        this.song = song;
    }

    @Override
    public int getCount() {
        return song.size();
    }

    @Override
    public Object getItem( int position ) {
        return song.get( position );
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
            convertView = inflater.inflate( R.layout.a_song, parent, false );
        }

        TextView tvASong = (TextView) convertView.findViewById( R.id.tvAsong );

        String verse = song.get( position );

        tvASong.setText( verse );
        return convertView;
    }
}
