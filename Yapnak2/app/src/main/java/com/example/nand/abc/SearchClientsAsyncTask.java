package com.example.nand.abc;

import android.os.AsyncTask;

/**
 * Created by Joshua on 16/05/2015.
 */
public class SearchClientsAsyncTask extends AsyncTask<Void, Void, Integer> {

    @Override
    protected Integer doInBackground(Void... params) {
        //Get user GPS coordinates and check accuracy is okay

        //Take their gps down to 3 digits

        //Search for clients where their X and Y coordinate is +/- 3

        //Send these clients to the main list panel

        return null;
    }

    protected void onPostExecute(Integer result) {

    }
}
