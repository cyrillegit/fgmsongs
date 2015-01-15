package com.intelness.fgmsongs;

import android.os.Bundle;

public class OptionsActivity extends MainActivity {
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        getLayoutInflater().inflate( R.layout.activity_options, frameLayout );

        setTitle( navDrawerItems[5] );
    }
}
