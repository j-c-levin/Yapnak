package com.uq.yapnak;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.plus.Plus;

/**
 * Created by Nand on 24/03/15.
 */
public class Login extends Activity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,View.OnClickListener {

    private EditText initials;
    private EditText phone;
    private Button loginButton;
    private EditText promo;
    private MenuItem itemMen;
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
        gSignInButton.setSize(SignInButton.SIZE_WIDE);
        findViewById(R.id.sign_in_button).setOnClickListener(this);

        initials = (EditText) findViewById(R.id.initialsEdit);
        phone = (EditText) findViewById(R.id.phoneNumberEdit);
        promo = (EditText) findViewById(R.id.promoBox);

        loginButton = (Button) findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] details = {initials.getText().toString() + phone.getText().toString().substring(7), phone.getText().toString(), promo.getText().toString()};
                //new UserLoginAsyncTask(getApplicationContext(), details).execute();
                //note: the details argument has been removed and probably won't be re-added.
                //new SQLConnectAsyncTask(getApplicationContext(), details).execute();
                Intent i = new Intent(Login.this, MainActivity.class);
                i.putExtra("initials",initials.getText().toString());
                v.getContext().startActivity(i);
                finish();
            }
        });
        new GcmRegistrationAsyncTask(this).execute();
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        Log.d("Debug", "User is connected");
        mSignInClicked = false;
        //retrieve user details and make whatever authenticated calls are necessary.
        Intent i = new Intent(this, MainActivity.class);
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
}
