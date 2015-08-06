package com.frontend.yapnak.promotion;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.uq.yapnak.R;

/**
 * Created by vahizan on 04/08/2015.
 */
public class PromotionDialog extends AlertDialog {

    private Activity activity;
    private Context context;
    private View view;
    private ListView promoView;
    private ListAdapter adapter;
    private Button close;

    public PromotionDialog(Context context,Activity activity) {
        super(context);

        this.context = context;
        this.activity = activity;

        view = activity.getLayoutInflater().inflate(R.layout.promo_layout,null);



        promoView = (ListView) view.findViewById(R.id.mainPromoListView);


        adapter = new  PromotionAdapter(getContext(),R.layout.promo_item,promoList());

        promoView.setAdapter(adapter);

        close = (Button) view.findViewById(R.id.closeButton);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });




        final TextView title = new TextView(getContext());
        title.setText("Promotions");
        title.setTextSize(25);
        title.setPadding(20, 40, 0, 40);
        //title.setBackgroundColor(Color.parseColor("#FFAB91"));
        title.setTextColor(Color.parseColor("#BF360C"));
        title.setGravity(Gravity.LEFT);

        setCustomTitle(title);

        this.setView(view);

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Resources res = getContext().getResources();

        final int id = res.getIdentifier("titleDivider", "id", "android");

        final View v = findViewById(id);

        if(v!=null) {
            v.setBackgroundColor(Color.parseColor("#BF360C"));
        }


    }
}
