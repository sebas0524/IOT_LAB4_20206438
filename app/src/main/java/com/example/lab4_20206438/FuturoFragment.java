package com.example.lab4_20206438;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.android.volley.Response;
import com.android.volley.VolleyError;

public class FuturoFragment extends Fragment {

    private EditText etLocation;
    private Button btnSearch;
    private RecyclerView rvFootballMatches;
    private TextView tvEmptyResults;
    private FuturoAdapter adapter;
    private List<FuturoItem> matchesList;
    private RequestQueue requestQueue;

    private static final String API_KEY = "ec24b1c6dd8a4d528c1205500250305";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_future, container, false);

        etLocation = view.findViewById(R.id.etLocation);
        btnSearch = view.findViewById(R.id.btnSearch);
        rvFootballMatches = view.findViewById(R.id.rvFootballMatches);
        tvEmptyResults = view.findViewById(R.id.tvEmptyResults);

        requestQueue = Volley.newRequestQueue(requireContext());

        matchesList = new ArrayList<>();
        adapter = new FuturoAdapter(matchesList);

        rvFootballMatches.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvFootballMatches.setAdapter(adapter);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String location = etLocation.getText().toString().trim();
                if (!location.isEmpty()) {
                    searchFootballMatches(location);
                } else {
                    Toast.makeText(requireContext(), "Por favor ingrese una ubicación", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private void searchFootballMatches(String location) {
        String url = "https://api.weatherapi.com/v1/sports.json?key=" + API_KEY + "&q=" + location;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray footballMatches = response.getJSONArray("football");
                            matchesList.clear();

                            if (footballMatches.length() > 0) {
                                tvEmptyResults.setVisibility(View.GONE);
                                rvFootballMatches.setVisibility(View.VISIBLE);

                                for (int i = 0; i < footballMatches.length(); i++) {
                                    JSONObject matchObj = footballMatches.getJSONObject(i);

                                    String stadium = matchObj.getString("stadium");
                                    String country = matchObj.getString("country");
                                    String region = matchObj.getString("region");
                                    String tournament = matchObj.getString("tournament");
                                    String start = matchObj.getString("start");
                                    String match = matchObj.getString("match");

                                    FuturoItem item = new FuturoItem(stadium, country, region, tournament, start, match);
                                    matchesList.add(item);
                                }

                                adapter.notifyDataSetChanged();
                            } else {
                                tvEmptyResults.setVisibility(View.VISIBLE);
                                rvFootballMatches.setVisibility(View.GONE);
                            }

                        } catch (JSONException e) {
                            Toast.makeText(requireContext(), "Error al procesar los datos: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            tvEmptyResults.setVisibility(View.VISIBLE);
                            rvFootballMatches.setVisibility(View.GONE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(requireContext(), "Error de conexión: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        tvEmptyResults.setVisibility(View.VISIBLE);
                        rvFootballMatches.setVisibility(View.GONE);
                    }
                });

        requestQueue.add(request);
    }
}