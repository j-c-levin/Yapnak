package com.frontend.yapnak.promotion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.uq.yapnak.R;


/**
 * Created by vahizan on 25/06/2015.
 */
public class PromotionAdapter extends ArrayAdapter<PromoItem> {

    private PromoItem item;
    private View v;

    public PromotionAdapter(Context context, int resource, PromoItem[] objects) {
        super(context, R.layout.promo_item, objects);


    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        if(convertView==null){


            LayoutInflater inflater = LayoutInflater.from(getContext());
            inflater.inflate(R.layout.promo_item,parent,false);
            convertView = inflater.inflate(R.layout.promo_item,parent,false);

            item = getItem(position);
            v = convertView;

            TextView title = (TextView) v.findViewById(R.id.promotionTitle);
            TextView subtitle = (TextView) v.findViewById(R.id.promotionSubtitle);
            ImageView image = (ImageView) v.findViewById(R.id.promoImage);

           image.setImageResource(item.getImage());
           title.setText(item.getPromoTitle());
           subtitle.setText(item.getPromoSubTitle());


           convertView.setTag(item);





        }else{
           item = (PromoItem) convertView.getTag();
        }


        return convertView;




    }
}
