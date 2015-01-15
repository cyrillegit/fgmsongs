package com.intelness.fgmsongs;

import android.os.Bundle;

public class ListSongsActivity extends MainActivity {

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        getLayoutInflater().inflate( R.layout.activity_listsongs, frameLayout );

        setTitle( navDrawerItems[0] );
    }

}
