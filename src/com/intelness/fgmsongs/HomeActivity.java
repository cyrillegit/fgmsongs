package com.intelness.fgmsongs;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.intelness.fgmsongs.utils.FGMSongsUtils;

public class HomeActivity extends MainActivity {

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        View layout = getLayoutInflater().inflate( R.layout.activity_home, frameLayout );
        setTitle( getResources().getString( R.string.songs ) );

        TextView tvHomeFirstVerse = (TextView) layout.findViewById( R.id.tvHomeFirstVerse );
        TextView tvSecondVerse = (TextView) layout.findViewById( R.id.tvHomeSecondVerse );
        TextView tvPsalms342 = (TextView) layout.findViewById( R.id.tvPsalms342 );

        tvHomeFirstVerse.setText( FGMSongsUtils.StringsWithNewLine( getResources()
                .getString( R.string.home_first_verse ) ) );
        tvSecondVerse.setText( FGMSongsUtils
                .StringsWithNewLine( getResources().getString( R.string.home_seconf_verse ) ) );
        tvPsalms342.setText( getResources().getString( R.string.psalms_34_2 ) );
    }

}
