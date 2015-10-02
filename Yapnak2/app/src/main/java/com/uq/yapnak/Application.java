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
//        Log.d("debug", "Beginning");
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("message", "hello there, how are you?");
//        map.put("recipientId", ParseInstallation.getCurrentInstallation().getInstallationId());
//        ParseCloud.callFunctionInBackground("pushToUser", map, new FunctionCallback<String>() {
//            @Override
//            public void done(String s, ParseException e) {
//                if (e == null) {
//                    Log.d("debug", s);
//                }
//            }
//        });

    }
}
