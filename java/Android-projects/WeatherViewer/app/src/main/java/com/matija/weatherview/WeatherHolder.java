package com.matija.weatherview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.matija.weatherviewer.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by matija on 29.6.17..
 */

public class WeatherHolder extends RecyclerView.ViewHolder {

    private ImageView conditionImageView;
    private TextView dayTextView;
    private TextView hiTextView;
    private TextView lowTextView;
    private TextView humidityTextView;

    private Map<String, Bitmap> bitmaps = new HashMap<>();

    private Context context;

    public WeatherHolder(View itemView, Context context) {
        super(itemView);
        this.conditionImageView = (ImageView) itemView.findViewById(R.id.conditionImageView);
        this.dayTextView = (TextView) itemView.findViewById(R.id.dayTextView);
        this.hiTextView = (TextView) itemView.findViewById(R.id.hiTextView);
        this.lowTextView = (TextView) itemView.findViewById(R.id.lowTextView);
        this.humidityTextView= (TextView) itemView.findViewById(R.id.humidityTextView);
        this.context = context;
    }

    public void bindWeather(Weather weather) {
        dayTextView.setText(context.getString(R.string.day_description, weather.dayOfWeek, weather.description));
        hiTextView.setText(context.getString(R.string.high_temp, weather.maxTemp));
        lowTextView.setText(context.getString(R.string.low_temp, weather.minTemp));
        humidityTextView.setText(context.getString(R.string.humidity, weather.humidity));

        if (bitmaps.containsKey(weather.iconURL)) {
            conditionImageView.setImageBitmap(bitmaps.get(weather.iconURL));
        }
        else {
            // aysnc task have to be created each time we need the task done, because async task can be executed only once
            new LoadImageTask(conditionImageView).execute(weather.iconURL);
        }
    }

    private class LoadImageTask extends AsyncTask<String, Void, Bitmap> {

        private ImageView imageView;

        public LoadImageTask(ImageView imageView) {
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap bitmap = null;
            HttpURLConnection conn = null;


            try {
                URL url = new URL(params[0]);
                conn = (HttpURLConnection) url.openConnection();
                InputStream inputStream = conn.getInputStream();
                bitmap = BitmapFactory.decodeStream(inputStream);

                bitmaps.put(params[0], bitmap);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                conn.disconnect();
            }
            // this bitmap will be forwared to the on post excute method
            return bitmap;
        }


        // this method runs on the GUI (main) thread
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            imageView.setImageBitmap(bitmap);
        }
    }
}
