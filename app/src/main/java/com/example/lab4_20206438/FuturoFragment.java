package com.example.lab4_20206438;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab4_20206438.R;
import com.example.lab4_20206438.FuturoAdapter;
import com.example.lab4_20206438.FuturoItem;

import java.util.ArrayList;
import java.util.List;

public class FuturoFragment extends Fragment {

    private RecyclerView recyclerView;
    private FuturoAdapter adapter;
    private List<FuturoItem> deporteList;

    public FuturoFragment() {
        // Constructor vacío requerido
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_future, container, false);

        recyclerView = view.findViewById(R.id.recycler_future);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        deporteList = generarDatosPrueba();
        adapter = new FuturoAdapter(deporteList);
        recyclerView.setAdapter(adapter);

        return view;
    }

    private List<FuturoItem> generarDatosPrueba() {
        List<FuturoItem> lista = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            lista.add(new FuturoItem("Partido " + (i + 1), "Ciudad " + i));
        }
        return lista;
    }
}