package com.yapnak.qrscanner;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.auth.clientlogin.ClientLogin;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import backend.qrscanner.joshua.yapnak.com.sQLEntityApi.SQLEntityApi;
import backend.qrscanner.joshua.yapnak.com.sQLEntityApi.model.ClientEntity;


/**
 * Created by vahizan on 24/08/2015.
 */
public class Login extends Activity {

    private EditText clientEmail;
    private EditText clientPass;
    private int errorID = -1;
    private final Activity a = this;
    private float originalUserY;

    private boolean exit = false;
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (exit) {
            finish();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.login);


        clientB = (Button) findViewById(R.id.clientLoginButton);
        helper = new DBHelper("LOGIN",this);

    }


    public void clientLogin(View v){

        clientEmail =(EditText) findViewById(R.id.clientEmailEdit);
        clientPass = (EditText) findViewById(R.id.clientPassEdit);


        //TODO: Compare Email and Pass against database and return boolean;

        //Right now I'm going to set default values to help client enter onto their main screen


        /*if(clientEmail.getText().toString().equalsIgnoreCase("") && clientPass.getText().toString().equalsIgnoreCase("")) {
            //Start Main Client Activity
            Intent mainClientPage = new Intent(this,MainActivity.class);
            mainClientPage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            mainClientPage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            mainClientPage.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(mainClientPage);

        }else{
            //ErrorDialog dialog = new ErrorDialog();
            //dialog.show(getFragmentManager(),"LoginError");
        }*/
        login();

    }

    private void login(){
        new Authenticate().execute(clientEmail.getText().toString(),clientPass.getText().toString());

    }


    private final String TABLE_NAME = "LOGINFO";
    private DBHelper helper;



    private boolean submitDB;
    private boolean loginM;
    private String emailA;
    private class Authenticate extends AsyncTask<String,Void,Void>{

        @Override
        protected Void doInBackground(String... params) {
            SQLEntityApi.Builder sqlb = new SQLEntityApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                    .setRootUrl("https://yapnak-app.appspot.com/_ah/api/");
            sqlb.setApplicationName("Yapnak");
            SQLEntityApi entity = sqlb.build();

            String passEncrypt = hashPass(params[1]);
            emailA = params[0];

            try {
                ClientEntity login = entity.clientLogin(emailA, passEncrypt).execute();
                Log.d("login", login.getStatus());
                if (login.getStatus().equalsIgnoreCase("True")) {

                    submitDB = true;
                    loginM=true;
                    Intent i = new Intent(a, MainActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    i.putExtra("clientID",login.getId());
                    startActivity(i);
                    a.finish();

                }else{
                    Log.d("fail", "Not valid email/password");
                    loginM=false;
                }
            }catch(IOException e){
                e.printStackTrace();

            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(!loginM){
                Toast.makeText(getApplicationContext(),"Not valid email/password",Toast.LENGTH_SHORT).show();
            }
            if(submitDB){
                new LocalDBInput().execute(emailA);
            }
        }
    }


    private class LocalDBInput extends AsyncTask<String,Void,Void>{


        @Override
        protected Void doInBackground(String... params) {
            try {

                helper.open();

                if(helper.clientExists(params[0])){
                    helper.updateValues(params[0]);
                    Log.d("db", "UPDATED");
                }else{
                    helper.insertValues(params[0]);
                    Log.d("db", "ADDED");
                }
                helper.close();


            }catch(SQLException e){
                e.printStackTrace();
            }




            return null;
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

    private ImageView arrow;
    private Button clientB;


}
