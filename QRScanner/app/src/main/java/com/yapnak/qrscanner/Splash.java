package com.yapnak.qrscanner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/**
 * Created by vahizan on 24/08/2015.
 */
public class Splash extends Activity {

    Activity  a;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Handler handler = new Handler();
        a = this;

        setContentView(R.layout.splash);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                 Intent i = new Intent(a,Login.class);
                 i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                 i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                 i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                 startActivity(i);
                 a.finish();
            }
        };

        handler.postDelayed(runnable,2000);
    }
}
