package com.example.nand.yapnak;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


public class MainActivity extends FragmentActivity {

    boolean expanded=false;
    TextView title;
    TextView txtLineOne;
    LinearLayout expandArea;
    CardView card;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        card = (android.support.v7.widget.CardView) findViewById(R.id.card);
        txtLineOne = (TextView) findViewById(R.id.textview1);
        expandArea = (LinearLayout) findViewById(R.id.expand_area);

        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (expanded) {
                    expandArea.setVisibility(View.GONE);
                    expanded = false;
                    
                } else {
                    expandArea.setVisibility(View.VISIBLE);
                    expanded = true;

                }
            }

        });
    }





        @Override
        public boolean onCreateOptionsMenu (Menu menu){
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu_main, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected (MenuItem item){
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
    }
