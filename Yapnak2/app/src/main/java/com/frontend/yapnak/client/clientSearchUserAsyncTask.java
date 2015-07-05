package com.frontend.yapnak.client;

import android.os.AsyncTask;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.yapnak.gcmbackend.sQLEntityApi.SQLEntityApi;
import com.yapnak.gcmbackend.sQLEntityApi.model.PointsEntity;

import java.io.IOException;

/**
 * Created by Joshua on 17/05/2015.
 */
public class clientSearchUserAsyncTask extends AsyncTask<Void, Void, PointsEntity> {

    private static SQLEntityApi sqlEntity;
    String userID;
    String clientEmail;
    MainClientActivity main;

    clientSearchUserAsyncTask(MainClientActivity main, String userID, String clientEmail) {
        this.clientEmail = clientEmail;
        this.userID = userID;
        this.main = main;
    }

    @Override
    protected PointsEntity doInBackground(Void... params) {
        SQLEntityApi.Builder builder = new SQLEntityApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                .setRootUrl("https://yapnak-app.appspot.com/_ah/api/");
        sqlEntity = builder.build();
        try {
            PointsEntity x = sqlEntity.getUser(userID, "joshua.c.levin@gmail.com").execute();
            if (x == null) {
                Log.d("Debug", "user not found");
                return null;
            }
            else {
                Log.d("debug", "found user: " + x.getUserID() + " : " + x.getPoints());
            }
            return x;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void onPostExecute(PointsEntity result) {
        if (result != null) {
            Log.d("Debug", "Completed.");
            main.load(result);
        } else {
            Log.d("Debug", "Failed to find user");
        }
    }
}