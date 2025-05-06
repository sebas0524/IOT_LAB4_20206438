package com.example.lab4_20206438;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab4_20206438.R;
import com.example.lab4_20206438.ForecastItem;

import java.util.List;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ViewHolder> {

    private List<ForecastItem> forecastList;

    public ForecastAdapter(List<ForecastItem> forecastList) {
        this.forecastList = forecastList;
    }

    @NonNull
    @Override
    public ForecastAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_forecast, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ForecastAdapter.ViewHolder holder, int position) {
        ForecastItem item = forecastList.get(position);
        holder.txtCiudad.setText(item.getCiudad());
        holder.txtDias.setText(item.getDias());
    }

    @Override
    public int getItemCount() {
        return forecastList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtCiudad, txtDias;

        public ViewHolder(View itemView) {
            super(itemView);
            txtCiudad = itemView.findViewById(R.id.txt_ciudad);
            txtDias = itemView.findViewById(R.id.txt_dias);
        }
    }
}
