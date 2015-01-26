package com.intelness.fgmsongs;

import java.util.Locale;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.intelness.fgmsongs.beans.ApplicationVariables;
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
    private RadioGroup           rgLanguages;
    private RadioButton          rbLanguage;
    private RadioButton          rbEnglish;
    private RadioButton          rbFrench;
    private int                  selectedRadioButton;
    private int                  language;
    private ApplicationVariables appVars;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        View layout = getLayoutInflater().inflate( R.layout.activity_options, frameLayout );
        setTitle( navDrawerItems[5] );

        btnOptionsValidate = (Button) layout.findViewById( R.id.btnOptionsValidate );
        btnOptionsCancel = (Button) layout.findViewById( R.id.btnOptionsCancel );
        rgLanguages = (RadioGroup) layout.findViewById( R.id.rgLanguages );
        rbEnglish = (RadioButton) layout.findViewById( R.id.rbEnglish );
        rbFrench = (RadioButton) layout.findViewById( R.id.rbFrench );

        appVars = super.getAllApplicationVariables();

        // set the current state on the radio button
        setCurrentState( appVars.getLanguage() );
        // get the state of the layout of the radio button
        getCurrentState();

        onClickBtnCancel();
        onClickBtnValidate();
    }

    public void onClickBtnValidate() {
        btnOptionsValidate.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick( View v ) {
                int selected = rgLanguages.getCheckedRadioButtonId();
                rbLanguage = (RadioButton) findViewById( selected );
                if ( rbLanguage.getText().toString().trim()
                        .equals( getResources().getString( R.string.english ).trim() ) ) {
                    language = 0;
                } else if ( rbLanguage.getText().toString().trim()
                        .equals( getResources().getString( R.string.french ).trim() ) ) {
                    language = 1;
                } else {
                    language = 0;
                }
                setLocaleLanguage( language );
                setLocaleResources( FGMSongsUtils.LOCALE[language] );
                Intent intent = new Intent( getApplicationContext(), SplashActivity.class );
                startActivity( intent );
            }
        } );
    }

    public void onClickBtnCancel() {
        btnOptionsCancel.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick( View v ) {
                setPreviousState();
            }
        } );
    }

    /**
     * get the current state of the options on the layout
     */
    public void getCurrentState() {
        selectedRadioButton = rgLanguages.getCheckedRadioButtonId();
    }

    /**
     * set the current state
     */
    public void setCurrentState( int language ) {
        switch ( language ) {
        case 0:
            rbEnglish.setChecked( true );
            rbFrench.setChecked( false );
            break;
        case 1:
            rbEnglish.setChecked( false );
            rbFrench.setChecked( true );
            break;

        default:
            break;
        }
    }

    /**
     * set the previous state of the layout
     */
    public void setPreviousState() {
        rgLanguages.check( selectedRadioButton );
    }

    void setLocaleResources( final String languageCode ) {

        Resources res = getBaseContext().getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        android.content.res.Configuration conf = res.getConfiguration();
        conf.locale = new Locale( languageCode.toLowerCase( Locale.ENGLISH ) );
        res.updateConfiguration( conf, dm );
    }

    /**
     * set global variables
     */
    public void setLocaleLanguage( int localeLanguage ) {
        ApplicationManager am = new ApplicationManager( this );
        am.setLanguage( localeLanguage );
        super.savePreferences();
    }
}
