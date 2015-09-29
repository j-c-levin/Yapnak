package com.uq.yapnak;

import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseInstallation;

import java.util.HashMap;

/**
 * Created by vahizan on 29/09/2015.
 */
public class Application extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(this,getResources().getString(R.string.app_id),getResources().getString(R.string.key));
        ParseInstallation.getCurrentInstallation().saveInBackground();
        Log.d("debug", "Beginning");
        HashMap<String, Object> map = new HashMap<>();
        map.put("message", "hello there, how are you?");
        map.put("recipientId", ParseInstallation.getCurrentInstallation().getInstallationId());
    }
}
