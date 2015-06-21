package com.uq.yapnak;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;

/**
 * Created by vahizan on 14/06/2015.
 */
public class GPSTrack extends Service implements LocationListener {

    private final Context context;
    private Location userLocation;

    boolean isGPSEnabled = false;
    boolean canGetLoc = false;
    boolean isNetworkEnabled = false;

    private double longitude;
    private double latitude;
    private Location location;

    private static final long MIN_UPDATE_DISTANCE =10;
    private static final long MIN_TIME_BEFORE_UPDATE = 1000*60*1;

    protected LocationManager locationManager ;


    public GPSTrack(Context context){

        this.context = context;
        getLocation();

    }

    public Location getLocation(){
        try{
            locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if(!isGPSEnabled && !isNetworkEnabled){

            }else{

                this.canGetLoc= true;

                if(isNetworkEnabled){
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,MIN_TIME_BEFORE_UPDATE,MIN_UPDATE_DISTANCE,this);

                    } if(locationManager!=null) {
                     location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                        if(location!=null){
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }

                if(isGPSEnabled){
                    if(location==null){

                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,MIN_TIME_BEFORE_UPDATE,MIN_UPDATE_DISTANCE,this);

                         if(locationManager!=null){

                             location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                             if(location!=null){

                                 latitude = location.getLatitude();
                                 longitude=location.getLongitude();

                             }
                         }
                    }
                }

            }

        }catch(Exception e){

            e.printStackTrace();
        }

        return location;

    }

    public double getLongitude(){

        if(location!=null) {
            longitude = this.location.getLongitude();

        }
        return longitude;
    }

    public double getLatitude(){
        if(location!=null) {
            latitude = this.location.getLatitude();

        }
        return latitude;
    }

    public boolean canGetLoc(){
        return canGetLoc;
    }

    public void showAlertSettings(){
        AlertDialog.Builder gps = new AlertDialog.Builder(this.context);
        gps.setTitle("GPS Settings");

        gps.setMessage("Go to settings to enable GPS");

        gps.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                context.startActivity(intent);

            }
        });


        gps.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
            }
        });

         gps.show();
    }

    public void stopUsingGPS(){
        if(locationManager!=null){
           locationManager.removeUpdates(GPSTrack.this);
        }
    }

    public Location getUserLocation(){
        userLocation.setLongitude(getLongitude());
        userLocation.setLatitude(getLatitude());

        return userLocation;
    }


    @Override
    public void onLocationChanged(Location location) {



    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
