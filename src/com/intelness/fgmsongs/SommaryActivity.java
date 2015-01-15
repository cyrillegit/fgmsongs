package com.intelness.fgmsongs;

import android.os.Bundle;

public class SommaryActivity extends MainActivity {
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        getLayoutInflater().inflate( R.layout.activity_summary, frameLayout );

        setTitle( navDrawerItems[4] );
    }
}
