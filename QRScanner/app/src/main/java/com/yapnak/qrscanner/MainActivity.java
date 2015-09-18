package com.yapnak.qrscanner;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.core.JsonParseException;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.json.JsonParser;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.yapnak.gcmbackend.clientEndpointApi.ClientEndpointApi;
import com.yapnak.gcmbackend.clientEndpointApi.model.ClientAuthEntity;
import com.yapnak.gcmbackend.clientEndpointApi.model.ClientEntity;
import com.yapnak.gcmbackend.clientEndpointApi.model.ClientOfferEntity;
import com.yapnak.gcmbackend.clientEndpointApi.model.ClientOfferListEntity;
import com.yapnak.gcmbackend.clientEndpointApi.model.RedemptionEntity;
import com.yapnak.gcmbackend.sQLEntityApi.SQLEntityApi;
import com.yapnak.gcmbackend.sQLEntityApi.model.QrEntity;


import net.sourceforge.zbar.Symbol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;




public class MainActivity extends Activity {

    private static final int ZBAR_RESULT = 12345;
    private Intent loginIntent;
    private long clientID;
    private TextView scanned,verified,offer,offerText,clientName;
    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginIntent=getIntent();
        scanQR();
        setContentView(R.layout.activity_main);
        clientID = loginIntent.getLongExtra("clientID",-1);
        scanned=(TextView) findViewById(R.id.scanned);
        verified= (TextView) findViewById(R.id.verified);
        //offer = (TextView) findViewById(R.id.offerID);
        offerText = (TextView) findViewById(R.id.offerText);
        clientName = (TextView) findViewById(R.id.clientName);

        pref = getSharedPreferences("clientlogin",Context.MODE_PRIVATE);



    }
    private boolean exit = false;
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (exit) {
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            pref.edit().putBoolean("on",false).apply();
            Intent i = new Intent(MainActivity.this, Login.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //OnClick
    public void scanQR(){
        //Intent camera = new Intent("com.google.zxing.client.android.SCAN");
        //camera.putExtra("SCAN_MODE", "QR_CODE_MODE");
        //startActivityForResult(camera, RESULT);
        zbarIntent();
    }

    private IntentIntegrator intent(){
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setCameraId(0)
                .setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES)
                .setPrompt("Scan Your QR Code Within Viewfinder")
                .setCaptureLayout(R.layout.capture_layout)
                .setLegacyCaptureLayout(R.layout.legacy_capture_layout)
                .setOrientation(90);

        /*integrator.setCameraId(0)
                .setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES)
                .setCaptureActivity(oCaptureActivity.class)
                .setOrientationLocked(false)
                .setPrompt("Scan QR Code")
                .setBeepEnabled(false);*/

        return integrator;
    }

    private void zbarIntent(){
        if(cameraAvailable()) {
            Intent intent = new Intent(this, ZBarScannerActivity.class);
            intent.putExtra(ZBarConstants.SCAN_MODES,new int[]{Symbol.QRCODE});
            startActivityForResult(intent,ZBAR_RESULT);
        }else{
            Toast.makeText(this, "Camera Unavailable", Toast.LENGTH_SHORT).show();
        }

    }

    private boolean cameraAvailable(){
        PackageManager manager = getPackageManager();
        return manager.hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    private String userID;
    private String clientIDString;
    private String offerID ;
    private boolean errorOccured;
    private String level,userName;
    private boolean internetOn;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(requestCode==ZBAR_RESULT){
            if(resultCode == Activity.RESULT_OK){
                String result = data.getStringExtra(ZBarConstants.SCAN_RESULT);


                if(connection()) {
                    if (result != null) {
                        String d = result;
                        JSONObject object=null;


                       /*try {
                           object = new JSONObject(d);
                           userID = object.getString("id");
                           offerID = object.getString("offer");

                           int offers = (int) Long.parseLong(offerID);
                           //new GetOffer().execute(offers);
                           new GetReward().execute(offers);
                           new GetClientInfo().execute();
                           Log.d("qrcode", "REDEEM DEAL");

                           Handler h = new Handler();
                           h.postDelayed(new Runnable() {
                               @Override
                               public void run() {
                                   intent().initiateScan();
                               }
                           }, 2000);

                       } catch (JSONException e) {
                           e.printStackTrace();
                       }


                       try{
                           object = new JSONObject(d);
                           level = object.getString("level");
                           userName = object.getString("id");
                           new RedeemUser().execute(userName);
                           Log.d("qrcode", "REDEEM REWARDS");


                           Handler h = new Handler();
                           h.postDelayed(new Runnable() {
                               @Override
                               public void run() {
                                   intent().initiateScan();
                               }
                           }, 2000);

                       }catch (JSONException e){
                       }
                       */


                        try{
                            object = new JSONObject(d);

                            boolean isReward = Boolean.parseBoolean(object.getString("isReward"));
                            if(isReward){
                                level = object.getString("level");
                                userName = object.getString("id");
                                new RedeemUser().execute(userName);
                                Log.d("qrcode", "REDEEM REWARDS");
                            }else{
                                userID = object.getString("id");
                                offerID = object.getString("offer");
                                int offers = (int) Long.parseLong(offerID);
                                //new GetOffer().execute(offers);
                                new GetReward().execute(offers);
                                new GetClientInfo().execute();
                                Log.d("qrcode", "REDEEM DEAL");
                            }


                        }catch(JSONException e){

                        }

                        Handler h = new Handler();
                        h.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //intent().initiateScan();
                                zbarIntent();
                            }
                        }, 2000);

                        //new SubmitQR(loginIntent.getLongExtra("clientID", -1)).execute(d);
                        //scanned.setText("CODE SCANNED");
                        //verified.setText("USER IS VERIFIED");
                        //Toast.makeText(this, "USER ID: " + userID  +"\nOFFER ID: "+ offerID +"\nCLIENT ID: "+ clientID , Toast.LENGTH_SHORT).show();


                    }else {
                        scanned.setText("SCAN ERROR");
                        verified.setText("INFORMATION IS UNAVAILABLE");
                        //offer.setText("N/A");
                        clientName.setText("N/A");

                        Handler h = new Handler();
                        h.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                               // intent().initiateScan();
                                zbarIntent();
                            }
                        }, 1000);
                    }
                }else{
                    scanned.setText("SCAN ERROR");
                    verified.setText("NO INTERNET");
                    offerText.setText("N/A");
                    clientName.setText("PLEASE TURN ON INTERNET");
                    Handler h = new Handler();
                    h.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                         //   intent().initiateScan();
                            zbarIntent();
                        }
                    }, 1000);
                }


            }
        }
        if(resultCode == Activity.RESULT_OK){
            /*if(requestCode == RESULT){

                String d = data.getStringExtra("SCAN_RESULT");
                String JSON = data.getStringExtra("SCAN_RESULT_FORMAT");

                Toast.makeText(this,"DATA "+ d +" FORMAT: "+ JSON,Toast.LENGTH_LONG).show();

            }
            */
            //IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        }
    }


    private boolean connection(){

        ConnectivityManager manager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
        boolean lte = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
        return (lte||wifi);
    }

    private String textOffer;


    private class RedeemUser extends AsyncTask<String,Void,Void>{

        private boolean status;
        private String rewardText;

        @Override
        protected Void doInBackground(String... params) {
            ClientEndpointApi.Builder builder = new ClientEndpointApi.Builder(AndroidHttp.newCompatibleTransport(),new AndroidJsonFactory(),null);
            ClientEndpointApi api = builder.build();

            try{
                RedemptionEntity e = api.redeemUser((int) clientID, params[0]).execute();
                status = Boolean.parseBoolean(e.getStatus());
                rewardText = e.getOfferText();

            }catch (IOException e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (connection()) {
                if (status) {
                    //offer.setText("REWARD REDEEMED");
                    offerText.setText("REWARD:" + rewardText);
                    scanned.setText("SCANNED SUCCESSFULLY");
                    verified.setText("VALID");
                    clientName.setText("");
                } else {
                    //offer.setText("REDEEMED ALREADY");
                    offerText.setText("REWARD:" + rewardText);
                    scanned.setText("SCAN FAILED");
                    verified.setText("INVALID CODE");
                }
            } else {
                scanned.setText("SCAN ERROR");
                verified.setText("NO INTERNET");
                //offer.setText("");
                offerText.setText("PLEASE TURN ON INTERNET");
                clientName.setText("");
            }
        }
    }

    private class GetClientInfo extends AsyncTask<Void,Void,String>{


        @Override
        protected String doInBackground(Void... params) {

            ClientEndpointApi.Builder builder = new ClientEndpointApi.Builder(AndroidHttp.newCompatibleTransport(),new AndroidJsonFactory(),null);
            ClientEndpointApi api = builder.build();
            try {

                if (pref != null) {
                    String email = pref.getString("email", "-1");
                    if (!email.equalsIgnoreCase("-1")) {
                        ClientEntity e = api.getClientInfo(email).execute();

                        return e.getName();
                    }
                    return null;
                }
                return null;

            }catch (IOException io){
                io.printStackTrace();
                return null;
            }

        }

        @Override
        protected void onPostExecute(String aVoid) {
            super.onPostExecute(aVoid);

            try {
                if (aVoid == null) {
                    clientName.setText("CLIENT NAME: N/A");
                } else {
                    clientName.setText("CLIENT NAME: " + aVoid);
                }
            }catch (NullPointerException e){
                e.printStackTrace();
            }
        }
    }

    private boolean rewardStatus;
    private class GetReward extends AsyncTask<Integer,Void,Boolean>{

        private long loyaltyPoints;
        private String offerT;
        private String clientN;
        private boolean status;
        @Override
        protected Boolean doInBackground(Integer... params) {
            ClientEndpointApi.Builder builder = new ClientEndpointApi.Builder(AndroidHttp.newCompatibleTransport(),new AndroidJsonFactory(),null);
            ClientEndpointApi api = builder.build();
            try{
                RedemptionEntity e = api.qrRedeem((int) clientID, params[0], userID).execute();
                offerT = e.getOfferText();
                loyaltyPoints = e.getLoyaltyPoints();
                status = Boolean.parseBoolean(e.getStatus());
                return status;

            }catch (IOException e){
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean aVoid) {
            super.onPostExecute(aVoid);

            //try {
            //rewardStatus = status;

            if (aVoid) {
                scanned.setText("CODE SCANNED");
                verified.setText("USER VERIFIED");
                //offer.setText("OFFER ID: " + offerID);
                offerText.setText(offerT);
                textOffer = offerT;
            } else {
                //offer.setText("OFFER INVALID");
                textOffer = offerT;
                offerText.setText("N/A");
                scanned.setText("SCAN ERROR");
                verified.setText("NOT VERIFIED");
            }



            /*} catch (NullPointerException e) {
                //offer.setText("OFFER INVALID");
                offerText.setText("N/A");
                scanned.setText("SCAN ERROR");
                verified.setText("NOT VERIFIED");
            }*/
        }




    }




        /*private class GetOffer extends AsyncTask<Long,Void,ClientOfferListEntity>{


        private long offerNumber;
        private String clientN;

        @Override
        protected ClientOfferListEntity doInBackground(Long... params) {
            try {

                offerNumber = params[0];
                long currentClient = params[0];

                ClientEndpointApi.Builder sqlb = new ClientEndpointApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                        .setRootUrl("https://yapnak-app.appspot.com/_ah/api/");
                sqlb.setApplicationName("Yapnak");
                ClientEndpointApi entity = sqlb.build();

                //clientID
                //(int)Long.parseLong(clientIDString)
                ClientOfferListEntity offer = entity.getAllOffers((int)clientID).execute();

                if(pref!=null){
                    String email = pref.getString("email","-1");
                    if(!email.equalsIgnoreCase("-1")) {
                        ClientEntity e = entity.getClientInfo(email).execute();
                        clientN=e.getName();
                    }
                }

                return offer;


            }catch(IOException io){
                io.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ClientOfferListEntity aVoid) {
            super.onPostExecute(aVoid);



            try{
                List<ClientOfferEntity> list =aVoid.getOfferList();

                for(int i = list.size()-1;i>=0;i--){
                  if((int)(long)list.get(i).getOfferId() ==(int)offerNumber ){
                      offerText.setText(list.get(i).getOfferText());
                      break;
                  }else{
                      offerText.setText("OFFER NOT VALID AT THIS RESTAURANT");
                      scanned.setText("SCAN ERROR");
                      verified.setText("NOT VERIFIED");
                  }
              }
                if(clientN==null) {
                    clientName.setText("CLIENT NAME: N/A");
                }else{
                    clientName.setText("CLIENT NAME: "+ clientN);
                }
                //offer.setText("OFFER ID: "+ offerNumber);

            }catch (NullPointerException e){
                //offer.setText("OFFER INVALID");
                offerText.setText("N/A");
                scanned.setText("SCAN ERROR");
                verified.setText("NOT VERIFIED");




            }
        }
    }*/


        /*private class SubmitQR extends AsyncTask<String,Void,Void>{

        private QrEntity qr;
        private long clientID;

        private SubmitQR(long client){
            clientID= client;
        }

        @Override
        protected Void doInBackground(String... params) {
            try {
                String userID = new JSONObject(params[0]).getString("id");
                String date = new JSONObject(params[0]).getString("date");
                String hash = new JSONObject(params[0]).getString("hash");

                SQLEntityApi.Builder sqlb = new SQLEntityApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                        .setRootUrl("https://yapnak-app.appspot.com/_ah/api/");
                sqlb.setApplicationName("Yapnak");
                SQLEntityApi entity = sqlb.build();

                qr = entity.qrSubmit((int)clientID, date, hash, userID).execute();
               // Log.d("status",qr.getStatus());


            }catch(JSONException e){
                e.printStackTrace();
            }catch(IOException io){
                io.printStackTrace();
            }
            return null;
        }
    }*/
}