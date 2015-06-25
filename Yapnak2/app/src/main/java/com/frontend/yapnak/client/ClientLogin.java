package com.frontend.yapnak.client;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.uq.yapnak.ErrorDialog;
import com.uq.yapnak.R;

/**
 * Created by vahizan on 24/06/2015.
 */
public class ClientLogin extends Activity {


    private EditText clientEmail;
    private EditText clientPass;
    private int errorID = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.client_login_activity);

    }


   public void clientLogin(View v){

       clientEmail =(EditText) findViewById(R.id.clientEmailEdit);
       clientPass = (EditText) findViewById(R.id.clientPassEdit);


       //TODO: Compare Email and Pass against database and return boolean;

       //Right now I'm going to set default values to help client enter onto their main screen

        if(clientEmail.getText().toString().equalsIgnoreCase("") && clientPass.getText().toString().equalsIgnoreCase("")) {
            //Start Main Client Activity
           Intent mainClientPage = new Intent(this,MainClientActivity.class);

            mainClientPage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            mainClientPage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            mainClientPage.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(mainClientPage);

        }else{
            ErrorDialog dialog = new ErrorDialog();
            dialog.show(getFragmentManager(),"LoginError");
        }

   }
}
