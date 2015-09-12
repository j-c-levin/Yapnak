package com.frontend.yapnak;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.uq.yapnak.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by vahizan on 16/04/2015.
 */

public class AdapterPrev extends ArrayAdapter<ItemPrev> implements Filterable {

    private View view;
    private ItemPrev deal;
    private ImageView tutorial1,tutorial2;
    private SharedPreferences tutorial;

    private final String FILE_NAME = "yapnak_details";

    public AdapterPrev(Context context, int resourse, ItemPrev[] values) {
        super(context, R.layout.item2,values);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

         if(convertView == null){

            LayoutInflater layoutInflater = ((Activity)getContext()).getLayoutInflater();

            convertView=layoutInflater.inflate(R.layout.item2,parent,false);

            setView(convertView);

            deal = getItem(position);

            TextView mainText = (TextView) view.findViewById(R.id.title);
            TextView subText = (TextView) view.findViewById(R.id.subtitle);
            TextView locationName = (TextView)view.findViewById(R.id.distance);
            TextView loyaltyPointsTitle = (TextView)view.findViewById(R.id.loyalty);
            TextView points = (TextView)view.findViewById(R.id.loyaltyPoints);

            /* Typeface custom_font = Typeface.createFromAsset(getContext().getAssets(),"fonts/canela_light.otf");
             mainText.setTypeface(custom_font);
             subText.setTypeface(custom_font);
             locationName.setTypeface(custom_font);
             loyaltyPointsTitle.setTypeface(custom_font);
             points.setTypeface(custom_font);
             */


            ImageView locationLogo = (ImageView) view.findViewById(R.id.locationIcon);
            ImageView restaurantLogo = (ImageView) view.findViewById(R.id.logo);
            ImageView hotDeal = (ImageView) view.findViewById(R.id.heatIcon);
            mainText.setText(deal.getMainText());
            subText.setText(deal.getSubText());
            locationLogo.setImageResource(deal.getLocationIcon());
            loyaltyPointsTitle.setText(deal.getLoyaltyPointsTitle());
            locationName.setText(deal.getDistanceTime());
            points.setText(deal.getPoints());
            hotDeal.setImageResource(deal.getHotDeal());
            //restaurantLogo.setImageResource(deal.getLogo());

             if(deal.isTutorial()){
                 tutorial = deal.getPreferences();
                 RelativeLayout extendHeight = (RelativeLayout) view.findViewById(R.id.extendHeight);
                 RelativeLayout extendIcon = (RelativeLayout) view.findViewById(R.id.extendIconLayout);
                 RelativeLayout extendText = (RelativeLayout) view.findViewById(R.id.extendTextLayout);
                 tutorial1 = (ImageView) view.findViewById(R.id.tutorial1);
                 tutorial2 = (ImageView) view.findViewById(R.id.tutorial2);


                 if (extendText.getVisibility() == View.GONE && extendIcon.getVisibility() == View.GONE) {
                     int value = tutorial.getInt("count",0);
                     Log.d("tutorialValue",String.valueOf(value));
                     if(value==0){
                         Animator.AnimatorListener animation = new AnimatorListenerAdapter() {
                             @Override
                             public void onAnimationStart(Animator animation) {
                                 tutorial1.setAlpha(0.0f);
                                 tutorial1.setVisibility(View.VISIBLE);

                             }

                             @Override
                             public void onAnimationEnd(Animator animation) {
                                // super.onAnimationEnd(animation);

                                 ObjectAnimator alpha = ObjectAnimator.ofFloat(tutorial1,"alpha",0.0f,1.0f);
                                 alpha.setDuration(200).start();
                             }
                         };
                         tutorial1.animate().setListener(animation).start();

                     }
                 }

             }

            //Implement ION Load Image FROM URL provided in dealList(SQLEntity sql) method in MainActivity;
             /*Ion.with(restaurantLogo)
                    .placeholder(R.drawable.manualicon)
                    .load(deal.getFetchImageURL());
             */
             new DownloadImage(restaurantLogo).execute(deal.getFetchImageURL());
             //Implement ION Load Image FROM URL provided in dealList(SQLEntity sql) method in MainActivity;
             //Load more images after authentication
             convertView.setTag(deal);
        }else{
            deal = (ItemPrev) convertView.getTag();
        }

        return convertView;

    }


    private class DownloadImage extends AsyncTask<String,Integer,Bitmap> {


        private DefaultHttpClient httpClient;
        private HttpGet get;
        private HttpEntity entity;
        private InputStream stream;
        private ImageView image;


        private DownloadImage(ImageView view){
            image = view;
        }

        @Override
        protected Bitmap doInBackground(String... urls) {

           // httpClient = new DefaultHttpClient();
            //get = new HttpGet(urls[0]);
            //HttpResponse response = httpClient.execute(get);
            //entity = response.getEntity();
            //HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            //connection.setDoInput(true);
            //connection.connect();
            //stream = connection.getInputStream();
               /* if(entity!=null){

                    stream = entity.getContent();
                    b = BitmapFactory.decodeStream(stream);

                    return b;
                }
                */
            try{
                Bitmap b= BitmapFactory.decodeStream((InputStream) new URL(urls[0]).getContent());
                return b;
            }catch (Exception e){
                e.printStackTrace();
                return  null;
            }
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);

            if(bitmap!=null){
                image.setImageBitmap(bitmap);
            }
        }
    }

    private void setView(View v){
        this.view = v;

    }

    public View getActualView(){
        return this.view;
    }


    @Override
    public int getItemViewType(int position) {
       return position;
    }

    @Override
    public int getViewTypeCount() {
       return getCount();
    }
}
