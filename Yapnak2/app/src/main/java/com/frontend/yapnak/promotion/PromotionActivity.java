package com.frontend.yapnak.promotion;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.uq.yapnak.R;

/**
 * Created by vahizan on 31/07/2015.
 */
public class PromotionActivity extends ActionBarActivity {


    private Intent getInfo;
    private String name;
    private ListView promoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.promo_layout);
        getInfo = getIntent();
        name = getInfo.getStringExtra("accName");

        setTitle("Promotional Items");

        inflatePromo();


    }



     private void inflatePromo(){

         ListAdapter adapter = new PromotionAdapter(this,R.layout.promo_item,promoList());
         promoView = (ListView) findViewById(R.id.mainPromoListView);
         promoView.setAdapter(adapter);


     }


    private PromoItem[] promoList(){
        PromoItem[] item = new PromoItem[4];

        for(int i =0;i<item.length;i++){
            PromoItem temp = new PromoItem();
            temp.setImage(R.drawable.dropdown_arrow);
            temp.setPromoTitle("Free Cake At Jimmy's Buffet");
            temp.setPromoSubTitle("I'm joking, it's not free");
            item[i] = temp;
        }

        return item;
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

        getMenuInflater().inflate(R.menu.menu_main,menu);


        MenuItem item = menu.findItem(R.id.userNameToolBar);

        String [] splitter = name.split("@");

        this.name=splitter[0];

        item.setTitle(splitter[0]);

        return true;

    }


}
