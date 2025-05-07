package com.example.lab4_20206438;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.lab4_20206438.R;
import com.example.lab4_20206438.LocationItem;

import java.util.List;

/*public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.ViewHolder> {

    private List<LocationItem> locationList;

    public LocationAdapter(List<LocationItem> locationList) {
        this.locationList = locationList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_location, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LocationItem item = locationList.get(position);
        holder.txtId.setText("ID: " + item.getId());
        holder.txtName.setText("Ciudad: " + item.getName());
        holder.txtRegion.setText("Región: " + item.getRegion());
        holder.txtCountry.setText("País: " + item.getCountry());
    }

    @Override
    public int getItemCount() {
        return locationList.size();
    }

    public void updateList(List<LocationItem> newList) {
        locationList.clear();
        locationList.addAll(newList);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtId, txtName, txtRegion, txtCountry;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtId = itemView.findViewById(R.id.txt_id);
            txtName = itemView.findViewById(R.id.txt_name);
            txtRegion = itemView.findViewById(R.id.txt_region);
            txtCountry = itemView.findViewById(R.id.txt_country);
        }
    }
}*/
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocationViewHolder> {

    private List<LocationItem> locationList;
    private OnItemClickListener listener;

    public LocationAdapter(List<LocationItem> locationList) {
        this.locationList = locationList;
    }

    public interface OnItemClickListener {
        void onItemClick(LocationItem location);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_location, parent, false);
        return new LocationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationViewHolder holder, int position) {
        LocationItem currentItem = locationList.get(position);
        holder.textName.setText(currentItem.getName());
        holder.textRegion.setText(currentItem.getRegion());
        holder.textCountry.setText(currentItem.getCountry());
    }

    @Override
    public int getItemCount() {
        return locationList.size();
    }

    public void updateList(List<LocationItem> newList) {
        this.locationList.clear();
        this.locationList.addAll(newList);
        notifyDataSetChanged();
    }

    class LocationViewHolder extends RecyclerView.ViewHolder {
        TextView textName;
        TextView textRegion;
        TextView textCountry;

        public LocationViewHolder(@NonNull View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.text_name);
            textRegion = itemView.findViewById(R.id.text_region);
            textCountry = itemView.findViewById(R.id.text_country);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && listener != null) {
                    listener.onItemClick(locationList.get(position));
                }
            });
        }
    }
}