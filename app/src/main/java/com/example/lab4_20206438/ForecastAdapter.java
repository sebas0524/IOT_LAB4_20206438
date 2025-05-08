package com.example.lab4_20206438;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.lab4_20206438.R;
import com.example.lab4_20206438.ForecastItem;

import java.util.List;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder> {

    private List<ForecastItem> forecastList;

    public ForecastAdapter(List<ForecastItem> forecastList) {
        this.forecastList = forecastList;
    }

    @NonNull
    @Override
    public ForecastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_forecast, parent, false);
        return new ForecastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ForecastViewHolder holder, int position) {
        ForecastItem currentItem = forecastList.get(position);

        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat outputFormat = new SimpleDateFormat("EEE, dd MMM", Locale.getDefault());
        try {
            Date date = inputFormat.parse(currentItem.getDate());
            holder.textDate.setText(outputFormat.format(date));
        } catch (ParseException e) {
            holder.textDate.setText(currentItem.getDate());
        }


        holder.textMaxTemp.setText(String.format(Locale.getDefault(), "%.1f°C", currentItem.getMaxTemp()));
        holder.textMinTemp.setText(String.format(Locale.getDefault(), "%.1f°C", currentItem.getMinTemp()));
        holder.textCondition.setText(currentItem.getCondition());

        if (currentItem.getConditionIcon() != null && !currentItem.getConditionIcon().isEmpty()) {
            String iconUrl = "https:" + currentItem.getConditionIcon();
            Picasso.get().load(iconUrl).into(holder.iconCondition);
        }

        holder.textAdditionalInfo.setText(String.format(Locale.getDefault(),
                "Prec: %.1f mm • Viento: %.1f km/h • Hum: %d%%",
                currentItem.getTotalPrecipitation(),
                currentItem.getWindSpeed(),
                currentItem.getHumidity()));
    }

    @Override
    public int getItemCount() {
        return forecastList.size();
    }

    public void updateList(List<ForecastItem> newList) {
        this.forecastList.clear();
        this.forecastList.addAll(newList);
        notifyDataSetChanged();
    }

    static class ForecastViewHolder extends RecyclerView.ViewHolder {
        TextView textDate;
        TextView textMaxTemp;
        TextView textMinTemp;
        TextView textCondition;
        ImageView iconCondition;
        TextView textAdditionalInfo;

        public ForecastViewHolder(@NonNull View itemView) {
            super(itemView);
            textDate = itemView.findViewById(R.id.text_date);
            textMaxTemp = itemView.findViewById(R.id.text_max_temp);
            textMinTemp = itemView.findViewById(R.id.text_min_temp);
            textCondition = itemView.findViewById(R.id.text_condition);
            iconCondition = itemView.findViewById(R.id.icon_condition);
            textAdditionalInfo = itemView.findViewById(R.id.text_additional_info);
        }
    }
}