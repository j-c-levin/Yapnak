package com.frontend.yapnak.rate;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.uq.yapnak.R;

/**
 * Created by vahizan on 11/07/2015.
 */
public class RatingBuilder extends AlertDialog {

    private Activity activity;
    private Context context;
    private RatingBar rating;
    private Button submit,cancel;
    private View v;
    private float ratingNumber;
    private AlertDialog d;

    public RatingBuilder(Context context,Activity activity) {
        super(context);

        this.context=context;
        this.activity=activity;
        d = this;

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

        submit = (Button)v.findViewById(R.id.submitRating);
        cancel = (Button)v.findViewById(R.id.cancelRating);
        submitClick();
        cancelClick();


        final TextView title = new TextView(getContext());
        title.setText("Feedback");
        title.setTextSize(25);
        title.setPadding(20, 40, 0, 40);
        title.setBackgroundColor(Color.parseColor("#FFAB91"));
        title.setTextColor(Color.parseColor("#BF360C"));
        title.setGravity(Gravity.LEFT);

        setCustomTitle(title);

    }

    private void submitClick(){
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });
    }

    private void cancelClick(){
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.cancel();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Resources res = getContext().getResources();

        final int id = res.getIdentifier("titleDivider", "id", "android");

        final View v = findViewById(id);

        if(v!=null) {
            v.setBackgroundColor(Color.parseColor("#BF360C"));
        }
    }

    public float getRating(){
        return ratingNumber;
    }

    private void setRating(float rating){
        this.ratingNumber= rating;
    }
}
