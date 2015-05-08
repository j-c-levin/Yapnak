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
class UserRegistrationAsyncTask extends AsyncTask<Void, Void, Integer> {

    private static UserEntityApi myApiService = null;
    private Context context;
    private String[] details = null;

    UserRegistrationAsyncTask(Context context, String[] details) {
        this.context = context;
        this.details = details;
    }

    @Override
    protected Integer doInBackground(Void... params) {
        //check if user exists
        UserEntityApi.Builder builder = new UserEntityApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                .setRootUrl("https://yapnak-app.appspot.com/_ah/api/");
        myApiService = builder.build();
        try {
            String p = details[0];
            UserEntity x = myApiService.get(p).execute();
            Log.d("Debug", "received entity " + x.getEmail());
        }catch (IOException e) {

        }
        return 1;
    }


    protected void onPostExecute(Integer result) {
        if (result == 1) {
            //Toast.makeText(context, "done", Toast.LENGTH_LONG).show();
            Log.d("Debug", "successfully logged in");
        }
    }
}
