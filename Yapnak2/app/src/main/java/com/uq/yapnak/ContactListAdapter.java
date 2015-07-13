package com.uq.yapnak;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.zip.Inflater;

/**
 * Created by vahizan on 13/07/2015.
 */
public class ContactListAdapter extends ArrayAdapter<String> {

    String string;

    public ContactListAdapter(Context context, int resource, String[] objects) {
        super(context, R.layout.contactlist_item, objects);


    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //return super.getView(position, convertView, parent);

        if(convertView==null){

            string = getItem(position);

            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.contactlist_item,null);

            TextView tv = (TextView) convertView.findViewById(R.id.contactName);

            tv.setText(string);


        }else{

            string = (String) convertView.getTag();
        }

        return convertView;
    }
}
