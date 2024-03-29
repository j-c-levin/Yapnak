package com.frontend.yapnak.maps.features;


import android.content.Intent;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.uq.yapnak.GPSTrack;
import com.uq.yapnak.R;
import com.yapnak.gcmbackend.clientEndpointApi.ClientEndpointApi;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;


/**
 * Created by vahizan on 26/05/2015.
 */
public class MapActivity extends ActionBarActivity implements View.OnClickListener{
    private double latitude;
    private double longitude;
    private double resLongitude;
    private double resLatitude;
    private GoogleMap googleMap;
    private String restaurant;
    private LatLng startPosition,destPosition;
    private EditText startAddressText,endAddressText;
    private GPSTrack tracker;
    private Toast errorToast;
    private String name;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Intent i = getIntent();
        //getSupportActionBar().setSubtitle(i.getStringExtra("init"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //name = i.getStringExtra("accName");

        tracker = new GPSTrack(MapActivity.this);

        if(tracker.canGetLoc()){
            setContentView(R.layout.map_activityv);
        }else {
            setContentView(R.layout.map_activity);
        }


        Intent mapIntent = getIntent();

        latitude= mapIntent.getExtras().getDouble("latitude");

        longitude= mapIntent.getExtras().getDouble("longitude");




        startAddressText = (EditText) findViewById(R.id.directionsFrom);

        if(tracker.canGetLoc()) {
            String location = tracker.getLatitude()+","+tracker.getLongitude();
            startAddressText.setText(location);
        }

        endAddressText = (EditText)findViewById(R.id.directionsTo);


        try {

            if (googleMap==null) {

                googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();


            }

        }catch(Exception e){

        }


        errorToast = Toast.makeText(this,"Please Try Again In Few Seconds",Toast.LENGTH_LONG);
   }

    public void getDirections(View view) {
        String  startAddr = startAddressText.getText().toString();
        startAddr.replace(" ","+");

        if(tracker.canGetLoc()) {
            String loc =tracker.getLatitude()+","+tracker.getLongitude();
            startAddressText.setText(loc);
        }

       String endAddr = endAddressText.getText().toString() ;
        endAddr.replace(" ","+");

        if(startAddr.equalsIgnoreCase("")||endAddr.equalsIgnoreCase("")){

            Toast.makeText(this,"Enter Start and End Address",Toast.LENGTH_SHORT).show();
        }else{

            new GetDirections().execute(startAddr,endAddr);
        }

    }






    class GetDirections extends AsyncTask<String,String,String>{


        @Override
        protected String doInBackground(String... params) {

            try {

                if(!tracker.canGetLoc()) {
                    String startAddr = params[0];
                    startAddr = startAddr.replaceAll(" ", "%20");
                    getLatLong(startAddr, false);


                    String endAddr = params[1];
                    endAddr = endAddr.replaceAll(" ", "%20");
                    getLatLong(endAddr, true);


                }else{

                    startPosition = new LatLng(tracker.getLatitude(),tracker.getLongitude());


                    String endAddr = params[1];
                    endAddr = endAddr.replaceAll(" ", "%20");
                    getLatLong(endAddr, true);

                }

            }catch(NullPointerException e){
                errorToast.show();

            }

            return null;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);




                String uriString = "http://maps.google.com/maps?addr=" + startPosition.latitude +
                        "," + startPosition.longitude
                        + "&daddr="
                        + destPosition.latitude
                        + ","
                        + destPosition.longitude;

                Intent mapIntent = new Intent((Intent.ACTION_VIEW), Uri.parse(uriString));

                startActivity(mapIntent);



        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishActivity(111);
    }

    protected void getLatLong(String address, boolean setDest){
        String uri = "https://maps.google.com/maps/api/geocode/json?address=" +
                address + "&sensor=false";

        HttpGet httpGet = new HttpGet(uri);

        HttpClient client = new DefaultHttpClient();

        HttpResponse response ;

        StringBuilder builder = new StringBuilder();

        try{


            response = client.execute(httpGet);
            HttpEntity entity = response.getEntity();

            InputStream stream = entity.getContent();

            int byteData;
            while((byteData = stream.read())!=-1){

                builder.append((char)byteData);

            }


        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONObject jsonObject;
        double lat = 0.0;
        double lng = 0.0;

        try{

            jsonObject = new JSONObject(builder.toString());
            lng = ((JSONArray) jsonObject.get("results")).getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getDouble("lng");
            lat = ((JSONArray) jsonObject.get("results")).getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getDouble("lat");

            if(setDest){

                destPosition = new LatLng(lat,lng);

            }else{

                startPosition = new LatLng(lat,lng);
            }





        } catch (JSONException e) {
            e.printStackTrace();
        }



    }






    @Override
    public void onClick(View v) {


        //tempMethod(location,restaurant);

    }

    public void getDirectionButton(View view){

        //Button directionButton = (Button) view.findViewById(R.id.getDirections);
        getDirections(view);





    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);

        getMenuInflater().inflate(R.menu.menu_main,menu);

        //MenuItem item = menu.findItem(R.id.userNameToolBar);

        //String [] splitter = name.split("@");

        //this.name=splitter[0];

        //item.setTitle(splitter[0]);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       int id = item.getItemId();

        if(item.getItemId() == android.R.id.home){
            Intent intent = NavUtils.getParentActivityIntent(this);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
            NavUtils.navigateUpTo(this, intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}


