package com.yapnak.qrscanner;

import com.journeyapps.barcodescanner.CaptureActivity;

/**
 * Created by vahizan on 25/08/2015.
 */
public class oCaptureActivity extends CaptureActivity {
    private boolean exit = false;
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (exit) {
            finish();
        }
    }
}
