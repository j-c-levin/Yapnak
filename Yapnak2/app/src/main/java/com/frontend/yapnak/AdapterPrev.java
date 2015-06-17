package com.frontend.yapnak;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.uq.yapnak.MoreInfo;
import com.uq.yapnak.R;

/**
 * Created by vahizan on 16/04/2015.
 */

public class AdapterPrev extends ArrayAdapter<ItemPrev> {

    private View view;
    private ItemPrev deal;




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



        //LayoutInflater layoutInflater = LayoutInflater.from(getContext());

        //View view = layoutInflater.inflate(R.layout.item2,parent,false);



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
            locationName.setText(deal.getDistance());
            points.setText(deal.getPoints());
            restaurantLogo.setImageResource(deal.getLogo());



            //Show Ratings and Comments page with long click;
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Intent intent = new Intent(getContext(),MoreInfo.class);

                    intent.putExtra("logo",deal.getLogo());
                    intent.putExtra("location",deal.getDistance());
                    intent.putExtra("rating",2.1);

                    getContext().startActivity(intent);



                    return true;
                }
            });



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
