package com.example.nand.abc;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.yapnak.gcmbackend.userEntityApi.UserEntityApi;
import com.yapnak.gcmbackend.userEntityApi.model.UserEntity;

import java.io.IOException;

/**
 * Created by Joshua on 16/05/2015.
 */
public class UserRegistrationAsyncTask extends AsyncTask<Void, Void, Integer> {

    private static UserEntityApi userEntity = null;
    private Context context;
    private String[] details = null;

    UserRegistrationAsyncTask(Context context, String[] details) {
        this.context = context;
        this.details = details;
/*      details[0] = id,
        details[1] = phone number,
        details[2] = promo code*/

    }

    @Override
    protected Integer doInBackground(Void... params) {
        //check if user exists
        UserEntityApi.Builder builder = new UserEntityApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                .setRootUrl("https://yapnak-app.appspot.com/_ah/api/");
        userEntity = builder.build();
        try {
            Log.d("Debug", "User does not exist, creating");
            //Create a new user with basic details
            UserEntity x = new UserEntity();
            x.setId(details[0]);
            x.setPhoneNumber(details[1]);
            userEntity.insert(x).execute();
            Log.d("Debug", "successfully inserted user");
            //Add referer ID to points
            if (!details[2].equals(""))
            {
                Log.d("Debug", "here it is:"+details[2].toString()+":here");
                    /*TODO: create a datastore search where it finds a pointsEntity where...hmm...
                    TODO: Where there referrerID is a match...and needs something else to distinguish this referral from any other referrals the user has made.*/
            }
            return 1;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }


    protected void onPostExecute(Integer result) {
        if (result == 1) {
            Log.d("Debug", "successfully registered");
            //Toast.makeText(context, "successfully registered", Toast.LENGTH_LONG).show();
        }
        else {
            Log.d("Debug", "failed logged in");
        }
    }
}
