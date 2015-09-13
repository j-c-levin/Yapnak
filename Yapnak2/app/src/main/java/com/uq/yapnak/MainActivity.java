package com.uq.yapnak;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.ContactsContract;
import android.support.v4.app.NotificationCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.frontend.yapnak.AdapterPrev;
import com.frontend.yapnak.ItemPrev;
import com.frontend.yapnak.maps.features.MapActivity;
import com.frontend.yapnak.navigationdrawer.NavBarItem;
import com.frontend.yapnak.navigationdrawer.NavigationBarAdapter;
import com.frontend.yapnak.promotion.PromoItem;
import com.frontend.yapnak.promotion.PromotionAdapter;
import com.frontend.yapnak.promotion.PromotionDialog;
import com.frontend.yapnak.rate.RatingBuilder;
import com.google.android.gms.maps.model.LatLng;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.native_tutorial.TutorialFragOne;
import com.native_tutorial.TutorialFragThree;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;
import com.yapnak.gcmbackend.sQLEntityApi.model.SQLEntity;
import com.yapnak.gcmbackend.sQLEntityApi.model.SQLList;
import com.yapnak.gcmbackend.userEndpointApi.UserEndpointApi;
import com.yapnak.gcmbackend.userEndpointApi.model.OfferEntity;
import com.yapnak.gcmbackend.userEndpointApi.model.OfferListEntity;
import com.yapnak.gcmbackend.userEndpointApi.model.RecommendEntity;
import com.yapnak.gcmbackend.userEndpointApi.model.UserDetailsEntity;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {
    private static final int NOTIFICATION_ID = 0;

    //GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,ResultCallback<People.LoadPeopleResult>
   // private GoogleApiClient mGoogleApiClient;
    public static final int FRAGMENT_RESULT = 1001;
    private Parcelable state;
    private SQLEntity sql;
    private SharedPreferences keep;
    private SharedPreferences remember;
    RecyclerView recyclerView;
    private static String USER_NAME;
    private static String PASS;
    private final int RESULT= 1;
    private final int SEARCH_RESULT = 123;
    private static String TAG_ABOUT = "About";
    private static String TAG_SETTINGS = "Settings";
    private static String TAG_REWARD = "Rewards";
    private static String TAG_FEEDBACK ="Feddback";
    private static String TAG_GIFT = "Gifts";
    private static String TAG_PROFILE ="Profile";
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
    private SQLList clientList;
    private String ID;
    private OfferListEntity list;
    private ContactDetails details;


    public String getID(){
        return this.ID;
    }
    public void setID(String id){
        this.ID = id;
    }
    public void setList(OfferListEntity list){
        this.list= list;
    }
    public OfferListEntity getList(){
        return list;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    private static class SavedList implements Parcelable{

        private OfferListEntity oList;

        public SavedList(OfferListEntity list){
            this.setList(oList);
        }

        protected SavedList(Parcel in) {
           ClassLoader cl = ClassLoader.getSystemClassLoader();
           in.readValue(cl);
        }

        public static final Creator<SavedList> CREATOR = new Creator<SavedList>() {
            @Override
            public SavedList createFromParcel(Parcel in) {
                return new SavedList(in);
            }

            @Override
            public SavedList[] newArray(int size) {
                return new SavedList[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {

            dest.writeValue(getList());

        }

        public OfferListEntity getList() {
            return oList;
        }

        public void setList(OfferListEntity list) {
            this.oList = list;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable("saved",new SavedList(getList()));
        super.onSaveInstanceState(outState);


    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {

       SavedList list= savedInstanceState.getParcelable("saved");


        SQLConnectAsyncTask.restore=true;
        this.setList(list.getList());
        new SQLConnectAsyncTask(getApplicationContext(),locationCheck,null, this).execute();


        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new GcmRegistrationAsyncTask(this).execute();


       /* mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API)
                .addScope(new Scope("profile"))
                .addScope(Plus.SCOPE_PLUS_LOGIN)
                .addScope(Plus.SCOPE_PLUS_PROFILE).build();
        */

        GPSTrack track = new GPSTrack(MainActivity.this);
        loc = track.getLocation();

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        setContentView(R.layout.activity_main1);

        locationCheck = getLocation();
        //if(locationCheck!=null) {

             SQLConnectAsyncTask.useDialog = true;
             new SQLConnectAsyncTask(getApplicationContext(), locationCheck,null, this).execute();
             if (SQLConnectAsyncTask.getListLoaded()) {
                 //dealList.notifyDataSetChanged();
             }


        this.name = getIntent();
        ID = (name.getStringExtra("userID")==null)? name.getStringExtra("initials") : name.getStringExtra("userID");

        details = new ContactDetails();
        details.setUserID(name.getStringExtra("userID"));
        details.setEmailAd(name.getStringExtra("email"));
        details.setPassword(name.getStringExtra("password"));
        details.setPhoneNum(name.getStringExtra("phone"));

        keep = getSharedPreferences("KeepMe", Context.MODE_PRIVATE);
        remember = getSharedPreferences("RememberMe", Context.MODE_PRIVATE);



       // Log.d("main-id",ID);

        //navBarToggle();
        //navigationBarContent();



    }


   /* private boolean exit = false;
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (exit) {
            finish();
        }
    }
    */

    private final String LOG_INFO="log";


    public void setUserName(Menu menu){
        //MenuItem item = menu.findItem(R.id.userNameToolBar);




            //personName = (name.getStringExtra("accName").equalsIgnoreCase("")) ? name.getStringExtra("initials") : name.getStringExtra("accName");
            // String[] names = personName.split("@");
            //item.setTitle(names[0]);
            //item.setTitle(ID);


    }

    public void tutorial(){

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.fragmentContainer,new TutorialFragOne(this)).setCustomAnimations(0,0).commit();

    }

    private Location locationCheck;

    /*@Override
    public void onConnected(Bundle connectionHint) {
        Log.d("debug", "onConnected");
        //Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        //if (mLastLocation != null) {

        if(locationCheck!=null){
           // Log.d("debug", "Location Found: " + mLastLocation.toString());
            Toast.makeText(getApplicationContext(),"Connected",Toast.LENGTH_SHORT).show();
            //new SQLConnectAsyncTask(getApplicationContext(), mLastLocation, this).execute();
            //new SQLConnectAsyncTask(getApplicationContext(), getLocation(), this).execute();
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
    */

    /*
    @Override
    public void onConnectionSuspended(int i) {
        Log.d("Location", "onConnectionSuspended");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d("Location", "onConnectionFailed");
    }
    */

    @Override
    protected void onResume() {
        Log.d("Location", "resuming googleAPI connection");
        super.onResume();
        //mGoogleApiClient.connect();
    }

    @Override
    protected void onPause() {
        Log.d("Location", "pausing googleAPI connection");

       // if (mGoogleApiClient.isConnected()) {
         //   mGoogleApiClient.disconnect();
        //}

        super.onPause();
    }

    private MainActivity activity = this;
    private String queryMain;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_with_search, menu);
            //setUserName(menu);

        SearchManager sManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView view = (SearchView) menu.findItem(R.id.search).getActionView();
        view.setSearchableInfo(
                sManager.getSearchableInfo(new ComponentName(getApplicationContext(), SearchMain.class)));

        view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                queryMain = query;
                Log.d("querySub", query);
                new SearchLocation().execute(queryMain);
                InputMethodManager input = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if(!input.isAcceptingText()){
                    view.clearFocus();
                    input.hideSoftInputFromInputMethod(view.getWindowToken(),0);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("queryChange", newText);
                return true;
            }
        });
        return true;
    }

    private Location getLoc(String address){
        String trim = address.trim();
        String finalAddress = trim.replaceAll(",","");
        String newAddress = finalAddress.replaceAll("\\s+", "");

        Log.d("address",newAddress);

        String url = "https://maps.google.com/maps/api/geocode/json?address="+newAddress+"&sensor=false";


        HttpGet get = new HttpGet(url);
        HttpClient client = new DefaultHttpClient();
        HttpResponse response;
        Location loc = new Location("");
        StringBuilder builder = new StringBuilder();
        try{

            response = client.execute(get);
            HttpEntity entity = response.getEntity();
            InputStream stream = entity.getContent();


            int data;
            while((data=stream.read())!=-1){
                builder.append((char)data);
            }
            stream.close();

        }catch (ClientProtocolException cpe){

        }catch (IOException io){

        }


        double lat=0.0;
        double lng=0.0;
        JSONObject object;

        try {
            object = new JSONObject(builder.toString());
            lat = ((JSONArray) object.get("results")).getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getDouble("lat");
            lng = ((JSONArray)object.get("results")).getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getDouble("lng");

            loc.setLatitude(lat);
            loc.setLongitude(lng);

            return loc;

        }catch(JSONException e){
            e.printStackTrace();
            return null;
        }
    }

    private class SearchLocation extends AsyncTask<String,String,Location>{
        private String loc;
        @Override
        protected Location doInBackground(String... params) {
             loc = params[0];
            Location location = getLoc(loc);
            return location;
        }

        @Override
        protected void onPostExecute(Location location) {
            super.onPostExecute(location);

            if(location!=null){
                Log.d("location","Latitude: "+String.valueOf(location.getLatitude())+ " Longitude: " + String.valueOf(location.getLongitude()));
                setLocation(location);
                SQLConnectAsyncTask.useDialog=true;
                new SQLConnectAsyncTask(getApplicationContext(),location,null,activity).execute();
            }else if(location==null && loc!=null){
                setRestaurantName(loc);
                SQLConnectAsyncTask.useDialog=true;
                new SQLConnectAsyncTask(getApplicationContext(),null,loc,activity).execute();
            }else if(location!=null && loc!=null){
                setRestaurantName(loc);
                setLocation(location);
                SQLConnectAsyncTask.useDialog=true;
                new SQLConnectAsyncTask(getApplicationContext(),getLocation(),loc,activity).execute();
                Toast.makeText(getApplicationContext(),"The Restaurant/Location You Queried Is Not Available",Toast.LENGTH_SHORT).show();
            }
        }
    }


    private boolean connection(){
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
        boolean lte = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();



        return (wifi||lte);
    }

    private boolean gpsCheck(){
        LocationManager loc = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        boolean network = loc.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        boolean gps = loc.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return gps||network;
    }

    private String hashing(String hash){
        StringBuffer buffer = new StringBuffer();

        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(hash.getBytes());
            byte[] digestedBytes = digest.digest();

            for (int i = 0; i < digestedBytes.length; i++) {

                String hex = Integer.toHexString(0xff & digestedBytes[i]);
                if (hex.length() == 1) {
                    buffer.append(0);
                }
                buffer.append(hex);
            }
            return buffer.toString();

        }catch(NoSuchAlgorithmException e){
            e.printStackTrace();
            return buffer.toString();
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
           /* if (mGoogleApiClient.isConnected()) {
                // Prior to disconnecting, run clearDefaultAccount().
                Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
                Plus.AccountApi.revokeAccessAndDisconnect(mGoogleApiClient)
                        .setResultCallback(new ResultCallback<Status>() {

                            @Override
                            public void onResult(Status status) {
                                signedOut();
                            }
                        });
                */


                Intent intent = new Intent(MainActivity.this,Login.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();

        }
        /*if(id==R.id.userNameToolBar){
            String url = "http://www.google.co.uk";
            String userid= ID;
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMDDhhmmss", Locale.UK);
            String date = sdf.format(cal.getTime());

            String toHash =userid+date+"YAPNAKRULES";

            String insert ="{ " +
                             "\"id\" : \""+userid+"\","+
                            "\"date\" :\""+date+"\","+
                            "\"hash\" : \""+hashing(toHash)+"\" }";



            AlertDialog generator = new QRGenerator(this,this,insert);
            generator.show();
        }*/
        if(id==R.id.search){

            Intent i = new Intent(this, SearchMain.class);
            startActivityForResult(i,SEARCH_RESULT);

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
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
        finish();

    }

    //view the main activitiy - clean up code and make it simpler


    private void showMain(){


        //load(sql);
        load();
        // navBarToggle();
        //navigationBarContent();
        getSupportActionBar().setSubtitle(temp.getStringExtra("initials"));

    }

    private AlertDialog neg,pos;

    @Override
    public void onClick(View v) {
        if (v.getTag().equals(TAG_ABOUT)) {
            aboutYapnak();
            Toast.makeText(this, "About", Toast.LENGTH_SHORT).show();
        } else if (v.getTag().equals(TAG_FEEDBACK)) {
            if(connection() &&(deals.getCount()>1)) {
                final FeedbackDialog feedback = new FeedbackDialog(this, this, ID);
                feedback.show();
            }
            /*
            pos = feedback.setPositiveButton("SUBMIT", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //TODO:submit feedback - store string into db table


                    EditText feedbackComment = feedback.getComments();

                    String text = feedbackComment.getText().toString();
                    //TODO:text must be stored in feedback table in the database

                    Toast.makeText(getApplicationContext(), "Thank You For Your Feedback  " + text, Toast.LENGTH_SHORT).show();

                    //load();
                    //load(sql);

                    dialog.dismiss();
                }
            }).create();

            pos.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialog) {
                    Color c = new Color();
                    pos.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(c.parseColor("#B71C1C"));
                }
            });

            //neg =

            feedback.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    dialog.cancel();

                }
            });.create();

            */

            /*
            neg.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialog) {
                    Color c = new Color();
                    neg.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(c.parseColor(""));
                }
            });
            */
        } else if (v.getTag().equals(TAG_REWARD)) {
            // howToUseYapnak();
            //Toast.makeText(this, "How To Use The App", Toast.LENGTH_LONG).show();
            userItems();
        } else if (v.getTag().equals(TAG_GIFT)) {
            //userItems();
        }else if(v.getTag().equals(TAG_PROFILE)){
            //SHOW PROFILE
            profileDialog(v);
        }else if(v.getTag().equals(TAG_SETTINGS)){
            SettingsDialog dialog = new SettingsDialog(this,details,remember,keep);
            dialog.show();
        }
    }



    private Button date;
    private RadioGroup gender;
    private AlertDialog posProf;
    private void profileDialog(View v){

        ///AlertDialog.Builder userItems = new ProfileDialog(this,this);

        ProfileDialog userItems = new ProfileDialog(this,this,ID);

       //userItems.setID(ID);

       /* posProf=userItems.setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).create();
        posProf.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Color c = new Color();
                posProf.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(c.parseColor("#B71C1C"));
            }
        });
        userItems.setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        */

        /*
        AlertDialog.Builder userItems= new AlertDialog.Builder(v.getContext());

        final LinearLayout linearLayout = new LinearLayout(v.getContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams layoutParams= new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        layoutParams.setMargins(20, 20, 20, 20);
        final RedEditText phoneNumber = new RedEditText(v.getContext());
        phoneNumber.setHint("Enter Phone Number");
        phoneNumber.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        phoneNumber.setMaxLines(1);
        phoneNumber.setSingleLine();
        phoneNumber.setGravity(Gravity.TOP);

        final RedEditText name = new RedEditText(v.getContext());
        name.setHint("Enter Full Name");
        name.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        name.setMaxLines(1);
        name.setSingleLine();
        name.setGravity(Gravity.TOP);

        date = new Button(v.getContext());

        LinearLayout.LayoutParams dateParams= new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dateParams.gravity = Gravity.CENTER_HORIZONTAL;
        dateParams.setMargins(50, 20, 50, 20);


        date.setText("Enter Date Of Birth");
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateDialog dialog = new DateDialog();
                dialog.show(getFragmentManager(),"dateDialog");
            }
        });

        gender = new RadioGroup(v.getContext());

        final RadioButton male = new RadioButton(v.getContext());
        male.setText("Male");
        final RadioButton female = new RadioButton(v.getContext());
        female.setText("Female");

        gender.addView(male);
        gender.addView(female);
        gender.setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout.LayoutParams genderParams= new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        //genderParams.gravity=Gravity.CENTER;
        genderParams.setMargins(100,20,50,40);

        userItems.setTitle("Profile");
        linearLayout.addView(phoneNumber,layoutParams);
        linearLayout.addView(name,layoutParams);
        linearLayout.addView(date,dateParams);
        linearLayout.addView(gender,genderParams);
        userItems.setView(linearLayout);
        userItems.setPositiveButton("OK", null);
        userItems.setNegativeButton("CANCEL", null);

        */

        /*
        input.setHint("EG: NS-6438");
        input.setSingleLine(false);
        input.setMaxLines(1);
        input.setTextColor(Color.BLACK);
        linearLayout.addView(message);
        linearLayout.addView(input);
        linearLayout.addView(OR);
        linearLayout.addView(contactButton);
        userItems.setView(linearLayout);
        */

        //AlertDialog dialog = userItems.create();

        userItems.show();


    }

    private class DateDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener {
        private int day, month, year;
        private Bundle bundle;
        private final int DATEFRAGMENT = 1;



        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            final Calendar calendar = Calendar.getInstance();
            day = calendar.get(Calendar.DAY_OF_MONTH);
            month = calendar.get(Calendar.MONTH);
            year = calendar.get(Calendar.YEAR);

            DatePickerDialog pickerDialog = new DatePickerDialog(getActivity(), this, day, month, year);
            return pickerDialog;


        }

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            //setTargetFragment(this, DATEFRAGMENT);
            int month = monthOfYear+1;
            String dateString = dayOfMonth + " / " + month + " / " + year;
            date.setText(dateString);
            this.dismiss();
        }
    }

    private class RecommendUser extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... params) {

            UserEndpointApi user = new UserEndpointApi(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(),null);
            try {
                UserDetailsEntity recommendee=null;
                 if(params[0]!=null) {
                     recommendee = user.getUserDetails().setMobNo(params[0]).execute();
                 }else{
                     recommendee = user.getUserDetails().setEmail(params[1]).execute();
                }
                 RecommendEntity recommender = user.recommend((int) getClientID(), ID).setOtherUserId(recommendee.getUserId()).execute();

                String message = "Status: "+recommender.getStatus() + " Recommend Message: " + recommender.getMessage() + "\nRecommendee Info Message and Status : "+ recommendee.getStatus() +" " + recommendee.getStatus() ;
                return message;
            }catch(IOException e){
                e.printStackTrace();
                return null;
            }

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("recommendMsg",s);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CONTACTPICK_RESULT){

            if(resultCode==RESULT_OK) {
                //String phoneNum = data.getStringExtra("phone_num");
                //contactButton.setText(phoneNum);
                Uri contactDetails = data.getData();

                Cursor c = getContentResolver().query(contactDetails,new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER},null,null,null);
                if(c.moveToFirst() && c!=null){

                    String phone = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    String email = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS));
                    new RecommendUser().execute(phone,email);
                    Toast.makeText(getApplicationContext(),"Phone Number: " + phone+ " Email: "+ email,Toast.LENGTH_SHORT).show();
                }
            }

        }else if(requestCode == FRAGMENT_RESULT){

                FragmentTransaction t = getFragmentManager().beginTransaction();
                t.replace(R.id.fragmentContainer, new TutorialFragThree(this));
                t.commit();

        }
        if(isTutorialOn()){
            if(requestCode == 111){
                int value = this.tutorial.getInt("tutorial2",-1);
                if(value==0){
                    animateButton(tButton1,textButton1,false);
                    animateButton(tButton2,textButton2,true);
                    this.tutorial.edit().putInt("tutorial2", 1).apply();
                }

            } if(requestCode == PICK_CONTACT){
                int value = this.tutorial.getInt("tutorial2",-1);
                if(value==1){
                    animateButton(tButton2,textButton2,false);
                    animateButton(tButton3,textButton3,true);
                    this.tutorial.edit().putInt("tutorial2", 2).apply();
                }

            }
        }

    }


    @Override
    protected void onStop() {
        super.onStop();
        tutorial = getSharedPreferences("tutorial",Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = tutorial.edit();

        edit.putInt("tutorial3",4);
        edit.putInt("count", 1);
        edit.apply();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        tutorial = getSharedPreferences("tutorial",Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = tutorial.edit();
        edit.putInt("tutorial3",4);
        edit.putInt("count",1);
        edit.apply();
    }

    //private ListView list;
    private final int PICK_CONTACT = 1;
    private Button contactButton;
    private String clientID;

    private final int CONTACTPICK_RESULT= 123;
    AlertDialog posRec;
    public void recommendMealButton(View v) {

        //RecommendDialog recommend = new RecommendDialog(this,this);
        //recommend.setUserID(ID);
        //recommend.setClientID(clientID);
        //contactButton = recommend.getContactListButton();
        //recommend.show();

        if (connection() &&(deals.getCount()>1) ){
            Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
            int value = this.tutorial.getInt("tutorial2",-1);
            startActivityForResult(intent, PICK_CONTACT);
        }
        /*contactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {

                        //Self implemented contacts list - Go into ContactList if you want to DO SOMETHING Once a contact is selected.
                        //Add code in onItemClick Method in contactList, if you want a list item to do something
                       // Intent intent = new Intent(getApplicationContext(), ContactList.class);
                        //startActivityForResult(intent, CONTACTPICK_RESULT);

                        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                        startActivityForResult(intent, PICK_CONTACT );


                        //Comment Out
                        Showing the google stock contacts picker
                        When contact chosen, DO SOMETHING in "onActivityResult" Method

                        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                        startActivityForResult(intent, PICK_CONTACT );
                        //COmment OUT

                    }
                });
            }
        });
        */

        /*

        posRec = recommend.setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();

            }
        }).create();

        posRec.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Color c = new Color();
                posRec.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(c.parseColor("B71C1C"));
                posRec.getButton(AlertDialog.BUTTON_POSITIVE).setHighlightColor(c.parseColor("B71C1C"));
            }
        });

        */



        /*
        AlertDialog.Builder userItems = new AlertDialog.Builder(v.getContext());
        final LinearLayout linearLayout = new LinearLayout(v.getContext());

        userItems.setTitle("👥 Recommend");
        //userItems.setMessage("Enter your friends Yapnak iD");
        userItems.setPositiveButton("OK", null);
        userItems.setNegativeButton("CANCEL", null);



        LayoutInflater inflater = this.getLayoutInflater();
        View dialog = inflater.inflate(R.layout.recommend_dialog,null);
        userItems.setView(dialog);


        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setPadding(40, 0, 40, 0);

        TextView message = new TextView(v.getContext());
        final EditText input = new EditText(v.getContext());

        TextView OR = new TextView(v.getContext());
        OR.setText("OR");

        final Button contactButton = new Button(v.getContext());


        contactButton.setText("Phonebook");

        contactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Handler().post(new Runnable() {
                    @Override
                    public void run() {

                        //Self implemented contacts list - Go into ContactList if you want to DO SOMETHING Once a contact is selected.
                        //Add code in onItemClick Method in contactList, if you want a list item to do something
                        //Intent intent = new Intent(getApplicationContext(), ContactList.class);
                        //startActivity(intent);


                        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                        startActivityForResult(intent, PICK_CONTACT);


                        /*
                        Showing the google stock contacts picker
                        When contact chosen, DO SOMETHING in "onActivityResult" Method

                        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                        startActivityForResult(intent, PICK_CONTACT );
                        */

              //      }
            //    });

          //  }
        //});





        //list = (ListView) findViewById(R.id.contactList);

        /*
        final RecommendDialog dialog = new RecommendDialog(this,this);

        dialog.setPositiveButton("OK", null);
        dialog.setNegativeButton("CANCEL", null);

        dialog.getContactListButton().setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //list
                //Fragment contactList = new ContactListFragment();
                //FragmentTransaction transaction = getFragmentManager().beginTransaction();

                //transaction.replace(dialog.getLayoutId(),contactList).addToBackStack(null).commit();

                new Handler().post(new Runnable() {
                    @Override
                    public void run() {

                        Intent intent = new Intent(getApplicationContext(),ContactListFragment.class);
                        startActivity(intent);
                    }
                });



            }
        });
        */


        /*
        LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        buttonParams.gravity = Gravity.CENTER;
        buttonParams.setMargins(0,20,0,20);

        input.setHint("EG: NS-6438");
        input.setSingleLine(false);
        input.setMaxLines(1);
        input.setTextColor(Color.BLACK);
        linearLayout.addView(message);
        linearLayout.addView(input);
        linearLayout.addView(OR,buttonParams);
        linearLayout.addView(contactButton,buttonParams);
        userItems.setView(linearLayout);



        //AlertDialog dialog = userItems.create();
        //dialog.show();
        userItems.show();
        */




    }

    public void setDirections(View v){
            double longitude = 0;
            double latitude = 0;
            if (tracker.canGetLoc()) {

                longitude = tracker.getLongitude();
                latitude = tracker.getLatitude();
            }

            Button takeMeThere = (Button) v.findViewById(R.id.takeMeThere);
            Intent maps = new Intent(this, MapActivity.class);
            maps.putExtra("init", initials);
            maps.putExtra("accName", personName);
            final int result = 1;

            ItemPrev itemAtSelectedPosition = getItemPrev();

            maps.putExtra("userLongitude", longitude);
            maps.putExtra("userLatitude", latitude);

            if (itemAtSelectedPosition != null) {
                maps.putExtra("resLongitude", itemAtSelectedPosition.getLongitude());
                maps.putExtra("resLatitude", itemAtSelectedPosition.getLatitude());
            }

        startActivityForResult(maps,111);

    }

    public void takeMeThereButton(View v) {

        if(connection() &&(deals.getCount()>1)) { //&& gpsCheck()
            tracker = new GPSTrack(MainActivity.this);


            if ((getItemPrev() != null)) {

                try {
                    setTakeMeThereView(v);
                    GetDirections directions = new GetDirections();
                    directions.doInBackground(tracker);
                    directions.execute();

                } catch (Exception e) {
                    Toast.makeText(this, "There was an error in retrieving the selected restaurant/deal's location - Please enter location manually", Toast.LENGTH_LONG).show();
                    setDirections(v);
                }

            } else {
                setDirections(v);
            }


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

                startActivityForResult(mapIntent,111);

            }catch(NullPointerException e){

                Toast.makeText(getApplicationContext(),"There was an error in retrieving the selected restaurant/deal's location - Please enter location manually",Toast.LENGTH_LONG).show();
                setDirections(getTakeMeThereView());

            }
        }
    }

    //item2 OnClick method for getDealButton

    public void getDeal(View v){
        if(connection()&&(deals.getCount()>1)) {
            String url = "http://www.google.co.uk";
            Intent userID = getIntent();
            String userid = userID.getStringExtra("userID");
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMDDhhmmss", Locale.UK);
            String date = sdf.format(cal.getTime());

            String toHash = userid + date + "YAPNAKRULES";

            JSONObject object = new JSONObject();
            try {
                object.put("id", userid);
                object.put("date",date);
                object.put("hash",toHash);
                object.put("offerid",getOfferID());
                object.put("clientid",getClientID());
            }catch (JSONException e){

            }
            /*String insert = "{ " +
                    "\"id\" : \"" + userid + "\"," +
                    "\"date\" :\"" + date + "\"," +
                    "\"hash\" : \"" + hashing(toHash) + "\" " +
                    "\"hash\" : \"" + "" + "\"" +
                    "}";*/

            String insert = object.toString();


            AlertDialog generator = new QRGenerator(this, this, insert);
            generator.setTitle("GET DEAL");
            int value = 0;
            if(isTutorialOn()) {
                value = this.tutorial.getInt("tutorial2", -1);
            }
            if(value==3){
                animateButton(tButton4,textButton4,false);
                Animator.AnimatorListener containerAnimation = new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        ObjectAnimator alphaButton = ObjectAnimator.ofFloat(buttonContainer, "alpha", 1.0f, 0.5f, 0.0f);
                        ObjectAnimator alphaText = ObjectAnimator.ofFloat(textContainer, "alpha", 1.0f, 0.5f, 0.0f);
                        AnimatorSet s = new AnimatorSet();
                        s.setDuration(200);
                        s.playTogether(alphaButton, alphaText);
                        s.start();
                    }
                };
                buttonContainer.animate().setListener(containerAnimation).start();
                buttonContainer.setVisibility(View.GONE);
                textContainer.setVisibility(View.GONE);

                this.tutorial.edit().putInt("tutorial2",4).apply();
            }
            Log.d("value",String.valueOf(value));
            generator.show();

        }
    }


    public void feedbackButton(View v) {
        Button feedbackButton = (Button) v.findViewById(R.id.feedbackButton);
        //RatingDialog rate = new RatingDialog();
        //rate.show(getFragmentManager(),"rating");
        //AlertDialog.Builder ratings = new RatingBuilder(this,this);

        if(connection() && (deals.getCount()>1)) {
            RatingBuilder ratings = new RatingBuilder(this, this,getItemPrev(),ID);

            int value =0;
            if(isTutorialOn()){
                value= this.tutorial.getInt("tutorial2",-1);
            }

            if(value==2){
                animateButton(tButton3,textButton3,false);
                animateButton(tButton4,textButton4,true);
                this.tutorial.edit().putInt("tutorial2",3).apply();
            }
            Log.d("value",String.valueOf(value));
            ratings.show();
        }
        /*
        ratings.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               //TODO:Submit Ratings to DB
               dialog.dismiss();
            }
        });
        ratings.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
            }
        });

        ratings.setTitle("Rate Deal");
        */
    }

    public void setLongPress(boolean lp) {
        longPress = lp;
    }

    public boolean getLongPress() {
        return this.longPress;
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
        dealList = new AdapterPrev(this, R.id.item2, dealList());
        ListAdapter promo = new PromotionAdapter(this,R.id.promo_item,gift());
        promoList = (ListView) findViewById(R.id.listViewPromotions);
        promoList.setAdapter(promo);
        scaleListY = promoList.getY();
        deals = (ListView) findViewById(R.id.listviewMain);
        deals.setTextFilterEnabled(true);
        deals.setBackgroundResource(R.drawable.curved_card);
        deals.setAdapter(dealList);
        deals.setOnItemLongClickListener(new OnLongTouchListener());
        deals.setOnItemClickListener(new ItemInfoListener(true));
        deals.setOnScrollListener(new ScrollListener());


    }



    private AdapterPrev dealList;
    public void load(OfferListEntity sql) {
        setContentView(R.layout.activity_main1);
        dealList = new AdapterPrev(this, R.id.item2, dealList(sql));

        ListAdapter promo = new PromotionAdapter(this,R.id.promo_item,gift());
        promoList = (ListView) findViewById(R.id.listViewPromotions);
        promoList.setAdapter(promo);

        scaleListY = promoList.getY();

        deals = (ListView) findViewById(R.id.listviewMain);
        deals.setTextFilterEnabled(true);
        deals.setAdapter(dealList);
        deals.setOnItemClickListener(new ItemInfoListener(false));
        deals.setOnItemLongClickListener(new OnLongTouchListener());
        deals.setOnScrollListener(new ScrollListener());





        /*if(deals.getAdapter().getCount()<=5){
            actionButton.setAlpha(1.0f);
            buttonAbout.setAlpha(1.0f);
            buttonFeedback.setAlpha(1.0f);
            buttonGift.setAlpha(1.0f);
            buttonProfile.setAlpha(1.0f);
            actionButton.setVisibility(View.VISIBLE);
            buttonAbout.setVisibility(View.VISIBLE);
            buttonFeedback.setVisibility(View.VISIBLE);
            buttonGift.setVisibility(View.VISIBLE);
            buttonProfile.setVisibility(View.VISIBLE);
        }
        */





        deals.setDivider(null);
        deals.setBackgroundResource(R.drawable.customshape);

    }

    private  ItemPrev itemTemp;
    private int currentPosition;
    private  float scaleListY;

    private SharedPreferences tutorial;

    public boolean isTutorialOn(){
        tutorial = getSharedPreferences("tutorial",Context.MODE_PRIVATE);

        if (tutorial.getInt("count",-1)==1 && tutorial.getInt("tutorial2",-1)==4) {
            return false;
        } else {
            if(tutorial.getInt("tutorial2", -1)==-1){
                SharedPreferences.Editor editor = tutorial.edit();
                editor.putBoolean("firstTime", true);
                editor.putInt("count", 0);
                editor.putInt("tutorial2",0);
                editor.putInt("tutorial3",0).apply();
            }else{


                int count = tutorial.getInt("count", -1);
                int tutorial2 = tutorial.getInt("tutorial2",-1);
                int tutorial3 = tutorial.getInt("tutorial3", -1);

                SharedPreferences.Editor editor = tutorial.edit();
                editor.putInt("count", count);
                editor.putInt("tutorial2", tutorial2);
                editor.putInt("tutorial3",tutorial3).apply();
            }
            return true;
        }
    }

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
    private SubActionButton buttonFeedback;
    private SubActionButton buttonReward;
    private SubActionButton buttonSettings;
    private SubActionButton buttonProfile;

    public void floatButton() {
        ImageView image = new ImageView(this);
        image.setImageResource(R.drawable.more);

        actionButton = new FloatingActionButton.Builder(this)
                .setContentView(image)
                .setBackgroundDrawable(R.drawable.selector_file_red)
                .build();


        ImageView iconAbout = new ImageView(this);
        iconAbout.setImageResource(R.drawable.abouticon);

        ImageView iconFeedback = new ImageView(this);
        iconFeedback.setImageResource(R.drawable.shareicon);

        ImageView iconReward = new ImageView(this);
        iconReward.setImageResource(R.drawable.trophy);

        ImageView iconSettings = new ImageView(this);
        iconSettings.setImageResource(R.drawable.settings);

        ImageView iconProfile = new ImageView(this);
        iconProfile.setImageResource(R.drawable.avatar_white);

        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);
        itemBuilder.setBackgroundDrawable(getResources().getDrawable(R.drawable.selector_file_green));

        int subActionSize = 75;

        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(subActionSize,subActionSize);
        itemBuilder.setLayoutParams(layoutParams);


        buttonAbout = itemBuilder.setContentView(iconAbout).build();
        //buttonFeedback = itemBuilder.setContentView(iconFeedback).build();
        buttonReward = itemBuilder.setContentView(iconReward).build();
        buttonSettings = itemBuilder.setContentView(iconSettings).build();
        buttonProfile = itemBuilder.setContentView(iconProfile).build();

        buttonAbout.setTag(TAG_ABOUT);
        //buttonFeedback.setTag(TAG_FEEDBACK);
        buttonReward.setTag(TAG_REWARD);
        buttonSettings.setTag(TAG_SETTINGS);
        buttonProfile.setTag(TAG_PROFILE);

        buttonAbout.setOnClickListener(this);
        //buttonFeedback.setOnClickListener(this);
        buttonReward.setOnClickListener(this);
        buttonSettings.setOnClickListener(this);
        buttonProfile.setOnClickListener(this);



        FloatingActionMenu actionMenu = new FloatingActionMenu.Builder(this)
                .addSubActionView(buttonAbout)
                 .addSubActionView(buttonSettings)//.addSubActionView(buttonGift)
                .addSubActionView(buttonProfile)
                .addSubActionView(buttonReward)
                .attachTo(actionButton)
                .build();



    }

    private void showFloating(){
        Animator.AnimatorListener listener = new AnimatorListenerAdapter() {

            @Override
            public void onAnimationStart(Animator animation) {

                actionButton.setVisibility(View.INVISIBLE);
                buttonAbout.setVisibility(View.INVISIBLE);
                //buttonFeedback.setVisibility(View.INVISIBLE);
                buttonSettings.setVisibility(View.INVISIBLE);
                buttonReward.setVisibility(View.INVISIBLE);
                buttonProfile.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                ObjectAnimator animator = ObjectAnimator.ofFloat(actionButton,"alpha",0.0f,1.0f);
                ObjectAnimator about = ObjectAnimator.ofFloat(buttonAbout,"alpha",0.0f,1.0f);
                //ObjectAnimator share = ObjectAnimator.ofFloat(buttonFeedback,"alpha",0.0f,1.0f);
                ObjectAnimator settings = ObjectAnimator.ofFloat(buttonSettings,"alpha",0.0f,1.0f);
                ObjectAnimator gift = ObjectAnimator.ofFloat(buttonReward,"alpha",0.0f,1.0f);
                AnimatorSet s = new AnimatorSet();
                //s.playTogether(animator,about,share,manual,gift);
                s.playTogether(animator, about,settings, gift);
                s.setDuration(200).start();

            }


        };
        actionButton.animate().setListener(listener).start();
        actionButton.setVisibility(View.VISIBLE);
        buttonAbout.setVisibility(View.VISIBLE);
        //buttonFeedback.setVisibility(View.VISIBLE);
        buttonSettings.setVisibility(View.VISIBLE);
        buttonReward.setVisibility(View.VISIBLE);


    }

    private Animator buttonAnimator(float start, float end){

        ValueAnimator animator = ValueAnimator.ofFloat(start, end);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                float y = (Float) animation.getAnimatedValue();
                actionButton.setAlpha(y);
                buttonAbout.setAlpha(y);
                buttonSettings.setAlpha(y);
                buttonReward.setAlpha(y);
                buttonProfile.setAlpha(y);

            }
        });

        return animator;

    }

    private void hideFloating(){

        Animator.AnimatorListener listener = new AnimatorListenerAdapter() {

            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                //super.onAnimationEnd(animation);

                ObjectAnimator alpha = ObjectAnimator.ofFloat(actionButton,"alpha",1.0f,0.0f);
                ObjectAnimator about = ObjectAnimator.ofFloat(buttonAbout,"alpha",1.0f,0.0f);
                ObjectAnimator settings = ObjectAnimator.ofFloat(buttonSettings,"alpha",1.0f,0.0f);
                ObjectAnimator gift = ObjectAnimator.ofFloat(buttonReward,"alpha",1.0f,0.0f);
                ObjectAnimator profile = ObjectAnimator.ofFloat(buttonProfile,"alpha",1.0f,0.0f);

                AnimatorSet s = new AnimatorSet();
                //s.playTogether(alpha, about, share, manual, gift);
                s.playTogether(alpha, about, gift,settings,profile);

                s.setDuration(200).start();

            }
        };

        actionButton.animate().setListener(listener).start();

        actionButton.setVisibility(View.GONE);
        buttonAbout.setVisibility(View.GONE);
        buttonSettings.setVisibility(View.GONE);
        buttonReward.setVisibility(View.GONE);
        buttonProfile.setVisibility(View.GONE);
    }

    private class ScrollListener implements ListView.OnScrollListener{

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {

            if(view.getId()==deals.getId()){

                final int firstItemPosition = deals.getFirstVisiblePosition();

                View v = view.getChildAt(0);
                float topValue = v==null ? 0 : v.getTop()-deals.getPaddingTop();
                View bottom = view.getChildAt(view.getChildCount()-1);
                float bottomValue = bottom==null? 0 : bottom.getBottom()-deals.getPaddingBottom();

            /*
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

                */
                /*
                if(bottomValue == 0 && actionButton.getVisibility()==View.VISIBLE){
                    hideFloating();
                    //buttonAnimator(1.0f,0.0f).setDuration(300).start();
                }else if(actionButton.getVisibility()!=View.VISIBLE){
                    showFloating();
                    //buttonAnimator(0.0f,1.0f).setDuration(300).start();
                    //actionButton.setVisibility(View.VISIBLE);
                }
                */
                if(deals.getAdapter().getCount()>=5) {

                    if (deals.getLastVisiblePosition() != deals.getAdapter().getCount() - 1 && !(deals.getChildAt(deals.getChildCount() - 1).getBottom() <= deals.getHeight())) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                actionButton.setAlpha(1.0f);
                                buttonAbout.setAlpha(1.0f);
                                buttonSettings.setAlpha(1.0f);
                                buttonReward.setAlpha(1.0f);
                                buttonProfile.setAlpha(1.0f);
                                actionButton.setVisibility(View.VISIBLE);
                                buttonAbout.setVisibility(View.VISIBLE);
                                buttonSettings.setVisibility(View.VISIBLE);
                                buttonReward.setVisibility(View.VISIBLE);
                                buttonProfile.setVisibility(View.VISIBLE);
                            }
                        }, 300);

                    } else if (deals.getLastVisiblePosition() == deals.getAdapter().getCount() - 1 && (deals.getChildAt(deals.getChildCount() - 1).getBottom() <= deals.getHeight())) {
                        buttonAnimator(1.0f, 0.0f).setDuration(300).start();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                actionButton.setVisibility(View.INVISIBLE);
                                buttonAbout.setVisibility(View.INVISIBLE);
                                buttonSettings.setVisibility(View.INVISIBLE);
                                buttonReward.setVisibility(View.INVISIBLE);
                                buttonProfile.setVisibility(View.INVISIBLE);
                            }
                        }, 310);
                    }
                }else{
                    actionButton.setAlpha(1.0f);
                    buttonAbout.setAlpha(1.0f);
                    buttonSettings.setAlpha(1.0f);
                    buttonReward.setAlpha(1.0f);
                    buttonProfile.setAlpha(1.0f);
                    actionButton.setVisibility(View.VISIBLE);
                    buttonAbout.setVisibility(View.VISIBLE);
                    buttonSettings.setVisibility(View.VISIBLE);
                    buttonReward.setVisibility(View.VISIBLE);
                    buttonProfile.setVisibility(View.VISIBLE);
                }
                currentPosition = firstItemPosition;

            }

        }

        private boolean enabled;
        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            /*
         boolean firstItem;
         boolean loaded = SQLConnectAsyncTask.getListLoaded();
            if(view.getId()==deals.getId() && loaded){

                if(view.getCount()>0 && view!=null){

                    firstItem = deals.getFirstVisiblePosition()==0;
                     enabled = firstItem && (deals.getChildAt(0).getTop()==0);
                }
             }
           //refresh.setEnabled(enabled);
        */
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

            ItemPrev item = (ItemPrev) parent.getItemAtPosition(position);
            Intent intent = new Intent(getApplicationContext(),MoreInfo.class);
            intent.putExtra("accName",ID);
            intent.putExtra("logo", item.getLogo());
            intent.putExtra("location", item.getDistanceTime());
            intent.putExtra("rating", 2.1);
            startActivityForResult(intent, RESULT);
            return true;
        }
    }


    private long offerID;
    private void setOfferID(long offerID){
        this.offerID=offerID;
    }
    private long getOfferID(){
        return offerID;
    }
    private class ItemInfoListener implements AdapterView.OnItemClickListener{
        private boolean inLoad;
        public ItemInfoListener(boolean bool){
            this.inLoad = bool;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


            itemTemp = (ItemPrev)parent.getItemAtPosition(position);
            setClientID(itemTemp.getClientID());
            setOfferID(itemTemp.getOfferID());
            //setItemPrev(itemTemp);
            if(position!=0) {
                GetItemPrev threadGetItem = new GetItemPrev();
                threadGetItem.doInBackground(itemTemp);
                ExtendInfoInBackGround extend = new ExtendInfoInBackGround(false);
                extend.doInBackground(view);


            }else{

                if(inLoad) {
                    GetItemPrev threadGetItem = new GetItemPrev();
                    threadGetItem.doInBackground(itemTemp);
                    ExtendInfoInBackGround extend = new ExtendInfoInBackGround(false);
                    extend.doInBackground(view);
                }else{
                    if(isTutorialOn()) {
                        GetItemPrev threadGetItem = new GetItemPrev();
                        threadGetItem.doInBackground(itemTemp);
                        ExtendInfoInBackGround extend = new ExtendInfoInBackGround(true);
                        extend.doInBackground(view);
                    }else{
                        GetItemPrev threadGetItem = new GetItemPrev();
                        threadGetItem.doInBackground(itemTemp);
                        ExtendInfoInBackGround extend = new ExtendInfoInBackGround(false);
                        extend.doInBackground(view);
                    }
                }
            }





        }
    }

    private long ClientID;
    private void setClientID(long id){
        this.ClientID = id;
    }
    private long getClientID(){
        return  this.ClientID;
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

    RelativeLayout extendTutHeight;
    RelativeLayout extendTutIcon;
    RelativeLayout extendTutText;
    LinearLayout buttonContainer;
    LinearLayout textContainer;
    ImageView tButton1;
    ImageView tButton2;
    ImageView tButton3;
    ImageView tButton4;
    ImageView textButton1;
    ImageView textButton2;
    ImageView textButton3;
    ImageView textButton4;
    ImageView tutorial1,tutorial2;

    private void animateButton(final ImageView button,final ImageView text, boolean show){
        if(show){
            Animator.AnimatorListener containerAnimation = new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    button.setVisibility(View.VISIBLE);
                    button.setAlpha(0.0f);
                    text.setVisibility(View.VISIBLE);
                    text.setAlpha(0.0f);
                }
                @Override
                public void onAnimationEnd(Animator animation) {
                    ObjectAnimator alphaButton = ObjectAnimator.ofFloat(button,"alpha",0.0f,0.5f,1.0f);
                    ObjectAnimator alphaText = ObjectAnimator.ofFloat(text,"alpha",0.0f,0.5f,1.0f);
                    AnimatorSet s = new AnimatorSet();
                    s.setDuration(300);
                    s.playTogether(alphaButton,alphaText);
                    s.start();
                }
            };
            button.animate().setListener(containerAnimation).start();
        }else{
            Animator.AnimatorListener containerAnimation = new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {

                }
                @Override
                public void onAnimationEnd(Animator animation) {
                    ObjectAnimator alphaButton = ObjectAnimator.ofFloat(button,"alpha",1.0f,0.5f,0.0f);
                    ObjectAnimator alphaText = ObjectAnimator.ofFloat(text,"alpha",1.0f,0.5f,0.0f);
                    AnimatorSet s = new AnimatorSet();
                    s.setDuration(300);
                    s.playTogether(alphaButton,alphaText);
                    s.start();
                }
            };
            button.animate().setListener(containerAnimation).start();
            button.setVisibility(View.GONE);
            text.setVisibility(View.GONE);
        }
    }
    private int value2;
    private void tutorial2(){


        if (extendText.getVisibility() != View.GONE && extendIcon.getVisibility() != View.GONE) {
            Animator.AnimatorListener containerAnimation = new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    buttonContainer.setVisibility(View.VISIBLE);
                    buttonContainer.setAlpha(0.0f);
                    textContainer.setVisibility(View.VISIBLE);
                    textContainer.setAlpha(0.0f);
                }
                @Override
                public void onAnimationEnd(Animator animation) {
                    ObjectAnimator alphaButton = ObjectAnimator.ofFloat(buttonContainer,"alpha",0.0f,0.5f,1.0f);
                    ObjectAnimator alphaText = ObjectAnimator.ofFloat(textContainer,"alpha",0.0f,0.5f,1.0f);
                    AnimatorSet s = new AnimatorSet();
                    s.setDuration(300);
                    s.playTogether(alphaButton,alphaText);
                    s.start();
                }
            };
            buttonContainer.animate().setListener(containerAnimation).start();


            int value = this.tutorial.getInt("tutorial2",-1);
            switch (value) {
                case 0:
                    animateButton(tButton1, textButton1, true);
                    break;
            }

        } else {

            int value = this.tutorial.getInt("tutorial2",-1);
            switch (value) {
                case 0:
                    animateButton(tButton1, textButton1, false);
                    break;
            }

            Animator.AnimatorListener containerAnimation = new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        ObjectAnimator alphaButton = ObjectAnimator.ofFloat(buttonContainer, "alpha", 1.0f, 0.5f, 0.0f);
                        ObjectAnimator alphaText = ObjectAnimator.ofFloat(textContainer, "alpha", 1.0f, 0.5f, 0.0f);
                        AnimatorSet s = new AnimatorSet();
                        s.setDuration(300);
                        s.playTogether(alphaButton, alphaText);
                        s.start();
                    }
                };
                buttonContainer.animate().setListener(containerAnimation).start();
                buttonContainer.setVisibility(View.GONE);
                textContainer.setVisibility(View.GONE);
            }
    }

    private ValueAnimator slideAnimator(int start,int end){
        ValueAnimator animator = ValueAnimator.ofInt(start, end);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //Update The Height of the card

                int value = (Integer) animation.getAnimatedValue();
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

        final int widthSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        final int heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        extendHeight.measure(widthSpec,heightSpec);
        initialHeight=extendHeight.getHeight();


            ValueAnimator valueAnimator = slideAnimator(extendHeight.getHeight(), (extendHeight.getMeasuredHeight() - 5));
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

    public void extendInfo(View v, final boolean tutorial) {
        //Extend info: Rate,like and Take Me There
        extendHeight= (RelativeLayout) v.findViewById(R.id.extendHeight);
        extendIcon =(RelativeLayout)v.findViewById(R.id.extendIconLayout);
        extendText =(RelativeLayout)v.findViewById(R.id.extendTextLayout);
        tutorial1 = (ImageView) v.findViewById(R.id.tutorial1);
        tutorial2 = (ImageView) v.findViewById(R.id.tutorial2);
        buttonContainer = (LinearLayout) v.findViewById(R.id.buttonTutorialContainer);
        textContainer = (LinearLayout) v.findViewById(R.id.buttonTextContainer);
        tButton1 = (ImageView) v.findViewById(R.id.buttonTutorial1);
        tButton2 = (ImageView) v.findViewById(R.id.buttonTutorial2);
        tButton3 = (ImageView) v.findViewById(R.id.buttonTutorial3);
        tButton4 = (ImageView) v.findViewById(R.id.buttonTutorial4);
        textButton1 = (ImageView) v.findViewById(R.id.buttonTutorialText1);
        textButton2 = (ImageView) v.findViewById(R.id.buttonTutorialText2);
        textButton3 = (ImageView) v.findViewById(R.id.buttonTutorialText3);
        textButton4 = (ImageView) v.findViewById(R.id.buttonTutorialText4);

        if(tutorial) {
            int value = this.tutorial.getInt("count",-1);

            if(this.tutorial.getInt("tutorial3",-1)==1){
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        tutorial2();
                    }
                }, 400);

            }

            if (extendText.getVisibility() == View.GONE && extendIcon.getVisibility() == View.GONE) {
                Log.d("collapseValue",String.valueOf(value));
                if(value==0){
                    Animator.AnimatorListener animation = new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            tutorial1.setVisibility(View.VISIBLE);
                            tutorial1.setAlpha(1.0f);

                        }
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            // super.onAnimationEnd(animation);
                            ObjectAnimator alpha = ObjectAnimator.ofFloat(tutorial1,"alpha",1.0f,0.0f);
                            alpha.setDuration(200).start();
                        }
                    };
                    tutorial1.animate().setListener(animation).start();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            tutorial1.setAlpha(0.0f);
                            tutorial1.setVisibility(View.GONE);
                        }
                    }, 400);
                    this.tutorial.edit().putInt("count", 1).apply();
                }

                if(this.tutorial.getInt("tutorial3",-1)==0 && value == 1) {
                    //int tutValue = this.tutorial.getInt("tutorial3", -1);
                    //Log.d("tutorial3Value", String.valueOf(tutValue));
                    Animator.AnimatorListener animation = new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            buttonContainer.setVisibility(View.VISIBLE);
                            buttonContainer.setAlpha(0.0f);
                            textContainer.setVisibility(View.VISIBLE);
                            textContainer.setAlpha(0.0f);
                            tutorial2.setVisibility(View.VISIBLE);
                            tutorial2.setAlpha(1.0f);
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            // super.onAnimationEnd(animation);
                            ObjectAnimator alphaButton = ObjectAnimator.ofFloat(buttonContainer,"alpha",0.0f,0.5f,1.0f);
                            ObjectAnimator alphaText = ObjectAnimator.ofFloat(textContainer,"alpha",0.0f,0.5f,1.0f);
                            ObjectAnimator alpha = ObjectAnimator.ofFloat(tutorial2,"alpha",1.0f,0.0f);
                            AnimatorSet s = new AnimatorSet();
                            s.setDuration(300);
                            s.playTogether(alphaButton,alpha,alphaText);
                            s.start();

                        }
                    };

                    tutorial2.animate().setListener(animation).start();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            tutorial2.setVisibility(View.GONE);
                            //tutorial2();
                        }
                    }, 210);
                    int val = this.tutorial.getInt("tutorial2",-1);
                    switch (val) {
                        case 0:
                            animateButton(tButton1, textButton1, true);
                            break;
                    }
                    this.tutorial.edit().putInt("tutorial3", 1).apply();
                }


                extend();
            } else {
                //SHOW SECOND TUTORIAL
                //Animate tutorial2 which replaces the first tutorial
                int tutValue = this.tutorial.getInt("tutorial3", -1);
                int newValue = this.tutorial.getInt("count",-1);
                Log.d("tutorial3Value", String.valueOf(tutValue) + " NEW COUNT VALUE IS: " + String.valueOf(newValue));
                if(newValue==1 && tutValue==0){
                    Animator.AnimatorListener animation = new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            tutorial2.setAlpha(0.0f);
                            tutorial2.setVisibility(View.VISIBLE);

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            // super.onAnimationEnd(animation);

                            ObjectAnimator alpha = ObjectAnimator.ofFloat(tutorial2, "alpha", 0.0f, 1.0f);
                            alpha.setDuration(200).start();
                        }
                    };
                    tutorial2.animate().setListener(animation).start();

                }
                collapse();
            }
        }else{
            if (extendText.getVisibility() == View.GONE && extendIcon.getVisibility() == View.GONE) {
                extend();
            } else {
                collapse();
            }
        }


    }



    private Handler animationC = new Handler();

    private Runnable checkAnimationUpdate = new Runnable() {
        @Override
        public void run() {
             updateValue();
             animationC.postDelayed(this,1000);
        }
    };

    private void stopUpdate(){
        animationC.removeCallbacks(checkAnimationUpdate);
    }

    private void runUpdate(){
        checkAnimationUpdate.run();
    }
    private void updateValue(){
        value2 = this.tutorial.getInt("tutorial2",-1);

    }
    private class ExtendInfoInBackGround extends  AsyncTask<View,String,View>{

        private boolean tutorial;
        public ExtendInfoInBackGround(boolean b){
            this.tutorial = b;
        }

        @Override
        protected View doInBackground(View... params) {

            View v = params[0];
            extendInfo(v,tutorial);

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

        ArrayList<PromoItem> items = new ArrayList<>();

        for(int i=0;i<4;i++){

            PromoItem temp = new PromoItem();
            temp.setImage(R.drawable.gift);
            temp.setPromoSubTitle("Free Cookie!");
            temp.setPromoTitle("Free Item Alert!");
            items.add(temp);
        }

        PromoItem[]list = items.toArray(new PromoItem[items.size()]);

        return list;
    }

    public ItemPrev[] dealList(){
        //ip = new ItemPrev[10];
        ArrayList<ItemPrev> items = new ArrayList<>();

        setListSize(10);
        // for(int i =0 ;i<50;i++) {

        ItemPrev temp = new ItemPrev();
        temp.setLatitude(90.0);
        temp.setLongitude(0.0);
        //TODO:add generic location to database
        //TODO: add photo download from google storage
        temp.setMainText("Is your internet on/location enabled?");
        temp.setRestaurantName("We couldn't connect, sorry");
        //TODO: points
        //ip[0] = temp;
        items.add(temp);

        ip = items.toArray(new ItemPrev[items.size()]);

        return ip;

    }

    public ItemPrev[] dealList(OfferListEntity sql) {

        try{
            List<OfferEntity> list = new ArrayList<OfferEntity>(sql.getOfferList());

            setListSize(list.size());

            ArrayList<ItemPrev> items = new ArrayList<>();

            for (int i = 0; i < list.size(); i++) {
                ItemPrev temp = new ItemPrev();
                //TODO:add generic location to database
                if(i==0){
                    temp.setIsTutorial(true);
                    SharedPreferences tutorial = getSharedPreferences("tutorial",Context.MODE_PRIVATE);
                    temp.setPreferences(tutorial);
                }else{
                    temp.setIsTutorial(false);
                    temp.setPreferences(null);
                }
                temp.setDistanceTime("To be added");
                //TODO: add photo download from google storage


                //download and display image from url
                String url = list.get(i).getClientPhoto();
                temp.setFetchImageURL(url);
                Log.d("debug", list.get(i).getClientName());
                /////////////////////////////////////////////

                //list.get(i).getFoodStyle()
                //temp.setMainText(list.get(i).getName());
                //temp.setRestaurantName(list.get(i).getName());
                //temp.setSubText(list.get(i).getOffer());
                temp.setOfferID(list.get(i).getOfferId());
                temp.setClientID(list.get(i).getClientId());
                temp.setMainText(list.get(i).getOfferText());
                temp.setRestaurantName(list.get(i).getClientName());
                temp.setSubText(list.get(i).getClientName());
                temp.setLatitude(list.get(i).getLatitude());
                temp.setLongitude(list.get(i).getLongitude());
                temp.setDistanceTime("to be added");



                //TODO: points
                //temp.setPoints(list.get(i).getPoints().toString());
                //ip[i] = temp;
                items.add(temp);
            }

            ItemPrev[]i =  items.toArray(new ItemPrev[list.size()]);

            return i;

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
    private int size;
    private int getListSize(){
        return this.size;
    }

    private void setListSize(int size){
        this.size = size;
    }

    public void aboutYapnak() {
       /* AlertDialog.Builder aboutYapnak = new AlertDialog.Builder(this);

        aboutYapnak.setTitle("About Yapnak");
        aboutYapnak.setMessage("Yapnak finds you lunch for a £5er! 💰💰💰");
        aboutYapnak.setPositiveButton("OK", null);
        aboutYapnak.show();
        */

        AboutYapnakDialog dialog = new AboutYapnakDialog(this,this);
        dialog.show();
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
        /*
        AlertDialog.Builder userItems = new AlertDialog.Builder(this);
        userItems.setTitle("Free Items");
        userItems.setMessage("🍪You currently have no free items available");
        userItems.setPositiveButton("OK", null);
        AlertDialog dialog = userItems.create();
        dialog.getWindow().setLayout(400, 400);
        dialog.show();
        */

        /*

        Intent promo = new Intent(this, PromotionActivity.class);
        promo.putExtra("accName",personName);
        startActivity(promo);
        */

        PromotionDialog promo = new PromotionDialog(this,this);
        promo.show();

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

    private Location loc;
    public Location getLocation(){
        return loc;
    }
    public void setLocation(Location loc){
        this.loc = loc;
    }
    private boolean isLocationOn(){
        GPSTrack t = new GPSTrack(MainActivity.this);
        return t.canGetLoc();
    }

    private SwipeRefreshLayout refresh;


    private Handler handler= new Handler();


    public void swipeRefresh(MainActivity activity){
        final MainActivity a =activity;
        refresh = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        refresh.setColorSchemeResources(R.color.darkRed, R.color.floatColour, R.color.deepOrange, R.color.peach, R.color.lightPeach);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        //if(getLocation()!=null) {
                            SQLConnectAsyncTask.useDialog = false;
                        if(getLocation()!=null && getRestaurantName()==null) {
                            new SQLConnectAsyncTask(getApplicationContext(),getLocation(),null, a).execute();
                            if (SQLConnectAsyncTask.getListLoaded()) {
                                refresh.setRefreshing(false);
                            }
                        }else if(getLocation()==null && getRestaurantName()!=null){
                            new SQLConnectAsyncTask(getApplicationContext(),null,getRestaurantName(), a).execute();
                            if (SQLConnectAsyncTask.getListLoaded()) {
                                refresh.setRefreshing(false);
                            }
                        }else if(getLocation()!=null && getRestaurantName()!=null){

                            SQLConnectAsyncTask.useDialog=true;
                            new SQLConnectAsyncTask(getApplicationContext(),getLocation(),getRestaurantName(),a).execute();
                        }else{
                            Toast.makeText(getApplicationContext(),"The location/restaurant you've searched is unavailable",Toast.LENGTH_SHORT).show();
                            refresh.setRefreshing(false);
                        }
                    }
                });
            }
        });
    }
    private String restaurantName;


}