package com.example.lab4_20206438;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab4_20206438.R;
import com.example.lab4_20206438.LocationAdapter;
import com.example.lab4_20206438.LocationItem;

import java.util.ArrayList;
import java.util.List;

public class LocationsFragment extends Fragment {

    private RecyclerView recyclerView;
    private LocationAdapter adapter;
    private List<LocationItem> locationList;

    public LocationsFragment() {
        // Constructor vacío requerido
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_locations, container, false);

        recyclerView = view.findViewById(R.id.recycler_locations);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        locationList = generarDatosPrueba();
        adapter = new LocationAdapter(locationList);
        recyclerView.setAdapter(adapter);

        return view;
    }

    private List<LocationItem> generarDatosPrueba() {
        List<LocationItem> lista = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            lista.add(new LocationItem("Item " + i));
        }
        return lista;
    }
}