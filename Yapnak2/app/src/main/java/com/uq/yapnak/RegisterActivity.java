package com.uq.yapnak;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
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
import com.yapnak.gcmbackend.userEndpointApi.model.RegisterUserEntity;

import java.io.IOException;

/**
 * Created by vahizan on 20/09/2015.
 */
public class RegisterActivity extends Activity{
    private EditText name,phone,email,pass,retypePass,promo;
    private Button register;
    private ImageView close;
    private Activity activity = this;
    private boolean promoAvailable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);
        name = (EditText) findViewById(R.id.nameEdit);
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
    }

    private void onRegisterClick(){
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkDetails();
                if (name.getText().toString().length() != 0
                        && pass.getText().toString().length() != 0
                        && email.getText().toString().length() != 0
                        && phone.getText().toString().length() != 0
                        && phone.getText().toString().length() == 11
                        && phone.getText().toString().length() > 0
                        && pass.getText().toString().equals(retypePass.getText().toString())) {

                    new RegisterUser().execute(pass.getText().toString(),
                            name.getText().toString(),
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
                    if (name.getText().toString().length() != 0
                            && pass.getText().toString().length() != 0
                            && email.getText().toString().length() != 0
                            && phone.getText().toString().length() != 0
                            && phone.getText().toString().length() == 11
                            && phone.getText().toString().length() > 0
                            && pass.getText().toString().equals(retypePass.getText().toString())) {

                        new RegisterUser().execute(pass.getText().toString(),
                                name.getText().toString(),
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
        if(name.getText().toString().length()==0){
            Toast.makeText(getApplicationContext(),"Please Enter Your Name",Toast.LENGTH_SHORT).show();
        }if(phone.getText().toString().length()==0){
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
        /*}if(name.getText().toString().length()==0 &&phone.getText().toString().length()==0 ){
            Toast.makeText(getApplicationContext(),"Please Enter Your Full Name and Phone Number",Toast.LENGTH_SHORT).show();
        }if(name.getText().toString().length()==0&&email.getText().toString().length()==0){
            Toast.makeText(getApplicationContext(),"Please Enter Your Full Name And E-mail",Toast.LENGTH_SHORT).show();
        }if(email.getText().toString().length()==0 &&phone.getText().toString().length()==0){
            Toast.makeText(getApplicationContext(),"Please Enter Your E-Mail and Phone Number",Toast.LENGTH_SHORT).show();
        }if(email.getText().toString().length()==0 &&pass.getText().toString().length()==0){
            Toast.makeText(getApplicationContext(),"Please Enter You E-Mail and Password",Toast.LENGTH_SHORT).show();
        }if(phone.getText().toString().length()==0 &&pass.getText().toString().length()==0){
            Toast.makeText(getApplicationContext(),"Please Enter Your Phone Number and Password",Toast.LENGTH_SHORT).show();*/
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
                    String[] names = params[1].split(" ");
                    if(promoAvailable){
                        if (names.length == 1) {
                            return user.registerUser(params[0]).setFirstName(params[1]).setEmail(params[3]).setMobNo(params[2]).setPromoCode(params[4]).execute();
                        } else {
                            return user.registerUser(params[0]).setFirstName(names[0]).setLastName(names[1]).setEmail(params[3]).setMobNo(params[2]).setPromoCode(params[4]).execute();
                        }
                    }else{
                        if (names.length == 1) {
                            return user.registerUser(params[0]).setFirstName(params[1]).setEmail(params[3]).setMobNo(params[2]).execute();
                        } else {
                            return user.registerUser(params[0]).setFirstName(names[0]).setLastName(names[1]).setEmail(params[3]).setMobNo(params[2]).execute();
                        }
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
                        Toast.makeText(getApplicationContext(),"Registration Successful",Toast.LENGTH_SHORT).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                activity.finish();
                            }
                        }, 1000);
                    }else{
                        Toast.makeText(getApplicationContext(),"Registration Failed",Toast.LENGTH_SHORT).show();
                        Toast.makeText(getApplicationContext(),"Message: "+registerUserEntity.getMessage(),Toast.LENGTH_SHORT).show();

                    }
                }catch(NullPointerException e){
                    Toast.makeText(getApplicationContext(),"Network Error",Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(getApplicationContext(),"No Internet!",Toast.LENGTH_SHORT).show();
            }
        }
    }



 private boolean internet(){
     ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
     boolean wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected();
     boolean threeg = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnected();
     return (wifi||threeg);
 }

}
