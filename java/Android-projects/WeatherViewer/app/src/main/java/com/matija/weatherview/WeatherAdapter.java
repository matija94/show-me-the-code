package com.matija.weatherview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.matija.weatherviewer.R;

import java.util.List;

/**
 * Created by matija on 29.6.17..
 */

public class WeatherAdapter extends RecyclerView.Adapter<WeatherHolder> {

    private List<Weather> weathers;
    private Context context;

    public WeatherAdapter(List<Weather> weathers, Context context) {
        this.context = context;
        this.weathers = weathers;
    }

    @Override
    public WeatherHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View inflate = inflater.inflate(R.layout.list_item, parent, false);
        return new WeatherHolder(inflate, context);
    }

    @Override
    public void onBindViewHolder(WeatherHolder holder, int position) {
        Weather weather = weathers.get(position);
        holder.bindWeather(weather);
    }

    @Override
    public int getItemCount() {
        return weathers.size();
    }


}