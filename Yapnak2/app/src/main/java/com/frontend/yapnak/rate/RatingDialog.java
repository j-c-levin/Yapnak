package com.frontend.yapnak.rate;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.frontend.yapnak.ItemPrev;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.uq.yapnak.R;
import com.yapnak.gcmbackend.userEndpointApi.UserEndpointApi;
import com.yapnak.gcmbackend.userEndpointApi.model.FeedbackEntity;

import java.io.IOException;

/**
 * Created by vahizan on 03/06/2015.
 */
public class RatingDialog extends DialogFragment {
    private float ratingNumber;
    private View dialogView;
    private RatingBar rating;
    private TextView ratingComment;
    private ItemPrev item;
    private String ID;

    public RatingDialog(ItemPrev item,String id){
        this.item = item;
        this.ID = id;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        dialogView = inflater.inflate(R.layout.ratings_dialog,null);
        builder.setView(dialogView);

        rating = (RatingBar) dialogView.findViewById(R.id.rateDeal);
        ratingComment = (TextView) dialogView.findViewById(R.id.ratingComment);


        rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                setRating(rating);
                TextView text = (TextView) dialogView.findViewById(R.id.ratingNumber);
                text.setText(String.valueOf(rating));

            }
        });

        //Add Action Buttons Cancel and OK
        builder.setPositiveButton(R.string.OK,new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


               //Store ratingNumber in sql database and average results to get general Rating.
               //Need to pass the ID of the card selected from MainActivity, add rating against the corresponding cardID
                new RateDeal().execute();
                RatingDialog.this.getDialog().dismiss();

            }
        })


       .setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialog, int which) {

               RatingDialog.this.getDialog().cancel();

           }
       });

        return builder.create();


    }

    public float getRating(){
        return ratingNumber;
    }

    private void setRating(float rating){
        this.ratingNumber= rating;
    }

    private class RateDeal extends AsyncTask<Void,Void,String>{

        @Override
        protected String doInBackground(Void... params) {
            UserEndpointApi user = new UserEndpointApi(AndroidHttp.newCompatibleTransport(),new AndroidJsonFactory(),null);

            try {
                FeedbackEntity feedback= user.feedback((int)item.getClientID(), false, (int)item.getOfferID(), (int) getRating(), ID).execute();
                String fbk = "Feedback Message: "+ feedback.getMessage() + "\nStatus: " +feedback.getStatus();
                return fbk;
            }catch(IOException e){
                e.printStackTrace();
                return null;
            }

        }

        @Override
        protected void onPostExecute(String s) {
            Log.d("feedbackMessage",s);
            super.onPostExecute(s);
        }
    }
}
