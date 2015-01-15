package com.intelness.fgmsongs;

import android.os.Bundle;

public class AddSongActivity extends MainActivity {
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        getLayoutInflater().inflate( R.layout.activity_addsong, frameLayout );

        setTitle( navDrawerItems[2] );
    }
}
