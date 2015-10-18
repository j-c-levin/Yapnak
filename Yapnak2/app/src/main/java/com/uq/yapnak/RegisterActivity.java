package com.uq.yapnak;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.yapnak.gcmbackend.userEndpointApi.UserEndpointApi;
import com.yapnak.gcmbackend.userEndpointApi.model.AuthenticateEntity;
import com.yapnak.gcmbackend.userEndpointApi.model.RegisterUserEntity;
import com.yapnak.gcmbackend.userEndpointApi.model.SimpleEntity;

import java.io.IOException;

/**
 * Created by vahizan on 20/09/2015.
 */
public class RegisterActivity extends Activity{
    private EditText name,phone,email,pass,retypePass,promo;
    private Button register;
    private ImageView close;
    private FragmentManager fragmentManager;
    private Activity activity = this;
    private boolean promoAvailable;
    private SharedPreferences reg,remember,keep;
    private ProgressDialog progress;
    private Intent getData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);
        getData = getIntent();
        progress = new ProgressDialog(this);
        fragmentManager = getFragmentManager();
        name = (EditText) findViewById(R.id.nameEdit);
        reg= getSharedPreferences("register",Context.MODE_PRIVATE);
        phone = (EditText) findViewById(R.id.phoneNumberEdit);
        email = (EditText) findViewById(R.id.emailEdit);
        pass = (EditText) findViewById(R.id.passwordEdit);
        retypePass= (EditText) findViewById(R.id.retypePasswordEdit);
        register = (Button) findViewById(R.id.regButton);
        close = (ImageView) findViewById(R.id.closeReg);
        promo = (EditText) findViewById(R.id.promoCodeEdit);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        onDone();
        onRegisterClick();
        remember = getSharedPreferences("RememberMe",Context.MODE_PRIVATE);
        keep = getSharedPreferences("KeepMe", Context.MODE_PRIVATE);
    }

    private void onRegisterClick(){
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkDetails();
                if (//name.getText().toString().length() != 0
                         pass.getText().toString().length() != 0
                        && email.getText().toString().length() != 0
                        && phone.getText().toString().length() != 0
                        && phone.getText().toString().length() == 11
                        && phone.getText().toString().length() > 0
                        && pass.getText().toString().equals(retypePass.getText().toString())) {

                    new RegisterUser().execute(pass.getText().toString(),
                            //name.getText().toString(),
                            phone.getText().toString(),
                            email.getText().toString(),
                            promo.getText().toString()
                    );

                }
            }
        });
    }
    private void onDone(){
        retypePass.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    checkDetails();
                    if (//name.getText().toString().length() != 0
                             pass.getText().toString().length() != 0
                            && email.getText().toString().length() != 0
                            && phone.getText().toString().length() != 0
                            && phone.getText().toString().length() == 11
                            && phone.getText().toString().length() > 0
                            && pass.getText().toString().equals(retypePass.getText().toString())) {

                        new RegisterUser().execute(pass.getText().toString(),
                                //name.getText().toString(),
                                phone.getText().toString(),
                                email.getText().toString(),
                                promo.getText().toString()
                        );

                        return true;
                    }

                    return false;
                }
                return false;
            }

        });
    }

    private void checkDetails(){
       /*if(name.getText().toString().length()==0){
            Toast.makeText(getApplicationContext(),"Please Enter Your Name",Toast.LENGTH_SHORT).show();
        }*/
        if(phone.getText().toString().length()==0){
            Toast.makeText(getApplicationContext(),"Please Enter Your Phone Number",Toast.LENGTH_SHORT).show();
        }if(email.getText().toString().length()==0){
            Toast.makeText(getApplicationContext(),"Please Enter Your Email",Toast.LENGTH_SHORT).show();
        }if(retypePass.getText().toString().length()==0){
            Toast.makeText(getApplicationContext(),"Please Re-Type Your Password",Toast.LENGTH_SHORT).show();
        }if(pass.getText().toString().length()==0){
            Toast.makeText(getApplicationContext(),"Please Type in Your Password",Toast.LENGTH_SHORT).show();
        }if(phone.getText().toString().length()>11){
            Toast.makeText(getApplicationContext(),"Please Enter a Valid Phone Number",Toast.LENGTH_SHORT).show();
        }if(phone.getText().toString().length()<11 && phone.getText().toString().length()>0){
            Toast.makeText(getApplicationContext(),"Please Enter a Valid Phone Number",Toast.LENGTH_SHORT).show();
        }if(!retypePass.getText().toString().equals(pass.getText().toString())){
            Toast.makeText(getApplicationContext(),"Passwords Do Not Match",Toast.LENGTH_SHORT).show();
        }if(promo.getText().length()==0){
            promoAvailable=false;
        }else{
            promoAvailable=true;
        }
    }

    private class RegisterUser extends AsyncTask<String,Void,RegisterUserEntity>{

        private boolean hasInternet;
        @Override
        protected RegisterUserEntity doInBackground(String... params) {

            if(internet()) {
                hasInternet=true;
                UserEndpointApi user = new UserEndpointApi(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null);

                try {
                    //String[] names = params[1].split(" ");
                    if(promoAvailable){
                        //if (names.length == 1) {
                          //  return user.registerUser(params[0]).setEmail(params[3]).setMobNo(params[2]).setPromoCode(params[4]).execute();
                        //} else {
                            return user.registerUser(params[0]).setEmail(params[2]).setMobNo(params[1]).setPromoCode(params[3]).execute();
                        //}
                    }else{
                        //if (names.length == 1) {
                         //   return user.registerUser(params[0]).setFirstName(params[1]).setEmail(params[3]).setMobNo(params[2]).execute();
                        //} else {
                            return user.registerUser(params[0]).setEmail(params[2]).setMobNo(params[1]).execute();
                       // }
                    }
                } catch (IOException e) {
                    return null;
                }
            }else{
                hasInternet = false;
                return null;
            }


        }



        @Override
        protected void onPostExecute(RegisterUserEntity registerUserEntity) {
            super.onPostExecute(registerUserEntity);

            if(hasInternet){
                try{
                    if(Boolean.parseBoolean(registerUserEntity.getStatus())){
                        Toast.makeText(getApplicationContext(), "Registration Successful", Toast.LENGTH_SHORT).show();
                        reg.edit().putBoolean("registeredFirstTime",true).putString("phone",phone.getText().toString()).putString("email", email.getText().toString()).apply();
                        new InternalUser().execute(email.getText().toString(),pass.getText().toString(),phone.getText().toString());
                    }else{
                        Toast.makeText(getApplicationContext(),"Registration Failed",Toast.LENGTH_SHORT).show();
                        Toast.makeText(getApplicationContext(),"Message: "+registerUserEntity.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }catch(NullPointerException e){
                    Toast.makeText(getApplicationContext(),"Network Error",Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(getApplicationContext(),"Currently Offline. Please Enable Internet Connection",Toast.LENGTH_SHORT).show();
            }
        }
    }



 private boolean internet(){
     ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
     boolean wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected();
     boolean lte = false;
     if (manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE) != null) {
         lte = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
     } else {
         Log.d("debug", "no mobile internet");
     }
     return (wifi||lte);
 }

    private boolean loginSuccess;
    private class LoginAnalytics extends AsyncTask<String,Void,Void>{

        @Override
        protected Void doInBackground(String... params) {

            UserEndpointApi api = new UserEndpointApi(AndroidHttp.newCompatibleTransport(),new AndroidJsonFactory(),null);

            try{
                SimpleEntity en = null;
                if(getData.getDoubleExtra("latitude",-1)==-1){
                    en = api.userLoginAnalytics(0.0,0.0,params[0]).execute();
                }else{
                    en = api.userLoginAnalytics(getData.getDoubleExtra("latitude",-1),getData.getDoubleExtra("longitude",-1),params[0]).execute();
                }

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
                    Log.d("errorMessage", "FAILED");
                }
            }catch (NullPointerException n){
                Log.d("errorMessage", "FAILED");
            }
        }
    }

    private class InternalUser extends AsyncTask<String,Integer,AuthenticateEntity>{


        private String registerLog;
        private String pass;
        private RegisterUserEntity reg;
        private AuthenticateEntity e;
        private String password,emailAddress,phoneNumber;
        public InternalUser(){
        }

        @Override
        protected AuthenticateEntity doInBackground(String... params) {
            emailAddress = params[0];
            phoneNumber= params[2];
            password = params[1];

            UserEndpointApi.Builder builder = new UserEndpointApi.Builder(AndroidHttp.newCompatibleTransport(),new AndroidJsonFactory(),null);
            builder.setRootUrl("https://yapnak-app.appspot.com/_ah/api/");
            builder.setApplicationName("yapnak");
            UserEndpointApi userEndpoint = builder.build();
            try{
                if(params[1]!=null) {
                    if(promoAvailable){
                        e = userEndpoint.authenticateUser(params[1]).setEmail(params[0]).setMobNo(params[2]).execute();
                    }else{
                        e = userEndpoint.authenticateUser(params[1]).setEmail(params[0]).setMobNo(params[2]).execute();
                    }
                    return e;

                }else{
                    return null;
                }
            }catch (IOException e){
                e.printStackTrace();
                return null;
            }
        }


        @Override
        protected void onPreExecute() {
            progress.setMessage("Signing in...");
            progress.setCancelable(false);
            progress.setCanceledOnTouchOutside(false);
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    progress.show();
                }
            });
        }

        @Override
        protected void onPostExecute(AuthenticateEntity s) {
            if(internet()) {
                //Log.d("loginResult", e.getMessage() + "  STATUS " + Boolean.parseBoolean(e.getStatus()) + "\nPhoneNumber: " + phoneNumber + "\nEmail: " + emailAddress + "\nPassword: " + password);
                try {
                    if (s != null  && Boolean.parseBoolean(s.getStatus())) {
                        new LoginAnalytics().execute(s.getUserId());
                        SharedPreferences.Editor pref = keep.edit();
                        pref.putString("userID", s.getUserId()).putString("password", pass).putString("email", email.getText().toString()).putString("phone", phone.getText().toString()).putBoolean("on", true).apply();
                        SharedPreferences.Editor keeper = remember.edit();
                        keeper.putString("userID", s.getUserId()).putString("password", pass).putString("email", email.getText().toString()).putString("phone", phone.getText().toString()).putBoolean("on", true).apply();
                        Log.d("usernameid", s.getUserId());
                        Intent i = new Intent(RegisterActivity.this, MainActivity.class);
                        i.putExtra("userID", s.getUserId());
                        i.putExtra("password", pass);
                        i.putExtra("email", email.getText().toString());
                        i.putExtra("phone", phone.getText().toString());
                        i.putExtra("on", true);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                    } else {
                        if (progress.isShowing()) {
                            progress.cancel();
                        }

                        ErrorDialog dialog = new ErrorDialog();
                        dialog.setInfoText("Please Enter Correct Login Information");
                        dialog.show(fragmentManager, "error");
                    }
                }
                catch(NullPointerException e){
                    if (progress.isShowing()) {
                        progress.cancel();
                    }

                    ErrorDialog dialog = new ErrorDialog();
                    dialog.setInfoText("There Has Been An Error\nPlease Try Again");
                    dialog.show(fragmentManager, "error");

                }
            }else{
                if (progress.isShowing()) {
                    progress.cancel();
                }
                ErrorDialog dialog = new ErrorDialog();
                dialog.setInfoText("Please Enable Your Internet Connection");
                dialog.show(fragmentManager, "error");
            }
        }
    }


}
