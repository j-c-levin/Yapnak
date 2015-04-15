package com.example.nand.abc;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.yapnak.backend.userApi.UserApi;
import com.yapnak.backend.userApi.model.User;

import java.io.IOException;

/**
 * Created by Joshua on 15/04/2015.
 */
class EndpointsAsyncTask extends AsyncTask<Void, Void, Integer> {

    private static UserApi myApiService = null;
    private Context context;

    EndpointsAsyncTask(Context context) {
        this.context = context;
    }

    @Override
    protected Integer doInBackground(Void... params) {
        UserApi.Builder builder = new UserApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                .setRootUrl("https://yapnak-app.appspot.com/_ah/api/");
        myApiService = builder.build();
        User newUser = new User();
        newUser.setId("jl123");
        newUser.setEmail("joshua.c.levin@gmail.com");
        newUser.setFirstName("Joshua");
        newUser.setLastName("Levin");
        try {
            myApiService.insert(newUser).execute();
            Log.d("Debug", "sent");
        } catch (IOException e) {

        }
        return 1;
    }


    protected void onPostExecute(Integer result) {
        if (result == 1) {
            Log.d("Debug", "pre-toast");
            Toast.makeText(context, "done", Toast.LENGTH_LONG).show();
            Log.d("Debug", "toast");
        }
    }
}
