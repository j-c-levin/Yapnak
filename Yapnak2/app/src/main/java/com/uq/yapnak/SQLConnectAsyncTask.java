package com.uq.yapnak;

import android.app.ProgressDialog;
import android.content.Context;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.yapnak.gcmbackend.sQLEntityApi.SQLEntityApi;
import com.yapnak.gcmbackend.sQLEntityApi.model.SQLList;

import java.io.IOException;

/**
 * Created by Joshua on 17/05/2015.
 */
public class SQLConnectAsyncTask extends AsyncTask<Void, Integer, SQLList> {

    private static SQLEntityApi sqlEntity;
    private Context context;
    private Location location;
    MainActivity main;
    private ProgressDialog progressDialog;

    SQLConnectAsyncTask(Context context, Location location, MainActivity main) {
        this.context = context;
        this.location = location;
        this.main = main;
        this.progressDialog = new ProgressDialog(this.main);
    }

    @Override
    protected SQLList doInBackground(Void... params) {
        if (sqlEntity == null) {
            SQLEntityApi.Builder builder = new SQLEntityApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                    .setRootUrl("https://yapnak-app.appspot.com/_ah/api/");
            builder.setApplicationName("Yapnak");
            sqlEntity = builder.build();
        }
//            real code to use to get location
//            SQLEntity x = sqlEntity.getClients(location.getLongitude(), location.getLatitude()).execute();
//            hard-coded just for debugging purposes because it's where I've stuck in some dummy data
        try {
            return sqlEntity.getClients(-0.308850, 51.685292).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        //super.onPreExecute();
        progressDialog.setMessage("Please Wait For List To Load...");
        progressDialog.show();
    }


    protected void onPostExecute(SQLList result) {
        if (result != null) {
            Log.d("Debug", "completed: " + result.getList());
            main.load(result);
            progressDialog.cancel();
        } else {
            Log.d("Debug", "Failed");
        }
    }
}