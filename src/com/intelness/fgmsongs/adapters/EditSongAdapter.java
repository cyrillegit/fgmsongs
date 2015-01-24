package com.intelness.fgmsongs.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.intelness.fgmsongs.R;
import com.intelness.fgmsongs.beans.Verse;
import com.intelness.fgmsongs.utils.FGMSongsUtils;

/**
 * adapter to edit song
 * 
 * @author McCyrille
 * @version 1.0
 */
public class EditSongAdapter extends BaseAdapter {

    private Context        context;
    private List<Verse>    verses;
    private LayoutInflater inflater;

    public EditSongAdapter( Context context, List<Verse> verses ) {
        this.context = context;
        this.verses = verses;
    }

    @Override
    public int getCount() {
        return verses.size();
    }

    @Override
    public Object getItem( int position ) {
        return verses.get( position );
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
            convertView = inflater.inflate( R.layout.edit_song, parent, false );
        }

        TextView tvEditSongHead = (TextView) convertView.findViewById( R.id.tvEditSongHead );
        EditText etEditSongBody = (EditText) convertView.findViewById( R.id.etEditSongBody );

        Verse verse = verses.get( position );

        // get a strophe , and change it so that it has back line
        String newVerse = FGMSongsUtils.StringsWithNewLine( verse.getStrophe() );

        if ( position == 0 ) {
            tvEditSongHead.setText( context.getText( R.string.title ) );
        } else {
            tvEditSongHead.setText( context.getText( R.string.verse ) + " " + position );
        }
        etEditSongBody.setText( newVerse );
        return convertView;
    }

}
