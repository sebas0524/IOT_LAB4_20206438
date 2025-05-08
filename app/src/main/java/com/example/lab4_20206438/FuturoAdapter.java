package com.example.lab4_20206438;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.lab4_20206438.R;
import com.example.lab4_20206438.FuturoItem;

import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FuturoAdapter extends RecyclerView.Adapter<FuturoAdapter.FuturoViewHolder> {

    private List<FuturoItem> futuroItems;

    public FuturoAdapter(List<FuturoItem> futuroItems) {
        this.futuroItems = futuroItems;
    }

    @NonNull
    @Override
    public FuturoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_future, parent, false);
        return new FuturoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FuturoViewHolder holder, int position) {
        FuturoItem item = futuroItems.get(position);
        holder.tvMatch.setText(item.getMatch());
        holder.tvTournament.setText(item.getTournament());
        holder.tvStadium.setText("Estadio: " + item.getStadium());

        String location = item.getCountry();
        if (!item.getRegion().isEmpty()) {
            location = item.getRegion() + ", " + location;
        }
        holder.tvLocation.setText("Ubicación: " + location);

        holder.tvDateTime.setText("Fecha y hora: " + item.getStart());
    }

    @Override
    public int getItemCount() {
        return futuroItems.size();
    }

    public void updateData(List<FuturoItem> newItems) {
        this.futuroItems = newItems;
        notifyDataSetChanged();
    }

    public static class FuturoViewHolder extends RecyclerView.ViewHolder {
        TextView tvMatch, tvTournament, tvStadium, tvLocation, tvDateTime;

        public FuturoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMatch = itemView.findViewById(R.id.tvMatch);
            tvTournament = itemView.findViewById(R.id.tvTournament);
            tvStadium = itemView.findViewById(R.id.tvStadium);
            tvLocation = itemView.findViewById(R.id.tvLocation);
            tvDateTime = itemView.findViewById(R.id.tvDateTime);
        }
    }
}