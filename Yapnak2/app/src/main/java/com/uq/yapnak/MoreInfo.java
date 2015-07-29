package com.uq.yapnak;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

/**
 * Created by vahizan on 03/06/2015.
 */
public class MoreInfo extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.more_info);
        result();
    }

    private void result(){
        Intent i = new Intent();
        i.putExtra("success","Swipe Left");
        i.putExtra("swipeleft",R.drawable.swipeleft);
        setResult(1,i);
    }


}
