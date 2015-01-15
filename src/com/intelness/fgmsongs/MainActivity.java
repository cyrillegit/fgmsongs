package com.intelness.fgmsongs;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity implements OnItemClickListener {

    protected static final String   TAG     = "MainActiviy";
    protected DrawerLayout          drawerLayout;
    protected ListView              listView;
    protected ActionBarDrawerToggle drawerListener;
    protected Toolbar               toolbar = null;
    protected MyAdapter             myAdapter;
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

        myAdapter = new MyAdapter( this );
        listView.setAdapter( myAdapter );

        listView.setOnItemClickListener( this );

        // get list of items of the drawer
        navDrawerItems = myAdapter.getNavDrawerItems();

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
        getSupportActionBar().setHomeButtonEnabled( true );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );

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

        default:
            break;
        }
    }

    public void selectItem( int position ) {
        listView.setItemChecked( position, true );
    }

    public void setTitle( String title ) {
        getSupportActionBar().setTitle( title );
    }
}

/**
 * 
 * @author McCyrille
 * 
 */
class MyAdapter extends BaseAdapter {
    private Context  context;
    private String[] navDrawerItems;
    private int[]    navDrawerIcons = { R.drawable.ic_action_view_as_list,
                                    R.drawable.ic_action_search,
                                    R.drawable.ic_action_new,
                                    R.drawable.ic_action_edit,
                                    R.drawable.ic_action_sort_by_size,
                                    R.drawable.ic_action_settings,
                                    R.drawable.ic_action_about };

    public MyAdapter( Context context ) {
        this.context = context;
        navDrawerItems = context.getResources().getStringArray( R.array.nav_drawer_items );
    }

    @Override
    public int getCount() {
        return navDrawerItems.length;
    }

    @Override
    public Object getItem( int position ) {
        return navDrawerItems[position];
    }

    @Override
    public long getItemId( int position ) {
        return position;
    }

    @Override
    public View getView( int position, View convertView, ViewGroup parent ) {
        View row = null;
        if ( convertView == null ) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            row = inflater.inflate( R.layout.custom_row, parent, false );
        } else {
            row = convertView;
        }
        TextView tvDrawerItem = (TextView) row.findViewById( R.id.tvDrawerItem );
        ImageView ivDrawerIcon = (ImageView) row.findViewById( R.id.ivDrawerIcon );

        tvDrawerItem.setText( navDrawerItems[position] );
        ivDrawerIcon.setImageResource( navDrawerIcons[position] );

        return row;
    }

    public String[] getNavDrawerItems() {
        return navDrawerItems;
    }
}
