package com.intelness.fgmsongs;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.intelness.fgmsongs.beans.ApplicationVariables;
import com.intelness.fgmsongs.globals.AppManager;
import com.intelness.fgmsongs.utils.FGMSongsUtils;

public class HomeActivity extends MainActivity {

    private static final String  TAG = "HomeActivity";
    private AlertDialog          alertDialog;
    private ApplicationVariables appVars;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        View layout = getLayoutInflater().inflate( R.layout.activity_home, frameLayout );
        setTitle( getResources().getString( R.string.songs ) );
        // get all the application variables from super class
        appVars = super.getAllApplicationVariables();

        TextView tvHomeFirstVerse = (TextView) layout.findViewById( R.id.tvHomeFirstVerse );
        TextView tvSecondVerse = (TextView) layout.findViewById( R.id.tvHomeSecondVerse );
        TextView tvPsalms342 = (TextView) layout.findViewById( R.id.tvPsalms342 );

        // int languageId = getLocaleLanguage();
        displayAlertDialog();
        if ( appVars.getLanguage() < 0 ) {
            displayAlertDialog();
        } else {
            Log.i( TAG, "language : " + FGMSongsUtils.LANGUAGES[appVars.getLanguage()] );
        }

        tvHomeFirstVerse.setText( FGMSongsUtils.StringsWithNewLine( getResources()
                .getString( R.string.home_first_verse ) ) );
        tvSecondVerse.setText( FGMSongsUtils
                .StringsWithNewLine( getResources().getString( R.string.home_seconf_verse ) ) );
        tvPsalms342.setText( getResources().getString( R.string.psalms_34_2 ) );

    }

    /**
     * display an alert dialog to choice a language
     * 
     */
    public void displayAlertDialog() {

        String selectLanguage = getResources().getString( R.string.select_language );
        super.displayAlertDialog( selectLanguage, "", SINGLE_CHOICE );
        Log.i( TAG, "choice : " + getChoice() );
        // setLocaleLanguage( getChoice() );

        /*
         * AlertDialog.Builder builder = new AlertDialog.Builder( this );
         * builder.setTitle( selectLanguage ); builder.setSingleChoiceItems(
         * FGMSongsUtils.LANGUAGES, -1, new DialogInterface.OnClickListener() {
         * 
         * @Override public void onClick( DialogInterface dialog, int which ) {
         * // savePreferences( which ); setLocaleLanguage( which );
         * alertDialog.dismiss(); } } ); alertDialog = builder.create();
         * alertDialog.show();
         */
    }

    /**
     * set locale
     * 
     * @deprecated since 2015-01-25
     */
    private void setLocaleLanguage( int language ) {
        final AppManager app = (AppManager) getApplicationContext();
        app.setLanguage( language );
    }

    /**
     * get locale
     * 
     * @deprecated since 2015-01-25
     */
    private int getLocaleLanguage() {
        final AppManager app = (AppManager) getApplicationContext();
        return app.getLanguage();
    }

}
