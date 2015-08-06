package com.frontend.yapnak.subview;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.uq.yapnak.DateItem;
import com.uq.yapnak.DatePickerAdapter;
import com.uq.yapnak.R;

import java.util.Calendar;

/**
 * Created by vahizan on 04/08/2015.
 */
public class MyDatePickerDialog extends AlertDialog {

    private Activity a;
    private Context context;
    private View v;
    private Button ok,cancel;
    private ListView day,month,year;
    private int yearStart;
    private YearList popYear;
    private ListAdapter dayAdapter,monthAdapter,yearAdapter;

    public String dayB,monthB,yearB;
    public MyDatePickerDialog(Context context, Activity activity) {
        super(context);

        this.a = activity;
        this.context = context;

        v = activity.getLayoutInflater().inflate(R.layout.custom_datepicker,null);
        this.setView(v);
        ok = (Button) v.findViewById(R.id.okDate);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dayB = dayItem.getValue();
                monthB = monthItem.getValue();
                yearB = yearItem.getValue();
                Toast.makeText(getContext(),"DOB : " + dayB + " " + monthB + " " + yearB,Toast.LENGTH_SHORT).show();

                dismiss();
            }
        });
        cancel = (Button) v.findViewById(R.id.cancelDate);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });

        populateList();

    }

    public String getDay(){
        return dayB;
    }

    public String getMonth(){
        return monthB;
    }

    public String getYear(){
        return yearB;
    }


    private void populateList(){
        popYear = new YearList();

        day = (ListView) v.findViewById(R.id.day);
        month = (ListView) v.findViewById(R.id.month);
        year = (ListView) v.findViewById(R.id.year);

        monthAdapter = new DatePickerAdapter(this.context,R.id.month,months());

        DateItem[] years = popYear.doInBackground(1920);
        yearAdapter = new DatePickerAdapter(this.context,R.id.year,years);
        dayAdapter = new DatePickerAdapter(this.context,R.id.day,defaultList());


        month.setAdapter(monthAdapter);
        month.setOnScrollListener(new ScrollListener());
        //month.setOnTouchListener(new Touch());

        year.setAdapter(yearAdapter);
        year.setOnScrollListener(new ScrollListener());
        //year.setOnTouchListener(new Touch());

        day.setAdapter(dayAdapter);
        day.setOnScrollListener(new ScrollListener());
      //  day.setOnTouchListener(new Touch());




    }

    private DateItem[] defaultList(){
        DateItem [] items = new DateItem[30];
        int j=1;
        for(int i=0;i<30;i++){
            DateItem temp = new DateItem();
            temp.setValue(j+"");
            items[i]=temp;
            j++;
        }
        return items;
    }
    private DateItem[] defaultYearList(int from){

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);

        int num = ((year)-from);
        DateItem[] yearItems = new DateItem[num+1];

        Toast.makeText(this.context,"Years " + num,Toast.LENGTH_LONG).show();

        int j =0;
        for(int i = from;i<=year;i++){

            DateItem temp = new DateItem();
            temp.setValue(i+"");
            yearItems[j] = temp;
            j++;
        }

        return yearItems;

    }


    private DateItem yearItem,monthItem,dayItem;
    private int secondPosition;

    private class ScrollListener implements ListView.OnScrollListener{
        //private DateItem yearItem,monthItem,dayItem;

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {

        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            boolean leapYear;
            DayList popDay = new DayList();
            secondPosition = firstVisibleItem+1;

            //Toast.makeText(getContext(),"VISIBLE ITEM COUNT = " + visibleItemCount + " FIRST VISIBLE ITEM = " + firstVisibleItem,Toast.LENGTH_LONG).show();
            //secondPosition = view.getFirstVisiblePosition()+1;

            if(view.getId()==day.getId()){
                //dayItem =  (DateItem) day.getItemAtPosition(day.getFirstVisiblePosition()+1);
                dayItem =  (DateItem) day.getItemAtPosition(firstVisibleItem+1);
                //Toast.makeText(getContext(),"Second Position Day : " + secondPosition,Toast.LENGTH_SHORT).show();

            }

            if(view.getId()==year.getId()){
                //yearItem = (DateItem) year.getItemAtPosition(day.getFirstVisiblePosition()+1);
                //Toast.makeText(getContext(),"Second Position Year : " + secondPosition,Toast.LENGTH_SHORT).show();
                yearItem =  (DateItem) year.getItemAtPosition(firstVisibleItem+1);
                int year = Integer.parseInt(yearItem.getValue());
                leapYear = (year % 4==0) ;
                popDay.setLeap(leapYear);
            }

            if(view.getId()==month.getId()){
                //monthItem = (DateItem) month.getItemAtPosition(month.getFirstVisiblePosition() + 1);
                monthItem =  (DateItem) month.getItemAtPosition(firstVisibleItem+1);
                //Toast.makeText(getContext(),"Second Position Month: " + secondPosition + " Month Name " + monthItem.getValue(),Toast.LENGTH_SHORT).show();
                int month = monthItem.getMonthValue();
                DayAdapter adapter = new DayAdapter();
                dayAdapter = adapter.doInBackground(popDay.doInBackground(month));
            }


        }
    }


    private DateItem[] months(){
        DateItem [] monthNum = new DateItem[12];

        String [] months = new String[]{"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sept","Oct","Nov","Dec"};
        int [] monthValues =new int[]{1,2,3,4,5,6,7,8,9,10,11,12};

        int i = 0;
        while(i<monthNum.length){
            DateItem temp = new DateItem();
            temp.setValue(months[i]);
            temp.setMonthValue(monthValues[i]);
            monthNum[i] = temp;

            i++;
        }

        //Toast.makeText(this.context,"Get Year Value "+monthNum[3].getValue(), Toast.LENGTH_LONG).show();

        return monthNum;
    }


    private class YearList extends AsyncTask<Integer,String,DateItem[]>{

        @Override
        protected DateItem[] doInBackground(Integer... params) {

            return backgroundSupport(params[0]);
        }

        protected DateItem[] backgroundSupport(int from){

           Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);

            DateItem[] yearItems = new DateItem[((year)-from)+1];

            int j =0;
            for(int i = from;i<=year;i++){

                DateItem temp = new DateItem();
                temp.setValue(i+"");
                yearItems[j] = temp;
                j++;
            }

            return yearItems;

        }



    }

    private class DayAdapter extends  AsyncTask<DateItem[],String,ListAdapter>{

        @Override
        protected ListAdapter doInBackground(DateItem[]... params) {
            ListAdapter adapter = new DatePickerAdapter(getContext(),R.id.day,params[0]);
            return adapter;
        }
    }
    private class DayList extends AsyncTask<Integer,String,DateItem[]>{

        private boolean leap;

        @Override
        protected DateItem[] doInBackground(Integer... params) {

            return backgroundSupport(params[0],leap);
        }

        protected DateItem[] backgroundSupport(Integer month,boolean isLeap){

            DateItem [] days;

           if(month == 1 || month == 3 || month == 5 || month == 7 || month ==8 || month ==10 || month ==12){

               days = new DateItem[31];


               int j=1;
               for(int i=0;i<days.length;i++){

                   DateItem temp = new DateItem();
                   temp.setValue(j+"");
                   days[i] = temp;
                   j++;

               }

           }else if(month == 2){

               if(isLeap){


                   days = new DateItem[29];

                   int j =1;
                   for(int i=0;i<days.length;i++){

                       DateItem temp = new DateItem();
                       temp.setValue(j+"");
                       days[i] = temp;
                       j++;

                   }

               }else{

                   days = new DateItem[28];

                   int j =1;
                   for(int i=0;i<days.length;i++){

                       DateItem temp = new DateItem();
                       temp.setValue(j+"");
                       days[i] = temp;
                       j++;

                   }

               }

           }else{

               days = new DateItem[30];

               int j =1;
               for(int i=0;i<days.length;i++){

                   DateItem temp = new DateItem();
                   temp.setValue(j+"");
                   days[i] = temp;
                   j++;

               }

           }

            return days;


        }


        protected void setLeap(boolean leap){
            this.leap=leap;
        }

    }

    private int yearLimit(){
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.YEAR);

    }

    public void setDateStart(int start){
        this.yearStart = start;
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Resources res = getContext().getResources();
        final int id = res.getIdentifier("titleDivider", "id", "android");
        final View view = findViewById(id);

        if(view!=null) {
            view.setBackgroundColor(Color.parseColor("#BF360C"));
        }

    }
}
