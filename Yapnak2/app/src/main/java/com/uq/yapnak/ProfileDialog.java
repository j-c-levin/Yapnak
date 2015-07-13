package com.uq.yapnak;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;



import java.util.Calendar;

/**
 * Created by vahizan on 11/07/2015.
 */
public class ProfileDialog extends AlertDialog.Builder {


    private Activity activity;
    private Context context;
    private  Button button;

    public ProfileDialog(Context context,Activity activity) {
        super(context);

        this.activity=activity;
        this.context=context;

        LayoutInflater inflater = activity.getLayoutInflater();

        View v = inflater.inflate(R.layout.profile_layout,null);
        this.setView(v);
        chooseDate(v);




    }

    public void chooseDate(View v){

        button = (Button) v.findViewById(R.id.dateInput);
        final DateDialog date = new DateDialog();



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                date.show(activity.getFragmentManager(), "datePicker");
            }
        });



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

            button.setText(dateString);


            this.dismiss();

        }
    }


}
