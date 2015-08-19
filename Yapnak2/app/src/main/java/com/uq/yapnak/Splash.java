package com.uq.yapnak;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
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

import java.io.IOException;

/**
 * Created by Nand on 23/03/15.
 */
public class Splash extends Activity {
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
    private Runnable runnable;

    private long timePaused;
    private long timeInSecs;
    private long startTime;
    private long updatedTime;

    private boolean isIn;

    private BackgroundWork work;

    @Override
    protected void onPause() {
        super.onPause();
        timePaused += timeInSecs;


        if (mGoogleApiClient.isConnected()) {

            isIn=true;
            newHandler.removeCallbacks(runnable);
            String acc = Plus.AccountApi.getAccountName(mGoogleApiClient);
            inPause=true;
            //new UserID().execute(acc);
        }
    }

    private boolean firstTime;

    @Override
    protected void onResume() {
        super.onResume();
        //Toast.makeText(getApplicationContext(), "IN ON RESUME", Toast.LENGTH_LONG).show();
        //pushYapnak();

        mGoogleApiClient.connect();
        newHandler = new Handler();
        newHandler.postDelayed(runnable, 2000);


        ImageView glow = (ImageView) findViewById(R.id.yapnakGlow);
        glow.setAlpha(0.0f);

        ObjectAnimator alphaGlow = ObjectAnimator.ofFloat(glow,"alpha",0.0f,0.5f,0.1f,0.5f,0.0f);

        for(int i =0 ;i<4;i++){

            alphaGlow.setDuration(1000).start();

        }




        runnable = new Runnable() {
            @Override
            public void run() {

                  if (!isIn) {
                        Intent i = new Intent(Splash.this, Login.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                        finish();
                    } else if (mGoogleApiClient.isConnected()) {

                        //Toast.makeText(getApplicationContext(),"Connected in Splash",Toast.LENGTH_SHORT).show();
                        newHandler.removeCallbacks(this);

                        /*Intent i = new Intent(Splash.this, MainActivity.class);
                        i.putExtra("accName", acc);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        startActivity(i);
                        */
                      String acc = Plus.AccountApi.getAccountName(mGoogleApiClient);
                      isIn=true;
                      firstTime = true;
                      new UserID().execute(acc);



                    }
            }
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.splash_activity);

        firstTime = false;
        activity = this;


        work = new BackgroundWork();
        //work.execute();
        mGoogleApiClient = work.doInBackground();
        //work.execute();
        //mSignInClicked = true;
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mSignInClicked = true;
        mGoogleApiClient.connect();

    }

    @Override
    protected void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
        //mGoogleApiClient.connect();
    }

    private String userId;



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
            }



        }

         @Override
         protected void onPostExecute(String s) {

             if(firstTime) {
                 Intent i = new Intent(Splash.this, FragmentSlideActivity.class);
                 i.putExtra("accName", accountName);
                 i.putExtra("userID", s);
                 i.putExtra("initials","n/A");
                 i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                 i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                 i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                 startActivity(i);
                 activity.finish();
             }else{

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


    private class BackgroundWork extends AsyncTask<Void,Integer,GoogleApiClient> implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

        private GoogleApiClient mClient;
        private final int CANCELLED = 1;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected GoogleApiClient doInBackground(Void... params) {

            mClient = new GoogleApiClient.Builder(getApplicationContext())
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(Plus.API)
                    .addScope(new Scope("profile"))
                    .addScope(Plus.SCOPE_PLUS_LOGIN)
                    .addScope(Plus.SCOPE_PLUS_PROFILE).build();
            return mClient;
        }



        @Override
        public void onConnected(Bundle bundle) {
            mSignInClicked = false;
            //retrieve user details and make whatever authenticated calls are necessary.
            //Intent i = new Intent(activity, MainActivity.class);

            //person = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
            //this.personName = person.getDisplayName();

            String acc = Plus.AccountApi.getAccountName(mGoogleApiClient);
           /*userId = getExternalId(acc);
            //Toast.makeText(getApplicationContext(), "USER ID external user" + userId + " E Mail = " + acc,Toast.LENGTH_LONG).show();
            i.putExtra("accName", acc);
            i.putExtra("userID", userId);
            Log.d("idc",userId);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            */
            isIn=true;
            new UserID().execute(acc);


        }

        @Override
        public void onConnectionSuspended(int i) {
              mClient.connect();
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
                        mClient.connect();
                    }
                }
            }


        }

        @Override
        protected void onPostExecute(GoogleApiClient googleApiClient) {
            super.onPostExecute(googleApiClient);

            mSignInClicked=true;
            //mGoogleApiClient = googleApiClient;

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);



        }
    }
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

        */