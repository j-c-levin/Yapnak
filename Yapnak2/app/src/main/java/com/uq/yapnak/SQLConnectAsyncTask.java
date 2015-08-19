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
import com.yapnak.gcmbackend.sQLEntityApi.model.SQLEntity;
import com.yapnak.gcmbackend.sQLEntityApi.model.SQLList;
import com.yapnak.gcmbackend.sQLEntityApi.model.UserEntity;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Joshua on 17/05/2015.
 */
public class SQLConnectAsyncTask extends AsyncTask<Void, Integer, SQLList> {

    private static SQLEntityApi sqlEntity;
     static boolean useDialog;
    private static boolean listLoaded=false;
    private Context context;
    private Location location;
    MainActivity main;
    private String ID;

    private static void setListLoaded(boolean loaded){
        listLoaded = loaded;
    }

    public static boolean getListLoaded(){
        return listLoaded;
    }

    private ProgressDialog progressDialog;

    SQLConnectAsyncTask(Context context, Location location, MainActivity main) {
        this.context = context;
        this.location = location;
        this.main = main;
        this.progressDialog = new ProgressDialog(this.main);

    }

    private GPSTrack track;
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

            //track = new GPSTrack(main);
            //Location loc = track.getLocation();

            SQLList result = sqlEntity.getClients(location.getLatitude(),location.getLongitude(),main.getID()).execute();

            ArrayList<SQLEntity> showOffers=new ArrayList<>();

            for(int i=0;i<result.getList().size();i++){
                if(result.getList().get(i).getShowOffer()==1){
                    showOffers.add(result.getList().get(i));
                }
            }

            SQLList showOffersList = new SQLList();
            showOffersList.setList(showOffers);

            return showOffersList;


        } catch (IOException e) {
            e.printStackTrace();

        }catch(NullPointerException ex){
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        //super.onPreExecute();
        if(useDialog) {
            progressDialog.setMessage("Please Wait For List To Load...");
            progressDialog.show();
        }
    }



    protected void onPostExecute(SQLList result) {
        if (result != null && location!=null) {
            Log.d("Debug", "completed: " + result.getList().size());
            main.load(result);
            main.floatButton();
            main.swipeRefresh(main);

            if(useDialog) {
                progressDialog.cancel();
            }
            setListLoaded(true);
        } else {
            main.load();
            main.floatButton();
            main.swipeRefresh(main);
            if(useDialog) {
                progressDialog.cancel();
            }
            setListLoaded(true);
            Toast.makeText(context,"Please Turn Location On",Toast.LENGTH_SHORT).show();
            Log.d("Debug", "Failed");
        }
    }
}