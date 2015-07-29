package com.frontend.yapnak.rate;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.uq.yapnak.R;

/**
 * Created by vahizan on 11/07/2015.
 */
public class RatingBuilder extends AlertDialog.Builder {

    private Activity activity;
    private Context context;
    private RatingBar rating;
    private View v;
    private float ratingNumber;

    public RatingBuilder(Context context,Activity activity) {
        super(context);

        this.context=context;
        this.activity=activity;

        LayoutInflater inflater = this.activity.getLayoutInflater();

         v = inflater.inflate(R.layout.ratings_dialog,null);


        rating = (RatingBar) v.findViewById(R.id.rateDeal);

        rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {



                TextView text = (TextView) v.findViewById(R.id.ratingNumber);
                text.setText(String.valueOf(rating));
                setRating(Float.parseFloat(String.valueOf(rating)));




            }
        });


        this.setView(v);

    }


    public float getRating(){
        return ratingNumber;
    }

    private void setRating(float rating){
        this.ratingNumber= rating;
    }
}
