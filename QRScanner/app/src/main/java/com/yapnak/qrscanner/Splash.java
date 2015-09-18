

package com.yapnak.qrscanner;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.yapnak.gcmbackend.clientEndpointApi.ClientEndpointApi;
import com.yapnak.gcmbackend.clientEndpointApi.model.ClientAuthEntity;
import com.yapnak.gcmbackend.sQLEntityApi.SQLEntityApi;
import com.yapnak.gcmbackend.sQLEntityApi.model.ClientEntity;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by vahizan on 24/08/2015.
 */
public class Splash extends Activity {

    private Activity  a;
    private Handler handler = new Handler();
    private SharedPreferences pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        a = this;
        pref = getSharedPreferences("clientlogin", Context.MODE_PRIVATE);

        setContentView(R.layout.splash);



    }


    private Runnable run = new Runnable() {
        @Override
        public void run() {

            if(connection()) {
                if (pref != null && pref.getBoolean("on", false)) {

                /*if(pref.getString("email","-1").equalsIgnoreCase("james@pintalate.com") && pref.getString("user","-1").equals("Test")){

                    Intent i = new Intent(Splash.this, MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                    a.finish();
                }*/
                    new LoginAuthenticate().execute(pref.getString("email", "-1"), pref.getString("pass", "-1"));

                } else {

                    Intent i = new Intent(Splash.this, Login.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                    a.finish();
                }

            }else{
                Toast.makeText(getApplicationContext(),"No Internet Connection",Toast.LENGTH_SHORT).show();
                Intent i = new Intent(Splash.this, Login.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                a.finish();
            }
        }
    };

    private class LoginAuthenticate extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... params) {

            String passEncrypt = params[1];
            String emailA = params[0];

            ClientEndpointApi.Builder builder = new ClientEndpointApi.Builder(AndroidHttp.newCompatibleTransport(),new AndroidJsonFactory(),null);
            ClientEndpointApi client = builder.build();




            try {
                ClientAuthEntity login = client.authenticateClient(params[0], params[1]).execute();
                //Log.d("login", login.getStatus());
                if (login.getStatus().equalsIgnoreCase("True")) {

                    Intent i = new Intent(Splash.this, MainActivity.class);
                    i.putExtra("clientID",login.getClientId());
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                    a.finish();

                }else{
                    Intent i = new Intent(Splash.this, Login.class);
                    i.putExtra("email",pref.getString("email",""));
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                    a.finish();

                }
            }catch(IOException e){

            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }


    private static String SALT = "Y3aQcfpTiUUdpSAY";
    final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();

    private String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        int v;
        for (int j = 0; j < bytes.length; j++) {
            v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
    private String hashPass(String pass){

        try {
            MessageDigest md = MessageDigest
                    .getInstance("SHA-256");
            md.update(SALT.getBytes());        // <-- Prepend SALT.
            md.update(pass.getBytes());

            byte[] out = md.digest();
            return bytesToHex(out);            // <-- Return the Hex Hash.
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";

    }

    @Override
    protected void onStart() {
        super.onStart();


        handler.postDelayed(run, 2000);
    }


    private boolean connection(){

        ConnectivityManager manager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
        boolean lte = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
        return (lte||wifi);
    }
}