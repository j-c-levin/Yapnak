package com.uq.yapnak;

import android.support.multidex.MultiDexApplication;

import com.parse.Parse;
import com.parse.ParseInstallation;

/**
 * Created by vahizan on 29/09/2015.
 */
public class Application extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(this, getResources().getString(R.string.app_id), getResources().getString(R.string.key));
        ParseInstallation.getCurrentInstallation().saveInBackground();

    }
}
