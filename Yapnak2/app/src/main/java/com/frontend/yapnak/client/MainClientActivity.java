package com.frontend.yapnak.client;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.uq.yapnak.R;

/**
 * Created by vahizan on 25/06/2015.
 */
public class MainClientActivity extends Activity{

    private EditText userCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_client_activity);


    }


    public void submitUserCode(View v){

        userCode = (EditText) findViewById(R.id.userCodeText);

        //TODO:Compare user code against Database and return boolean

        if(userCode.getText().toString().equalsIgnoreCase("")){

            CodeErrorDialog errorDialog = new CodeErrorDialog();
            errorDialog.show(getFragmentManager(),"UserCodeError");

        }else{

        }

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
