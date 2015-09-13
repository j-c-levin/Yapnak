package com.uq.yapnak;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.Toast;


import com.frontend.yapnak.tutorial.FragmentSlideActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.plus.Plus;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.yapnak.gcmbackend.sQLEntityApi.SQLEntityApi;
import com.yapnak.gcmbackend.sQLEntityApi.model.UserEntity;
import com.yapnak.gcmbackend.userEndpointApi.UserEndpointApi;
import com.yapnak.gcmbackend.userEndpointApi.model.AuthenticateEntity;
import com.yapnak.gcmbackend.userEndpointApi.model.UserDetailsEntity;

import java.io.IOException;

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
        timePaused += timeInSecs;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.splash_activity);
        firstTime = false;
        activity = this;
    }

    @Override
    protected void onStart() {
        super.onStart();
        /*mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API)
                .addScope(new Scope("profile"))
                .addScope(Plus.SCOPE_PLUS_LOGIN)
                .addScope(Plus.SCOPE_PLUS_PROFILE).build();

        mSignInClicked = true;
        mGoogleApiClient.connect();*/
        Log.d("onStart","Doing work");
        h.postDelayed(run,2000);
        Log.d("onStart", "Finished work");



    }

    private Handler h = new Handler();
    private Runnable run = new Runnable() {
        @Override
        public void run() {
            try {
                SharedPreferences login = getSharedPreferences("KeepMe", Context.MODE_PRIVATE);
                if (login.getBoolean("on", false)) {
                    if (!login.getString("password", "-1").equalsIgnoreCase("-1")) {
                        Log.d("emails", login.getString("email", "NO EMAIL"));
                        new CheckLogin(login).execute();
                    }
                }else{
                    Intent i = new Intent(Splash.this, Login.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                    a.finish();
                }
            } catch(Exception exception){

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


    private class CheckLogin extends AsyncTask<String,Void,Void>{

        private SharedPreferences preferences;
        private boolean hasExecuted;

        protected CheckLogin(SharedPreferences preferences){
            this.preferences=preferences;
        }

        @Override
        protected Void doInBackground(String... params) {

            /*SQLEntityApi.Builder builder = new SQLEntityApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                    .setRootUrl("https://yapnak-app.appspot.com/_ah/api/");
            builder.setApplicationName("Yapnak");
            SQLEntityApi sqlEntity = builder.build();
            */

            hasExecuted=false;
            UserEndpointApi api = new UserEndpointApi(AndroidHttp.newCompatibleTransport(),new AndroidJsonFactory(),null);

            try{

                //AuthenticateEntity e = api.authenticateUser(preferences.getString("pass", "-1")).execute();
                UserDetailsEntity e= api.getUserDetails().setEmail(preferences.getString("email", "-1")).execute();
                //execute();
                if(preferences!=null && Boolean.parseBoolean(e.getStatus())) {
                    if (e.getUserId().equalsIgnoreCase(preferences.getString("userID", "-1"))) {
                        Intent i = new Intent(Splash.this, MainActivity.class);
                        i.putExtra("userID", e.getUserId());
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                        hasExecuted = Boolean.parseBoolean(e.getStatus());
                        a.finish();
                    }
                }else{
                    hasExecuted=false;
                }


            }catch(IOException e){

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void s) {
            super.onPostExecute(s);
            if(!hasExecuted){
                Intent i = new Intent(Splash.this, Login.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                //stopStoredPref();
                a.finish();
            }
        }
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