package com.uq.yapnak;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.frontend.yapnak.AdapterPrev;
import com.frontend.yapnak.ItemPrev;
import com.frontend.yapnak.maps.features.MapActivity;
import com.frontend.yapnak.navigationdrawer.NavBarItem;
import com.frontend.yapnak.navigationdrawer.NavigationBarAdapter;
import com.frontend.yapnak.rate.RatingDialog;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.plus.People;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.yapnak.gcmbackend.sQLEntityApi.model.SQLEntity;


public class MainActivity extends ActionBarActivity implements View.OnClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        ResultCallback<People.LoadPeopleResult> {
    private static final int NOTIFICATION_ID = 0;


    private GoogleApiClient mGoogleApiClient;

    private SQLEntity sql;

    RecyclerView recyclerView;

    private static String TAG_ABOUT = "About";
    private static String TAG_SHARE = "Share";
    private static String TAG_MANUAL = "Manual";
    private static String TAG_GIFT = "Gifts";
    private android.widget.RelativeLayout.LayoutParams layoutParams;
    private static final String ACTION_BUTTON_TAG = "ActionButton";
    private Button cancelButton;
    private Button submitButton;
    private MenuItem itemMen;
    private String msg;
    private Thread t;
    private NotificationManager notif;
    private Notification note;
    private boolean click = true;
    private FragmentManager fragmentManager;
    //private GestureDetectorCompat gestureDetect;
    private ItemPrev[] ip;

    private boolean longPress;
    private float x;
    private float y;
    private String location;
    private String restaurant;
    private String item = "YapNak";
    private String mainItem;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavBarItem[] naviBarItems = new NavBarItem[3];

    private FloatingActionButton actionButton;
    private ListView deals;
    private Intent temp;

    private String firstName;
    private String lastName;

    private GPSTrack tracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN)
                .addScope(Plus.SCOPE_PLUS_PROFILE)
                .build();

        temp = getIntent();
        String intials = temp.getStringExtra("initials");

        getSupportActionBar().setSubtitle(intials);
        setContentView(R.layout.activity_main1);
        navBarToggle();
        navigationBarContent();
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        Log.d("debug", "onConnected");
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            Log.d("debug", "Location Found: " + mLastLocation.toString());
            new SQLConnectAsyncTask(getApplicationContext(), mLastLocation, this).execute();
        }
        Plus.PeopleApi.loadVisible(mGoogleApiClient, null).setResultCallback(this);
    }

    @Override
    public void onResult(People.LoadPeopleResult loadPeopleResult) {
        if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
            Person user = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
            if (user.getName().hasGivenName()) {
                firstName = user.getName().getGivenName();
                Log.d("debug", firstName);
            }
            if (user.getName().hasFamilyName()) {
                lastName = user.getName().getFamilyName();
                Log.d("debug", lastName);
            }
        }
        else {
            Log.d("debug", "failed");
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d("Location", "onConnectionSuspended");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d("Location", "onConnectionFailed");
    }

    @Override
    protected void onResume() {
        Log.d("Location", "resuming googleAPI connection");
        super.onResume();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onPause() {
        Log.d("Location", "pausing googleAPI connection");
        super.onPause();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }


    public class Feedback extends ActionBarActivity {

        //@Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            final View view = inflater.inflate(R.layout.feedback_activity, null);

            cancelButton = (Button) view.findViewById(R.id.cancelButton);
            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    load(sql);

                }
            });


            submitButton = (Button) view.findViewById(R.id.submitButton);
            submitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {



                    EditText feedback = (EditText) v.findViewById(R.id.commentField);

                    String text = feedback.getText().toString();
                    //TODO:text must be stored in feedback table in the database

                    Toast.makeText(getApplicationContext(), "Thank You For Your Feedback", Toast.LENGTH_SHORT).show();
                    load(sql);
                }
            });


            return view;
        }

    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_feedback) {

          try {
              setContentView(R.layout.feedback_activity);
              submitButton = (Button) findViewById(R.id.submitButton);
              cancelButton = (Button) findViewById(R.id.cancelButton);


              cancelButton.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                       showMain();
                  }
              });


              submitButton.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      Toast.makeText(getApplicationContext(), "Thank You For Your Feedback", Toast.LENGTH_SHORT).show();
                      showMain();
                  }
              });

          }catch(Exception e){
              Toast.makeText(getApplicationContext(), "You are currently in feedback window", Toast.LENGTH_SHORT).show();
          }


            return true;
        }else if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;

        }

        else if (id == R.id.menu_sign_out) {
            if (mGoogleApiClient.isConnected()) {
// Prior to disconnecting, run clearDefaultAccount().
                Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
                Plus.AccountApi.revokeAccessAndDisconnect(mGoogleApiClient)
                        .setResultCallback(new ResultCallback<Status>() {

                            @Override
                            public void onResult(Status status) {
                                signedOut();
                            }
                        });

            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void signedOut() {
        Intent i = new Intent(this, Login.class);
        startActivity(i);
    }

    //view the main activitiy - clean up code and make it simpler

    private void showMain(){

        load(sql);
        navBarToggle();
        navigationBarContent();
        getSupportActionBar().setSubtitle(temp.getStringExtra("initials"));

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

    /*public void floatButton() {
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
    }*/


    public void extendInfo(View v) {
        //Extend info: Rate,like and Take Me There
        LinearLayout layout = (LinearLayout) v.findViewById(R.id.extendHeight);

        if (layout.getHeight() != 600) {
            layout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 600));
        } else {

            layout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 250));
        }

        TextView view = (TextView) v.findViewById(R.id.distance);

        this.location = (String) view.getText().toString();



          /*
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.abc_slide_out_bottom, R.anim.abc_slide_in_bottom);
            ExtensionFragment frag = new ExtensionFragment();

            /*if (frag==null) {
                fragmentTransaction.attach(frag);
                fragmentTransaction.show(frag);


            } else {
                fragmentTransaction.detach(frag);
                fragmentTransaction.hide(frag);

            }

            fragmentTransaction.add(R.id.item2,frag);

            fragmentTransaction.commit();

            */
    }

    public void recommendMealButton(View v) {
        Button recommendMeal = (Button) v.findViewById(R.id.recommendMeal);


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

    public void takeMeThereButton(View v) {
        Button takeMeThere = (Button) v.findViewById(R.id.takeMeThere);
        Intent maps = new Intent(this, MapActivity.class);
        final int result = 1;
        this.restaurant = "McDonalds";

        tracker = new GPSTrack(MainActivity.this);

        double longitude=0;
        double latitude=0;
        if(tracker.canGetLoc()){

            longitude = tracker.getLongitude();
            latitude = tracker.getLatitude();
        }

        maps.putExtra("longitude",longitude);
        maps.putExtra("latitude",latitude);
        //maps.putExtra("LocationIntent", location);
        //maps.putExtra("RestaurantNameIntent", restaurant);
        startActivity(maps);

    }


    public void feedbackButton(View v) {
        Button feedbackButton = (Button) v.findViewById(R.id.feedbackButton);

        //Intent rate = new Intent(this, RateActivity.class);
        RatingDialog rate = new RatingDialog();
        rate.show(getFragmentManager(),"rating");

        //startActivity(rate);


    }

    public void setLongPress(boolean lp) {
        longPress = lp;
    }

    public boolean getLongPress() {
        return this.longPress;
    }

    /*public void floatButton() {
        final ImageView image = new ImageView(this);
        image.setImageResource(R.drawable.ic_new_float);

        actionButton = new FloatingActionButton.Builder(this)
                .setContentView(image)
                .setBackgroundDrawable(R.drawable.selector_file_red)
                .build();

        actionButton.setTag(ACTION_BUTTON_TAG);

        actionButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(getClick()==true) {
                    setLongPress(true);
                    return true;
                }
                return false;
            }
        });

        //After long press, allow the button to move.

        actionButton.setOnTouchListener(new View.OnTouchListener() {
            private float x,y;
            private RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.relLayout);
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    setClick(true);
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    setLongPress(false);


                }if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    x = event.getRawX() - (v.getHeight() / 2);
                    y = event.getRawY() - (v.getWidth() / 2);

                    if(getLongPress()==true && x<relativeLayout.getWidth() && y<relativeLayout.getHeight()) {


                        actionButton.setX(x);
                        actionButton.setY(y);
                        setClick(false);
                        return true;

                    }

                }

                return false;

            }

            public void setX(float x) {
                this.x = x;

            }

            public void setY(float y) {
                this.y = y;
            }

            public float getX() {
                return x;
            }

            public float getY() {
                return y;
            }
        });








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



    }*/

    //commented out because we have code to actually grab information from the database.
    public void load() {
/*
        setContentView(R.layout.activity_main1);
        ListAdapter dealList = new AdapterPrev(this, R.id.item2, dealList());
        deals = (ListView) findViewById(R.id.listviewMain);
        deals.setAdapter(dealList);

        deals.setBackgroundResource(R.drawable.customshape);

*/
    }





    /*
     *
     *
    NAVIGATION DRAWER IMPLEMENTATION BELOW
     *
     *
     */


    //Listener


    private class DrawerItemListener implements ListView.OnItemClickListener {

        FragmentManager fragmentManager;


        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            mainItem = item;
            NavBarItem navItem = (NavBarItem) parent.getItemAtPosition(position);

            selectedItem(navItem.getNavBarItem());

        }


    }


    private void selectedItem(String item) {


        fragmentManager = getFragmentManager();


        if (item.equalsIgnoreCase("About YapNak")) {

            /*Fragment fragment = new AboutYapNak();


                    fragmentManager.beginTransaction().replace(R.id.relLayout, fragment)
                    .commit();

            */

            aboutYapnak();


        } else if (item.contains("How To Use")) {

            /*Fragment fragment = new HowToUse();

                     fragmentManager.beginTransaction()
                    .replace(R.id.relLayout,fragment)
                    .commit();
            */

            howToUseYapnak();

        } else if (item.equalsIgnoreCase("Misc")) {

            Toast.makeText(this, "MISCELLANEOUS", Toast.LENGTH_LONG).show();
            userItems();
        }

    }


    @Override
    public void setTitle(CharSequence title) {

        this.item = (String) title;
        getSupportActionBar().setTitle(title);


    }

    public void navBarToggle() {

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);


        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close) {


            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mainItem);
            }

            public void onDrawerOpened(View view) {

                super.onDrawerOpened(view);

                getSupportActionBar().setTitle(item);

            }


        };

        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }


    public void navigationBarContent() {

        //String [] tempList= {"About YapNak","How To Use YapNak","Misc"};

        NavBarItem about = new NavBarItem();
        about.setNavBarItem("About YapNak");
        NavBarItem howTo = new NavBarItem();
        howTo.setNavBarItem("How To Use YapNak");
        NavBarItem misc = new NavBarItem();
        misc.setNavBarItem("Misc");

        naviBarItems[0] = about;
        naviBarItems[1] = howTo;
        naviBarItems[2] = misc;


        ListAdapter adapter = new NavigationBarAdapter(this, R.layout.navigation_bar_layout, naviBarItems);

        ListView navBarItems = (ListView) findViewById(R.id.left_drawer);


        navBarItems.setAdapter(adapter);
        navBarItems.setOnItemClickListener(new DrawerItemListener());
    }


    public void load(SQLEntity sql) {
        //recyclerView.setAdapter(new Adapter(sql));
        setContentView(R.layout.activity_main1);
        ListAdapter dealList = new AdapterPrev(this, R.id.item2, dealList(sql));
        deals = (ListView) findViewById(R.id.listviewMain);
        deals.setAdapter(dealList);
        deals.setBackgroundResource(R.drawable.customshape);
    }

    public ItemPrev[] dealList(SQLEntity sql) {

        try{

            ip = new ItemPrev[sql.getList().size()];

            for (int i = 0; i < ip.length; i++) {
                ItemPrev temp = new ItemPrev();
                //TODO:add generic location to database
                temp.setDistance("to be added");
                //TODO: add photo download from google storage
                temp.setLogo(R.drawable.mcdonalds);
                temp.setMainText(sql.getList().get(i).getFoodStyle());
                temp.setRestaurantName(sql.getList().get(i).getName());
                temp.setSubText(sql.getList().get(i).getOffer());
                //TODO: points
                temp.setPoints("to be added");
                ip[i] = temp;

            }

            return ip;

        }catch(Exception e){

            ip = new ItemPrev[1];


            ItemPrev temp = new ItemPrev();
            //TODO:add generic location to database
            temp.setDistance("to be added");
            //TODO: add photo download from google storage
            temp.setLogo(R.drawable.mcdonalds);
            temp.setMainText("Cannot Retrieve Information");
            temp.setRestaurantName("N/A");
            temp.setSubText("N/A ");
            //TODO: points
            temp.setPoints("to be added");
            ip[0] = temp;

            return ip;


        }



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