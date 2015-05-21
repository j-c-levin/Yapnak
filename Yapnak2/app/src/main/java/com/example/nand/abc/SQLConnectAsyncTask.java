package com.example.nand.abc;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.yapnak.gcmbackend.sQLEntityApi.SQLEntityApi;

import java.io.IOException;

/**
 * Created by Joshua on 17/05/2015.
 */
public class SQLConnectAsyncTask extends AsyncTask<Void, Void, Integer> {

    private static SQLEntityApi sqlEntity;
    private Context context;
    private String[] details = null;

    SQLConnectAsyncTask(Context context, String[] details) {
        this.context = context;
        this.details = details;
/*      details[0] = id,
        details[1] = phone number,
        details[2] = promo code*/

    }

    @Override
    protected Integer doInBackground(Void... params) {
        SQLEntityApi.Builder builder = new SQLEntityApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                .setRootUrl("https://yapnak-app.appspot.com/_ah/api/");
        sqlEntity = builder.build();
        try {
            sqlEntity.insert("testing").execute();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    protected void onPostExecute(Integer result) {
    Log.d("Debug", "Completed.");

    }
}