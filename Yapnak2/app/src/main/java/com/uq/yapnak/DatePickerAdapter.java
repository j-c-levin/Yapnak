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
   private DateItem [] items;

    public DatePickerAdapter(Context context, int resource, DateItem[] objects) {
        super(context,R.layout.date_item, objects);

        this.items=objects;


    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView==null){

            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.date_item,parent,false);

            item = getItem(position);
            text = (TextView) convertView.findViewById(R.id.numberText);
            text.setText(item.getValue());
            try {
                text.setTextColor(Color.parseColor(item.getBackgroundColor()));
            }catch (Exception e){
                text.setTextColor(Color.parseColor("#9E9E9E"));
            }
            convertView.setTag(item);

        }else{
            item = (DateItem) convertView.getTag();
        }

        return convertView;
    }

    @Override
    public DateItem getItem(int position) {
       // return super.getItem(position);
        return items[position];
    }
}
