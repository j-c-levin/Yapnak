package com.uq.yapnak;

import android.content.Context;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.yapnak.gcmbackend.sQLEntityApi.SQLEntityApi;
import com.yapnak.gcmbackend.sQLEntityApi.model.SQLEntity;

import java.io.IOException;
import java.util.List;

/**
 * Created by Joshua on 17/05/2015.
 */
public class SQLConnectAsyncTask extends AsyncTask<Void, Void, SQLEntity> {

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
    protected SQLEntity doInBackground(Void... params) {
        SQLEntityApi.Builder builder = new SQLEntityApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                .setRootUrl("https://yapnak-app.appspot.com/_ah/api/");
        sqlEntity = builder.build();
        try {
//            real code to use to get location
//            SQLEntity x = sqlEntity.getClients(location.getLatitude(),location.getLongitude()).execute();
//            hard-coded just for debugging purposes because it's where I've stuck in some dummy data
            SQLEntity x = sqlEntity.getClients(51.685292,-0.308850).execute();
            List t = x.getList();
            if (t == null) {
            Log.d("Debug", "SQL failed: " + location.getLongitude() + " " + location.getLatitude());
                return null;
            }
            else {
                Log.d("debug", "size is: " + t.size());
            }
            return x;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void onPostExecute(SQLEntity result) {
        if (result != null) {
            Log.d("Debug", "Completed.");
            main.load(result);
        } else {
            Log.d("Debug", "Failed to connect to SQL");
        }
    }
}