package com.intelness.fgmsongs;

import android.os.Bundle;

public class EditSongActivity extends MainActivity {
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        getLayoutInflater().inflate( R.layout.activity_editsong, frameLayout );

        setTitle( navDrawerItems[3] );
    }
}
