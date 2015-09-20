package com.frontend.yapnak.rate;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.frontend.yapnak.ItemPrev;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.uq.yapnak.R;
import com.yapnak.gcmbackend.userEndpointApi.UserEndpointApi;
import com.yapnak.gcmbackend.userEndpointApi.model.FeedbackEntity;

import java.io.IOException;

/**
 * Created by vahizan on 11/07/2015.
 */
public class RatingBuilder extends AlertDialog {

    private Activity activity;
    private Context context;
    private RatingBar rating;
    private Button submit,cancel;
    private RadioGroup radioB;
    private View v;
    private float ratingNumber;
    private AlertDialog d;
    private ItemPrev item;
    private String ID;
    private EditText comment;
    public RatingBuilder(Context context,Activity activity,ItemPrev item,String ID) {
        super(context);

        this.context=context;
        this.activity=activity;
        d = this;
        this.item =item;
        this.ID = ID;

        LayoutInflater inflater = this.activity.getLayoutInflater();

         v = inflater.inflate(R.layout.ratings_dialog,null);


        rating = (RatingBar) v.findViewById(R.id.rateDeal);

        comment = (EditText) v.findViewById(R.id.ratingComment);

        rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                setRating(Float.parseFloat(String.valueOf(rating)));
            }
        });

        radioB = (RadioGroup) v.findViewById(R.id.storeCodeAccept);

        radioB.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton b = (RadioButton) v.findViewById(checkedId);
                if (b.getText().toString().equalsIgnoreCase("yes")) {
                    setAccepted(true);
                } else {
                    setAccepted(false);
                }
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
        //title.setBackgroundColor(Color.parseColor("#FFAB91"));
        title.setTextColor(Color.parseColor("#BF360C"));
        title.setGravity(Gravity.LEFT);

        setCustomTitle(title);

    }

    private void submitClick(){
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = (comment.getText().toString().length()==0) ? "" : comment.getText().toString();
                if(comment.getText().toString().length()!=0){

                }
                new RateDeal().execute(s);

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
    private boolean accept;
    private void setAccepted(boolean accept){
        this.accept = accept;
    }
    private boolean getAccepted(){
        return accept;
    }


    private class RateDeal extends AsyncTask<String,Void,FeedbackEntity> {

        private ProgressDialog progress;
        private boolean success;

        protected RateDeal(){
            progress = new ProgressDialog(context);
        }

        @Override
        protected FeedbackEntity doInBackground(String... params) {
            UserEndpointApi user = new UserEndpointApi(AndroidHttp.newCompatibleTransport(),new AndroidJsonFactory(),null);
            String fbk ="";
            if(connection()) {
                try {
                    FeedbackEntity feedback;
                    if (params[0].length()>0) {
                        feedback = user.feedback((int) item.getClientID(), getAccepted(), (int) item.getOfferID(), (int) getRating(), ID).setComment(params[0]).execute();

                    } else {

                        feedback = user.feedback((int) item.getClientID(), getAccepted(), (int) item.getOfferID(), (int) getRating(), ID).execute();
                        //fbk = "Feedback Message: " + feedback.getMessage() + "\nStatus: " + feedback.getStatus();
                    }

                    return feedback;
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }else{
                return null;
            }

        }

        @Override
        protected void onPreExecute() {
            progress.setMessage("Submitting Feedback...");
            progress.setCanceledOnTouchOutside(false);
            progress.setCancelable(false);
            new Handler().post(new Runnable() {
                @Override
                public void run(){progress.show();}});}

        @Override
        protected void onPostExecute(FeedbackEntity s) {
            super.onPostExecute(s);

            try{

                if(Boolean.parseBoolean(s.getStatus())) {
                    Toast.makeText(context, "Feedback/Rating Submitted", Toast.LENGTH_SHORT).show();
                    if(progress.isShowing()){
                        progress.cancel();
                    }
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            d.dismiss();
                        }
                    }, 1000);

                }else if(!connection()){
                    if(progress.isShowing()){
                        progress.cancel();
                    }
                    Toast.makeText(context,"Please Enable Your Internet Connection",Toast.LENGTH_SHORT).show();

                }else{
                    if(progress.isShowing()){
                        progress.cancel();
                    }
                    Toast.makeText(context,"Submission Error, Please Try Again",Toast.LENGTH_SHORT).show();
                }

            }catch(NullPointerException e){
                if(progress.isShowing()){
                    progress.cancel();
                }
                Toast.makeText(context,"Submission Error, Please Try Again",Toast.LENGTH_SHORT).show();
            }

        }
    }

    private boolean connection(){
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
        boolean lte = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();

        return (wifi||lte);
    }
}
