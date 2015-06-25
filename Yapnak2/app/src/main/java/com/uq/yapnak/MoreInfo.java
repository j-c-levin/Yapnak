package com.uq.yapnak;

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
    }


}
