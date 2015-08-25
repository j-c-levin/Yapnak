package com.yapnak.qrscanner;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


public class MainActivity extends Activity {

    private static final int RESULT = 123;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scanQR();
    }
    private boolean exit = false;
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (exit) {
            finish();
        }
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
    public void scanQR(){

            //Intent camera = new Intent("com.google.zxing.client.android.SCAN");
            //camera.putExtra("SCAN_MODE", "QR_CODE_MODE");
            //startActivityForResult(camera, RESULT);
            IntentIntegrator integrator = new IntentIntegrator(this);
            integrator.setCameraId(0)
                    .setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES)
                    .setOrientationLocked(false)
                    .setPrompt("Scan QR Code")
                    .setBeepEnabled(false)
                    .initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Activity.RESULT_OK){
            /*if(requestCode == RESULT){

                String d = data.getStringExtra("SCAN_RESULT");
                String JSON = data.getStringExtra("SCAN_RESULT_FORMAT");

                Toast.makeText(this,"DATA "+ d +" FORMAT: "+ JSON,Toast.LENGTH_LONG).show();

            }
            */
            IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
            if(result!=null){
                String d = result.getContents();
                String JSON = result.getFormatName();
                Toast.makeText(this,"DATA "+ d +" FORMAT: "+ JSON,Toast.LENGTH_LONG).show();
                scanQR();
            }
        }
    }
}
