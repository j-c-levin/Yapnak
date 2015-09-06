package com.uq.yapnak;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.SQLException;

/**
 * Created by vahizan on 04/09/2015.
 */
public class DBHelper {
    private final String DB_NAME="LOGIN" ;
    private final String sql = "CREATE TABLE login_details WHERE ";
    private SQLiteDatabase db;
    private Helper helper;
    private Context context;

    private DBHelper(Context context){
        this.context =  context;
    }


    public DBHelper openConnection() throws SQLException{
        helper = new Helper(context,DB_NAME,null,2);
        db = helper.getReadableDatabase();
        return this;
    }
    private void close(){
        helper.close();
    }

    private class Helper extends SQLiteOpenHelper{


        public Helper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            //Create Table
            db.execSQL(sql);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }

}
