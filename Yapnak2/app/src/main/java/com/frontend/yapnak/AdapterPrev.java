package com.frontend.yapnak;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.uq.yapnak.R;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStream;
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

            convertView= layoutInflater.inflate(R.layout.item2,parent,false);

            setView(convertView);

            deal = getItem(position);

            TextView mainText = (TextView) view.findViewById(R.id.title);
            TextView subText = (TextView) view.findViewById(R.id.subtitle);
            TextView locationName = (TextView)view.findViewById(R.id.distance);
            TextView loyaltyPointsTitle = (TextView)view.findViewById(R.id.loyalty);
            TextView points = (TextView)view.findViewById(R.id.loyaltyPoints);
            TextView foodStyle = (TextView)view.findViewById(R.id.foodStyle);

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
            foodStyle.setText(deal.getFoodStyle());

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

            //httpClient = new DefaultHttpClient();
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
            url = urls[0];
            try{

                //Bitmap bitmap= BitmapFactory.decodeStream((InputStream) new URL(urls[0]).getContent());

                Bitmap bitmap = compressImage((InputStream) new URL(urls[0]).getContent(),100,10);
                /*BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
                Paint paint = new Paint();
                paint.setShader(shader);
                paint.setAntiAlias(true);
                Canvas c = new Canvas(bitmap);
                c.drawCircle(bitmap.getWidth()/2,bitmap.getHeight()/2,bitmap.getWidth()/2,paint);*/

                return bitmap;
            }catch (Exception e){
                e.printStackTrace();
                return  null;
            }
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);

            if(bitmap!=null){
                if(bitmap.getWidth()<image.getWidth()){
                    image.setMaxWidth(bitmap.getWidth());
                }
                if(bitmap.getHeight()<image.getHeight()){
                    image.setMaxHeight(bitmap.getHeight());
                }
                image.setImageBitmap(bitmap);
            }
        }
    }

    private void setView(View v){
        this.view = v;

    }

    private int pictureWidth=0;
    private int pictureHeight=0;
    public View getActualView(){
        return this.view;
    }

    private String url;
    private int calculateSampleSize(BitmapFactory.Options options,int reqWidth,int reqHeight){
      final int width = options.outWidth;
      final int height = options.outHeight;
        pictureHeight = options.outHeight;
        pictureWidth=options.outWidth;

        int inSampleSize=1;

        if(height>reqHeight || width>reqWidth){

            final int halfHeight = height/2;
            final int halfWidth = width/2;

            while((halfHeight/inSampleSize>reqHeight)&&(halfWidth/inSampleSize)>reqWidth){
                      inSampleSize*=2;
            }
        }
        return inSampleSize;
    }

    private Bitmap compressImage(InputStream stream,int reqWidth,int reqHeight){

        try {
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(stream, null, options);
            options.inSampleSize = calculateSampleSize(options, reqWidth, reqHeight);


            options.inJustDecodeBounds = false;


            stream.close();
            stream = (InputStream) new URL(url).getContent();

            Bitmap b= BitmapFactory.decodeStream(stream, null, options);
            stream.close();
            return b;
        }catch(IOException e){
            return null;
        }
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
