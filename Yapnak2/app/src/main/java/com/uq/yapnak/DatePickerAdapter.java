package com.uq.yapnak;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by vahizan on 04/08/2015.
 */
public class DatePickerAdapter extends ArrayAdapter<DateItem> {

   private DateItem item;
   private TextView text;

    public DatePickerAdapter(Context context, int resource, DateItem[] objects) {
        super(context,R.layout.date_item, objects);


    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView==null){

            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.date_item,parent,false);

            item = getItem(position);
            text = (TextView) convertView.findViewById(R.id.numberText);
            text.setText(item.getValue());
            //text.setTextColor(Color.parseColor(item.getBackgroundColor()));
            convertView.setTag(item);

        }else{
            item = (DateItem) convertView.getTag();
        }

        return convertView;
    }
}
