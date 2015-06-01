package com.frontend.yapnak;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.uq.yapnak.R;

/**
 * Created by vahizan on 16/04/2015.
 */

public class AdapterPrev extends ArrayAdapter<ItemPrev> {

    /*private LinearLayout locationLayout;
    private LinearLayout buttonsLayout;
    private LinearLayout textForButtonLayout;
    private GestureDetectorCompat gestureDetect;
    */

    public AdapterPrev(Context context, int resourse, ItemPrev[] values) {

        super(context, R.layout.item2,values);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = LayoutInflater.from(getContext());

        View view = layoutInflater.inflate(R.layout.item2,parent,false);

        ItemPrev deal = getItem(position);

        TextView mainText = (TextView) view.findViewById(R.id.title);
        TextView subText = (TextView) view.findViewById(R.id.subtitle);
        TextView locationName = (TextView)view.findViewById(R.id.distance);
        TextView loyaltyPointsTitle = (TextView)view.findViewById(R.id.loyalty);
        TextView points = (TextView)view.findViewById(R.id.loyaltyPoints);
        ImageView locationLogo = (ImageView) view.findViewById(R.id.locationIcon);
        ImageView restaurantLogo = (ImageView) view.findViewById(R.id.logo);


        /*
        locationLayout = (LinearLayout) view.findViewById(R.id.switch_to_location);
        buttonsLayout = (LinearLayout)view.findViewById(R.id.extendLayout);
        textForButtonLayout= (LinearLayout) view.findViewById(R.id.customIconLayout);
        */

        mainText.setText(deal.getMainText());
        subText.setText(deal.getSubText());
        locationLogo.setImageResource(deal.getLocationIcon());
        loyaltyPointsTitle.setText(deal.getLoyaltyPointsTitle());
        locationName.setText(deal.getLocation());
        points.setText(deal.getPoints());
        restaurantLogo.setImageResource(deal.getLogo());




        return view;

    }






}
