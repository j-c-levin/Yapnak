package com.uq.yapnak;

import android.content.Context;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.yapnak.gcmbackend.sQLEntityApi.SQLEntityApi;
import com.yapnak.gcmbackend.sQLEntityApi.model.SQLList;

import java.io.IOException;

/**
 * Created by Joshua on 17/05/2015.
 */
public class SQLConnectAsyncTask extends AsyncTask<Void, Void, SQLList> {

    private static SQLEntityApi sqlEntity;
    private Context context;
    private Location location;
    MainActivity main;

    SQLConnectAsyncTask(Context context, Location location, MainActivity main) {
        this.context = context;
        this.location = location;
        this.main = main;
/*      details[0] = id,
        details[1] = phone number,
        details[2] = promo code*/
    }

    @Override
    protected SQLList doInBackground(Void... params) {
        SQLEntityApi.Builder builder = new SQLEntityApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                .setRootUrl("https://yapnak-app.appspot.com/_ah/api/");
        sqlEntity = builder.build();
        try {
//            real code to use to get location
//            SQLEntity x = sqlEntity.getClients(location.getLongitude(), location.getLatitude()).execute();
//            hard-coded just for debugging purposes because it's where I've stuck in some dummy data
            SQLList x = sqlEntity.getClients(-0.308850, 51.685292).execute();
            if (x == null) {
            Log.d("Debug", "SQL failed: " + location.getLongitude() + " " + location.getLatitude());
                return null;
            }
            else {
                Log.d("debug", "size is: " + x.size());
            }
            return x;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void onPostExecute(SQLList result) {
        if (result != null) {
            Log.d("Debug", "Completed.");
            main.load(result);
        } else {
            Log.d("Debug", "Failed to connect to SQL");
        }
    }
}