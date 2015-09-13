package com.uq.yapnak;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.frontend.yapnak.client.ClientLogin;
import com.frontend.yapnak.subview.MyEditText;
import com.frontend.yapnak.subview.MyProgressDialog;
import com.frontend.yapnak.tutorial.FragmentSlideActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.uq.yapnak.ErrorDialog;
import com.uq.yapnak.MainActivity;
import com.uq.yapnak.R;
import com.uq.yapnak.SecureDetails;
import com.yapnak.gcmbackend.sQLEntityApi.SQLEntityApi;
import com.yapnak.gcmbackend.sQLEntityApi.model.UserEntity;
import com.yapnak.gcmbackend.userEndpointApi.UserEndpointApi;
import com.yapnak.gcmbackend.userEndpointApi.model.AuthenticateEntity;
import com.yapnak.gcmbackend.userEndpointApi.model.RegisterUserEntity;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;


//implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,View.OnClickListener {
public class Login extends Activity{

    private final String FILE_NAME="yapnak_details";
    private EditText email;
    private EditText phone;
    private SecureDetails secure;
    private String emailAd,phoneNum;
    private Button loginButton;
    private EditText password;
    private MenuItem itemMen;
    private ErrorDialog error;
    private boolean resume;
    private FragmentManager fragmentManager;
    private boolean needToCreateNewFile;
    private String personName;
    Person person;
    private Activity activity = this;
    /**
     * True if the sign-in button was clicked.  When true, we know to resolve all
     * issues preventing sign-in without waiting.
     */

    private float originalUserY;
    private boolean mSignInClicked;

    /**
     * True if we are in the process of resolving a ConnectionResult
     */
    private boolean mIntentInProgress;

    /* Request code used to invoke sign in user interactions. */
    private static final int RC_SIGN_IN = 0;

    /* Client used to interact with Google APIs. */
    private GoogleApiClient mGoogleApiClient;
    private boolean wifi,lte;

    /*private Handler startHandle = new Handler();
    private void stopOnCreate(){
        startHandle.removeCallbacks(googleButtonClick);
    }
    private void startOnCreate(){
        connecting.run();
    }
    private Runnable connecting = new Runnable() {
        @Override
        public void run() {
            if(mGoogleApiClient.isConnecting()){
                Toast.makeText(getApplicationContext(), "Connecting... ", Toast.LENGTH_SHORT).show();
                startHandle.postDelayed(this, 1000);
            }else{
                if(!isInternetOn()) {
                    Toast.makeText(getApplicationContext(), "Please Enable Your 3g/Wifi ", Toast.LENGTH_SHORT).show();
                }
            }
        }
    };
    */


    SharedPreferences remember;
    @Override




    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        secure = new SecureDetails();

        //Template google signin code.
       /* mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API)
                .addScope(new Scope("profile"))
                .addScope(Plus.SCOPE_PLUS_LOGIN)
                .addScope(Plus.SCOPE_PLUS_PROFILE)
                .build();
        SignInButton gSignInButton = (SignInButton) findViewById(R.id.sign_in_button);
        //Button gSignInButton = (Button) findViewById(R.id.sign_in_button);
        gSignInButton.setSize(SignInButton.SIZE_WIDE);
        findViewById(R.id.sign_in_button).setOnClickListener(this);*/

        error= new ErrorDialog();
        email = (EditText) findViewById(R.id.emailEdit);
        phone = (EditText) findViewById(R.id.phoneNumberEdit);
        password = (EditText) findViewById(R.id.passwordEdit);

        remember = getSharedPreferences("RememberMe",Context.MODE_PRIVATE);


        new Handler().post(new Runnable() {
            @Override
            public void run() {
                if(remember!=null) {
                    Log.d("remember",String.valueOf(remember.getBoolean("on",false)));
                    if (remember.getBoolean("on",false)) {
                        email.setText(remember.getString("email", ""));
                        phone.setText(remember.getString("phone", ""));
                        String decryptPass = "";
                        try {
                            decryptPass = secure.decrypt(remember.getString("password", ""));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        password.setText(decryptPass);
                    }
                }
            }
        });


        Color c = new Color();

        //promo.getBackground().setColorFilter(c.parseColor("#FF5722"), PorterDuff.Mode.SRC_IN);
        //arrow = (ImageView) findViewById(R.id.arrowDownUser);
        //userB = (Button) findViewById(R.id.userButton);
        //originalUserY = userB.getY();

        loginButton = (Button) findViewById(R.id.loginButton);
        fragmentManager = getFragmentManager();
        //arrowTouch();
        //buttonAnimate();

        Handler h = new Handler();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    String[] details = {email.getText().toString(),phone.getText().toString(), password.getText().toString()}; //,promo.getText().toString()};
                    //new UserLoginAsyncTask(getApplicationContext(), details).execute();
                    //note: the details argument has been removed and probably won't be re-added.
                    //new SQLConnectAsyncTask(getApplicationContext(), details).execute();


                    /* Intent i = new Intent(Login.this, MainActivity.class);
                     i.putExtra("email", email.getText().toString());
                     i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                     i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                     i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                     v.getContext().startActivity(i);
                     finish();
                        */


                    if(email.getText().toString()!=null && password.getText().toString()!=null && (!email.getText().toString().equalsIgnoreCase("")&& !password.getText().toString().equalsIgnoreCase(""))) {
                        Log.d("password", password.getText().toString());
                        emailAd = email.getText().toString();
                        phoneNum = phone.getText().toString();
                        new InternalUser(v).execute(email.getText().toString().trim(), password.getText().toString().trim(), phone.getText().toString().trim());

                    }else{
                        error.show(fragmentManager, "error");
                        if(remember.getString("email","-1").equalsIgnoreCase("-1")){
                            remember.edit().clear().apply();
                        }
                    }



                } catch (StringIndexOutOfBoundsException e) {
                    error.show(fragmentManager, "error");
                }
            }
        });

//        startOnCreate();

    }


    private class InternalUser extends AsyncTask<String,Integer,String>{

        private View view;
        private String registerLog;
        private String pass;
        private RegisterUserEntity reg;
        private AuthenticateEntity e;
        private MyProgressDialog progress;
        private String password,emailAddress,phoneNumber;

        public InternalUser(View v){

            this.view = v;
        }
        @Override
        protected String doInBackground(String... params) {

            /*SQLEntityApi.Builder builder = new SQLEntityApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                    .setRootUrl("https://yapnak-app.appspot.com/_ah/api/");
            builder.setApplicationName("Yapnak");
            SQLEntityApi sqlEntity = builder.build();
            */


              emailAddress = params[0];
              phoneNumber= params[2];
              password = params[1];

              UserEndpointApi.Builder builder = new UserEndpointApi.Builder(AndroidHttp.newCompatibleTransport(),new AndroidJsonFactory(),null);
              builder.setRootUrl("https://yapnak-app.appspot.com/_ah/api/");
              builder.setApplicationName("yapnak");
              UserEndpointApi userEndpoint = builder.build();



            try{
                pass = secure.encrypt(params[1]);
            }catch(Exception ex){
                  ex.printStackTrace();
            }

            try{

                //RegisterUserEntity reg = userEndpoint.registerUser("1234567").setEmail("bob@somemail.com").setMobNo("00000000000").execute();
                //registerLog= reg.getMessage();

                //RegisterUserEntity reg = userEndpoint.registerUser("1234567").setEmail("bobby@somemail.com").setMobNo("00000000000").execute();
                //registerLog= reg.getMessage();

               // RegisterUserEntity reg = userEndpoint.registerUser("1").setEmail("bobb@somemail.com").setMobNo("00000000000").execute();
                //registerLog= reg.getMessage();

               // RegisterUserEntity reg = userEndpoint.registerUser("bob123").setEmail("json@somemail.com").setMobNo("00000000000").execute();
                //registerLog= reg.getMessage();




                if(params[1]!=null) {
                    e = userEndpoint.authenticateUser(params[1]).setEmail(params[0]).setMobNo(params[2]).execute();
                    if(e.getUserId()!=null) {
                        return e.getUserId();
                    }else{
                        return null;
                    }
                }else{
                    return null;
                }
            }catch (IOException e){
                e.printStackTrace();
                return null;
            }

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String s) {

            //Log.d("register",registerLog);
            Log.d("loginResult", e.getMessage() + "  STATUS " + Boolean.parseBoolean(e.getStatus()) + "\nPhoneNumber: "+ phoneNumber +"\nEmail: " + emailAddress + "\nPassword: " + password);
            if(s!=null){
                Log.d("usernameid",s);
                Intent i = new Intent(Login.this, MainActivity.class);
                i.putExtra("userID", s);
                i.putExtra("password", pass);
                i.putExtra("email",emailAd);
                i.putExtra("phone",phoneNum);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                view.getContext().startActivity(i);
            }else{
                ErrorDialog dialog = new ErrorDialog();
                dialog.show(fragmentManager,"error");
            }
        }
    }

    private ImageView arrow;
    private Button userB;

    private AnimatorSet arrowAppear(){
        ValueAnimator animator = ValueAnimator.ofFloat(-1000.0f,(originalUserY+110));

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {


                float y = (Float) animation.getAnimatedValue();


                userB.setY(y);

            }
        });

        ObjectAnimator alpha = ObjectAnimator.ofFloat(userB,"alpha",0.0f,1.0f);

        AnimatorSet s = new AnimatorSet();
        s.playTogether(animator, alpha);

        return s;

    }


    private AnimatorSet arrowGone(){
        ValueAnimator animator = ValueAnimator.ofFloat(originalUserY,-1000.0f);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {


                float y = (float) animation.getAnimatedValue();


                userB.setY(y);

            }
        });

        ObjectAnimator alpha = ObjectAnimator.ofFloat(userB,"alpha",1.0f,0.0f);

        AnimatorSet s = new AnimatorSet();
        s.playTogether(animator, alpha);

        return s;
    }

    private void buttonAnimate(){

        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (userB.getVisibility() != View.VISIBLE) {
                    arrowAppear().setDuration(400).start();
                    userB.setVisibility(View.VISIBLE);

                    userB.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent clientActivity = new Intent(getApplicationContext(), ClientLogin.class);
                            startActivity(clientActivity);
                        }
                    });

                } else {
                    arrowGone().setDuration(400).start();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            userB.setVisibility(View.INVISIBLE);
                        }
                    }, 300);
                }

            }
        });


    }

    private void arrowTouch(){

        final float originalY = userB.getY();

        boolean vis =  userB.getVisibility()!=View.VISIBLE;




        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Animator.AnimatorListener animate = new AnimatorListenerAdapter() {

                    @Override
                    public void onAnimationStart(Animator animation) {
                        //super.onAnimationStart(animation);

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        //super.onAnimationEnd(animation);

                        if(userB.getVisibility()!=View.VISIBLE) {

                            ObjectAnimator moveY = ObjectAnimator.ofFloat(userB, "y", -1000, userB.getY());
                            ObjectAnimator alpha = ObjectAnimator.ofFloat(userB, "alpha", 0.0f, 0.5f, 1.0f);

                            AnimatorSet s = new AnimatorSet();
                            s.playTogether(moveY, alpha);
                            s.setDuration(400);
                            s.setInterpolator(new AccelerateDecelerateInterpolator());
                            s.start();

                        }else{

                            ObjectAnimator moveY = ObjectAnimator.ofFloat(userB, "y",originalY,-1000);
                            ObjectAnimator alpha = ObjectAnimator.ofFloat(userB, "alpha", 1.0f, 0.5f, 0.0f);

                            AnimatorSet s = new AnimatorSet();
                            s.playTogether(moveY, alpha);
                            s.setDuration(400);
                            s.setInterpolator(new AccelerateDecelerateInterpolator());
                            s.start();

                        }

                    }
                };
                float userX = userB.getX();
                float userY = userB.getY();
                //TranslateAnimation buttonAnim = new TranslateAnimation(-);
                      userB.animate().setListener(animate).start();
                      userB.setVisibility(View.VISIBLE);


                userB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent clientActivity = new Intent(getApplicationContext(), ClientLogin.class);
                        clientActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        clientActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        clientActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(clientActivity);
                    }
                });



            }
        });

    }

    private boolean isInternetOn(){
        ConnectivityManager manager =  (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        wifi= manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
        lte =manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();

        return wifi||lte;
    }

    private String userID;

    private class GetUserId extends AsyncTask<String,Integer,String>{


        private String email;

        @Override
        protected String doInBackground(String... params) {
            this.email  = params[0];
            try{
                SQLEntityApi.Builder builder = new SQLEntityApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                        .setRootUrl("https://yapnak-app.appspot.com/_ah/api/");
                builder.setApplicationName("Yapnak");

                SQLEntityApi api = builder.build();
                UserEntity user = api.insertExternalUser(params[0]).execute();
                //Toast.makeText(getApplicationContext(),"USER ID In external user",Toast.LENGTH_LONG).show();
                return user.getUserID();

            }catch (IOException e){
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {

            setAccName(s);
            ConnectivityManager manager =  (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
            wifi= manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
            lte =manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();

            /*if(wifi || lte) {

                if (!s.equalsIgnoreCase("")) {
                    Intent i = new Intent(activity, FragmentSlideActivity.class);
                    i.putExtra("userID", s);
                    i.putExtra("accName", email);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                }
            }else{

            }
            */
            //handler.post(runnable);
            repeat();

        }

        private String accName;
        private void setAccName(String a){
            accName=a;
        }
        private String getAccName(){
            return this.accName;
        }
        private Handler handler=new Handler();
        private void repeat(){
            runnable.run();
        }
        private void stop(){
            handler.removeCallbacks(runnable);
        }
        private Runnable runnable=new Runnable() {
            @Override
            public void run() {
                if(wifi||lte){
                    if(mGoogleApiClient.isConnected()) {
                        Intent i = new Intent(activity, FragmentSlideActivity.class);
                        i.putExtra("userID", getAccName());
                        i.putExtra("accName", email);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        stop();
                        startActivity(i);
                    }else if(mGoogleApiClient.isConnecting()){
                        Toast.makeText(getApplicationContext(),"Connecting",Toast.LENGTH_SHORT).show();
                        handler.postDelayed(this,2000);
                    }
                }
            }
        };
    }

/*
    @Override
    public void onConnected(Bundle connectionHint) {
        Log.d("Debug", "User is connected");
        //Toast.makeText(this, "Connected", Toast.LENGTH_LONG).show();
        mSignInClicked = false;
        //retrieve user details and make whatever authenticated calls are necessary.


        //person = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
        //this.personName = person.getDisplayName();
        if(isInternetOn()) {
            stopSignInClick();
            stopOnCreate();
            String acc = Plus.AccountApi.getAccountName(mGoogleApiClient);
            new GetUserId().execute(acc);

        }

    }

    @Override
    public void onConnectionSuspended(int cause) {
        mGoogleApiClient.connect();
    }

    public void onClick(View view) {
        if (view.getId() == R.id.sign_in_button && !mGoogleApiClient.isConnecting()) {

            if(isInternetOn()) {
                mSignInClicked = true;
                mGoogleApiClient.connect();
            }else{
                Toast.makeText(getApplicationContext(),"Please Enable Your 3g/Wifi",Toast.LENGTH_SHORT).show();
            }

        }

    }

    private Handler handle = new Handler();
    private void stopSignInClick(){
        handle.removeCallbacks(googleButtonClick);
    }
    private void startButtonClick(){
        googleButtonClick.run();
    }
    private Runnable googleButtonClick = new Runnable() {
        @Override
        public void run() {
            if(mGoogleApiClient.isConnecting()){
                Toast.makeText(getApplicationContext(), "Connecting... ", Toast.LENGTH_SHORT).show();
                handle.postDelayed(this, 2000);
            }else{
                Toast.makeText(getApplicationContext(), "Please Enable Your 3g/Wifi ", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        if (!mIntentInProgress) {
            if (mSignInClicked && result.hasResolution()) {
                // The user has already clicked 'sign-in' so we attempt to resolve all
                // errors until the user is signed in, or they cancel.
                try {
                    result.startResolutionForResult(this, RC_SIGN_IN);
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

    @Override
    protected void onStart() {
        super.onStart();
       // mGoogleApiClient.connect();


        new Handler().post(new Runnable() {
            @Override
            public void run() {
                remember = getSharedPreferences("RememberMe",Context.MODE_PRIVATE);
                if(remember!=null) {

                    if (remember.getBoolean("on",false)) {
                        phone.setText(remember.getString("phone", ""));
                        email.setText(remember.getString("email", ""));
                        String decryptedPass = null;

                        try {

                            Log.d("beforeDecryption",remember.getString("password","-1"));
                            decryptedPass = secure.decrypt(remember.getString("password", "-1"));
                            Log.d("decryptedPass",decryptedPass);
                        } catch (Exception e) {
                           e.printStackTrace();
                        }
                        password.setText(decryptedPass);

                    }
                }
            }
        });





    }



    @Override
    protected void onStop() {
        super.onStop();
        //mGoogleApiClient.disconnect();
    }

    @Override
    protected void onResume() {
        super.onResume();

       // if(mSignInClicked) {
         //   startButtonClick();
        //}
    }

    @Override
    protected void onActivityResult(int requestCode, int responseCode, Intent intent) {
        /*if (requestCode == RC_SIGN_IN) {
            if (responseCode != RESULT_OK) {
                mSignInClicked = false;
            }

            mIntentInProgress = false;

            if (!mGoogleApiClient.isConnected()) {
                mGoogleApiClient.reconnect();
            }
        }
        */
    }


    private boolean checkUserPass(){

        FileInputStream checkFile =null;
        BufferedReader reader =null;
        String[] userPass = new String[2];

        try{

            checkFile = openFileInput(FILE_NAME);

            reader = new BufferedReader(new InputStreamReader(checkFile));

            String lines = reader.readLine();
            int i = 0;
            while(lines!=null){

                userPass[i] = lines;
                i++;
            }

            reader.close();



            //TODO: Compare user and pass to database, if they match - automatically sign into main login page.

            SecureDetails details = new SecureDetails();
            String [] decryptedDetails = new String[2];
            try {
                decryptedDetails= details.decrypt(userPass[0], userPass[1]);


            }catch(Exception e){

            }

            if(decryptedDetails[0].equals("") && decryptedDetails[1].equals("")){
                //allow user to login
                return true;
            }
            return false;


        }catch(FileNotFoundException file){

            file.printStackTrace();
            //TODO: needToCreateNewFile ?  need to open an output stream and encrypt pass/user then write into a private file.
            needToCreateNewFile = true;
            return false;

        }catch(IOException ex){
            ex.printStackTrace();
            return false;
        }

    }

    public boolean createNewFile(String[] userPass){

        //Encrypt USER and PASS
        SecureDetails details = new SecureDetails();
        String [] encryptedUserPass = new String[2];

        try {

            encryptedUserPass = details.encrypt(userPass[0], userPass[1]);

        }catch(Exception e){
            e.printStackTrace();
        }


        FileOutputStream write = null;
        BufferedWriter writer = null;

        try {
            write = openFileOutput(FILE_NAME, MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(write));


            writer.write(encryptedUserPass[0]);
            writer.newLine();
            writer.write(encryptedUserPass[1]);
            writer.close();

            return true;

        }catch(FileNotFoundException e){

            e.printStackTrace();
            return false;

        }catch(IOException ex){
            //
            ex.printStackTrace();
            return false;

        }
    }

    public boolean signedOut(){
        try{
            FileOutputStream stream = openFileOutput(FILE_NAME,MODE_PRIVATE);
            String empty = "";
            stream.write(empty.getBytes());
            stream.close();
            /*BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(stream));
            writer.write("");
            writer.close();
            */
            return true;

        }catch(IOException e ){
            return false;
        }
    }
}
