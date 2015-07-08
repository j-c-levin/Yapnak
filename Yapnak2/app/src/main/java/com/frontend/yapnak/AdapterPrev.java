package com.frontend.yapnak;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;
import com.uq.yapnak.R;

/**
 * Created by vahizan on 16/04/2015.
 */

public class AdapterPrev extends ArrayAdapter<ItemPrev> {

    private View view;
    private ItemPrev deal;
    private final String FILE_NAME = "yapnak_details";



    public AdapterPrev(Context context, int resourse, ItemPrev[] values) {

        super(context, R.layout.item2,values);

    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

         if(convertView == null){

            LayoutInflater layoutInflater = ((Activity)getContext()).getLayoutInflater();

            convertView=layoutInflater.inflate(R.layout.item2,parent,false);

            setView(convertView);

            deal = getItem(position);

            TextView mainText = (TextView) view.findViewById(R.id.title);
            TextView subText = (TextView) view.findViewById(R.id.subtitle);
            TextView locationName = (TextView)view.findViewById(R.id.distance);
            TextView loyaltyPointsTitle = (TextView)view.findViewById(R.id.loyalty);
            TextView points = (TextView)view.findViewById(R.id.loyaltyPoints);

            /* Typeface custom_font = Typeface.createFromAsset(getContext().getAssets(),"fonts/canela_light.otf");
             mainText.setTypeface(custom_font);
             subText.setTypeface(custom_font);
             locationName.setTypeface(custom_font);
             loyaltyPointsTitle.setTypeface(custom_font);
             points.setTypeface(custom_font);
             */


            ImageView locationLogo = (ImageView) view.findViewById(R.id.locationIcon);
            ImageView restaurantLogo = (ImageView) view.findViewById(R.id.logo);
            ImageView hotDeal = (ImageView) view.findViewById(R.id.heatIcon);





            mainText.setText(deal.getMainText());
            subText.setText(deal.getSubText());
            locationLogo.setImageResource(deal.getLocationIcon());
            loyaltyPointsTitle.setText(deal.getLoyaltyPointsTitle());
            locationName.setText(deal.getDistanceTime());
            points.setText(deal.getPoints());
            restaurantLogo.setImageResource(deal.getLogo());
            hotDeal.setImageResource(deal.getHotDeal());

            //Implement ION Load Image FROM URL provided in dealList(SQLEntity sql) method in MainActivity;

             /*Ion.with(restaurantLogo)
                    .placeholder(R.drawable.manualicon)
                    .load(deal.getFetchImageURL());
             */

             //Implement ION Load Image FROM URL provided in dealList(SQLEntity sql) method in MainActivity;


             //Load more images after authentication






             //Load more images after authentication


             convertView.setTag(deal);


        }else{
            deal = (ItemPrev) convertView.getTag();
        }

        return convertView;

    }






    private void setView(View v){
        this.view = v;

    }

    public View getActualView(){
        return this.view;
    }



}
