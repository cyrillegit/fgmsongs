package com.intelness.fgmsongs;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import com.intelness.fgmsongs.beans.ApplicationVariables;
import com.intelness.fgmsongs.listeners.OnSelectLanguageListener;
import com.intelness.fgmsongs.managers.ApplicationManager;
import com.intelness.fgmsongs.utils.FGMSongsUtils;

/**
 * 
 * @author McCyrille
 * @version 1.0
 */
public class OptionsActivity extends MainActivity {
    private Button               btnOptionsValidate;
    private Button               btnOptionsCancel;
    private int                  language;
    private ApplicationVariables appVars;
    private View                 layout;
    private Spinner              sSelectLanguage;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        layout = getLayoutInflater().inflate( R.layout.activity_options, frameLayout );
        setTitle( navDrawerItems[5] );

        btnOptionsValidate = (Button) layout.findViewById( R.id.btnOptionsValidate );
        btnOptionsCancel = (Button) layout.findViewById( R.id.btnOptionsCancel );
        // btnOptionsCancel.setVisibility( View.INVISIBLE );
        sSelectLanguage = (Spinner) findViewById( R.id.sSelectLanguage );

        appVars = super.getAllApplicationVariables();

        onClickBtnCancel();
        onClickBtnValidate();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setLanguageToSpinner( appVars.getLanguage() );
        onSelectLanguage();
        onSwipeScreen( getApplicationContext(), layout, OPTIONS_ACTIVITY_POSITION );
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    /**
     * triggered when button validate is clicked
     */
    public void onClickBtnValidate() {
        btnOptionsValidate.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick( View v ) {

                setLocaleLanguage( getLanguage() );
                Intent intent = new Intent( getApplicationContext(), SplashActivity.class );
                startActivity( intent );
            }
        } );
    }

    /**
     * triggered when button cancel is clicked
     */
    public void onClickBtnCancel() {
        btnOptionsCancel.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick( View v ) {
                setLanguage( appVars.getLanguage() );
                Intent intent = new Intent( getApplicationContext(), HomeActivity.class );
                startActivity( intent );
            }
        } );
    }

    /**
     * set language to spinner
     * 
     * @param position
     *            of the language
     * @since 2015-02-18
     */
    public void setLanguageToSpinner( int position ) {
        sSelectLanguage.setSelection( position );
    }

    /**
     * set language selected
     * 
     * @since 2015-02-18
     */
    public void onSelectLanguage() {
        sSelectLanguage.setOnItemSelectedListener( new OnSelectLanguageListener() {

            @Override
            public void onItemSelected( AdapterView<?> parent, View view, int position, long id ) {

                setLanguage( position );

            }

            @Override
            public void onNothingSelected( AdapterView<?> parent ) {
                // TODO Auto-generated method stub
                super.onNothingSelected( parent );
            }
        } );
    }

    /**
     * set global variables
     */
    public void setLocaleLanguage( int localeLanguage ) {
        ApplicationManager am = new ApplicationManager( this );
        am.setLocaleResources( FGMSongsUtils.LOCALE[localeLanguage] );
        am.setLanguage( localeLanguage );
        super.savePreferences();
    }

    public int getLanguage() {
        return language;
    }

    public void setLanguage( int language ) {
        this.language = language;
    }
}
