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

public class FuturoAdapter extends RecyclerView.Adapter<FuturoAdapter.ViewHolder> {

    private List<FuturoItem> deporteList;

    public FuturoAdapter(List<FuturoItem> deporteList) {
        this.deporteList = deporteList;
    }

    @NonNull
    @Override
    public FuturoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_future, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FuturoAdapter.ViewHolder holder, int position) {
        FuturoItem item = deporteList.get(position);
        holder.txtPartido.setText(item.getPartido());
        holder.txtCiudad.setText(item.getCiudad());
    }

    @Override
    public int getItemCount() {
        return deporteList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtPartido, txtCiudad;

        public ViewHolder(View itemView) {
            super(itemView);
            txtPartido = itemView.findViewById(R.id.txt_partido);
            txtCiudad = itemView.findViewById(R.id.txt_ciudad_partido);
        }
    }
}