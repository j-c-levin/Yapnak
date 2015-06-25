package com.uq.yapnak;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by vahizan on 25/06/2015.
 */
public class MainLoginActivity extends Activity implements View.OnClickListener{

    private Button userLogin;
    private Button clientLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_user_login_splash);
    }

    @Override
    public void onClick(View v) {

            int userLoginID = R.id.mainLoginUserButton;
            int clientLoginID = R.id.mainLoginClientButton;

           // userLogin = (Button)findViewById(R.id.loginUserButton);
            //clientLogin = (Button)findViewById(R.id.loginClientButton);

            if(v.getId()==userLoginID){

                Intent userActivity = new Intent(this,Login.class);
                startActivity(userActivity);


            }else if(v.getId() == clientLoginID){

                Intent clientActivity = new Intent(this,ClientLogin.class);
                startActivity(clientActivity);

            }else{
                //Do nothing
            }


    }
}
