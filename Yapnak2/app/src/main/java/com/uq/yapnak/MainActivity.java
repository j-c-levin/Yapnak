package com.uq.yapnak;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.frontend.yapnak.AdapterPrev;
import com.frontend.yapnak.ItemPrev;
import com.frontend.yapnak.maps.features.MapActivity;
import com.frontend.yapnak.navigationdrawer.NavBarItem;
import com.frontend.yapnak.navigationdrawer.NavigationBarAdapter;
import com.frontend.yapnak.promotion.PromoItem;
import com.frontend.yapnak.promotion.PromotionAdapter;
import com.frontend.yapnak.rate.RatingDialog;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.plus.People;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;
import com.yapnak.gcmbackend.sQLEntityApi.model.SQLEntity;
import com.yapnak.gcmbackend.sQLEntityApi.model.SQLList;


import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity implements View.OnClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        ResultCallback<People.LoadPeopleResult> {
    private static final int NOTIFICATION_ID = 0;


    private GoogleApiClient mGoogleApiClient;

    private SQLEntity sql;

    RecyclerView recyclerView;
    private static String USER_NAME;
    private static String PASS;

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
    private ItemPrev itemInfo;
    private PromoItem promoItem;
    private FloatingActionButton actionButton;
    private ListView deals,promoList;
    private Intent temp,name;

    private String firstName;
    private String lastName;

    private GPSTrack tracker;
    private RelativeLayout extendIcon;
    private RelativeLayout extendText;
    private RelativeLayout extendHeight;
    private  String initials;
    private String personName;


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



        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        setContentView(R.layout.activity_main1);
        load();
        floatButton();

        //navBarToggle();
        //navigationBarContent();

    }



    private boolean exit = false;
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (exit) {
            finish();
        }
    }

    private final String LOG_INFO="log";
    public void setUserName(Menu menu){
        MenuItem item = menu.findItem(R.id.userNameToolBar);

        try{

       /*String userName = Plus.AccountApi.getAccountName(mGoogleApiClient);

        String[]name = userName.split("@");

        item.setTitle(name[0]);*/



            name = getIntent();
            personName= name.getStringExtra("accName");
            item.setTitle(personName);
            personName = name.getStringExtra("accName");


            String[]name = personName.split("@");

            item.setTitle(name[0]);







        }catch(Exception e){

            e.printStackTrace();
            temp = getIntent();
            initials = temp.getStringExtra("initials");
            item.setTitle(initials);

        }

    }


        @Override
    public void onConnected(Bundle connectionHint) {
        Log.d("debug", "onConnected");
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            Log.d("debug", "Location Found: " + mLastLocation.toString());
            new SQLConnectAsyncTask(getApplicationContext(), mLastLocation, this).execute();
        }

            if(Plus.PeopleApi.getCurrentPerson(mGoogleApiClient)!=null){

                Person currentPerson = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
                personName = currentPerson.getDisplayName();

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

        setUserName(menu);


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
                    load();
                    //load(sql);

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

                    load();
                    //load(sql);


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
        if (id == R.id.menu_sign_out) {
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



        /*

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

            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "You are currently in feedback window", Toast.LENGTH_SHORT).show();
            }


            return true;
            // }else if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            //   return true;

        }else if(id== R.id.about) {

            aboutYapnak();

        }else if(id == R.id.howToUse) {
            howToUseYapnak();

        }else if(id == -1){
            userItems();


        }
         */



    }

    public void signedOut() {
        Intent i = new Intent(this, Login.class);
        startActivity(i);
    }

    //view the main activitiy - clean up code and make it simpler

    private void showMain(){


        //load(sql);
        load();
       // navBarToggle();
        //navigationBarContent();
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

    private ValueAnimator slideAnimator(int start,int end){
        ValueAnimator animator = ValueAnimator.ofInt(start, end);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //Update The Height of the card

                int value= (Integer) animation.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = extendHeight.getLayoutParams();
                layoutParams.height = value;
                extendHeight.setLayoutParams(layoutParams);
            }
        });

        return animator;
    }

    private int initialHeight;
    public void extend(){

        extendIcon.setVisibility(View.VISIBLE);
        extendText.setVisibility(View.VISIBLE);

        final int widthSpec = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
        final int heightSpec = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);

        extendHeight.measure(widthSpec,heightSpec);

        initialHeight=extendHeight.getHeight();
        ValueAnimator valueAnimator = slideAnimator(extendHeight.getHeight(),(extendHeight.getMeasuredHeight()-5));
        valueAnimator.start();


    }

    public void collapse(){
        int finalHeight = extendHeight.getHeight();

        ValueAnimator animator = slideAnimator(finalHeight,initialHeight);

        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

                extendIcon.setVisibility(View.GONE);
                extendText.setVisibility(View.GONE);

                /*
                ViewGroup.LayoutParams layoutParams = extendHeight.getLayoutParams();
                extendHeight.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,extendHeight.getHeight()/2));
                */


            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        animator.start();

    }

    public void extendInfo(View v) {
        //Extend info: Rate,like and Take Me There
         extendHeight= (RelativeLayout) v.findViewById(R.id.extendHeight);
         extendIcon =(RelativeLayout)v.findViewById(R.id.extendIconLayout);
         extendText =(RelativeLayout)v.findViewById(R.id.extendTextLayout);



        if(extendText.getVisibility()==View.GONE && extendIcon.getVisibility() == View.GONE ){

            extend();


        }else{

            collapse();
        }


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

    public void setDirections(View v){

        double longitude=0;
        double latitude=0;
        if(tracker.canGetLoc()){

            longitude = tracker.getLongitude();
            latitude = tracker.getLatitude();
        }


        Button takeMeThere = (Button) v.findViewById(R.id.takeMeThere);
        Intent maps = new Intent(this, MapActivity.class);
        maps.putExtra("init",initials);
        final int result = 1;

        ItemPrev itemAtSelectedPosition = getItemPrev();

        maps.putExtra("userLongitude",longitude);
        maps.putExtra("userLatitude",latitude);

        if(itemAtSelectedPosition!=null) {
            maps.putExtra("resLongitude", itemAtSelectedPosition.getLongitude());
            maps.putExtra("resLatitude", itemAtSelectedPosition.getLatitude());
        }

        startActivity(maps);

    }

    public void takeMeThereButton(View v) {

        tracker = new GPSTrack(MainActivity.this);



        if((getItemPrev()!=null)){

            try {
                setTakeMeThereView(v);
                GetDirections directions = new GetDirections();
                directions.doInBackground(tracker);
                directions.execute();

            }catch(Exception e){
                Toast.makeText(this,"There was an error in retrieving the selected restaurant/deal's location - Please enter location manually",Toast.LENGTH_LONG).show();
                setDirections(v);
            }

        }else{
            setDirections(v);
        }



    }

    private View takeMeThere;

    private void setTakeMeThereView(View v){
        takeMeThere= v;

    }
    private View getTakeMeThereView(){
        return takeMeThere;

    }
    private class GetDirections extends AsyncTask<GPSTrack,String,String>{

        private LatLng resPosition;
        private LatLng userPosition;

        @Override
        protected String doInBackground(GPSTrack... params) {


            try{


                GPSTrack tracker = params[0];

                if(tracker.canGetLoc()){
                    userPosition = new LatLng(tracker.getLatitude(),tracker.getLongitude());

                }else{

                }

                if(getItemPrev()!=null ){


                    try {
                        ItemPrev temp = getItemPrev();
                        resPosition = new LatLng(temp.getLatitude(), temp.getLongitude());

                    }catch(NullPointerException e){

                        Toast.makeText(getApplicationContext(),"There was an error in retrieving the selected restaurant/deal's location - Please enter location manually",Toast.LENGTH_LONG).show();
                        setDirections(getTakeMeThereView());

                    }

                }



            }catch(NullPointerException e){

            }catch(ArrayIndexOutOfBoundsException ex){

            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {

                String uriString = "http://maps.google.com/maps?addr=" + userPosition.latitude +
                        "," + userPosition.longitude
                        + "&daddr="
                        + resPosition.latitude
                        + ","
                        + resPosition.longitude;

                Intent mapIntent = new Intent((Intent.ACTION_VIEW), Uri.parse(uriString));

                startActivity(mapIntent);

            }catch(NullPointerException e){

                Toast.makeText(getApplicationContext(),"There was an error in retrieving the selected restaurant/deal's location - Please enter location manually",Toast.LENGTH_LONG).show();
                setDirections(getTakeMeThereView());

            }
        }
    }


    public void feedbackButton(View v) {
        Button feedbackButton = (Button) v.findViewById(R.id.feedbackButton);
        RatingDialog rate = new RatingDialog();
        rate.show(getFragmentManager(),"rating");

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
}*/






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
        //actionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //actionBarDrawerToggle.onConfigurationChanged(newConfig);
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

        /*ListView navBarItems = (ListView) findViewById(R.id.left_drawer);


        navBarItems.setAdapter(adapter);
        navBarItems.setOnItemClickListener(new DrawerItemListener());
        */
    }



    //commented out because we have code to actually grab information from the database.
    public void load() {

        setContentView(R.layout.activity_main1);
        ListAdapter dealList = new AdapterPrev(this, R.id.item2, dealList());


        ListAdapter promo = new PromotionAdapter(this,R.id.promo_item,gift());
        promoList = (ListView) findViewById(R.id.listViewPromotions);
        promoList.setAdapter(promo);

        scaleListY = promoList.getY();

        deals = (ListView) findViewById(R.id.listviewMain);
        deals.setBackgroundResource(R.drawable.curved_card);
        deals.setAdapter(dealList);
        deals.setOnItemLongClickListener(new OnLongTouchListener());
        deals.setOnItemClickListener(new ItemInfoListener());
        deals.setOnScrollListener(new ScrollListener());


    }


    public void load(SQLList sql) {
        //recyclerView.setAdapter(new Adapter(sql));
        setContentView(R.layout.activity_main1);
        ListAdapter dealList = new AdapterPrev(this, R.id.item2, dealList(sql));

        ListAdapter promo = new PromotionAdapter(this,R.id.promo_item,gift());
        promoList = (ListView) findViewById(R.id.listViewPromotions);
        promoList.setAdapter(promo);

        scaleListY = promoList.getY();

        deals = (ListView) findViewById(R.id.listviewMain);
        deals.setAdapter(dealList);
        deals.setOnItemClickListener(new ItemInfoListener());
        deals.setOnItemLongClickListener(new OnLongTouchListener());
        deals.setOnScrollListener(new ScrollListener());
        deals.setDivider(null);
        deals.setBackgroundResource(R.drawable.customshape);

    }

    private  ItemPrev itemTemp;
    private int currentPosition;
    private  float scaleListY;


    private class ScrollBackgroundTask extends AsyncTask<ListView,String,ListView>{


        @Override
        protected ListView doInBackground(ListView... params) {
            return null;
        }

        @Override
        protected void onPostExecute(ListView listView) {
            super.onPostExecute(listView);
        }
    }


    private SubActionButton buttonAbout;
    private SubActionButton buttonShare;
    private SubActionButton buttonManual;
    private SubActionButton buttonGift;

    public void floatButton() {
        ImageView image = new ImageView(this);
        image.setImageResource(R.drawable.ic_new_float);

        actionButton = new FloatingActionButton.Builder(this)
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

         buttonAbout = itemBuilder.setContentView(iconAbout).build();
         buttonShare = itemBuilder.setContentView(iconShare).build();
         buttonManual = itemBuilder.setContentView(iconManual).build();
         buttonGift = itemBuilder.setContentView(iconGift).build();

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



    private void showFloating(){

         Animator.AnimatorListener listener = new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                //super.onAnimationEnd(animation);
                ObjectAnimator animator = ObjectAnimator.ofFloat(actionButton,"alpha",0.0f,1.0f);
                ObjectAnimator about = ObjectAnimator.ofFloat(buttonAbout,"alpha",0.0f,1.0f);
                ObjectAnimator share = ObjectAnimator.ofFloat(buttonShare,"alpha",0.0f,1.0f);
                ObjectAnimator manual = ObjectAnimator.ofFloat(buttonManual,"alpha",0.0f,1.0f);
                ObjectAnimator gift = ObjectAnimator.ofFloat(buttonGift,"alpha",0.0f,1.0f);
                AnimatorSet s = new AnimatorSet();
                s.playTogether(animator,about,share,manual,gift);
                s.setDuration(200).start();
            }

            @Override
            public void onAnimationStart(Animator animation) {
                //super.onAnimationStart(animation);
                actionButton.setVisibility(View.INVISIBLE);
                buttonAbout.setVisibility(View.INVISIBLE);
                buttonShare.setVisibility(View.INVISIBLE);
                buttonManual.setVisibility(View.INVISIBLE);
                buttonGift.setVisibility(View.INVISIBLE);
            }
        };
        actionButton.animate().setListener(listener).start();
        actionButton.setVisibility(View.VISIBLE);
        buttonAbout.setVisibility(View.VISIBLE);
        buttonShare.setVisibility(View.VISIBLE);
        buttonManual.setVisibility(View.VISIBLE);
        buttonGift.setVisibility(View.VISIBLE);




    }

    private void hideFloating(){

        Animator.AnimatorListener listener = new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                //super.onAnimationEnd(animation);
                ObjectAnimator alpha = ObjectAnimator.ofFloat(actionButton,"alpha",1.0f,0.0f);
                ObjectAnimator about = ObjectAnimator.ofFloat(buttonAbout,"alpha",1.0f,0.0f);
                ObjectAnimator share = ObjectAnimator.ofFloat(buttonShare,"alpha",1.0f,0.0f);
                ObjectAnimator manual = ObjectAnimator.ofFloat(buttonManual,"alpha",1.0f,0.0f);
                ObjectAnimator gift = ObjectAnimator.ofFloat(buttonGift,"alpha",1.0f,0.0f);

                AnimatorSet s = new AnimatorSet();
                s.playTogether(alpha, about, share, manual, gift);
                s.setDuration(200).start();



            }


        };

     actionButton.animate().setListener(listener).start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                actionButton.setVisibility(View.GONE);
                buttonAbout.setVisibility(View.GONE);
                buttonShare.setVisibility(View.GONE);
                buttonManual.setVisibility(View.GONE);
                buttonGift.setVisibility(View.GONE);

            }
        }, 500);





    }

    private class ScrollListener implements ListView.OnScrollListener{

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {



            if(view.getId()==deals.getId()){

                final int firstItemPosition = deals.getFirstVisiblePosition();

                View v = view.getChildAt(0);
                float topValue = v==null ? 0 : v.getTop()-deals.getPaddingTop();


                if(firstItemPosition>currentPosition && (actionButton.getVisibility()!=View.GONE) && (scrollState==SCROLL_STATE_FLING) &&(scrollState != SCROLL_STATE_TOUCH_SCROLL)){
                     //scrolling down

                    //collapseList();
                    //hideFloating();

                }else if(currentPosition>firstItemPosition && (actionButton.getVisibility()!=View.GONE) &&(scrollState==SCROLL_STATE_FLING) &&(scrollState != SCROLL_STATE_TOUCH_SCROLL)){
                    //scrolling up

                    //hideFloating();


                }else if((scrollState == SCROLL_STATE_IDLE)&&(topValue==0)&& (actionButton.getVisibility()!=View.VISIBLE)){
                    //reveal redeemable gifts
                    //extendList();
                    //showFloating();
                }
                else if((scrollState==SCROLL_STATE_IDLE) && actionButton.getVisibility()!=View.VISIBLE){
                    //showFloating();

                }
                else if(scrollState==SCROLL_STATE_IDLE && (topValue==0) && (promoList.getVisibility()!=View.VISIBLE)){

                }

                currentPosition = firstItemPosition;

            }

        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        }
    }


    private ValueAnimator slideView(int start,int end){
        ValueAnimator animator = ValueAnimator.ofInt(start, end);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //Update The Height of the card

                int value= (Integer) animation.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = promoList.getLayoutParams();
                layoutParams.height = value;
                promoList.setLayoutParams(layoutParams);
            }
        });

        return animator;
    }

    public void extendList(){

        final int widthSpec = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
        final int heightSpec = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);

        promoList.measure(widthSpec,heightSpec);

        ValueAnimator valueAnimator = slideView(0,promoList.getMeasuredHeight());
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                promoList.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {

                promoList.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        valueAnimator.start();


    }

    public void collapseList(){
        int finalHeight = promoList.getHeight();

        ValueAnimator animator = slideView(promoList.getHeight(),0);

        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

               promoList.setVisibility(View.GONE);

                /*
                ViewGroup.LayoutParams layoutParams = extendHeight.getLayoutParams();
                extendHeight.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,extendHeight.getHeight()/2));
                */


            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        animator.start();

    }

    private class OnLongTouchListener implements AdapterView.OnItemLongClickListener{

        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

            //LongClickInfo longClickCheck = new LongClickInfo();
            //longClickCheck.onPostExecute(view);

            ItemPrev item = (ItemPrev)parent.getItemAtPosition(position);
            Intent intent = new Intent(getApplicationContext(),MoreInfo.class);
            intent.putExtra("logo", item.getLogo());
            intent.putExtra("location", item.getDistanceTime());
            intent.putExtra("rating",2.1);
            startActivity(intent);
            return true;

        }
    }

    private class ItemInfoListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


            itemTemp = (ItemPrev)parent.getItemAtPosition(position);
            //setItemPrev(itemTemp);
            GetItemPrev threadGetItem =  new GetItemPrev();
            threadGetItem.doInBackground(itemTemp);

            ExtendInfoInBackGround extend = new ExtendInfoInBackGround();
            extend.doInBackground(view);



         }
    }



    private class GetItemPrev extends AsyncTask<ItemPrev,String,ItemPrev>{

        @Override
        protected ItemPrev doInBackground(ItemPrev... params) {

            ItemPrev temp = params[0];
            setItemPrev(temp);

            return null;
        }


    }

    private View longClickView;
    private class LongClickInfo extends AsyncTask<View,String,View>{

        @Override
        protected View doInBackground(View... params) {

            longClickView = params[0];

            return null;
        }

        @Override
        protected void onPostExecute(View view) {
            super.onPostExecute(view);



            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    Intent intent = new Intent(getApplicationContext(),MoreInfo.class);
                    intent.putExtra("logo", itemTemp.getLogo());
                    intent.putExtra("location", itemTemp.getDistanceTime());
                    intent.putExtra("rating",2.1);
                    startActivity(intent);
                    return true;



                }
            });



        }
    }

    private class ExtendInfoInBackGround extends  AsyncTask<View,String,View>{

        @Override
        protected View doInBackground(View... params) {

            View v = params[0];

            extendInfo(v);

            return null;
        }
    }

    private void setItemPrev(ItemPrev itemPrev){
        this.itemInfo = itemPrev;
    }

    private ItemPrev getItemPrev(){
        return this.itemInfo;
    }


    public PromoItem[] gift(){
        PromoItem [] list = new PromoItem[4];

        for(int i=0;i<list.length;i++){

            PromoItem temp = new PromoItem();
            temp.setImage(R.drawable.gift);
            temp.setPromoSubTitle("Free Cookie!");
            temp.setPromoTitle("Free Item Alert!");
            list[i]= temp;
        }

        return list;
    }

    public ItemPrev[] dealList(){

        ip = new ItemPrev[10];


       // for(int i =0 ;i<50;i++) {

            ItemPrev temp = new ItemPrev();
            temp.setLatitude(51.523992);
            temp.setLongitude(-0.03798);
            //TODO:add generic location to database
            temp.setDistanceTime("2 mins");
            //TODO: add photo download from google storage
            temp.setLogo(R.drawable.mcdonalds);
            temp.setMainText("Happy Meal");
            temp.setRestaurantName("Mc Donalds");
            temp.setSubText("Happy Meal £2");
             temp.setHotDeal(R.drawable.yapnak_colorsmall);
            //TODO: points
            temp.setPoints("to be added");
            //ip[0] = temp;
            ip[0] = temp;


            ItemPrev temp2 = new ItemPrev();
            temp2.setLatitude(51.523992);
            temp2.setLongitude(-0.03798);
            //TODO:add generic location to database
            temp2.setDistanceTime("1 min");
            //TODO: add photo download from google storage
            temp2.setLogo(R.drawable.wrapitup);
            temp2.setMainText("Burrito Deal");
            temp2.setRestaurantName("Wrap It Up");
            temp2.setSubText("Buy 1 Get 1 Free = £4");
            //TODO: points
            temp2.setPoints("to be added");
            ip[1] = temp2;


            ItemPrev temp3 = new ItemPrev();
            //TODO:add generic location to database
            temp3.setLatitude(51.523992);
            temp3.setLongitude(-0.03798);
            temp3.setDistanceTime("1 min");
            //TODO: add photo download from google storage
            temp3.setLogo(R.drawable.pizzaexpresslogo);
            temp3.setMainText("Any Size Pizza");
            temp3.setRestaurantName("Pizza Express");
            temp3.setSubText("Half Price = £4");
            //TODO: points
            temp3.setPoints("to be added");
            ip[2] = temp3;


            ItemPrev temp4 = new ItemPrev();
            //TODO:add generic location to database
            temp4.setLatitude(51.523992);
            temp4.setLongitude(-0.03798);
            temp4.setDistanceTime("3 mins");
            //TODO: add photo download from google storage
            temp4.setLogo(R.drawable.gbklogo);
            temp4.setMainText("Main Meal Deal");
            temp4.setRestaurantName("Gourmet Burger Kitchen");
            temp4.setSubText("£10 off Meal - £5");
            //TODO: points
            temp4.setPoints("to be added");
            ip[3] = temp4;


            ItemPrev temp5 = new ItemPrev();
            //TODO:add generic location to database
            temp5.setLatitude(51.523992);
            temp5.setLongitude(-0.03798);
            temp5.setDistanceTime("2 mins");
            //TODO: add photo download from google storage
            temp5.setLogo(R.drawable.tescologo);
            temp5.setMainText("Meal Deal");
            temp5.setRestaurantName("Tesco");
            temp5.setSubText("Sandwich,Drink,Snack = £3 ");
            //TODO: points
            temp5.setPoints("to be added");
            ip[4] = temp5;


            ItemPrev temp6 = new ItemPrev();
            temp6.setLatitude(51.523992);
            temp6.setLongitude(-0.03798);
            //TODO:add generic location to database
            temp6.setDistanceTime("7 mins");
            //TODO: add photo download from google storage
            temp6.setLogo(R.drawable.wrapitup);
            temp6.setMainText("Burritos Deal");
            temp6.setRestaurantName("Wrap It Up");
            temp6.setSubText("Buy 1 get 1 half price £5");
            //TODO: points
            temp6.setPoints("to be added");
            //ip[0] = temp;
            ip[5] = temp6;


            ItemPrev temp7 = new ItemPrev();
            temp7.setLatitude(51.523992);
            temp7.setLongitude(-0.03798);
            //TODO:add generic location to database
            temp7.setDistanceTime("1 min");
            //TODO: add photo download from google storage
            temp7.setLogo(R.drawable.pizzaexpresslogo);
            temp7.setMainText("Any Size Pizza");
            temp7.setHotDeal(R.drawable.yapnak_colorsmall);
            temp7.setRestaurantName("Pizza Express");
            temp7.setSubText("Any Size Pizza  = £4");
            //TODO: points
            temp7.setPoints("to be added");
            ip[6] = temp7;


            ItemPrev temp8 = new ItemPrev();
            //TODO:add generic location to database
            temp8.setLatitude(51.523992);
            temp8.setLongitude(-0.03798);
            temp8.setDistanceTime("7 mins");
            //TODO: add photo download from google storage
            temp8.setLogo(R.drawable.pizzaexpresslogo);
            temp8.setMainText("Any Size Pizza");
            temp8.setRestaurantName("Pizza Express");
            temp8.setSubText("Half Price = £4");
            //TODO: points
            temp8.setPoints("to be added");
            ip[7] = temp8;


            ItemPrev temp9 = new ItemPrev();
            //TODO:add generic location to database
            temp9.setLatitude(51.523992);
            temp9.setLongitude(-0.03798);
            temp9.setDistanceTime("6 mins");
            //TODO: add photo download from google storage
            temp9.setLogo(R.drawable.gbklogo);
            temp9.setMainText("Main Meal Deal");
            temp9.setRestaurantName("Gourmet Burger Kitchen");
            temp9.setSubText("£10 off Meal - £5");
            //TODO: points
            temp9.setPoints("to be added");
            ip[8] = temp9;


            ItemPrev temp10 = new ItemPrev();
            //TODO:add generic location to database
            temp10.setLatitude(51.523992);
            temp10.setLongitude(-0.03798);
            temp10.setDistanceTime("5 mins");
            //TODO: add photo download from google storage
            temp10.setLogo(R.drawable.tescologo);
            temp10.setMainText("Meal Deal");
            temp10.setRestaurantName("Tesco");
            temp10.setSubText("Sandwich,Drink,Snack = £3 ");
            //TODO: points
            temp10.setPoints("to be added");
            ip[9] = temp10;

       // }




        return ip;

    }

    public ItemPrev[] dealList(SQLList sql) {



        try{
            List<SQLEntity> list = new ArrayList<SQLEntity>(sql.getList());
            ip = new ItemPrev[list.size()];
            for (int i = 0; i < ip.length; i++) {
                ItemPrev temp = new ItemPrev();
                //TODO:add generic location to database

                temp.setDistanceTime("To be added");
                //TODO: add photo download from google storage
               // temp.setLogo(R.drawable.mcdonalds);

                //Implement Koush Ion - populate ListView

                //download and display image from url
                String url = list.get(i).getPhoto();
                //String url = sql.getList().get(i).getPhoto();
                temp.setFetchImageURL(url);
                /////////////////////////////////////////////

                temp.setMainText(sql.getList().get(i).getFoodStyle());
                temp.setRestaurantName(sql.getList().get(i).getName());
                temp.setSubText(sql.getList().get(i).getOffer());
                temp.setLatitude(sql.getList().get(i).getY());
                temp.setLongitude(sql.getList().get(i).getX());

                temp.setDistanceTime("to be added");

                temp.setLogo(R.drawable.yapnakmonster);
                temp.setMainText(list.get(i).getFoodStyle());
                temp.setRestaurantName(list.get(i).getName());
                temp.setSubText(list.get(i).getOffer());
                temp.setLatitude(list.get(i).getY());
                temp.setLongitude(list.get(i).getX());

                //TODO: points
                temp.setPoints("to be added");
                ip[i] = temp;
            }

            return ip;

        }catch(Exception e){
            e.printStackTrace();
            ip = new ItemPrev[1];


            ItemPrev temp = new ItemPrev();
            //TODO:add generic location to database
            temp.setDistanceTime("to be added");
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