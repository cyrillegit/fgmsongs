package com.intelness.fgmsongs;

import android.os.Bundle;

public class AboutActivity extends MainActivity {
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        getLayoutInflater().inflate( R.layout.activity_about, frameLayout );

        setTitle( navDrawerItems[6] );
        getSupportActionBar().setIcon( R.drawable.ic_launcher );
    }
}
