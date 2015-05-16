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
 * Created by Joshua on 15/04/2015.
 */
class UserLoginAsyncTask extends AsyncTask<Void, Void, Integer> {

    private static UserEntityApi userEntity = null;
    private Context context;
    private String[] details = null;

    UserLoginAsyncTask(Context context, String[] details) {
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
            // details[0];
            UserEntity x = userEntity.get(details[0]).execute();
            Log.d("Debug", x.toString());
            if (x.getId() != null) {
                return 1;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }


    protected void onPostExecute(Integer result) {
        if (result == 1) {
            //Toast.makeText(context, "done", Toast.LENGTH_LONG).show();
            Log.d("Debug", "successfully logged in");
        } else {
            Log.d("Debug", "failed logged in");
            new UserRegistrationAsyncTask(context, details).execute();
        }
    }
}


