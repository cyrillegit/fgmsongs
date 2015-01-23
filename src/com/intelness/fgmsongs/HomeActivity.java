package com.intelness.fgmsongs;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.intelness.fgmsongs.globals.AppManager;
import com.intelness.fgmsongs.utils.FGMSongsUtils;

public class HomeActivity extends MainActivity {

    private static final String TAG = "HomeActivity";
    private AlertDialog         alertDialog;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        View layout = getLayoutInflater().inflate( R.layout.activity_home, frameLayout );
        setTitle( getResources().getString( R.string.songs ) );

        TextView tvHomeFirstVerse = (TextView) layout.findViewById( R.id.tvHomeFirstVerse );
        TextView tvSecondVerse = (TextView) layout.findViewById( R.id.tvHomeSecondVerse );
        TextView tvPsalms342 = (TextView) layout.findViewById( R.id.tvPsalms342 );

        int languageId = getLocaleLanguage();
        if ( languageId < 0 ) {
            displayAlertDialog();
        } else {
            Log.i( TAG, "language : " + FGMSongsUtils.LANGUAGES[languageId] );
        }

        tvHomeFirstVerse.setText( FGMSongsUtils.StringsWithNewLine( getResources()
                .getString( R.string.home_first_verse ) ) );
        tvSecondVerse.setText( FGMSongsUtils
                .StringsWithNewLine( getResources().getString( R.string.home_seconf_verse ) ) );
        tvPsalms342.setText( getResources().getString( R.string.psalms_34_2 ) );

    }

    /**
     * display an alert dialog to choice a language
     */
    public void displayAlertDialog() {

        String selectLanguage = getResources().getString( R.string.select_language );

        AlertDialog.Builder builder = new AlertDialog.Builder( this );
        builder.setTitle( selectLanguage );
        builder.setSingleChoiceItems( FGMSongsUtils.LANGUAGES, -1, new DialogInterface.OnClickListener() {

            @Override
            public void onClick( DialogInterface dialog, int which ) {
                // savePreferences( which );
                setLocaleLanguage( which );
                alertDialog.dismiss();
            }
        } );
        alertDialog = builder.create();
        alertDialog.show();
    }

    // /**
    // * save the language
    // *
    // * @param languageId
    // * id of the selected language
    // */
    // public void savePreferences( int languageId ) {
    // SharedPreferences.Editor editor = getSharedPreferences(
    // FGMSongsUtils.PREFERENCES, MODE_PRIVATE ).edit();
    // editor.putInt( FGMSongsUtils.LANGUAGE, languageId );
    // editor.commit();
    // }

    // /**
    // * retrieve preferences
    // *
    // * @return prferences
    // */
    // public int retrievePreferences() {
    // final AppManager app = (AppManager) getApplicationContext();
    // SharedPreferences prefs = getSharedPreferences(
    // FGMSongsUtils.PREFERENCES, MODE_PRIVATE );
    // app.setLanguage( prefs.getInt( FGMSongsUtils.LANGUAGE, -1 ) );
    // app.setLastCustomNumberSong( prefs.getInt(
    // FGMSongsUtils.LAST_CUSTOM_NUMBER_SONG,
    // FGMSongsUtils.FIRST_CUSTOM_NUMBER_SONG ) );
    // return prefs.getInt( FGMSongsUtils.LANGUAGE, -1 );
    // }

    /**
     * set locale
     */
    private void setLocaleLanguage( int language ) {
        final AppManager app = (AppManager) getApplicationContext();
        app.setLanguage( language );
    }

    /**
     * get locale
     */
    private int getLocaleLanguage() {
        final AppManager app = (AppManager) getApplicationContext();
        return app.getLanguage();
    }
}
