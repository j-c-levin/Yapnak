package com.uq.yapnak;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.yapnak.gcmbackend.userEndpointApi.UserEndpointApi;
import com.yapnak.gcmbackend.userEndpointApi.model.SimpleEntity;

import java.io.IOException;

/**
 * Created by vahizan on 21/09/2015.
 */
public class ForgotLogin extends Activity {

    private EditText email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.forgot_login);
        email = (EditText) findViewById(R.id.forgotLoginEmailEdit);
    }

    public void forgotLogin(){

        if(connection() && email.getText().toString().length()>0) {
            new ForgotLoginDetails().execute(email.getText().toString());
        }else{
            Toast.makeText(getApplicationContext(),"Please Check Your Internet Connection",Toast.LENGTH_SHORT).show();
        }

    }

    private boolean connection(){
        ConnectivityManager manager= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected();
        boolean threeg = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnected();

        return (wifi||threeg);
    }

    private class ForgotLoginDetails extends AsyncTask<String,Void,SimpleEntity>{

        @Override
        protected SimpleEntity doInBackground(String... params) {
            UserEndpointApi api = new UserEndpointApi(AndroidHttp.newCompatibleTransport(),new AndroidJsonFactory(),null);

            try{
                return api.forgotLogin(params[0]).execute();
            }catch(IOException e){
                return null;
            }
        }

        @Override
        protected void onPostExecute(SimpleEntity aVoid) {
            super.onPostExecute(aVoid);

            try{
                if(aVoid!=null && Boolean.parseBoolean(aVoid.getStatus())){
                    Toast.makeText(getApplicationContext(),"An E-mail Will Be Sent To You Shortly",Toast.LENGTH_SHORT).show();
                }
            }catch (NullPointerException e){
                Toast.makeText(getApplicationContext(),"E-mail Regarding Password Reset, Was Not Sent",Toast.LENGTH_SHORT).show();

            }
        }
    }

}
