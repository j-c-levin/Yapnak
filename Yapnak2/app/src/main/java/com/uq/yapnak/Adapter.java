package com.uq.yapnak;

import android.app.AlertDialog;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yapnak.gcmbackend.sQLEntityApi.model.SQLList;

import java.util.ArrayList;

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
        private LinearLayout extendedLayout;
        private TextView distance;
        private TextView topRestaurant;
        private TextView forgotPassword;
        private LinearLayout customExtentionLayout;
        private Button ratingButton;
        private Button takeMeThere;
        private Button recommendRestaurant;

        public ViewHolder(final View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.title);
            subtitle = (TextView) itemView.findViewById(R.id.subtitle);
            logo = (ImageView) itemView.findViewById(R.id.logo);
            extendArea = (LinearLayout) itemView.findViewById(R.id.extendLayout);
            customExtentionLayout = (LinearLayout) itemView.findViewById(R.id.customIconLayout);
            card = (android.support.v7.widget.CardView) itemView.findViewById(R.id.card);
            loyalityPoints = (TextView) itemView.findViewById(R.id.loyaltyPoints);
            distance = (TextView) itemView.findViewById(R.id.distance);
            ratingButton = (Button) itemView.findViewById(R.id.feedbackButton);
            takeMeThere = (Button) itemView.findViewById(R.id.takeMeThere);
            recommendRestaurant = (Button) itemView.findViewById(R.id.recommendMeal);


            card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (extendArea.getVisibility() == View.GONE && customExtentionLayout.getVisibility() == View.GONE) {
                        extendArea.setVisibility(View.VISIBLE);
                        customExtentionLayout.setVisibility(View.VISIBLE);

                    } else {
                        extendArea.setVisibility(View.GONE);
                        customExtentionLayout.setVisibility(View.GONE);

                    }

                }
            });


            takeMeThere.setOnClickListener(new View.OnClickListener() {
                //this code should work but I don't know how to join it up properly.
                @Override
                public void onClick(View v) {
                    //Uri gmmIntentUri = Uri.parse("google.navigation:q=" + x + "," + y +"&mode=w");
                   // Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    //mapIntent.setPackage("com.google.android.apps.maps");
                   // startActivity(mapIntent);
                }
            });

            recommendRestaurant.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder userItems = new AlertDialog.Builder(v.getContext());
                    LinearLayout linearLayout = new LinearLayout(v.getContext());

                    userItems.setTitle("👥 Recommend");
                    userItems.setMessage("Enter your friends Yapnak iD");
                    userItems.setPositiveButton("OK", null);
                    userItems.setNegativeButton("CANCEL", null);

                    linearLayout.setOrientation(LinearLayout.VERTICAL);
                    linearLayout.setPadding(40, 0, 40, 0);

                    TextView message = new TextView(v.getContext());
                    final EditText input = new EditText(v.getContext());

                    input.setHint("EG: NS-6438");
                    input.setSingleLine(false);
                    input.setMaxLines(1);
                    input.setTextColor(Color.BLACK);
                    linearLayout.addView(message);
                    linearLayout.addView(input);
                    userItems.setView(linearLayout);


                    AlertDialog dialog = userItems.create();
                    dialog.show();
                }
            });
        }
    }

    private ArrayList<Item> items;

    public Adapter(SQLList sql) {
        super();

        // Create some items
        items = new ArrayList<Item>();
        for (int i = 0; i < sql.getList().size(); i++) {
            items.add(new Item(sql.getList().get(i).getName(), sql.getList().get(i).getOffer(), sql.getList().get(i).getX(), sql.getList().get(i).getY()));
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
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
        holder.subtitle.setText(item.getFoodStyle());
        holder.loyalityPoints.setText(item.getLoyaltyPoints());
        holder.logo.setImageResource(item.getLogo());
        holder.distance.setText(item.getDistance());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

}
