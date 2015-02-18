package com.intelness.fgmsongs;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.intelness.fgmsongs.beans.ApplicationVariables;
import com.intelness.fgmsongs.managers.ApplicationManager;
import com.intelness.fgmsongs.utils.FGMSongsUtils;

/**
 * home activity
 * 
 * @author McCyrille
 * @version 1.0
 * @since 2015-02-13
 */
public class HomeActivity extends MainActivity {

    private static final String  TAG = "HomeActivity";
    private ApplicationVariables appVars;
    private View                 layout;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        layout = getLayoutInflater().inflate( R.layout.activity_home, frameLayout );
        setTitle( getResources().getString( R.string.songs ) );
        // get all the application variables from super class
        appVars = super.getAllApplicationVariables();

        TextView tvHomeFirstVerse = (TextView) layout.findViewById( R.id.tvHomeFirstVerse );
        TextView tvSecondVerse = (TextView) layout.findViewById( R.id.tvHomeSecondVerse );
        TextView tvPsalms342 = (TextView) layout.findViewById( R.id.tvPsalms342 );

        if ( appVars.getLanguage() < 0 ) {
            displayAlertDialog();
        } else {
            Log.i( TAG, "language : " + FGMSongsUtils.LANGUAGES[appVars.getLanguage()] );
        }

        tvHomeFirstVerse.setText( FGMSongsUtils.StringsWithNewLine( getResources()
                .getString( R.string.home_first_verse ) ) );
        tvSecondVerse.setText( FGMSongsUtils
                .StringsWithNewLine( getResources().getString( R.string.home_second_verse ) ) );
        tvPsalms342.setText( getResources().getString( R.string.psalms_34_2 ) );

    }

    @Override
    protected void onResume() {
        super.onResume();
        onSwipeScreen( this, layout, HOME_ACTIVITY_POSITION );
    }

    /**
     * display an alert dialog to choice a language
     * 
     */
    public void displayAlertDialog() {

        String selectLanguage = getResources().getString( R.string.select_language );

        AlertDialog.Builder builder = new AlertDialog.Builder( this );
        builder.setTitle( selectLanguage );
        builder.setSingleChoiceItems(
                FGMSongsUtils.LANGUAGES, -1, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick( DialogInterface dialog, int which ) {
                        setLocaleLanguage( which );
                        dialog.dismiss();
                    }
                } );
        builder.create().show();
    }

    /**
     * set locale
     * 
     */
    private void setLocaleLanguage( int language ) {
        ApplicationManager am = new ApplicationManager( this );
        am.setLanguage( language );
    }

}
