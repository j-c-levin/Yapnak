package com.example.nand.abc;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Joshua on 17/05/2015.
 */
public class SQLConnectAsyncTask extends AsyncTask<Void, Void, Integer> {

    private Context context;
    private String[] details = null;

    SQLConnectAsyncTask(Context context, String[] details) {
        this.context = context;
        this.details = details;
/*      details[0] = id,
        details[1] = phone number,
        details[2] = promo code*/

    }

    @Override
    protected Integer doInBackground(Void... params) {
        Connection connection;
        String query = "Select * from user";
        try {
            Log.d("Debug", "Starting.");
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://173.194.230.210/yapnak_main", "client", "g7lFVLRzYdJoWXc3");

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            Log.d("Debug", resultSet.toString());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 1;
    }

    protected void onPostExecute(Integer result) {
    Log.d("Debug", "Completed.");

    }
}