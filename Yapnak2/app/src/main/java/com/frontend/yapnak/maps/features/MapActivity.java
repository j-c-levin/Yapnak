package com.frontend.yapnak.maps.features;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.uq.yapnak.R;


/**
 * Created by vahizan on 26/05/2015.
 */
public class MapActivity extends Activity implements View.OnClickListener{
    private String location;
    private String restaurant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.map_activity);

        Intent mapIntent = getIntent();

        location= mapIntent.getExtras().getString("LocationIntent");

        restaurant= mapIntent.getExtras().getString("RestaurantNameIntent");




    }


    public void tempMethod(String loc,String res){

        TextView location = (TextView) findViewById(R.id.location);
        location.setText(loc);

        TextView restName = (TextView) findViewById(R.id.restaurantName);
        restName.setText(res);



    }

    @Override
    public void onClick(View v) {


        tempMethod(location,restaurant);

    }


}
