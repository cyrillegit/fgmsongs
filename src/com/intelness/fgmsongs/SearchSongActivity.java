package com.intelness.fgmsongs;

import android.os.Bundle;

public class SearchSongActivity extends MainActivity {

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        getLayoutInflater().inflate( R.layout.activity_searchsong, frameLayout );

        setTitle( navDrawerItems[1] );
    }
}
