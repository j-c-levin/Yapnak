package com.example.nand.abc;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Timer;

/**
 * Created by Nand on 24/03/15.
 */
public class Login extends Activity {

    private EditText initials;
    private EditText phone;
    private Button loginButton;
    private EditText promo;
    private MenuItem itemMen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        initials = (EditText) findViewById(R.id.initialsEdit);
        phone = (EditText) findViewById(R.id.phoneNumberEdit);
        promo = (EditText) findViewById(R.id.promoBox);

        loginButton = (Button) findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (initials.getText().toString().equals("ns") && phone.getText().toString().equals("123") || promo.getText().toString().equals("cookies")) {
                    Toast.makeText(getApplicationContext(), "Welcome: " + initials.getText().toString() + "-" + phone.getText().toString(), Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(Login.this, MainActivity.class);
                    v.getContext().startActivity(i);
                    userItems();
                    finish();

                } else {
                    Toast.makeText(getApplicationContext(), "Login Fail", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void userItems() {
        AlertDialog.Builder userItems = new AlertDialog.Builder(this);
        userItems.setTitle("Free Items");
        userItems.setMessage("🍪You currently have no free items available");
        userItems.setPositiveButton("OK", null);
        AlertDialog dialog = userItems.create();
        dialog.getWindow().setLayout(400, 400);
        dialog.show();

    }
}