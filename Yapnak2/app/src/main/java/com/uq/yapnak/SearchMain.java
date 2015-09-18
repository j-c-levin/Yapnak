package com.uq.yapnak;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.search_features.SearchAdapter;
import com.search_features.SearchItem;
import com.uq.yapnak.R;
import com.uq.yapnak.SQLConnectAsyncTask;
import com.yapnak.gcmbackend.sQLEntityApi.SQLEntityApi;
import com.yapnak.gcmbackend.sQLEntityApi.model.All;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by vahizan on 03/09/2015.
 */
public class SearchMain extends Activity {

    private EditText searchText;
    private CharSequence seq;
    private ListView listView;
    private SearchAdapter adapter;
    private ArrayList<SearchItem>temp = defaultRestaurants();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*setContentView(R.layout.search_layout);
        searchText = (EditText) findViewById(R.id.searchField);
        listView = (ListView) findViewById(R.id.resultList);
        adapter = new SearchAdapter(getApplicationContext(),R.layout.search_layout);
        adapter.updateEntries(temp);
        listView.setAdapter(adapter);
        textListener();
        listItemClick();
        */

        handleIntent(getIntent());


    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent){
        if(Intent.ACTION_SEARCH.equals(intent.getAction())){
           String searchQuery = intent.getStringExtra(SearchManager.QUERY);


            Log.d("query",searchQuery);

            new SearchLocation().execute(searchQuery);

        }
    }

    private void listItemClick(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SearchItem item = (SearchItem) parent.getItemAtPosition(position);
                stop();
            }
        });
    }

    private Handler handler = new Handler();


    private Runnable run = new Runnable() {
        @Override
        public void run() {
             int count = getChars().length();
             temp = new ArrayList<>();
             int index=0;

            Log.d("characters",getChars().toString());

            /*for(int i=0;i<restaurantNames().size();i++){
                if(restaurantNames().get(i).substring(0,count).equalsIgnoreCase(getChars().toString())){
                    SearchItem item = new SearchItem();
                    item.setNumber(String.valueOf(index));
                    item.setSearchResult(restaurantNames().get(i));
                    temp.add(index,item);
                    index++;
                }
            }*/

            for(int i=0;i<defaultNames().size();i++){
                if(defaultNames().get(i).length()>getChars().length() && count!=0) {
                    if (defaultNames().get(i).substring(0, count).equalsIgnoreCase(getChars().toString())) {
                        SearchItem item = new SearchItem();
                        item.setNumber(String.valueOf(index));
                        item.setSearchResult(defaultNames().get(i));
                        temp.add(index,item);
                        index++;
                    }
                }else{
                    if(defaultNames().get(i).equalsIgnoreCase(getChars().toString())){
                        SearchItem item = new SearchItem();
                        item.setNumber(String.valueOf(index));
                        item.setSearchResult(defaultNames().get(i));
                        temp.add(index, item);
                        adapter.notifyDataSetChanged();
                        index++;
                    }
                }
            }
            //adapter = null;
            //adapter = new SearchAdapter(getApplicationContext(),R.layout.search_layout,temp);
            //adapter.notifyDataSetChanged();
            adapter.updateEntries(temp);
            adapter.notifyDataSetChanged();
        }
    };

    private void stop(){
        handler.removeCallbacks(run);
    }
    private void start(){
        run.run();
    }

    private void setChars(CharSequence s){
        this.seq=s;
    }
    private CharSequence getChars(){
        return this.seq;
    }

    private void textListener(){

        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                    setChars(s);
                    Log.d("chars",s.toString());
                    start();

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("afterChange",s.toString());
            }
        });


    }
    private List<String> list;
    private List<String> restaurantNames(){
        list = new ArrayList<>();
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                list = new Names().doInBackground();
            }
        });
        return list;
    }

    private List<String> defaultNames(){
        String [] names = {"Wrap It Up","Mc Donalds","Nandos","Jimmys","Gourmet Burger Kitchen","KFC","Burger King","Taco Bell"};
        List<String> list = Arrays.asList(names);
        return list;
    }

    private ArrayList<SearchItem> restaurants(){
        List<String> list = restaurantNames();
        ArrayList<SearchItem> items = new ArrayList<>();

        for(int i=0;i<list.size();i++){
            SearchItem item = new SearchItem();
            item.setNumber(String.valueOf(i));
            Log.d("restaurantName",list.get(i));
            item.setSearchResult(list.get(i));
            items.add(i,item);
        }

        return items;
    }

    private ArrayList<SearchItem> defaultRestaurants(){

        ArrayList<SearchItem> items = new ArrayList<>();
        String [] names = {"Wrap It Up","Mc Donalds","Nandos","Jimmys","Gourmet Burger Kitchen","KFC","Burger King","Taco Bell","Pizza Hut","Pizza Express","Domino's Pizza"};
        List<String> list = Arrays.asList(names);

        for(int i=0;i<list.size();i++){
            SearchItem item = new SearchItem();
            item.setNumber(String.valueOf(i));
            Log.d("restaurantName",list.get(i));
            item.setSearchResult(list.get(i));
            items.add(i,item);
        }

        return items;

    }

    private class Names extends AsyncTask<Void,Void,List<String>>{

        private List<String> names;

        @Override
        protected List<String> doInBackground(Void... params) {

            SQLEntityApi.Builder builder = new SQLEntityApi.Builder(AndroidHttp.newCompatibleTransport(),new AndroidJsonFactory(),null)
                    .setRootUrl("https://yapnak-app.appspot.com/_ah/api/");
            builder.setApplicationName("Yapnak");

            SQLEntityApi api = builder.build();

            try {
                List<All> list = api.getAllClients().execute().getList();
                int count = list.size();
                for(int i =0;i<count;i++){
                    names.add(i, list.get(i).getClientName());
                }

                return names;
            }catch(IOException e){
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<String> strings) {
            super.onPostExecute(strings);
        }
    }

    private Location getLoc(String address){
        String url = "http://maps.google.com/maps/api/geocode/json?address="+address+"&sensor=false";

        HttpGet get = new HttpGet(url);
        HttpClient client = new DefaultHttpClient();
        HttpResponse response;
        StringBuilder builder = new StringBuilder();
        try{

        response = client.execute(get);
        HttpEntity entity = response.getEntity();
            InputStream stream = entity.getContent();

            int data;
            while((data=stream.read())!=-1){
                builder.append((char)data);
            }

        }catch (ClientProtocolException cpe){

        }catch (IOException io){

        }

        double lat;
        double lng;
        JSONObject object;

        try {
            object = new JSONObject(builder.toString());
            lat = ((JSONArray) object.get("results")).getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getDouble("latitude");
            lng = ((JSONArray)object.get("results")).getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getDouble("longitude");
            Location loc;
            loc = new Location("");
            loc.setLatitude(lat);
            loc.setLongitude(lng);
            return loc;

        }catch(JSONException e){

            return null;
        }
    }

    private class SearchLocation extends AsyncTask<String,String,String>{

        Location location;
        @Override
        protected String doInBackground(String... params) {
            String loc = params[0];
            location = getLoc(loc);

             return params[0];

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if(location!=null){
            //    new SQLConnectAsyncTask(getApplicationContext(),location,(MainActivity)getParent()).execute();
            }else {
              //  new SQLConnectAsyncTask(getApplicationContext(),null,(MainActivity)getParent()).execute();
            }
        }
    }


}
