package com.intelness.fgmsongs;

import android.annotation.TargetApi;
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
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.SpinnerAdapter;

import com.intelness.fgmsongs.adapters.DrawerAdapter;

/**
 * this class is the class that implements the navigation drawer, all the other
 * activities extend this class
 * 
 * @author McCyrille
 * @version 1.0
 * @since 1.0
 */
public class MainActivity extends ActionBarActivity implements OnItemClickListener {

    private static final String     TAG      = "MainActiviy";

    protected static final String   POSITION = "position";
    protected DrawerLayout          drawerLayout;
    protected ListView              listView;
    protected ActionBarDrawerToggle drawerListener;
    protected Toolbar               toolbar  = null;
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

        spinnerAdapter = ArrayAdapter.createFromResource( this, R.array.planets,
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
        Intent mainIntent;

        switch ( position ) {
        case 0:
            mainIntent = new Intent( getApplicationContext(), ListSongsActivity.class );
            startActivity( mainIntent );
            break;

        case 1:
            mainIntent = new Intent( getApplicationContext(), SearchSongActivity.class );
            startActivity( mainIntent );
            break;

        case 2:
            mainIntent = new Intent( getApplicationContext(), AddSongActivity.class );
            startActivity( mainIntent );
            break;

        case 3:
            mainIntent = new Intent( getApplicationContext(), EditSongActivity.class );
            startActivity( mainIntent );
            break;

        case 4:
            mainIntent = new Intent( getApplicationContext(), SommaryActivity.class );
            startActivity( mainIntent );
            break;

        case 5:
            mainIntent = new Intent( getApplicationContext(), OptionsActivity.class );
            startActivity( mainIntent );
            break;

        case 6:
            mainIntent = new Intent( getApplicationContext(), AboutActivity.class );
            startActivity( mainIntent );
            break;

        case 7:
            // mainIntent = new Intent( getApplicationContext(),
            // SplashActivity.class );
            // mainIntent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK |
            // Intent.FLAG_ACTIVITY_CLEAR_TASK );
            // mainIntent.putExtra( "EXIT", true );
            this.finish();
            System.exit( 0 );
            break;
        default:
            break;
        }
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

}