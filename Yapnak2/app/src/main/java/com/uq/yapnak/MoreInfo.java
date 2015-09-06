package com.uq.yapnak;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

/**
 * Created by vahizan on 03/06/2015.
 */
public class MoreInfo extends ActionBarActivity {


    private Intent getInfo;
    private String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.more_info);

         result();
         //setTitleClickable();


    }

    private void result(){

        /*Intent i = new Intent();
        i.putExtra("accName", name);
        i.putExtra("success","Swipe Left");
        i.putExtra("swipeleft", R.drawable.swipeleft);
        setResult(1, i);
        */
    }

    private void setTitleClickable(){
        final Resources res = getResources();
        final int id = res.getIdentifier("action_bar_title","id","android");
        final View actionBarTitle = this.findViewById(id);

        actionBarTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Main Activity", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        if(item.getItemId()==android.R.id.home){

            Intent intent = NavUtils.getParentActivityIntent(this);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
            NavUtils.navigateUpTo(this, intent);
            return true;

        }



        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        //MenuItem item = menu.findItem(R.id.userNameToolBar);

        try {

           /* getInfo = getIntent();
            name = getInfo.getStringExtra("accName");


            String[] splitter = name.split("@");

            this.name = splitter[0];

            item.setTitle(splitter[0]);*/

        }catch(NullPointerException e){

           // item.setTitle("User");
        }

        return true;

    }
}
