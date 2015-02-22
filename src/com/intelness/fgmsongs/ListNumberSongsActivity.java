package com.intelness.fgmsongs;

import java.util.List;

import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridLayout.LayoutParams;

import com.intelness.fgmsongs.beans.ApplicationVariables;
import com.intelness.fgmsongs.beans.Song;
import com.intelness.fgmsongs.utils.FGMSongsUtils;

/**
 * list of number of songs
 * 
 * @author McCyrille
 * @since 2015-02-22
 */
public class ListNumberSongsActivity extends MainActivity implements OnClickListener {

    private GridLayout           glNumberSongs;
    private ApplicationVariables appVars;
    private List<Song>           songs;
    private View                 layout;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        layout = getLayoutInflater().inflate( R.layout.activity_listnumbersongs, frameLayout );
        setTitle( navDrawerItems[0] );

        // get all the global variables, particularly songs
        appVars = super.getAllApplicationVariables();
        songs = appVars.getSongs();

        glNumberSongs = (GridLayout) findViewById( R.id.glNumberSongs );
        glNumberSongs.removeAllViews();

        setButtonsInGridLayout();

    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {
        setMenuItemNumber( menu, getSongNumber() );
        return super.onCreateOptionsMenu( menu );
    }

    @Override
    public void onClick( View v ) {

        super.setSongNumber( (String) v.getTag() );

        int index = FGMSongsUtils.getIndexByNumber( Integer.valueOf( (String) v.getTag() ), songs );
        goToSongActivity( index );

    }

    /**
     * 
     */
    private void setButtonsInGridLayout() {

        int total = songs.size();
        int column = 5;
        int row = total / column;
        glNumberSongs.setColumnCount( column );
        glNumberSongs.setRowCount( row + 1 );
        for ( int i = 0, c = 0, r = 0; i < total; i++, c++ )
        {
            if ( c == column )
            {
                c = 0;
                r++;
            }

            Button btn = new Button( this );
            btn.setTag( String.valueOf( songs.get( i ).getNumber() ) );
            btn.setText( String.valueOf( songs.get( i ).getNumber() ) );

            GridLayout.LayoutParams param = new GridLayout.LayoutParams();
            param.height = LayoutParams.WRAP_CONTENT;
            param.width = LayoutParams.WRAP_CONTENT;
            param.rightMargin = 5;
            param.topMargin = 5;
            param.setGravity( Gravity.CENTER );
            param.columnSpec = GridLayout.spec( c );
            param.rowSpec = GridLayout.spec( r );
            btn.setLayoutParams( param );
            btn.setOnClickListener( this );
            glNumberSongs.addView( btn );
        }

    }

}
