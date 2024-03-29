package com.uq.yapnak;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;


import com.google.android.gms.common.api.GoogleApiClient;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.yapnak.gcmbackend.userEndpointApi.UserEndpointApi;
import com.yapnak.gcmbackend.userEndpointApi.model.SimpleEntity;
import com.yapnak.gcmbackend.userEndpointApi.model.UserDetailsEntity;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * Created by Nand on 23/03/15.
 */
public class Splash extends Activity {//implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private boolean inPause;
    private static final int NOTIFICATION_ID = 0 ;
    private long timer = 2000;
    private GoogleApiClient mGoogleApiClient;
    //implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener

    /**
     * True if the sign-in button was clicked.  When true, we know to resolve all
     * issues preventing sign-in without waiting.
     */
    private boolean mSignInClicked;

    /**
     * True if we are in the process of resolving a ConnectionResult
     */
    private boolean mIntentInProgress;

    /* Request code used to invoke sign in user interactions. */
    private static final int RC_SIGN_IN = 0;

    private Activity activity;
    private Handler newHandler;
    private long timePaused;
    private long timeInSecs;
    private long startTime;
    private long updatedTime;
    private boolean isIn,onConnected;



    @Override
    protected void onPause() {
        super.onPause();
        //timePaused += timeInSecs;

       /* if(!onConnected) {
            repeatTask();
        }
        */
    }

    private boolean firstTime;
    private long num;
    private Handler handle =new Handler();
    @Override
    protected void onResume() {
        super.onResume();

        //stopTask();

        ImageView glow = (ImageView) findViewById(R.id.yapnakGlow);
        glow.setAlpha(0.0f);
        ObjectAnimator alphaGlow = ObjectAnimator.ofFloat(glow,"alpha",0.0f,0.5f,0.1f,0.5f,0.0f);
        for(int i =0 ;i<3;i++){
            alphaGlow.setDuration(1000).start();
        }

    }



   /*

    private int UPDATE_TIME=3000;
    private void repeatTask(){
        runnable.run();
    }
    private void updateTime(){
        UPDATE_TIME+=2000;
    }

    private void stopTask(){

        h.removeCallbacks(runnable);

    }
   private Runnable runnable = new Runnable() {
        @Override
        public void run() {

            ConnectivityManager connect = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            boolean wifi = connect.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
            boolean data = connect.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();

            Log.d("wifi", String.valueOf(wifi));
            Log.d("data",String.valueOf(data));

            if(wifi||data) {
                if (mGoogleApiClient.isConnecting()) {
                    updateTime();
                    h.postDelayed(this, UPDATE_TIME);
                } else if (mGoogleApiClient.isConnected()) {
                    stopTask();
                    String acc = Plus.AccountApi.getAccountName(mGoogleApiClient);
                    isIn = true;
                    onResume = true;
                    new InternetConnection().execute(acc);
                    Log.d("onResume", "on resume");
                } else {
                    Intent i = new Intent(Splash.this, Login.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    stopTask();
                    startActivity(i);
                    finish();
                }
            }else{
                Intent i = new Intent(Splash.this, Login.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                stopTask();
                Toast.makeText(getApplicationContext(),"No Internet",Toast.LENGTH_SHORT).show();
                startActivity(i);
                finish();
            }
        }
    };
    */


    private boolean providerNotEnabled;
    private Location userLoc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.splash_activity);

        try {
            if (providerEnabled()) {
                GPSTrack track = new GPSTrack(this);
                userLoc = null;
                userLoc.setLongitude(track.getLongitude());
                userLoc.setLatitude(track.getLatitude());
            } else {
                providerNotEnabled = true;
            }
        }catch(NullPointerException e){
            providerNotEnabled = true;
        }
        //firstTime = false;
        //activity = this;


    }

    private boolean providerEnabled(){
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if(manager!=null) {
            boolean gps = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            boolean network = manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            return (network||gps);
        }
        return false;
    }

    private SharedPreferences login;
    @Override
    protected void onStart() {
        super.onStart();
        login = getSharedPreferences("KeepMe", Context.MODE_PRIVATE);
        Log.d("onStart","Doing work");
        h.postDelayed(run, 2000);
        Log.d("onStart", "Finished work");



    }

    private Handler h = new Handler();
    private Runnable run = new Runnable() {
        @Override
        public void run() {

               Log.d("loginProcess",String.valueOf(login.getBoolean("on",false)) + "\nUSER ID "+ login.getString("userID","N/A"));
               if (login.getBoolean("on", false)) {
                    Log.d("loginProcess",String.valueOf(login.getBoolean("on",false)));
                    if (!login.getString("password", "-1").equalsIgnoreCase("-1")) {
                        new CheckLogin().execute();
                        Log.d("loginProcess", login.getString("email", "NO EMAIL"));
                    }else{
                        Intent i = new Intent(Splash.this, Login.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                        a.finish();
                    }
                }else{
                    Log.d("loginProcess","preferences NULL");
                    Intent i = new Intent(Splash.this, Login.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                    a.finish();

                }

            }
    };

    private void stopStoredPref(){
        h.removeCallbacks(run);
    }
    private void startStoredPref(){
        run.run();
    }
    private Activity a = this;


    private boolean loginSuccess;
    private class LoginAnalytics extends AsyncTask<String,Void,Void>{

        @Override
        protected Void doInBackground(String... params) {
            UserEndpointApi api = new UserEndpointApi(AndroidHttp.newCompatibleTransport(),new AndroidJsonFactory(),null);
            try{
                SimpleEntity en=null;
                if(providerNotEnabled) {
                     en = api.userLoginAnalytics(0.0, 0.0, params[0]).execute();
                }else{
                     en = api.userLoginAnalytics(userLoc.getLatitude(), userLoc.getLongitude(), params[0]).execute();
                }
                Log.d("loginSuccess",en.getStatus() + "\nMESSAGE "+en.getMessage());
                loginSuccess = Boolean.parseBoolean(en.getStatus());
                return null;
            }catch(IOException e){
                return null;
            }
        }

        @Override
        protected void onPostExecute(Void e) {
            super.onPostExecute(e);
            try{
                if(loginSuccess){
                    Log.d("loginSuccess","success");
                }else{
                    Log.d("errorMessage","FAILED");
                }
            }catch (NullPointerException n){
                Log.d("errorMessage", "FAILED");
            }
        }
    }
    private class CheckLogin extends AsyncTask<String,Void,UserDetailsEntity> {

        private boolean hasExecuted;

        protected CheckLogin(){
        }

        @Override
        protected UserDetailsEntity doInBackground(String... params) {
            UserEndpointApi api = new UserEndpointApi(AndroidHttp.newCompatibleTransport(),new AndroidJsonFactory(),null);
            try{
               UserDetailsEntity e= api.getUserDetails().setEmail(login.getString("email", "-1")).execute();
               return e;
            }catch(IOException e){
                return null;
            }
        }

        @Override
        protected void onPostExecute(UserDetailsEntity s) {
            super.onPostExecute(s);


            try {
                if (s!=null && Boolean.parseBoolean(s.getStatus())) {
                    Log.d("userID","USER ID FROM PREF = "+ login.getString("userID", "-1") +"\nFROM DB "+ s.getUserId());

                    if (s.getUserId().equalsIgnoreCase(login.getString("userID", "-1"))) {
                        Log.d("loginProcess","Preferences and Status  NOT NULL");
                        new LoginAnalytics().execute(login.getString("userID","-1"));
                        Intent i = new Intent(Splash.this, MainActivity.class);
                        i.putExtra("userID", s.getUserId());
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                        hasExecuted = Boolean.parseBoolean(s.getStatus());
                        a.finish();
                    }
                }else{
                    hasExecuted = false;
                }
            }catch (NullPointerException e){
                hasExecuted = false;
            }

            if(!hasExecuted){
                Intent i = new Intent(Splash.this, Login.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                a.finish();
            }
        }
    }


    final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();

    static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        int v;
        for (int j = 0; j < bytes.length; j++) {
            v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    private static String SALT = "Y3aQcfpTiUUdpSAY";

    static String hashPassword(String in) {
        try {
            MessageDigest md = MessageDigest
                    .getInstance("SHA-256");
            md.update(SALT.getBytes());        // <-- Prepend SALT.
            md.update(in.getBytes());

            byte[] out = md.digest();
            return bytesToHex(out);            // <-- Return the Hex Hash.
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
    //********************************

    //Generates some random numbers
    public static int randInt() {

        // NOTE: Usually this should be a field rather than a method
        // variable so that it is not re-seeded every call.
        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int max = 9998;
        int min = 1000;
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }

    /*
    @Override
    protected void onStop() {
        super.onStop();
        //mGoogleApiClient.disconnect();
    }

    private String userId;
    private boolean onResume;

    private class UserID extends AsyncTask<String,Integer,String>{

         private String userName;
         private String accountName;

        @Override
        protected String doInBackground(String... params) {

            accountName = params[0];

            try{
                SQLEntityApi.Builder builder = new SQLEntityApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                        .setRootUrl("https://yapnak-app.appspot.com/_ah/api/");
                builder.setApplicationName("Yapnak");
                SQLEntityApi api = builder.build();
                Log.d("acc",params[0]);
                UserEntity user = api.insertExternalUser(params[0]).execute();
                Log.d("userid",user.getUserID());
                userName = user.getUserID();
                return userName;

            }catch (IOException e){
                e.printStackTrace();
                Log.d("error","error!!!!1");
                return userName;
            }catch (NullPointerException n){
                return userName = "N/a";
            }



        }

         @Override
         protected void onPostExecute(String s) {

                if (noInternet) {
                    Intent i = new Intent(Splash.this, Login.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                    activity.finish();

                }else if(firstTime && !noInternet && onResume){

                    Intent i = new Intent(Splash.this, FragmentSlideActivity.class);
                    i.putExtra("accName", accountName);
                    i.putExtra("userID", s);
                    i.putExtra("initials", "n/A");
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                    activity.finish();

                } else {

                     Intent i = new Intent(Splash.this, MainActivity.class);
                     i.putExtra("accName", accountName);
                     i.putExtra("userID", s);
                     i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                     i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                     i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                     startActivity(i);
                     activity.finish();
                }
             }

     }
    private boolean noInternet;

    private class InternetConnection extends AsyncTask<String,Void,String>{
        @Override
        protected String doInBackground(String... params) {

            ConnectivityManager connect = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            boolean wifi = connect.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
            boolean data = connect.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();

            Log.d("wifi", String.valueOf(wifi));
            Log.d("data",String.valueOf(data));
            if(!wifi && !data){
                noInternet = true;
                onResume = false;
                Intent i = new Intent(Splash.this, Login.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }


            return params[0];
        }

        @Override
        protected void onPostExecute(String aVoid) {
            super.onPostExecute(aVoid);
            new UserID().execute(aVoid);


        }
    }

private boolean inBackground;
private class BackgroundWork extends AsyncTask<Void,Integer,GoogleApiClient> implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

        private final int CANCELLED = 1;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected GoogleApiClient doInBackground(Void... params) {

            mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(Plus.API)
                    .addScope(new Scope("profile"))
                    .addScope(Plus.SCOPE_PLUS_LOGIN)
                    .addScope(Plus.SCOPE_PLUS_PROFILE).build();
            return mGoogleApiClient;


        }



        @Override
        public void onConnected(Bundle bundle) {
            mSignInClicked = false;
            //retrieve user details and make whatever authenticated calls are necessary.
            //Intent i = new Intent(activity, MainActivity.class);
            //person = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
            //this.personName = person.getDisplayName();

            String acc = Plus.AccountApi.getAccountName(mGoogleApiClient);
            isIn=true;
            firstTime = false;
            inBackground = true;
            onResume=false;
            Log.d("background","In Background Work");
            new InternetConnection().execute(acc);



        }

        @Override
        public void onConnectionSuspended(int i) {
              mGoogleApiClient.connect();
        }

        @Override
        public void onConnectionFailed(ConnectionResult result) {

            if (!mIntentInProgress) {
                if (mSignInClicked && result.hasResolution()) {
                    // The user has already clicked 'sign-in' so we attempt to resolve all
                    // errors until the user is signed in, or they cancel.
                    try {
                        result.startResolutionForResult(activity, RC_SIGN_IN);
                        mIntentInProgress = true;
                    } catch (IntentSender.SendIntentException e) {
                        // The intent was canceled before it was sent.  Return to the default
                        // state and attempt to connect to get an updated ConnectionResult.
                        mIntentInProgress = false;
                        mGoogleApiClient.connect();
                    }
                }
            }


        }

        @Override
        protected void onPostExecute(GoogleApiClient googleApiClient) {
            super.onPostExecute(googleApiClient);

            mSignInClicked=true;
            mGoogleApiClient.connect();

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);



        }
    }
    */

   /* @Override
    public void onConnected(Bundle bundle) {
        mSignInClicked = false;
        String acc = Plus.AccountApi.getAccountName(mGoogleApiClient);
        isIn=true;
        firstTime = false;
        onResume=false;
        Log.d("background", "In Background Work");
        onConnected= true;
        repeatTask();



    }


    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {

        if (!mIntentInProgress) {
            if (mSignInClicked && result.hasResolution()) {
                // The user has already clicked 'sign-in' so we attempt to resolve all
                // errors until the user is signed in, or they cancel.
                try {
                    result.startResolutionForResult(activity, RC_SIGN_IN);
                    mIntentInProgress = true;
                } catch (IntentSender.SendIntentException e) {
                    // The intent was canceled before it was sent.  Return to the default
                    // state and attempt to connect to get an updated ConnectionResult.
                    mIntentInProgress = false;
                    mGoogleApiClient.connect();
                }
            }
        }


    }

*/


}

    /*
         h = new Handler();
         startTime =0L;

         timePaused = 0L;
         updatedTime = 0L;

        startTime=(SystemClock.currentThreadTimeMillis()*1000);

        runnable = new Runnable() {
            @Override
            public void run() {
                if (mGoogleApiClient.isConnected() && mGoogleApiClient != null) {
                    pushYapnak();
                    Intent i = new Intent(Splash.this, MainActivity.class);
                    String acc = Plus.AccountApi.getAccountName(mGoogleApiClient);
                    i.putExtra("accName", acc);
                    Toast.makeText(getApplicationContext(), acc, Toast.LENGTH_LONG).show();
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                    finish();
                } else {
                    timeInSecs = (SystemClock.currentThreadTimeMillis()*1000) - startTime;
                    updatedTime = timePaused + timeInSecs;
                }
            }
        };

        h.postDelayed(runnable,10000);

     */

         /*

        if (mGoogleApiClient.isConnected() && mGoogleApiClient != null) {

            pushYapnak();
            Intent i = new Intent(Splash.this, MainActivity.class);
            String acc = Plus.AccountApi.getAccountName(mGoogleApiClient);
            i.putExtra("accName", acc);
            Toast.makeText(getApplicationContext(), acc, Toast.LENGTH_LONG).show();
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            finish();

        } else {

            timer = (!mGoogleApiClient.isConnected())? 300000 : 2000 ;

            pushYapnak();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {



                    if(!mGoogleApiClient.isConnected()){

                       /* Intent i = new Intent(Splash.this, MainLoginActivity.class);

                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                        finish();


                    }else{


                        Intent i = new Intent(Splash.this, MainActivity.class);
                        String acc = Plus.AccountApi.getAccountName(mGoogleApiClient);
                        i.putExtra("accName", acc);
                        Toast.makeText(getApplicationContext(), acc, Toast.LENGTH_LONG).show();
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                        finish();
                    }


                }




            },timer);
        }


     private class OnResumeID extends AsyncTask<String,Integer,String>{

        private String userName;
        private String accountName;



        @Override
        protected String doInBackground(String... params) {

            accountName = params[0];


            try{
                SQLEntityApi.Builder builder = new SQLEntityApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                        .setRootUrl("https://yapnak-app.appspot.com/_ah/api/");
                builder.setApplicationName("Yapnak");
                SQLEntityApi api = builder.build();
                Log.d("acc",params[0]);
                UserEntity user = api.insertExternalUser(params[0]).execute();
                Log.d("userid",user.getUserID());
                userName = user.getUserID();
                return userName;

            }catch (IOException e){
                e.printStackTrace();
                Log.d("error","error!!!!1");
                return userName;
            }catch (NullPointerException n){
                return userName = "N/a";
            }



        }

        @Override
        protected void onPostExecute(String s) {

                if (firstTime && !noInternet) {
                    Intent i = new Intent(Splash.this, FragmentSlideActivity.class);
                    i.putExtra("accName", accountName);
                    i.putExtra("userID", s);
                    i.putExtra("initials", "n/A");
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                    activity.finish();

                }

        }
    }



        */