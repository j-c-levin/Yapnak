package com.uq.yapnak;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

/**
 * Created by Nand on 23/03/15.
 */
public class Splash extends Activity {
    private static final int NOTIFICATION_ID = 0 ;
    private long timer = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.splash_activity);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(Splash.this, MainLoginActivity.class);
                startActivity(i);
                finish();
            }
        }, timer);
    }

}
