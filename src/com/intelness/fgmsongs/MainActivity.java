package com.intelness.fgmsongs;

import java.util.ArrayList;
import java.util.List;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.SpinnerAdapter;

import com.intelness.fgmsongs.adapters.DrawerAdapter;
import com.intelness.fgmsongs.beans.ApplicationVariables;
import com.intelness.fgmsongs.beans.PreferencesVariables;
import com.intelness.fgmsongs.beans.Song;
import com.intelness.fgmsongs.beans.SongDAO;
import com.intelness.fgmsongs.listeners.OnSwipeTouchListener;
import com.intelness.fgmsongs.managers.ApplicationManager;
import com.intelness.fgmsongs.managers.SharedPreferencesManager;
import com.intelness.fgmsongs.utils.FGMSongsUtils;

/**
 * this class is the class that implements the navigation drawer, all the other
 * activities extend this class
 * 
 * @author McCyrille
 * @version 1.0
 * @since
 */
public class MainActivity extends ActionBarActivity implements OnItemClickListener {

    private static final String     TAG                                   = "MainActiviy";
    protected static final String   POSITION                              = "position";
    protected static final String   SONGS                                 = "songs";
    protected static final int      LIST_SONGS_ACTIVITY_POSITION          = 0;
    protected static final int      SEARCH_SONG_ACTIVITY_POSITION         = 1;
    protected static final int      ADD_SONG_ACTIVITY_POSITION            = 2;
    protected static final int      LIST_EDITABLE_SONGS_ACTIVITY_POSITION = 3;
    protected static final int      SUMMARY_ACTIVITY_POSITION             = 4;
    protected static final int      OPTIONS_ACTIVITY_POSITION             = 5;
    protected static final int      ABOUT_ACTIVITY_POSITION               = 6;
    protected static final int      HOME_ACTIVITY_POSITION                = 7;
    protected static final int      EIGHT                                 = 8;

    protected DrawerLayout          drawerLayout;
    protected ListView              listView;
    protected ActionBarDrawerToggle drawerListener;
    protected Toolbar               toolbar                               = null;
    protected DrawerAdapter         drawerAdapter;
    protected String[]              navDrawerItems;
    protected SpinnerAdapter        spinnerAdapter;
    protected FrameLayout           frameLayout;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        // frameLayout : other activities layout are going to be put in this
        // layout
        frameLayout = (FrameLayout) findViewById( R.id.mainContent );

        drawerLayout = (DrawerLayout) findViewById( R.id.drawerLayout );
        listView = (ListView) findViewById( R.id.drawerList );

        drawerAdapter = new DrawerAdapter( this );
        listView.setAdapter( drawerAdapter );

        listView.setOnItemClickListener( this );

        // get list of items of the drawer
        navDrawerItems = drawerAdapter.getNavDrawerItems();

        drawerListener = new ActionBarDrawerToggle( this, drawerLayout,
                toolbar, R.string.drawer_open,
                R.string.drawer_close ) {

            @Override
            public void onDrawerClosed( View drawerView ) {
                Log.i( TAG, "Drawer closed" );
            }

            @Override
            public void onDrawerOpened( View drawerView ) {
                Log.i( TAG, "Drawer opened" );
            }

        };
        drawerLayout.setDrawerListener( drawerListener );
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled( true );
        actionBar.setDisplayHomeAsUpEnabled( true );
        actionBar.setDisplayUseLogoEnabled( true );

        // // actionBar.setCustomView( R.layout.actionbar_view );
        // // EditText search = (EditText)
        // actionBar.getCustomView().findViewById( R.id.searchfield );
        // search.setOnEditorActionListener( new OnEditorActionListener() {
        //
        // @Override
        // public boolean onEditorAction( TextView v, int actionId, KeyEvent
        // event ) {
        // Log.i( TAG, "search triggered1" );
        // return false;
        // }
        // } );
        // actionBar.setDisplayOptions( ActionBar.DISPLAY_USE_LOGO );

        spinnerAdapter = ArrayAdapter.createFromResource( this, R.array.edit_song_choices,
                android.R.layout.simple_spinner_dropdown_item );

    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate( R.menu.splash, menu );
        return super.onCreateOptionsMenu( menu );
    }

    @Override
    protected void onPostCreate( Bundle savedInstanceState ) {
        super.onPostCreate( savedInstanceState );
        drawerListener.syncState();
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item ) {
        if ( drawerListener.onOptionsItemSelected( item ) ) {
            return true;
        }
        return super.onOptionsItemSelected( item );
    }

    @Override
    public void onConfigurationChanged( Configuration newConfig ) {
        super.onConfigurationChanged( newConfig );
        drawerListener.onConfigurationChanged( newConfig );
    }

    @TargetApi( Build.VERSION_CODES.HONEYCOMB )
    @Override
    public void onItemClick( AdapterView<?> parent, View view, int position, long id ) {
        selectItem( position );
        goToActivity( position );
        // Intent mainIntent;
        //
        // switch ( position ) {
        // case 0:
        // mainIntent = new Intent( getApplicationContext(),
        // ListSongsActivity.class );
        // startActivity( mainIntent );
        // break;
        //
        // case 1:
        // mainIntent = new Intent( getApplicationContext(),
        // SearchSongActivity.class );
        // startActivity( mainIntent );
        // break;
        //
        // case 2:
        // mainIntent = new Intent( getApplicationContext(),
        // AddSongActivity.class );
        // startActivity( mainIntent );
        // break;
        //
        // case 3:
        // mainIntent = new Intent( getApplicationContext(),
        // ListEditableSongsActivity.class );
        // startActivity( mainIntent );
        // break;
        //
        // case 4:
        // mainIntent = new Intent( getApplicationContext(),
        // SummaryActivity.class );
        // startActivity( mainIntent );
        // break;
        //
        // case 5:
        // mainIntent = new Intent( getApplicationContext(),
        // OptionsActivity.class );
        // startActivity( mainIntent );
        // break;
        //
        // case 6:
        // mainIntent = new Intent( getApplicationContext(), AboutActivity.class
        // );
        // startActivity( mainIntent );
        // break;
        //
        // case 7:
        // mainIntent = new Intent( getApplicationContext(), HomeActivity.class
        // );
        // startActivity( mainIntent );
        // break;
        // default:
        // break;
        // }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        drawerLayout.closeDrawer( listView );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i( TAG, "ondestroy" );
        savePreferences();
    }

    public void selectItem( int position ) {
        listView.setItemChecked( position, true );
    }

    /**
     * set the title on the bar of nav drawer
     * 
     * @param title
     *            set the title on bar nav drawer
     */
    public void setTitle( String title ) {
        getSupportActionBar().setTitle( title );
    }

    /**
     * save the preferences
     * 
     * @since 2015-01-25
     */
    protected void savePreferences() {

        ApplicationManager am = new ApplicationManager( this );
        SharedPreferencesManager spm = new SharedPreferencesManager( this );

        ApplicationVariables appVars = am.getApplicationVariables();
        PreferencesVariables prefVars = new PreferencesVariables();

        prefVars.setLocaleLanguage( appVars.getLanguage() );
        prefVars.setLastCustomNumberSong( appVars.getLastCustomNumberSong() );

        spm.setPreferencesVariables( prefVars );
    }

    /**
     * get all the application variables
     * 
     * @return bean ApplicationVariables
     */
    protected ApplicationVariables getAllApplicationVariables() {
        ApplicationManager am = new ApplicationManager( this );
        return am.getApplicationVariables();
    }

    /**
     * delete the song, both in DB and internal storage, and update the songs
     * 
     * @param song
     *            to be deleted
     * @param language
     * @since 2015-01-26
     */
    protected void updateSongsOnDelete( Song song, int language ) {
        SongDAO sDao = new SongDAO( getApplicationContext(), language );
        ApplicationManager am = new ApplicationManager( getApplicationContext() );

        String filename = FGMSongsUtils.CUSTOM + song.getNumber() + FGMSongsUtils.XML_EXTENSION;
        deleteFile( filename );

        sDao.deleteSong( song );
        List<Song> newSongs = sDao.getAllSongs();
        ArrayList<String> newTitleSongs = FGMSongsUtils.getAllTitleSongs( newSongs );

        am.setSongs( newSongs );
        am.setTitleSongs( newTitleSongs );
    }

    /**
     * go to a particular activity according to the position
     * 
     * @param position
     *            of the activity
     * @since 2015-02-15
     */
    protected void goToActivity( int position ) {
        Intent mainIntent;

        switch ( position ) {
        case LIST_SONGS_ACTIVITY_POSITION:
            mainIntent = new Intent( getApplicationContext(), ListSongsActivity.class );
            startActivity( mainIntent );
            break;

        case SEARCH_SONG_ACTIVITY_POSITION:
            mainIntent = new Intent( getApplicationContext(), SearchSongActivity.class );
            startActivity( mainIntent );
            break;

        case ADD_SONG_ACTIVITY_POSITION:
            mainIntent = new Intent( getApplicationContext(), AddSongActivity.class );
            startActivity( mainIntent );
            break;

        case LIST_EDITABLE_SONGS_ACTIVITY_POSITION:
            mainIntent = new Intent( getApplicationContext(), ListEditableSongsActivity.class );
            startActivity( mainIntent );
            break;

        case SUMMARY_ACTIVITY_POSITION:
            mainIntent = new Intent( getApplicationContext(), SummaryActivity.class );
            startActivity( mainIntent );
            break;

        case OPTIONS_ACTIVITY_POSITION:
            mainIntent = new Intent( getApplicationContext(), OptionsActivity.class );
            startActivity( mainIntent );
            break;

        case ABOUT_ACTIVITY_POSITION:
            mainIntent = new Intent( getApplicationContext(), AboutActivity.class );
            startActivity( mainIntent );
            break;

        case HOME_ACTIVITY_POSITION:
            mainIntent = new Intent( getApplicationContext(), HomeActivity.class );
            startActivity( mainIntent );
            break;
        default:
            break;
        }
    }

    /**
     * display a specific song
     * 
     * @param position
     *            of the song
     * @since 2015-02-13
     */
    protected void goToSongActivity( int position ) {
        Bundle bundle = new Bundle();
        bundle.putInt( POSITION, position );
        Intent intent = new Intent( getApplicationContext(), SongActivity.class );
        intent.putExtras( bundle );
        startActivity( intent );
        finish();
    }

    /**
     * manage swipe screen
     * 
     * @param context
     * @param view
     * @param position
     * @since 2015-02-13
     */
    protected void onSwipeScreen( Context context, View view, final int position ) {
        view.setOnTouchListener( new OnSwipeTouchListener( context ) {

            @Override
            public void onSwipeRight() {
                int positions = ( position <= 0 ) ? EIGHT : position;
                goToActivity( ( positions - 1 ) % EIGHT );
                overridePendingTransition( R.anim.slide_in_left, R.anim.slide_out_right );
            }

            @Override
            public void onSwipeLeft() {
                goToActivity( ( position + 1 ) % EIGHT );
                overridePendingTransition( R.anim.slide_in_right, R.anim.slide_out_left );
            }

            @Override
            public void onSwipeBottom() {
                int positions = ( position <= 0 ) ? EIGHT : position;
                goToActivity( ( positions - 1 ) % EIGHT );
                overridePendingTransition( R.anim.slide_in_top, R.anim.slide_out_bottom );
            }

            @Override
            public void onSwipeTop() {
                goToActivity( ( position + 1 ) % EIGHT );
                overridePendingTransition( R.anim.slide_in_bottom, R.anim.slide_out_top );
            }

            @Override
            public boolean onTouch( View v, MotionEvent event ) {
                // Toast.makeText( getApplicationContext(), "on touch",
                // Toast.LENGTH_LONG ).show();
                return super.onTouch( v, event );
            }

        } );
    }

    /**
     * manage swipe screen
     * 
     * @param context
     * @param view
     * @param position
     * @since 2015-02-13
     */
    protected void onSwipeScreenSong( Context context, View view, final int position, final ArrayList<Integer> indexes ) {
        final int SIZE = indexes.size();
        view.setOnTouchListener( new OnSwipeTouchListener( context ) {

            @Override
            public void onSwipeRight() {
                int positions = ( position <= 0 ) ? SIZE : position;
                goToSongActivity( indexes.get( ( positions - 1 ) % SIZE ) );
                overridePendingTransition( R.anim.slide_in_left, R.anim.slide_out_right );
            }

            @Override
            public void onSwipeLeft() {
                goToSongActivity( indexes.get( ( position + 1 ) % SIZE ) );
                overridePendingTransition( R.anim.slide_in_right, R.anim.slide_out_left );
            }

            @Override
            public void onSwipeBottom() {
                goToSongActivity( indexes.get( ( position + 1 ) % SIZE ) );
                overridePendingTransition( R.anim.slide_in_top, R.anim.slide_out_bottom );
            }

            @Override
            public void onSwipeTop() {
                int positions = ( position <= 0 ) ? SIZE : position;
                goToSongActivity( indexes.get( ( positions - 1 ) % SIZE ) );
                overridePendingTransition( R.anim.slide_in_bottom, R.anim.slide_out_top );
            }

            @Override
            public boolean onTouch( View v, MotionEvent event ) {
                return super.onTouch( v, event );
            }

        } );
    }

}