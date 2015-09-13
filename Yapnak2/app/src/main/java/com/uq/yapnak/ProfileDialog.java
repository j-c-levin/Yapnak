package com.uq.yapnak;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
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
        gender = (Button) v.findViewById(R.id.genderGroupButtons);
        dob = (Button)v.findViewById(R.id.dateInput);


        genderDialog = new GenderDialog(context,activity);
        gender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                genderDialog.setMainButton(gender);
                genderDialog.show();
            }
        });

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
                 pnumber = phone.getText().toString();
                 names = name.getText().toString().split(" ");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                Date date=null;
                try {
                     date = sdf.parse(dateString);
                }catch (ParseException ex){
                    ex.printStackTrace();
                }

                String buttonDate = button.getText().toString().replace("/","");
                String actualDate = buttonDate.replaceAll("\\s+","");
                Log.d("names", "Name: " + names[0] + " " + names[1] + " DATE = " + dateString +" Actual Date: "+actualDate );

               // try {
                    if (ID != null) {
                        if (names.length == 1) {
                            new SubmitDetails().execute(names[0]," ", pnumber, email.getText().toString(), password.getText().toString(), dateString);
                        } else {
                            new SubmitDetails().execute(names[0],names[1], pnumber, email.getText().toString(), password.getText().toString(), dateString);
                        }
                    } else {
                        Toast.makeText(getContext(), "Failed to submit profile", Toast.LENGTH_SHORT).show();
                    }

                    d.dismiss();
               // }catch (NullPointerException e) {
                  //  Toast.makeText(getContext(), "Please Fill in the Empty Fields", Toast.LENGTH_SHORT).show();
                //}
            }
        });
    }


    private class SubmitDetails extends AsyncTask<String,Integer,String>{

        @Override
        protected String doInBackground(String... params) {
            try{
                UserEndpointApi api = new UserEndpointApi(AndroidHttp.newCompatibleTransport(),new AndroidJsonFactory(),null);
                String email,pass,phone,dob;
                email = (params[3]!=null)?params[3]:"";
                pass = (params[4]!=null)?params[4]:"";
                dob = (params[5]!=null)?params[5]:"";
                phone = (params[2]!=null)?params[2]:"";

                SetUserDetailsEntity userDetails = api.setUserDetails(ID,null)
                        .setEmail(params[3])
                        .setFirstName(params[0])
                        .setLastName(params[1])
                        .setMobNo(params[2])
                        .setDateOfBirth(params[5])
                        .setPassword(params[4]).execute();

                log = "STATUS: "+userDetails.getStatus() + "MESSAGE : " + userDetails.getMessage();
                return log;
            }catch(IOException e){
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Log.d("profileDialog",log);
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
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
            Calendar cal = Calendar.getInstance();
            cal.set(year,monthOfYear,dayOfMonth);
            dateString = format.format(cal);
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


        @Override
        protected UserDetailsEntity doInBackground(Void... params) {

            try {
                /*SQLEntityApi.Builder builder = new SQLEntityApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                        .setRootUrl("https://yapnak-app.appspot.com/_ah/api/");
                builder.setApplicationName("Yapnak");

                SQLEntityApi sqlEntity = builder.build();
                sqlEntity.getUserDetails(ID);
                */

                UserEndpointApi user = new UserEndpointApi (AndroidHttp.newCompatibleTransport(),new AndroidJsonFactory(),null);
                return user.getUserDetails().setUserId(ID).execute();

            }catch(IOException e){
              e.printStackTrace();
                return null;
            }
        }
        @Override
        protected void onPostExecute(UserDetailsEntity userEntity) {

          try{
              if (Boolean.parseBoolean(userEntity.getStatus())) {
                  phone.setText(userEntity.getMobNo());
                  name.setText(userEntity.getFirstName() + " " + userEntity.getLastName());
                  dob.setText(userEntity.getDateOfBirth().toString());
                  email.setText(userEntity.getEmail());
              }
          }catch (NullPointerException e){
              e.printStackTrace();
          }


        }
    }
    public void setID(String id){
        ID = id;
    }
}
