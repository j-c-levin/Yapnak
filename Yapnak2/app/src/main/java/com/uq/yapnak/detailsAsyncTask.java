package com.uq.yapnak;

import android.os.AsyncTask;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.yapnak.gcmbackend.userEndpointApi.UserEndpointApi;
import com.yapnak.gcmbackend.userEndpointApi.model.UserDetailsEntity;

import java.io.IOException;

/**
 * Created by Joshua on 07/10/2015.
 */
public class detailsAsyncTask extends AsyncTask<String,Void,Void> {

    @Override
    protected Void doInBackground(String... params) {
        Log.d("debug", "began");
        UserEndpointApi user = new UserEndpointApi(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null);
        Log.d("debug", "built: " + params[0]);

        try {
            ParsePush.subscribeInBackground(params[0]);
            UserDetailsEntity result = user.getUserDetails().setUserId(params[0]).execute();
            Log.d("debug", "got results");
            Log.d("debug", "Found user points:" + result.getLoyaltyPoints());
            if (result.getLoyaltyPoints() <= 5 ) {
                ParsePush.subscribeInBackground("new");
            } else if (result.getLoyaltyPoints() <= 10 ) {
                ParsePush.unsubscribeInBackground("new");
                ParsePush.subscribeInBackground("once");
            } else {
                ParsePush.unsubscribeInBackground("new");
                ParsePush.unsubscribeInBackground("once");
                ParsePush.subscribeInBackground("repeat");
            }
            Log.d("debug", "subscriptions: " + ParseInstallation.getCurrentInstallation().getList("channels").toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return null;
        }
    }

}
