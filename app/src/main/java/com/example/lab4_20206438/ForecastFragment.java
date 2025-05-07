package com.example.lab4_20206438;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.lab4_20206438.R;
import com.example.lab4_20206438.ForecastAdapter;
import com.example.lab4_20206438.ForecastItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ForecastFragment extends Fragment {

    private RecyclerView recyclerView;
    private ForecastAdapter adapter;
    private List<ForecastItem> forecastList;

    public ForecastFragment() {
        // Constructor vacío requerido
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forecast, container, false);

        recyclerView = view.findViewById(R.id.recycler_forecast);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        forecastList = generarDatosPrueba();
        adapter = new ForecastAdapter(forecastList);
        recyclerView.setAdapter(adapter);

        return view;
    }

    private List<ForecastItem> generarDatosPrueba() {
        List<ForecastItem> lista = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            lista.add(new ForecastItem("Ciudad " + i, (i + 1) + " días"));
        }
        return lista;
    }
}