package com.yapnak.qrscanner;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by vahizan on 24/08/2015.
 */
public class DBHelper extends SQLiteOpenHelper {
    private Context c;
    private String tableName;



    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        c=context;
        tableName = name;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + tableName + "(id INTEGER PRIMARY KEY AUTOINCREMENT,client_email TEXT NOT NULL,login_time DATETIME,expiry DATETIME,count INTEGER )";
        db.execSQL(sql);


    }

    private SQLiteDatabase db;

    public void insertValues(String clientemail){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/DD", Locale.UK);
        String currentLogin = sdf.format(cal.getTime());
        values.put("client_email",clientemail);
        values.put("login_time",currentLogin);
        values.put("count",1);
        Date expiry = new Date();
        long oneWeek = 1000*60*60*24*7;
        long expiryTime= cal.getTimeInMillis()+oneWeek;
        expiry.setTime(expiryTime);
        values.put("expiry",sdf.format(expiry));
        db.insert(tableName, null, values);
        db.close();

    }

    private int updateCount(String clientEmail){
        db = this.getWritableDatabase();
        int i = 0;
        Cursor c = db.query(tableName,new String[]{"count"},"WHERE client_email = ? ",new String[]{clientEmail},null,null,null);
        if(c.moveToFirst()){
            c.moveToFirst();
           i = c.getInt(c.getColumnIndex("count"));
        }

        db.close();
        return i;
    }

    public void updateValues(String clientEmail){
        int update = updateCount(clientEmail);
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/DD", Locale.UK);
        String currentLogin = sdf.format(cal.getTime());
        values.put("login_time",currentLogin);
        values.put("count",update+1);
        db.update(tableName,values,null,null);
        db.close();
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
