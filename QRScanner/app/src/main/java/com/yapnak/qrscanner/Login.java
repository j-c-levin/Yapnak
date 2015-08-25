package com.yapnak.qrscanner;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import backend.qrscanner.joshua.yapnak.com.sQLEntityApi.SQLEntityApi;
import backend.qrscanner.joshua.yapnak.com.sQLEntityApi.model.ClientEntity;
import backend.qrscanner.joshua.yapnak.com.sQLEntityApi.model.SQLEntity;


/**
 * Created by vahizan on 24/08/2015.
 */
public class Login extends Activity {

    private EditText clientEmail;
    private EditText clientPass;
    private int errorID = -1;
    private float originalUserY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.login);

        clientB = (Button) findViewById(R.id.clientLoginButton);




    }


    public void clientLogin(View v){

        clientEmail =(EditText) findViewById(R.id.clientEmailEdit);
        clientPass = (EditText) findViewById(R.id.clientPassEdit);


        //TODO: Compare Email and Pass against database and return boolean;

        //Right now I'm going to set default values to help client enter onto their main screen


        if(clientEmail.getText().toString().equalsIgnoreCase("") && clientPass.getText().toString().equalsIgnoreCase("")) {
            //Start Main Client Activity
            Intent mainClientPage = new Intent(this,MainActivity.class);
            mainClientPage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            mainClientPage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            mainClientPage.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(mainClientPage);

        }else{
            //ErrorDialog dialog = new ErrorDialog();
            //dialog.show(getFragmentManager(),"LoginError");
        }

    }

    private void login(){
        SQLEntityApi.Builder sqlb = new SQLEntityApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                .setRootUrl("https://yapnak-app.appspot.com/_ah/api/");
        sqlb.setApplicationName("Yapnak");
        SQLEntityApi entity = sqlb.build();

        String passEncrypt = hashPass(clientPass.getText().toString());
        String email = clientEmail.getText().toString();

        try{
            //compare email and pass against DB
            ClientEntity client = entity.getClientInfo(clientEmail.getText().toString()).execute();

        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"Not valid email/password",Toast.LENGTH_SHORT).show();
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
