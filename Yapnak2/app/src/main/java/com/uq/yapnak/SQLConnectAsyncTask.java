package com.uq.yapnak;

import android.app.ProgressDialog;
import android.content.Context;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.frontend.yapnak.subview.MyProgressDialog;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.yapnak.gcmbackend.sQLEntityApi.SQLEntityApi;
import com.yapnak.gcmbackend.sQLEntityApi.model.SQLEntity;
import com.yapnak.gcmbackend.sQLEntityApi.model.SQLList;
import com.yapnak.gcmbackend.userEndpointApi.UserEndpointApi;
import com.yapnak.gcmbackend.userEndpointApi.model.OfferEntity;
import com.yapnak.gcmbackend.userEndpointApi.model.OfferListEntity;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Joshua on 17/05/2015.
 */
public class SQLConnectAsyncTask extends AsyncTask<Void, Integer, OfferListEntity> {

    //private static SQLEntityApi sqlEntity;
    static boolean useDialog;
    private static OfferListEntity listEntity;
    static boolean restore;
    static boolean loaded;
    private static boolean listLoaded=false;
    private Context context;
    private Location location;
    MainActivity main;
    private String ID;
    private String restaurant;
    private boolean notLocation;

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
        notLocation=false;


    }

    SQLConnectAsyncTask(Context context, MainActivity main,OfferListEntity list) {
        this.context = context;
        this.main = main;
        listEntity = list;
        notLocation=true;
        this.progressDialog = new ProgressDialog(this.main);


    }



    private GPSTrack track;
    @Override
    protected OfferListEntity doInBackground(Void... params) {
        /*if (sqlEntity == null) {
            SQLEntityApi.Builder builder = new SQLEntityApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                    .setRootUrl("https://yapnak-app.appspot.com/_ah/api/");
            builder.setApplicationName("Yapnak");
            sqlEntity = builder.build();
        }
        */
//            real code to use to get location
//            SQLEntity x = sqlEntity.getClients(location.getLongitude(), location.getLatitude()).execute();
//            hard-coded just for debugging purposes because it's where I've stuck in some dummy data

        UserEndpointApi userApi = new UserEndpointApi(AndroidHttp.newCompatibleTransport(),new AndroidJsonFactory(),null);


        try {

            //track = new GPSTrack(main);
            //Location loc = track.getLocation();
            /*SQLList result = sqlEntity.getClients(location.getLatitude(),location.getLongitude(),main.getID()).execute();

            ArrayList<SQLEntity> showOffers=new ArrayList<>();

            for(int i=0;i<result.getList().size();i++){
                if(result.getList().get(i).getShowOffer()==1){
                    showOffers.add(result.getList().get(i));
                }
            }

            SQLList showOffersList = new SQLList();
            showOffersList.setList(showOffers);
          */
            if(!notLocation){
                if(location!=null) {
                    listEntity = userApi.getClients(location.getLatitude(), location.getLongitude()).execute();
                }else{
                    listEntity=null;
                }
            }
            /*if(location!=null && restaurant!=null ){
                ArrayList<OfferEntity> temp = new ArrayList<>();

                OfferListEntity loc = userApi.getClients(location.getLatitude(), location.getLongitude()).execute();
                OfferListEntity res = userApi.searchClients(restaurant).execute();
                int countRes = res.getOfferList().size();
                int countLoc = loc.getOfferList().size();

                if(countLoc<countRes){
                    int count = 0;
                    while(count!=countLoc){

                        if(loc.getOfferList().get(count).getOfferId()==res.getOfferList().get(count).getOfferId()){
                            temp.add(loc.getOfferList().get(count));
                        }else{
                            temp.add(loc.getOfferList().get(count));
                            temp.add(res.getOfferList().get(count));
                        }
                        count++;
                    }
                    OfferListEntity joined = new OfferListEntity();
                    joined.setOfferList(temp);
                    joined.setStatus(loc.getStatus());
                    joined.setMessage(loc.getMessage());
                    joined.setFoundOffers(loc.getFoundOffers());
                    joined.setFactory(loc.getFactory());
                    joined.setUnknownKeys(loc.getUnknownKeys());
                    listEntity = joined;

                } else{

                    int count = 0;
                    while(count!=countRes){

                        if(res.getOfferList().get(count).getOfferId()==loc.getOfferList().get(count).getOfferId()){
                            temp.add(res.getOfferList().get(count));
                        }else{
                            temp.add(loc.getOfferList().get(count));
                            temp.add(res.getOfferList().get(count));
                        }
                        count++;
                    }
                    OfferListEntity joined = new OfferListEntity();
                    joined.setOfferList(temp);
                    joined.setStatus(res.getStatus());
                    joined.setMessage(res.getMessage());
                    joined.setFoundOffers(res.getFoundOffers());
                    joined.setFactory(res.getFactory());
                    joined.setUnknownKeys(res.getUnknownKeys());
                    listEntity = joined;
                }



            }*/

            /*else if(location!=null){
                listEntity = userApi.getClients(location.getLatitude(),location.getLongitude()).execute();

            }*/


            return listEntity;


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
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setCancelable(false);
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    progressDialog.show();
                }
            });

        }
    }



    protected void onPostExecute(OfferListEntity result) {
        if (result != null) {
            //Log.d("Debug", "completed: " + result.getOfferList().size()  + "\nSTATUS: "+ result.getStatus() +"\nMessage: "+ result.getMessage());
            main.load(result);
            main.floatButton();
            main.setList(result);
            main.swipeRefresh(main);

            if (useDialog) {
                progressDialog.cancel();
                loaded = true;
            }
            setListLoaded(true);

        }else if(restore){

            main.load(main.getList());
            main.floatButton();
            main.swipeRefresh(main);
            if(useDialog) {
                progressDialog.cancel();
                loaded=true;
            }
            setListLoaded(true);

            Toast.makeText(context,"Session Restored!",Toast.LENGTH_SHORT).show();

        } else {
            main.load();
            main.setList(result);;
            main.floatButton();
            main.swipeRefresh(main);
            if(useDialog) {
                progressDialog.cancel();
                loaded=true;
            }
            setListLoaded(true);
            Toast.makeText(context,"Please Turn Location/Internet On",Toast.LENGTH_SHORT).show();
            Log.d("Debug", "Failed");
        }
    }

}