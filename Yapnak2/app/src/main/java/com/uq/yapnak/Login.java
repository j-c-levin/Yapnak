package com.uq.yapnak;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Color;
import android.graphics.PorterDuff;
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
import com.frontend.yapnak.tutorial.FragmentSlideActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.uq.yapnak.ErrorDialog;
import com.uq.yapnak.MainActivity;
import com.uq.yapnak.R;
import com.uq.yapnak.SecureDetails;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;



public class Login extends Activity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,View.OnClickListener {

    private final String FILE_NAME = "yapnak_details";
    private EditText initials;
    private EditText phone;
    private Button loginButton;
    private EditText promo;
    private MenuItem itemMen;
    private FragmentManager fragmentManager;
    private boolean needToCreateNewFile;
    private String personName;
    Person person;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        //Template google signin code.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
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
        findViewById(R.id.sign_in_button).setOnClickListener(this);


        initials = (EditText) findViewById(R.id.initialsEdit);
        phone = (EditText) findViewById(R.id.phoneNumberEdit);
        promo = (EditText) findViewById(R.id.promoBox);

        Color c = new Color();

        promo.getBackground().setColorFilter(c.parseColor("#FF5722"), PorterDuff.Mode.SRC_IN);


        arrow = (ImageView) findViewById(R.id.arrowDownUser);
        userB = (Button) findViewById(R.id.userButton);

        originalUserY = userB.getY();

        loginButton = (Button) findViewById(R.id.loginButton);
        fragmentManager = getFragmentManager();
        //arrowTouch();
        buttonAnimate();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    String[] details = {initials.getText().toString() + phone.getText().toString().substring(7), phone.getText().toString(), promo.getText().toString()};
                    //new UserLoginAsyncTask(getApplicationContext(), details).execute();
                    //note: the details argument has been removed and probably won't be re-added.
                    //new SQLConnectAsyncTask(getApplicationContext(), details).execute();


                    /* Intent i = new Intent(Login.this, MainActivity.class);
                     i.putExtra("initials", initials.getText().toString());
                     i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                     i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                     i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                     v.getContext().startActivity(i);
                     finish();
                        */


                    Intent i = new Intent(Login.this, FragmentSlideActivity.class);
                    i.putExtra("initials", initials.getText().toString());
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    v.getContext().startActivity(i);



                } catch (StringIndexOutOfBoundsException e) {

                    ErrorDialog error = new ErrorDialog();

                    error.show(fragmentManager, "error");


                }
            }
        });


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

                if(userB.getVisibility()!=View.VISIBLE){
                    arrowAppear().setDuration(400).start();
                    userB.setVisibility(View.VISIBLE);

                    userB.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent clientActivity = new Intent(getApplicationContext(), ClientLogin.class);
                            startActivity(clientActivity);
                        }
                    });

                }else{
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

        Toast.makeText(this,"Visibility : " + vis,Toast.LENGTH_LONG).show();


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



    @Override
    public void onConnected(Bundle connectionHint) {
        Log.d("Debug", "User is connected");
        Toast.makeText(this, "Connected", Toast.LENGTH_LONG).show();
        mSignInClicked = false;
        //retrieve user details and make whatever authenticated calls are necessary.
        Intent i = new Intent(this, FragmentSlideActivity.class);

        //person = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
        //this.personName = person.getDisplayName();

        String acc = Plus.AccountApi.getAccountName(mGoogleApiClient);

        i.putExtra("accName",acc);

        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

    @Override
    public void onConnectionSuspended(int cause) {
        mGoogleApiClient.connect();
    }

    public void onClick(View view) {
        if (view.getId() == R.id.sign_in_button && !mGoogleApiClient.isConnecting()) {
            mSignInClicked = true;
            mGoogleApiClient.connect();

        }
    }

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

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();

    }

    @Override
    protected void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }

    @Override
    protected void onActivityResult(int requestCode, int responseCode, Intent intent) {
        if (requestCode == RC_SIGN_IN) {
            if (responseCode != RESULT_OK) {
                mSignInClicked = false;
            }

            mIntentInProgress = false;

            if (!mGoogleApiClient.isConnected()) {
                mGoogleApiClient.reconnect();
            }
        }
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
