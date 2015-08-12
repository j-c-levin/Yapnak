package com.uq.yapnak;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.res.Configuration;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.provider.ContactsContract;
import android.support.v4.app.NotificationCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
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
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
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
import java.util.Calendar;
import java.util.List;


public class MainActivity extends ActionBarActivity implements View.OnClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
       ResultCallback<People.LoadPeopleResult> {
    private static final int NOTIFICATION_ID = 0;


    private GoogleApiClient mGoogleApiClient;
    private Parcelable state;
    private SQLEntity sql;

    RecyclerView recyclerView;
    private static String USER_NAME;
    private static String PASS;
    private final int RESULT= 1;
    private static String TAG_ABOUT = "About";
    private static String TAG_SHARE = "Share";
    private static String TAG_MANUAL = "Manual";
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
    public String ID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new GcmRegistrationAsyncTask(this).execute();


        mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API)
                .addScope(new Scope("profile"))
                .addScope(Plus.SCOPE_PLUS_LOGIN)
                .addScope(Plus.SCOPE_PLUS_PROFILE).build();

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        setContentView(R.layout.activity_main1);

       // locationCheck = getLocation();
       // if(locationCheck!=null) {

             SQLConnectAsyncTask.useDialog = true;
             new SQLConnectAsyncTask(getApplicationContext(), locationCheck, this).execute();
             if (SQLConnectAsyncTask.getListLoaded()) {
                 //dealList.notifyDataSetChanged();
             }

        /*}else {
            load();
            Toast.makeText(getApplicationContext(), "Turn Location On", Toast.LENGTH_SHORT).show();
            floatButton();
        }
        */


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


            this.name = getIntent();
            personName = (name.getStringExtra("accName").equalsIgnoreCase("")) ? name.getStringExtra("initials") : name.getStringExtra("accName");

            ID = (name.getStringExtra("userID")==null)? name.getStringExtra("initials") : name.getStringExtra("userID");

            String[] names = personName.split("@");

            //item.setTitle(names[0]);
            item.setTitle(ID);
            item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {

                    return true;
                }
            });

    }


    private Location locationCheck;
    @Override
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

        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }

        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);


            setUserName(menu);
        return true;
    }

    public class Feedback extends ActionBarActivity {

        /*
        //@Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            final View view = inflater.inflate(R.layout.feedback_activity, null);

            /
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

        */
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

            }else{
                Intent intent = new Intent(MainActivity.this,Login.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
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

    private AlertDialog neg,pos;

    @Override
    public void onClick(View v) {
        if (v.getTag().equals(TAG_ABOUT)) {

            aboutYapnak();
            Toast.makeText(this, "About", Toast.LENGTH_SHORT).show();

        } else if (v.getTag().equals(TAG_FEEDBACK)) {

            final FeedbackDialog feedback = new FeedbackDialog(this,this);
            feedback.setID(ID);



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



            feedback.show();


        } else if (v.getTag().equals(TAG_MANUAL)) {
            howToUseYapnak();
            Toast.makeText(this, "How To Use The App", Toast.LENGTH_LONG).show();
        } else if (v.getTag().equals(TAG_GIFT)) {
            userItems();
        }else if(v.getTag().equals(TAG_PROFILE)){
            //SHOW PROFILE


            profileDialog(v);

        }

    }



    private Button date;
    private RadioGroup gender;
    private AlertDialog posProf;
    private void profileDialog(View v){

        ///AlertDialog.Builder userItems = new ProfileDialog(this,this);
        ProfileDialog userItems = new ProfileDialog(this,this);
        userItems.setID(ID);

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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);




    }

    //private ListView list;
    private final int PICK_CONTACT = 1;

    AlertDialog posRec;
    public void recommendMealButton(View v) {

        RecommendDialog recommend = new RecommendDialog(this,this);

        recommend.getContactListButton().setOnClickListener(new View.OnClickListener() {
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
                    }
                });

            }
        });

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

        recommend.show();


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
        maps.putExtra("accName",personName);
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
        //RatingDialog rate = new RatingDialog();
        //rate.show(getFragmentManager(),"rating");
        //AlertDialog.Builder ratings = new RatingBuilder(this,this);

        RatingBuilder ratings = new RatingBuilder(this,this);

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

        ratings.show();
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



    private AdapterPrev dealList;
    public void load(SQLList sql) {

        //recyclerView.setAdapter(new Adapter(sql));
        setContentView(R.layout.activity_main1);
        dealList = new AdapterPrev(this, R.id.item2, dealList(sql));
        ListAdapter promo = new PromotionAdapter(this,R.id.promo_item,gift());
        promoList = (ListView) findViewById(R.id.listViewPromotions);
        promoList.setAdapter(promo);

        scaleListY = promoList.getY();

        deals = (ListView) findViewById(R.id.listviewMain);
        deals.setAdapter(dealList);
        deals.setOnItemClickListener(new ItemInfoListener());
        deals.setOnItemLongClickListener(new OnLongTouchListener());
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
    private SubActionButton buttonFeedback;
    //private SubActionButton buttonManual;
    private SubActionButton buttonGift;
    private SubActionButton buttonProfile;

    public void floatButton() {
        ImageView image = new ImageView(this);
        image.setImageResource(R.drawable.ic_new_float);

        actionButton = new FloatingActionButton.Builder(this)
                .setContentView(image)
                .setBackgroundDrawable(R.drawable.selector_file_red)
                .build();


        ImageView iconAbout = new ImageView(this);
        iconAbout.setImageResource(R.drawable.abouticon);

        ImageView iconFeedback = new ImageView(this);
        iconFeedback.setImageResource(R.drawable.shareicon);

        //ImageView iconManual = new ImageView(this);
        //iconManual.setImageResource(R.drawable.manualicon);

        ImageView iconGift = new ImageView(this);
        iconGift.setImageResource(R.drawable.gift);

        ImageView iconProfile = new ImageView(this);
        iconProfile.setImageResource(R.drawable.yapnak_colorsmall);

        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);
        itemBuilder.setBackgroundDrawable(getResources().getDrawable(R.drawable.selector_file_grey));

        buttonAbout = itemBuilder.setContentView(iconAbout).build();
        buttonFeedback = itemBuilder.setContentView(iconFeedback).build();
        //buttonManual = itemBuilder.setContentView(iconManual).build();
        buttonGift = itemBuilder.setContentView(iconGift).build();
        buttonProfile = itemBuilder.setContentView(iconProfile).build();

        buttonAbout.setTag(TAG_ABOUT);
        buttonFeedback.setTag(TAG_FEEDBACK);
        //buttonManual.setTag(TAG_MANUAL);
        buttonGift.setTag(TAG_GIFT);
        buttonProfile.setTag(TAG_PROFILE);

        buttonAbout.setOnClickListener(this);
        buttonFeedback.setOnClickListener(this);
        //buttonManual.setOnClickListener(this);
        buttonGift.setOnClickListener(this);
        buttonProfile.setOnClickListener(this);

        FloatingActionMenu actionMenu = new FloatingActionMenu.Builder(this)
                .addSubActionView(buttonAbout)
                .addSubActionView(buttonFeedback)
                .addSubActionView(buttonGift)
                .addSubActionView(buttonProfile)
                .attachTo(actionButton)
                .build();


    }

    private void showFloating(){



        Animator.AnimatorListener listener = new AnimatorListenerAdapter() {

            @Override
            public void onAnimationStart(Animator animation) {

                actionButton.setVisibility(View.INVISIBLE);
                buttonAbout.setVisibility(View.INVISIBLE);
                buttonFeedback.setVisibility(View.INVISIBLE);
                //buttonManual.setVisibility(View.VISIBLE);
                buttonGift.setVisibility(View.INVISIBLE);
                buttonProfile.setVisibility(View.INVISIBLE);
          }

            @Override
            public void onAnimationEnd(Animator animation) {
                ObjectAnimator animator = ObjectAnimator.ofFloat(actionButton,"alpha",0.0f,1.0f);
                ObjectAnimator about = ObjectAnimator.ofFloat(buttonAbout,"alpha",0.0f,1.0f);
                ObjectAnimator share = ObjectAnimator.ofFloat(buttonFeedback,"alpha",0.0f,1.0f);
                //ObjectAnimator manual = ObjectAnimator.ofFloat(buttonManual,"alpha",0.0f,1.0f);
                ObjectAnimator gift = ObjectAnimator.ofFloat(buttonGift,"alpha",0.0f,1.0f);
                AnimatorSet s = new AnimatorSet();
                //s.playTogether(animator,about,share,manual,gift);
                s.playTogether(animator, about, share, gift);
                s.setDuration(200).start();

            }


        };
        actionButton.animate().setListener(listener).start();
        actionButton.setVisibility(View.VISIBLE);
        buttonAbout.setVisibility(View.VISIBLE);
        buttonFeedback.setVisibility(View.VISIBLE);
        //buttonManual.setVisibility(View.VISIBLE);
        buttonGift.setVisibility(View.VISIBLE);


    }

    private Animator buttonAnimator(float start, float end){

        ValueAnimator animator = ValueAnimator.ofFloat(start, end);


        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                float y = (Float) animation.getAnimatedValue();
                actionButton.setAlpha(y);
                buttonAbout.setAlpha(y);
                buttonFeedback.setAlpha(y);
                buttonGift.setAlpha(y);
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
                ObjectAnimator share = ObjectAnimator.ofFloat(buttonFeedback,"alpha",1.0f,0.0f);
                ObjectAnimator gift = ObjectAnimator.ofFloat(buttonGift,"alpha",1.0f,0.0f);
                ObjectAnimator profile = ObjectAnimator.ofFloat(buttonProfile,"alpha",1.0f,0.0f);

                AnimatorSet s = new AnimatorSet();
                //s.playTogether(alpha, about, share, manual, gift);
                s.playTogether(alpha, about, share, gift,profile);

                s.setDuration(200).start();

            }
        };

        actionButton.animate().setListener(listener).start();

        actionButton.setVisibility(View.GONE);
        buttonAbout.setVisibility(View.GONE);
        buttonFeedback.setVisibility(View.GONE);
        buttonGift.setVisibility(View.GONE);
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
                                buttonFeedback.setAlpha(1.0f);
                                buttonGift.setAlpha(1.0f);
                                buttonProfile.setAlpha(1.0f);
                                actionButton.setVisibility(View.VISIBLE);
                                buttonAbout.setVisibility(View.VISIBLE);
                                buttonFeedback.setVisibility(View.VISIBLE);
                                buttonGift.setVisibility(View.VISIBLE);
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
                                buttonFeedback.setVisibility(View.INVISIBLE);
                                buttonGift.setVisibility(View.INVISIBLE);
                                buttonProfile.setVisibility(View.INVISIBLE);
                            }
                        }, 310);
                    }
                }else{
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
            intent.putExtra("accName",personName);
            intent.putExtra("logo", item.getLogo());
            intent.putExtra("location", item.getDistanceTime());
            intent.putExtra("rating", 2.1);
            startActivityForResult(intent, RESULT);
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

    public ItemPrev[] dealList(SQLList sql) {

        try{
            List<SQLEntity> list = new ArrayList<SQLEntity>(sql.getList());

            setListSize(list.size());

            ArrayList<ItemPrev> items = new ArrayList<>();

            for (int i = 0; i < list.size(); i++) {
                ItemPrev temp = new ItemPrev();
                //TODO:add generic location to database

                temp.setDistanceTime("To be added");
                //TODO: add photo download from google storage


                //download and display image from url
                String url = list.get(i).getPhoto();
                temp.setFetchImageURL(url);
                Log.d("debug", list.get(i).getName());
                /////////////////////////////////////////////

                temp.setMainText(list.get(i).getFoodStyle());
                temp.setRestaurantName(list.get(i).getName());
                temp.setSubText(list.get(i).getOffer());
                temp.setLatitude(list.get(i).getY());
                temp.setLongitude(list.get(i).getX());
                temp.setDistanceTime("to be added");
                temp.setMainText(list.get(i).getFoodStyle());
                temp.setRestaurantName(list.get(i).getName());
                temp.setSubText(list.get(i).getOffer());
                temp.setLatitude(list.get(i).getY());
                temp.setLongitude(list.get(i).getX());

                //TODO: points
                temp.setPoints(list.get(i).getPoints().toString());
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

    public Location getLocation(){
        GPSTrack track = new GPSTrack(MainActivity.this);
        return track.getLocation();
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
                            new SQLConnectAsyncTask(getApplicationContext(),getLocation(), a).execute();
                            if (SQLConnectAsyncTask.getListLoaded()) {
                                refresh.setRefreshing(false);
                            }
                        //}

                        /*else{
                            load();
                            floatButton();
                            refresh.setRefreshing(false);
                        }
                        */
                    }
                });

            }
        });
    }



}