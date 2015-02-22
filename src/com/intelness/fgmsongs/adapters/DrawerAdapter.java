package com.intelness.fgmsongs.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.intelness.fgmsongs.R;

/**
 * class that manage the items on the nav drawer
 * 
 * @author McCyrille
 * @since 1.0
 */
public class DrawerAdapter extends BaseAdapter {

    private Context  context;
    private String[] navDrawerItems;
    private int[]    navDrawerIcons = { R.drawable.ic_action_view_as_list,
                                    R.drawable.ic_action_search_light,
                                    R.drawable.ic_action_new,
                                    R.drawable.ic_action_edit,
                                    R.drawable.ic_action_sort_by_size,
                                    R.drawable.ic_action_settings,
                                    R.drawable.ic_action_about,
                                    R.drawable.ic_action_cancel };

    public DrawerAdapter( Context context ) {
        this.context = context;
        this.navDrawerItems = context.getResources().getStringArray( R.array.nav_drawer_items );
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

    /**
     * return an array of items in the nav drawer
     * 
     * @return list of items in the nav drawer
     */
    public String[] getNavDrawerItems() {
        return navDrawerItems;
    }

}
