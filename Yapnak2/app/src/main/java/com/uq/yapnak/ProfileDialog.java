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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;


import com.frontend.yapnak.subview.MyDatePickerDialog;
import com.frontend.yapnak.subview.RedEditText;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.yapnak.gcmbackend.sQLEntityApi.SQLEntityApi;
import com.yapnak.gcmbackend.sQLEntityApi.model.SQLEntity;
import com.yapnak.gcmbackend.sQLEntityApi.model.UserEntity;

import java.io.IOException;
import java.util.Calendar;

/**
 * Created by vahizan on 11/07/2015.
 */
public class ProfileDialog extends AlertDialog {


    private Activity activity;
    private Context context;
    private  Button button,submit,cancel;
    private Color color;
    private EditText name,phone,email;
    private AlertDialog d;
    private String ID;


    public ProfileDialog(Context context,Activity activity) {
        super(context);

        this.activity=activity;
        this.context=context;
        d = this;

        LayoutInflater inflater = activity.getLayoutInflater();

        View v = inflater.inflate(R.layout.profile_layout,null);
        this.setView(v);

        submit = (Button) v.findViewById(R.id.submitProfile);
        cancel = (Button) v.findViewById(R.id.cancelProfile);
        phone = (EditText) v.findViewById(R.id.phoneNumberEditText);
        name = (EditText) v.findViewById(R.id.nameEdit);
       // email = (EditText)v.findViewById(R.id.emailEdit);




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


    private void submitProfile(){
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String phoneNum = phone.getText().toString();
                final String[] names = name.getText().toString().split(" ");
                try{
                    SQLEntityApi.Builder apiB = new SQLEntityApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null);
                    apiB.setRootUrl("https://yapnak-app.appspot.com/_ah/api/");
                    apiB.setApplicationName("Yapnak");
                    SQLEntityApi api = apiB.build();
                    api.setUserDetails(names[0],names[1],phoneNum,ID);

                }catch(IOException e){
                    e.printStackTrace();
                }
                d.dismiss();
            }
        });
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

       // button = (Button) v.findViewById(R.id.dateInput);
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
            String dateString = dayOfMonth + " / " + month + " / " + year;
            button.setText(dateString);
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

        if(ID!=null){
            new FillUserInfo().execute();
        }
    }

    private class FillUserInfo extends AsyncTask<Void,Integer,UserEntity>{


        @Override
        protected UserEntity doInBackground(Void... params) {

            try {
                SQLEntityApi.Builder builder = new SQLEntityApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                        .setRootUrl("https://yapnak-app.appspot.com/_ah/api/");
                builder.setApplicationName("Yapnak");

                SQLEntityApi sqlEntity = builder.build();
                sqlEntity.getUserDetails(ID);
                return sqlEntity.getUserDetails(ID).execute();

            }catch(IOException e){
              e.printStackTrace();
                return null;
            }
        }
        @Override
        protected void onPostExecute(UserEntity userEntity) {

            phone.setText(userEntity.getMobNo());
            name.setText(userEntity.getFirstName()+" "+userEntity.getLastName());

        }
    }
    public void setID(String id){
        ID = id;
    }
}
