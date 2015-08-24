package com.yapnak.qrscanner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


public class MainActivity extends Activity {

    private static final int RESULT = 123;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //OnClick
    public void scanQR(View v){
        Intent camera = new Intent("com.google.zxing.client.android.SCAN");
        camera.putExtra("SCAN_MODE","QR_CODE_MODE");
        startActivityForResult(camera,RESULT);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Activity.RESULT_OK){
            if(requestCode == RESULT){

                String d = data.getStringExtra("SCAN_RESULT");
                String JSON = data.getStringExtra("SCAN_RESULT_FORMAT");

                Toast.makeText(this,"DATA "+ d +" FORMAT: "+ JSON,Toast.LENGTH_LONG).show();

            }
        }
    }
}
