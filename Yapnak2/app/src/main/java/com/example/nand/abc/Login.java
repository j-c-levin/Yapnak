package com.example.nand.abc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Nand on 24/03/15.
 */
public class Login extends Activity {

    private EditText initials;
    private EditText phone;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        initials = (EditText) findViewById(R.id.initialsEdit);
        phone = (EditText) findViewById(R.id.phoneNumberEdit);
        loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (initials.getText().toString().equals("ns") && phone.getText().toString().equals("123")) {
                    Toast.makeText(getApplicationContext(), "Welcome: " + initials.getText().toString() + "-" + phone.getText().toString(), Toast.LENGTH_SHORT).show();
                    finish();
                    Intent i = new Intent(Login.this, MainActivity.class);
                    v.getContext().startActivity(i);
                } else {
                    Toast.makeText(getApplicationContext(), "Login Fail", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}
