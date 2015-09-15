package com.yapnak.qrscanner;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.fasterxml.jackson.core.JsonParseException;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.json.JsonParser;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import backend.qrscanner.joshua.yapnak.com.sQLEntityApi.SQLEntityApi;
import backend.qrscanner.joshua.yapnak.com.sQLEntityApi.model.QrEntity;


public class MainActivity extends Activity {

    private static final int RESULT = 123;
    private Intent loginIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginIntent=getIntent();
        scanQR();
        setContentView(R.layout.activity_main);

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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //OnClick
    public void scanQR(){
        //Intent camera = new Intent("com.google.zxing.client.android.SCAN");
        //camera.putExtra("SCAN_MODE", "QR_CODE_MODE");
        //startActivityForResult(camera, RESULT);
        intent().initiateScan();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Activity.RESULT_OK){
            /*if(requestCode == RESULT){

                String d = data.getStringExtra("SCAN_RESULT");
                String JSON = data.getStringExtra("SCAN_RESULT_FORMAT");

                Toast.makeText(this,"DATA "+ d +" FORMAT: "+ JSON,Toast.LENGTH_LONG).show();

            }
            */
            IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
            if(result!=null){
                String d = result.getContents();
                //String JSON = result.getFormatName();

                new SubmitQR(loginIntent.getLongExtra("clientID",-1)).execute(d);
                //Toast.makeText(this, "DATA " + d + " FORMAT: " + JSON, Toast.LENGTH_SHORT).show();
                Handler h = new Handler();
                h.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        intent().initiateScan();
                    }
                }, 1000);

            }
        }
    }





    private class SubmitQR extends AsyncTask<String,Void,Void>{

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
                Log.d("status",qr.getStatus());


            }catch(JSONException e){
                e.printStackTrace();
            }catch(IOException io){
                io.printStackTrace();
            }
            return null;
        }
    }
}
