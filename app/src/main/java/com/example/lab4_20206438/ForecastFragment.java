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


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.DefaultRetryPolicy;


public class ForecastFragment extends Fragment {

    private static final String TAG = "ForecastFragment";
    private static final String API_KEY = "ec24b1c6dd8a4d528c1205500250305";
    private static final String BASE_URL = "https://api.weatherapi.com/v1/forecast.json";

    // Arguments keys
    private static final String ARG_LOCATION_ID = "location_id";
    private static final String ARG_LOCATION_NAME = "location_name";
    private static final String ARG_LOCATION_REGION = "location_region";
    private static final String ARG_LOCATION_COUNTRY = "location_country";
    private static final String ARG_DAYS = "days";

    // Views
    private RecyclerView recyclerView;
    private ForecastAdapter adapter;
    private List<ForecastItem> forecastList;
    private LinearLayout layoutLocationInfo;
    private LinearLayout layoutDirectSearch;
    private TextView textLocationName;
    private TextView textLocationRegionCountry;
    private EditText inputLocationId;
    private EditText inputDays;
    private Button btnSearchForecast;
    private RequestQueue requestQueue;

    // Location data
    private String locationId;
    private String locationName;
    private String locationRegion;
    private String locationCountry;
    private int days = 14; // Default to maximum days

    public ForecastFragment() {
    }


    public static ForecastFragment newInstance(String locationId, String locationName,
                                               String locationRegion, String locationCountry, int days) {
        ForecastFragment fragment = new ForecastFragment();
        Bundle args = new Bundle();
        args.putString(ARG_LOCATION_ID, locationId);
        args.putString(ARG_LOCATION_NAME, locationName);
        args.putString(ARG_LOCATION_REGION, locationRegion);
        args.putString(ARG_LOCATION_COUNTRY, locationCountry);
        args.putInt(ARG_DAYS, days);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        forecastList = new ArrayList<>();

        if (getArguments() != null) {
            locationId = getArguments().getString(ARG_LOCATION_ID);
            locationName = getArguments().getString(ARG_LOCATION_NAME);
            locationRegion = getArguments().getString(ARG_LOCATION_REGION);
            locationCountry = getArguments().getString(ARG_LOCATION_COUNTRY);
            days = getArguments().getInt(ARG_DAYS, 14);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_forecast, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        requestQueue = Volley.newRequestQueue(requireContext());

        // Initialize views
        recyclerView = view.findViewById(R.id.recyclerViewForecast);
        layoutLocationInfo = view.findViewById(R.id.layout_location_info);
        layoutDirectSearch = view.findViewById(R.id.layout_direct_search);
        textLocationName = view.findViewById(R.id.text_location_name);
        textLocationRegionCountry = view.findViewById(R.id.text_location_region_country);
        inputLocationId = view.findViewById(R.id.input_location_id);
        inputDays = view.findViewById(R.id.input_days);
        btnSearchForecast = view.findViewById(R.id.btn_search_forecast);


        adapter = new ForecastAdapter(forecastList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);


        if (locationId != null && !locationId.isEmpty()) {

            layoutLocationInfo.setVisibility(View.VISIBLE);
            layoutDirectSearch.setVisibility(View.GONE);


            textLocationName.setText(locationName);
            textLocationRegionCountry.setText(String.format("%s, %s", locationRegion, locationCountry));


            fetchForecast(locationId, days);
        } else {
            layoutLocationInfo.setVisibility(View.GONE);
            layoutDirectSearch.setVisibility(View.VISIBLE);

            btnSearchForecast.setOnClickListener(v -> {
                String id = inputLocationId.getText().toString().trim();
                String daysStr = inputDays.getText().toString().trim();

                if (TextUtils.isEmpty(id)) {
                    Toast.makeText(getContext(), "Por favor ingresa un ID de ubicación", Toast.LENGTH_SHORT).show();
                    return;
                }

                int daysCount = TextUtils.isEmpty(daysStr) ? 14 : Integer.parseInt(daysStr);
                if (daysCount < 1 || daysCount > 14) {
                    Toast.makeText(getContext(), "Los días deben estar entre 1 y 14", Toast.LENGTH_SHORT).show();
                    return;
                }

                fetchForecast(id, daysCount);
            });
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (requestQueue != null) {
            requestQueue.cancelAll(TAG);
        }
    }

    private void fetchForecast(String locationId, int days) {
        String url = BASE_URL + "?key=" + API_KEY + "&q=id:" + locationId + "&days=" + days;
        Log.d(TAG, "Forecast URL: " + url);

        Toast.makeText(getContext(), "Cargando pronóstico...", Toast.LENGTH_SHORT).show();

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                response -> {
                    Log.d(TAG, "Forecast response received");
                    forecastList.clear();

                    try {
                        if (layoutDirectSearch.getVisibility() == View.VISIBLE) {
                            JSONObject location = response.getJSONObject("location");
                            layoutLocationInfo.setVisibility(View.VISIBLE);
                            textLocationName.setText(location.getString("name"));
                            textLocationRegionCountry.setText(String.format("%s, %s",
                                    location.getString("region"),
                                    location.getString("country")));
                        }

                        JSONObject forecast = response.getJSONObject("forecast");
                        JSONArray forecastDays = forecast.getJSONArray("forecastday");

                        for (int i = 0; i < forecastDays.length(); i++) {
                            JSONObject dayForecast = forecastDays.getJSONObject(i);
                            String date = dayForecast.getString("date");

                            JSONObject day = dayForecast.getJSONObject("day");
                            double maxTemp = day.getDouble("maxtemp_c");
                            double minTemp = day.getDouble("mintemp_c");
                            double avgTemp = day.getDouble("avgtemp_c");
                            double totalPrecip = day.getDouble("totalprecip_mm");
                            double maxWind = day.getDouble("maxwind_kph");
                            int avgHumidity = day.getInt("avghumidity");

                            JSONObject condition = day.getJSONObject("condition");
                            String conditionText = condition.getString("text");
                            String conditionIcon = condition.getString("icon");

                            ForecastItem item = new ForecastItem(
                                    date, maxTemp, minTemp, conditionText, conditionIcon,
                                    avgTemp, totalPrecip, maxWind, avgHumidity);

                            forecastList.add(item);
                            Log.d(TAG, "Added forecast for date: " + date);
                        }

                        adapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        Log.e(TAG, "Error parsing forecast JSON: " + e.getMessage());
                        Toast.makeText(getContext(), "Error al procesar los datos", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    String errorMsg = error.toString();
                    if (error.networkResponse != null) {
                        errorMsg += " - Código: " + error.networkResponse.statusCode;
                    }
                    Log.e(TAG, "Error fetching forecast: " + errorMsg);
                    Toast.makeText(getContext(), "Error al obtener pronóstico: " + errorMsg, Toast.LENGTH_LONG).show();
                }
        );

        request.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                2,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
        request.setTag(TAG);
        requestQueue.add(request);
    }
}
