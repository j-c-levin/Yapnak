package com.frontend.yapnak.client;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.uq.yapnak.R;
import com.yapnak.gcmbackend.sQLEntityApi.model.PointsEntity;

/**
 * Created by vahizan on 25/06/2015.
 */
public class MainClientActivity extends Activity{

    private EditText userCode;
    private boolean exit = false;

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if(exit){
            finish();
        }



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_client_activity);


    }


    public void submitUserCode(View v){

        userCode = (EditText) findViewById(R.id.userCodeText);
        //change this so that it puts the client's email into it
        new clientSearchUserAsyncTask(this, userCode.getText().toString(), "joshua.c.levin@gmail.com").execute();

        //TODO:Compare user code against Database and return boolean

        if(userCode.getText().toString().equalsIgnoreCase("")){

            CodeErrorDialog errorDialog = new CodeErrorDialog();
            errorDialog.show(getFragmentManager(),"UserCodeError");

        }else{

        }

    }

    public void load(PointsEntity result) {
        //make a notification of some kind in here
        Log.d("debug", result.getUserID() + " " + result.getPoints());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }
}
