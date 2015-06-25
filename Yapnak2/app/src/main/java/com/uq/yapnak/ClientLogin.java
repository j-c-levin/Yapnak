package com.uq.yapnak;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by vahizan on 24/06/2015.
 */
public class ClientLogin extends Activity implements View.OnClickListener{


    private Button userLogin;
    private Button clientLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.client_login_activity);

    }


    @Override
    public void onClick(View v) {
        int id = v.getId();

        if(id == R.id.clientLoginButton){

            //Start Main Client Activity
            Toast.makeText(this,"Clicked Button" , Toast.LENGTH_SHORT).show();

        }
    }
}
