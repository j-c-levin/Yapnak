package com.uq.yapnak;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
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
import java.util.List;

/**
 * Created by Joshua on 17/05/2015.
 */
public class SQLConnectAsyncTask extends AsyncTask<Void, Integer, OfferListEntity> {

    //private static SQLEntityApi sqlEntity;
    private static OfferListEntity listEntity;
    static boolean useDialog;

    static boolean restore;
    static boolean loaded;
    private static boolean listLoaded=false;
    private Context context;
    private Location location;
    MainActivity main;
    private String ID;
    private String restaurant;

    private static void setListLoaded(boolean loaded){
        listLoaded = loaded;
    }

    public static boolean getListLoaded(){
        return listLoaded;
    }

    private ProgressDialog progressDialog;

    SQLConnectAsyncTask(Context context, Location location,String restaurant, MainActivity main) {
        this.context = context;
        this.location = location;
        this.main = main;
        this.progressDialog = new ProgressDialog(this.main);
        this.restaurant = restaurant;

    }



    private GPSTrack track;
    @Override
    protected OfferListEntity doInBackground(Void... params) {
       /* if (sqlEntity == null) {
            SQLEntityApi.Builder builder = new SQLEntityApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                    .setRootUrl("https://yapnak-app.appspot.com/_ah/api/");
            builder.setApplicationName("Yapnak");
            sqlEntity = builder.build();
        }*/
//            real code to use to get location
//            SQLEntity x = sqlEntity.getClients(location.getLongitude(), location.getLatitude()).execute();
//            hard-coded just for debugging purposes because it's where I've stuck in some dummy data
        UserEndpointApi user = new UserEndpointApi(AndroidHttp.newCompatibleTransport(),new AndroidJsonFactory(),null);


        try {

            //track = new GPSTrack(main);
            //Location loc = track.getLocation();

            //SQLList result = sqlEntity.getClients(location.getLatitude(),location.getLongitude(),main.getID()).execute();

            /*ArrayList<SQLEntity> showOffers=new ArrayList<>();

            for(int i=0;i<result.getList().size();i++){
                if(result.getList().get(i).getShowOffer()==1){
                    showOffers.add(result.getList().get(i));
                }
            }

            SQLList showOffersList = new SQLList();
            showOffersList.setList(showOffers);*/
            if(location!=null && restaurant==null ) {
                listEntity = user.getClients(location.getLatitude(), location.getLongitude()).execute();
            }else if(restaurant!=null && location==null){
                listEntity= user.searchClients(restaurant).execute();

            }else {

                OfferListEntity loca = user.getClients(location.getLatitude(),location.getLongitude()).execute();
                OfferListEntity res = user.searchClients(restaurant).execute();
                boolean countRes = res.getOfferList().size()>1;
                boolean countLoc = loca.getOfferList().size()>1;
                if(!countRes){
                    listEntity=loca;
                }else if(!countLoc){
                    listEntity=res;
                }else{
                    List<OfferEntity> tempList = new ArrayList<>();
                    int resNumber = res.getOfferList().size();
                    int locNumber = loca.getOfferList().size();
                    if(locNumber<resNumber){
                        int finalNum =locNumber;
                        int count=0;
                        while(count!=loca.getOfferList().size()-1){
                            if(loca.getOfferList().get(count).getOfferId()!=res.getOfferList().get(count).getOfferId()){
                                tempList.add(loca.getOfferList().get(count));
                                tempList.add(res.getOfferList().get(count));
                            }else{
                                tempList.add(loca.getOfferList().get(count));
                            }
                        }
                        OfferListEntity tempEntity = new OfferListEntity();
                        tempEntity.setOfferList(tempList);
                        tempEntity.setMessage(loca.getMessage());
                        tempEntity.setStatus(loca.getStatus());
                        tempEntity.setFoundOffers(loca.getFoundOffers());
                        listEntity=tempEntity;

                    }else{
                        int finalNum =resNumber;
                        int count=0;
                        while(count!=res.getOfferList().size()-1){
                            if(loca.getOfferList().get(count).getOfferId()!=res.getOfferList().get(count).getOfferId()){
                                tempList.add(loca.getOfferList().get(count));
                                tempList.add(res.getOfferList().get(count));
                            }else{
                                tempList.add(res.getOfferList().get(count));
                            }
                        }
                        OfferListEntity tempEntity = new OfferListEntity();
                        tempEntity.setOfferList(tempList);
                        tempEntity.setMessage(res.getMessage());
                        tempEntity.setStatus(res.getStatus());
                        tempEntity.setFoundOffers(res.getFoundOffers());
                        listEntity=tempEntity;
                    }
                }
            }
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
        if (result != null && location!=null) {
            Log.d("Debug", "completed: " + result.getOfferList());
            main.load(result);
            main.floatButton();
            main.setList(result);
            main.swipeRefresh(main);

            if (useDialog) {
                progressDialog.cancel();
                loaded=true;
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