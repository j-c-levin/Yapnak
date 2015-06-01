package com.frontend.yapnak.navigationdrawer;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.uq.yapnak.R;


/**
 * Created by vahizan on 26/05/2015.
 */
public class NavigationBarAdapter extends ArrayAdapter<NavBarItem>{


    public NavigationBarAdapter(Context context, int resource, NavBarItem[]values) {
        super(context, R.layout.navigation_bar_layout,values);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View v = layoutInflater.inflate(R.layout.navigation_bar_layout,parent,false);

        NavBarItem navBarItem = getItem(position);


        TextView itemName = (TextView) v.findViewById(R.id.NavBarItemName);

        itemName.setText(navBarItem.getNavBarItem());



        return v;

    }
}
