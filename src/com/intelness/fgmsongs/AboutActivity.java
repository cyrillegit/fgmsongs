package com.intelness.fgmsongs;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class AboutActivity extends MainActivity {
    private TextView tvVersion;
    private View     layout;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        layout = getLayoutInflater().inflate( R.layout.activity_about, frameLayout );

        setTitle( navDrawerItems[6] );
        getSupportActionBar().setIcon( R.drawable.ic_launcher );

        tvVersion = (TextView) layout.findViewById( R.id.tvFGMSongsVersion );
        String version = "";

        try {
            PackageManager pm = this.getPackageManager();
            PackageInfo info = pm.getPackageInfo( this.getPackageName(), 0 );
            version = info.versionName;
        } catch ( NameNotFoundException e ) {
            e.printStackTrace();
        }

        tvVersion.setText( version );
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {
        setMenuItemNumber( menu, getSongNumber() );
        return super.onCreateOptionsMenu( menu );
    }

    @Override
    protected void onResume() {
        super.onResume();
        onSwipeScreen( getApplicationContext(), layout, ABOUT_ACTIVITY_POSITION );
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
