package com.example.nand.abc;

import android.animation.LayoutTransition;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nand on 04/03/15.
 */
public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView subtitle;
        private LinearLayout extendArea;
        private android.support.v7.widget.CardView card;
        private ImageView logo;
        private Button recommendButton;
        private Button mapButton;
        private TextView loyalityPoints;


        public ViewHolder(final View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.title);
            subtitle = (TextView) itemView.findViewById(R.id.subtitle);
            logo = (ImageView) itemView.findViewById(R.id.logo);
            extendArea = (LinearLayout) itemView.findViewById(R.id.extendLayout);
            card = (android.support.v7.widget.CardView) itemView.findViewById(R.id.card);
            loyalityPoints = (TextView) itemView.findViewById(R.id.loyaltyPoints);


            logo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (extendArea.getVisibility() == View.GONE) {
                        extendArea.setVisibility(View.VISIBLE);
                    } else {
                        extendArea.setVisibility(View.GONE);

                    }

                }
            });

        }
    }

    private static final int ITEM_COUNT = 50;
    private ArrayList<Item> items;

    public Adapter() {
        super();

        // Create some items
        items = new ArrayList<>();

        items.add(new Item("Gourmet Burger Kitchen ", "Portion of fat chips", "10,000", R.drawable.gbklogo));
        items.add(new Item("McDonalds", "Free Mcflurry with every meal", "500", R.drawable.mcdonalds));
        items.add(new Item("Wrap It Up", "70p off any drink when you buy a medium burrito", "1", R.drawable.wrapitup));
        items.add(new Item("Pizza Express", "15% NUS Discounts", "5", R.drawable.pizzaexpresslogo));
        items.add(new Item("Tesco", "Any sandwich or past + crisps + drink - JUST £3!", "37", R.drawable.tescologo));
        items.add(new Item("Gourmet Burger Kitchen ", "Portion of fat chips", "10,000", R.drawable.gbklogo));
        items.add(new Item("McDonalds", "Free Mcflurry with every meal", "500", R.drawable.mcdonalds));
        items.add(new Item("Wrap It Up", "70p off any drink when you buy a medium burrito", "1", R.drawable.wrapitup));
        items.add(new Item("Pizza Express", "15% NUS Discounts", "5", R.drawable.pizzaexpresslogo));
        items.add(new Item("Tesco", "Any sandwich or past + crisps + drink - JUST £3!", "37", R.drawable.tescologo));


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Item item = items.get(position);

        holder.title.setText(item.getTitle());
        holder.subtitle.setText(item.getSubtle());
        holder.loyalityPoints.setText(item.getLoyaltyPoints());
        holder.logo.setImageResource(item.getLogo());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


}
