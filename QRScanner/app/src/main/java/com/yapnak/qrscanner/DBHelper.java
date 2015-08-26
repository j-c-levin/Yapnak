package com.yapnak.qrscanner;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by vahizan on 24/08/2015.
 */
public class DBHelper {
    private Context c;
    private final String tableName = "Authenticate";

    private final String sql = "CREATE TABLE " + tableName + "(id INTEGER PRIMARY KEY AUTOINCREMENT,client_email TEXT NOT NULL,login_time DATETIME,expiry DATETIME,count INTEGER )";
    private Helper helper;

    private class Helper extends SQLiteOpenHelper {


        public Helper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(sql);

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }

    private String dbname;
    public DBHelper(String name, Context context){
        dbname = name;
        c=context;
    }
    private SQLiteDatabase sqldb;

    public DBHelper open() throws SQLException{

        helper = new Helper(c,dbname,null,2);
        sqldb=helper.getWritableDatabase();
        return this;
    }

    public void close(){
        helper.close();
    }





        public void insertValues(String clientemail) {

            ContentValues values = new ContentValues();
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/DD", Locale.UK);
            String currentLogin = sdf.format(cal.getTime());
            values.put("client_email", clientemail);
            values.put("login_time", currentLogin);
            values.put("count", 1);
            Date expiry = new Date();
            long oneWeek = 1000 * 60 * 60 * 24 * 7;
            long expiryTime = cal.getTimeInMillis() + oneWeek;
            expiry.setTime(expiryTime);
            values.put("expiry", sdf.format(expiry));
            sqldb.insert(tableName, null, values);


        }

        private int updateCount(String clientEmail) {

            int i = 0;
            Cursor c = sqldb.query(tableName, new String[]{"count"}, "client_email = ? ", new String[]{clientEmail}, null, null, null);
            if (c.moveToFirst()) {
                c.moveToFirst();
                i = c.getInt(c.getColumnIndex("count"));
            }


            return i;
        }

        public void updateValues(String clientEmail) {
            int update = updateCount(clientEmail);

            ContentValues values = new ContentValues();
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/DD", Locale.UK);
            String currentLogin = sdf.format(cal.getTime());
            values.put("login_time", currentLogin);
            values.put("count", update + 1);
            sqldb.update(tableName, values, null, null);

        }

       public boolean clientExists(String clientEmail){

           Cursor c = sqldb.query(tableName,new String[]{"client_email"},"client_email = ? ",new String[]{clientEmail},null,null,null);

           if(c.moveToFirst()){
               return true;
           }else{
               return false;
           }

       }




    }

