package com.uq.yapnak;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.frontend.yapnak.subview.MyDatePickerDialog;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.util.DateTime;
import com.yapnak.gcmbackend.sQLEntityApi.SQLEntityApi;
import com.yapnak.gcmbackend.sQLEntityApi.model.UserEntity;
import com.yapnak.gcmbackend.userEndpointApi.UserEndpointApi;
import com.yapnak.gcmbackend.userEndpointApi.model.SetUserDetailsEntity;
import com.yapnak.gcmbackend.userEndpointApi.model.UserDetailsEntity;
import com.yapnak.gcmbackend.userEndpointApi.model.UserImageEntity;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by vahizan on 11/07/2015.
 */
public class ProfileDialog extends AlertDialog {


    private Activity activity;
    private Context context;
    private  Button button,submit,cancel;
    private Color color;
    private EditText name,phone,email,password;
    private AlertDialog d;
    private String ID;
    private String log;
    private Button gender,dob;
    private GenderDialog genderDialog;



    public ProfileDialog(Context context,Activity activity,String ID) {
        super(context);

        this.activity=activity;
        this.context=context;
        d = this;
        this.ID = ID;


        LayoutInflater inflater = activity.getLayoutInflater();

        View v = inflater.inflate(R.layout.profile_layout,null);
        this.setView(v);

        submit = (Button) v.findViewById(R.id.submitProfile);
        cancel = (Button) v.findViewById(R.id.cancelProfile);
        phone = (EditText) v.findViewById(R.id.phoneNumberEditText);
        name = (EditText) v.findViewById(R.id.nameEdit);
        email = (EditText)v.findViewById(R.id.emailEdit);
        password = (EditText)v.findViewById(R.id.passwordEdit);
        //gender = (Button) v.findViewById(R.id.genderGroupButtons);
        dob = (Button)v.findViewById(R.id.dateInput);


        //genderDialog = new GenderDialog(context,activity);
       /* gender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                genderDialog.setMainButton(gender);
                genderDialog.show();
            }
        });*/

        TextView title = new TextView(this.getContext());
        title.setText("Profile");
        title.setGravity(Gravity.LEFT);
        title.setPadding(20, 40, 0, 40);
        title.setTextSize(25);
        //title.setBackgroundColor(Color.parseColor("#FFAB91"));
        title.setTextColor(Color.parseColor("#BF360C"));
        setCustomTitle(title);

        submitProfile();
        cancelProfile();

        chooseDate(v);

    }


      String [] names;
      String pnumber;
    private void submitProfile(){
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // pnumber = phone.getText().toString();
                // names = name.getText().toString().split(" ");
                 SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");


                String buttonDate = button.getText().toString().replace("/", "");
                String actualDate = buttonDate.replaceAll("\\s+", "");

               if(ID!=null) {


                   /*if(names.length==1) {
                           dateString = sdf.format(dateTime);
                           Log.d("names", "Name: " + names[0] + " " + names[1] + " DATE = " + dateString + " Actual Date: " + actualDate);

                           if(connection()) {
                               new SubmitDetails(5).execute(names[0], " ", pnumber, email.getText().toString(), password.getText().toString(), dateString);
                           }else{
                               Toast.makeText(getContext(), "Please Enable Your Internet Connection", Toast.LENGTH_SHORT).show();

                           }

                       }else if(names.length>1){
                           if(connection()) {
                               new SubmitDetails(5).execute(names[0], names[1], pnumber, email.getText().toString(), password.getText().toString(), dateString);
                           }else{
                               Toast.makeText(getContext(), "Please Enable Your Internet Connection", Toast.LENGTH_SHORT).show();
                           }
                       }else{
                           name.setHint("Please Fill In Name");
                           name.setHintTextColor(Color.parseColor("#D32F2F"));
                       }*/

                   if (dob.getText().toString().length()!=0
                           && name.getText().toString().length()!=0
                           && phone.getText().toString().length()==11
                           && email.getText().toString().length()!=0
                           && password.getText().toString().length()!=0){

                       pnumber = phone.getText().toString();
                       names = name.getText().toString().split(" ");

                           if(names.length==1) {
                               if(dateTime!=null) {
                                   dateString = sdf.format(dateTime);
                                   Log.d("names", "Name: " + names[0] + " " + names[1] + " DATE = " + dateString + " Actual Date: " + actualDate);

                                   if (connection()) {
                                       new SubmitDetails(5).execute(names[0], " ", phone.getText().toString(), email.getText().toString(), password.getText().toString(), dateString);
                                   } else {
                                       Toast.makeText(getContext(), "Please Enable Your Internet Connection", Toast.LENGTH_SHORT).show();
                                   }
                               }else{
                                   if (connection()) {
                                       new SubmitDetails(4).execute(names[0], " ", phone.getText().toString(), email.getText().toString(), password.getText().toString());
                                   } else {
                                       Toast.makeText(getContext(), "Please Enable Your Internet Connection", Toast.LENGTH_SHORT).show();
                                   }
                               }

                           }else if(names.length>1){

                               if(dateTime!=null) {
                                   dateString = sdf.format(dateTime);
                                   if (connection()) {
                                       new SubmitDetails(5).execute(names[0], names[1], phone.getText().toString(), email.getText().toString(), password.getText().toString(), dateString);
                                   } else {
                                       Toast.makeText(getContext(), "Please Enable Your Internet Connection", Toast.LENGTH_SHORT).show();
                                   }
                               }else{
                                   if (connection()) {
                                       new SubmitDetails(4).execute(names[0], names[1], phone.getText().toString(), email.getText().toString(), password.getText().toString(), dateString);
                                   } else {
                                       Toast.makeText(getContext(), "Please Enable Your Internet Connection", Toast.LENGTH_SHORT).show();
                                   }
                               }

                           }

                   } else {
                       if(phone.getText().length()==0) {
                           phone.setHint("Enter Phone Number");
                           phone.setHintTextColor(Color.parseColor("#D32F2F"));
                       }if((phone.getText().toString().length()>0 && phone.getText().toString().length()<11) || phone.getText().toString().length()>11){
                           phone.setHint("Enter Phone Number");
                           phone.setHintTextColor(Color.parseColor("#D32F2F"));
                           Toast.makeText(getContext(),"Phone Number Must be 11 Digits",Toast.LENGTH_SHORT).show();

                       }if(email.getText().toString().length()==0){
                           email.setHint("Enter E-mail ");
                           email.setHintTextColor(Color.parseColor("#D32F2F"));
                       }if(password.getText().toString().length()==0){
                           password.setHint("Enter Password");
                           password.setHintTextColor(Color.parseColor("#D32F2F"));
                       //}if(gender.getText()==null){
                         //  gender.setHint("Enter Password");
                           //gender.setHintTextColor(Color.parseColor("#D32F2F"));
                       }if(dob.getText().toString().length()==0){
                           dob.setHint("Date of Birth");
                           dob.setHintTextColor(Color.parseColor("#D32F2F"));
                       }if(name.getText().toString().length()==0){
                           name.setHint("Please Enter Name");
                           name.setHintTextColor(Color.parseColor("#D32F2F"));
                       }
                   }

               }else{
                   Toast.makeText(getContext(), "Failed to submit profile", Toast.LENGTH_SHORT).show();
                   d.dismiss();
               }


               // }catch (NullPointerException e) {
                  //  Toast.makeText(getContext(), "Please Fill in the Empty Fields", Toast.LENGTH_SHORT).show();
                //}
            }
        });
    }

    private void textChecker(final EditText text){
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                if (text.getText() == null) {
                    text.setHintTextColor(Color.parseColor("#D32F2F"));
                }
            }
        });
    }

    private class SubmitDetails extends AsyncTask<String,Integer,String>{

        private boolean replyStatus;
        private String responseMsg;
        private ProgressDialog progress;
        private int length;
        private boolean noInternet;
        public SubmitDetails(int length){
            this.length=length;
            progress = new ProgressDialog(context);
        }
        @Override
        protected String doInBackground(String... params) {
            if(connection()) {
                try {
                    UserEndpointApi api = new UserEndpointApi(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null);

                    if (length == 5) {
                        SetUserDetailsEntity userDetails = api.setUserDetails(ID, null)
                                .setEmail(params[3])
                                .setFirstName(params[0])
                                .setLastName(params[1])
                                .setMobNo(params[2])
                                .setDateOfBirth(params[5])
                                .setPassword(params[4]).execute();
                        responseMsg = userDetails.getMessage();
                        log = "STATUS: " + userDetails.getStatus() + "MESSAGE : " + userDetails.getMessage();
                        replyStatus = Boolean.parseBoolean(userDetails.getStatus());
                        return log;
                    } else {
                        SetUserDetailsEntity userDetails = api.setUserDetails(ID, null)
                                .setEmail(params[3])
                                .setFirstName(params[0])
                                .setLastName(params[1])
                                .setMobNo(params[2])
                                .setPassword(params[4]).execute();

                        responseMsg = userDetails.getMessage();
                        replyStatus = Boolean.parseBoolean(userDetails.getStatus());
                        log = "STATUS: " + userDetails.getStatus() + "MESSAGE : " + userDetails.getMessage();
                        return log;

                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }else{
                noInternet =true;
                return null;
            }
        }

        @Override
        protected void onPreExecute() {
            //super.onPreExecute();
            progress.setMessage("Processing Details");
            progress.setCancelable(true);
            progress.setCanceledOnTouchOutside(true);
            progress.show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Log.d("profileDialog",log);

            if(!noInternet) {
                if (replyStatus) {
                    if (progress.isShowing()) {
                        progress.cancel();
                    }

                    Toast.makeText(context, "Profile Updated!", Toast.LENGTH_SHORT).show();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            d.dismiss();
                        }
                    }, 1000);

                } else {
                    if (progress.isShowing()) {
                        progress.cancel();
                    }

                    Toast.makeText(context, "Profile Was Not Updated Due to an Error", Toast.LENGTH_SHORT).show();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            d.dismiss();
                        }
                    }, 1000);
                }
            }else{
                Toast.makeText(context, "No Internet", Toast.LENGTH_SHORT).show();
                d.dismiss();
            }
        }
    }


    private void cancelProfile(){
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                d.cancel();

            }
        });
    }

    private MyDatePickerDialog datePicker;
    public void chooseDate(View v){

        button = (Button) v.findViewById(R.id.dateInput);
        final DateDialog date = new DateDialog();
        //datePicker = new MyDatePickerDialog(button,getContext(),activity);



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                date.show(activity.getFragmentManager(), "datePicker");
                //datePicker.show();
            }
        });




    }

    private String dateString;
    private Date dateTime;
   private class DateDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        private int day, month, year;
        private Bundle bundle;
        private final int DATEFRAGMENT = 1;

       @Override
       public void onCreate(Bundle savedInstanceState) {
           super.onCreate(savedInstanceState);
       }

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
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date d=null;
            try {
                d = format.parse(year+"-"+month+"-"+dayOfMonth);
            }catch (ParseException e){

            }


            //dateString = format.format(cal);
            dateTime = d;

            String dateS = dayOfMonth+" / "+month+" / "+year;
            button.setText(dateS);
            this.dismiss();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Resources res = getContext().getResources();
        final int id = res.getIdentifier("titleDivider", "id", "android");
        final View divider = findViewById(id);

        if(divider!=null){
            divider.setBackgroundColor(Color.parseColor("#BF360C"));
        }
            new FillUserInfo().execute();

    }

    private class FillUserInfo extends AsyncTask<Void,Integer,UserDetailsEntity>{

        private String message;

        @Override
        protected UserDetailsEntity doInBackground(Void... params) {

            try {
                /*SQLEntityApi.Builder builder = new SQLEntityApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                        .setRootUrl("https://yapnak-app.appspot.com/_ah/api/");
                builder.setApplicationName("Yapnak");

                SQLEntityApi sqlEntity = builder.build();
                sqlEntity.getUserDetails(ID);
                */

                Log.d("userID",ID);
                UserEndpointApi.Builder api = new UserEndpointApi.Builder(AndroidHttp.newCompatibleTransport(),new AndroidJsonFactory(),null).setApplicationName("Yapnak").setRootUrl("https://yapnak-app.appspot.com/_ah/api/");
                UserEndpointApi user = api.build();
                UserDetailsEntity details = user.getUserDetails().setUserId(ID).execute();
                message = details.getMessage() + " STATUS: "+ details.getStatus();
                return details;

            }catch(IOException e){
              e.printStackTrace();
                return null;
            }
        }
        @Override
        protected void onPostExecute(UserDetailsEntity userEntity) {

            Log.d("FillUser",message);
         // try{
            if(userEntity!=null) {
                if (Boolean.parseBoolean(userEntity.getStatus())) {
                    phone.setText(userEntity.getMobNo());
                    if(!userEntity.getFirstName().equalsIgnoreCase("null") && !userEntity.getLastName().equalsIgnoreCase("null")) {
                        name.setText(userEntity.getFirstName() + " " + userEntity.getLastName());
                    }if(!userEntity.getFirstName().equalsIgnoreCase("null")&& userEntity.getLastName().equalsIgnoreCase("null")){
                        name.setText(userEntity.getFirstName() );
                    }else{
                        name.setText(userEntity.getLastName());
                    }
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
                    Date d = new Date();
                    try {
                        if (userEntity.getDateOfBirth() != null) {
                            d.setTime(userEntity.getDateOfBirth().getValue());
                        }
                    }catch (NullPointerException e){
                        d.setTime(Calendar.getInstance().getTimeInMillis());
                    }

                    dob.setText(sdf.format(d));
                    email.setText(userEntity.getEmail());
                }
            }
          //}catch (NullPointerException e){
            //  e.printStackTrace();
         // }


        }
    }
    public void setID(String id){
        ID = id;
    }

    private boolean connection(){
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
        boolean lte = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();

        return (wifi||lte);
    }
}
