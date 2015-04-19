package com.example.nand.abc;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

public class MainActivity extends ActionBarActivity implements View.OnClickListener {
    private static final int NOTIFICATION_ID = 0;
    private static String TAG_ABOUT = "About";
    private static String TAG_SHARE = "Share";
    private static String TAG_MANUAL = "Manual";
    private static String TAG_GIFT = "Gifts";

    private Button cancelButton;
    private Button submitButton;
    private MenuItem itemMen;

    private NotificationManager notif;
    private Notification note;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        load();
        showNotification();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        itemMen = menu.findItem(R.id.userName);

//        itemMen.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                return true;
//            }
//        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_feedback) {

            setContentView(R.layout.feedback_activity);

            submitButton = (Button) findViewById(R.id.submitButton);
            cancelButton = (Button) findViewById(R.id.cancelButton);

            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    load();
                }
            });

            submitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), "Thank You For Your Feedback", Toast.LENGTH_SHORT).show();
                    load();

                }
            });


            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (v.getTag().equals(TAG_ABOUT)) {

            aboutYapnak();
            Toast.makeText(this, "About", Toast.LENGTH_SHORT).show();

        } else if (v.getTag().equals(TAG_SHARE)) {

            Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show();

        } else if (v.getTag().equals(TAG_MANUAL)) {
            howToUseYapnak();
            Toast.makeText(this, "How To Use The App", Toast.LENGTH_LONG).show();
        } else if (v.getTag().equals(TAG_GIFT)) {
            userItems();
        }

    }

    public void floatButton() {
        ImageView image = new ImageView(this);
        image.setImageResource(R.drawable.ic_new_float);

        FloatingActionButton actionButton = new FloatingActionButton.Builder(this)
                .setContentView(image)
                .setBackgroundDrawable(R.drawable.selector_file_red)
                .build();

        ImageView iconAbout = new ImageView(this);
        iconAbout.setImageResource(R.drawable.abouticon);

        ImageView iconShare = new ImageView(this);
        iconShare.setImageResource(R.drawable.shareicon);

        ImageView iconManual = new ImageView(this);
        iconManual.setImageResource(R.drawable.manualicon);

        ImageView iconGift = new ImageView(this);
        iconGift.setImageResource(R.drawable.gift);

        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);
        itemBuilder.setBackgroundDrawable(getResources().getDrawable(R.drawable.selector_file_grey));

        SubActionButton buttonAbout = itemBuilder.setContentView(iconAbout).build();
        SubActionButton buttonShare = itemBuilder.setContentView(iconShare).build();
        SubActionButton buttonManual = itemBuilder.setContentView(iconManual).build();
        SubActionButton buttonGift = itemBuilder.setContentView(iconGift).build();

        buttonAbout.setTag(TAG_ABOUT);
        buttonShare.setTag(TAG_SHARE);
        buttonManual.setTag(TAG_MANUAL);
        buttonGift.setTag(TAG_GIFT);

        buttonAbout.setOnClickListener(this);
        buttonShare.setOnClickListener(this);
        buttonManual.setOnClickListener(this);
        buttonGift.setOnClickListener(this);

        FloatingActionMenu actionMenu = new FloatingActionMenu.Builder(this)
                .addSubActionView(buttonAbout)
                .addSubActionView(buttonShare)
                .addSubActionView(buttonGift)
                .addSubActionView(buttonManual)
                .attachTo(actionButton)
                .build();
    }

    public void load() {
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setAdapter(new Adapter());
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        floatButton();
    }

    public void aboutYapnak() {
        AlertDialog.Builder aboutYapnak = new AlertDialog.Builder(this);
        aboutYapnak.setTitle("About Yapnak");
        aboutYapnak.setMessage("Yapnak finds you lunch for a £5er! 💰💰💰");
        aboutYapnak.setPositiveButton("OK", null);
        aboutYapnak.show();
    }

    public void howToUseYapnak() {
        AlertDialog.Builder howToUse = new AlertDialog.Builder(this);
        howToUse.setTitle("How To Use Yapnak");
        howToUse.setMessage("Free lunch?\n\n" +
                "   Use loyalty points for free meals!\n\n" +
                "🍴 Make sure the restaurant takes your code!\n\n" +
                "🍴 Recommend your friends to a restaurant for extra loyalty!\n\n" +
                "🍴 10 points = free lunch!");

        howToUse.setPositiveButton("OK", null);
        AlertDialog dialog = howToUse.create();
        dialog.getWindow().setLayout(400, 400);
        dialog.show();
    }

    public void userItems() {
        AlertDialog.Builder userItems = new AlertDialog.Builder(this);
        userItems.setTitle("Free Items");
        userItems.setMessage("🍪You currently have no free items available");
        userItems.setPositiveButton("OK", null);
        AlertDialog dialog = userItems.create();
        dialog.getWindow().setLayout(400, 400);
        dialog.show();

    }

    public void showNotification() {
        notif = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                this);
        note = builder.setContentIntent(contentIntent)
                .setSmallIcon(R.drawable.notifications)
                .setTicker("Message From Yapnak")
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                .setContentTitle("Yapnak")
                .setContentText("🍪 No Free Items Currently Available")
                .build();

        notif.notify(NOTIFICATION_ID, note);

    }
}