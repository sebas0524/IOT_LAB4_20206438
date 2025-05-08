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
import android.app.AlertDialog;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.android.volley.DefaultRetryPolicy;

public class ForecastFragment extends Fragment implements SensorEventListener {

    private static final String TAG = "ForecastFragment";
    private static final String API_KEY = "ec24b1c6dd8a4d528c1205500250305";
    private static final String BASE_URL = "https://api.weatherapi.com/v1/forecast.json";

    private static final String ARG_LOCATION_ID = "location_id";
    private static final String ARG_LOCATION_NAME = "location_name";
    private static final String ARG_LOCATION_REGION = "location_region";
    private static final String ARG_LOCATION_COUNTRY = "location_country";
    private static final String ARG_DAYS = "days";

    // Vistas
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

    private String locationId;
    private String locationName;
    private String locationRegion;
    private String locationCountry;
    private int days = 14;
    private static final String ARG_SHOW_SEARCH = "show_search_bar";


    private SensorManager sensorManager;
    private Sensor accelerometer;
    private static final float SHAKE_THRESHOLD = 20.0f;
    private long lastShakeTime = 0;

    public ForecastFragment() {}

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

        // Sensor
        sensorManager = (SensorManager) requireActivity().getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
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
            layoutDirectSearch.setVisibility(View.VISIBLE);


            inputLocationId.setText("");
            inputDays.setText("");

            textLocationName.setText(locationName);
            textLocationRegionCountry.setText(String.format("%s, %s", locationRegion, locationCountry));

            fetchForecast(locationId, days);
        } else {
            layoutLocationInfo.setVisibility(View.GONE);
            layoutDirectSearch.setVisibility(View.VISIBLE);
        }

        // Configurar el botón de búsqueda (tanto para la primera carga como para búsquedas adicionales)
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

    private void fetchForecast(String locationId, int days) {
        String url = BASE_URL + "?key=" + API_KEY + "&q=id:" + locationId + "&days=" + days;
        Log.d(TAG, "Forecast URL: " + url);

        Toast.makeText(getContext(), "Cargando pronóstico...", Toast.LENGTH_SHORT).show();

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET, url, null,
                response -> {
                    forecastList.clear();

                    try {
                        // Siempre actualizar la información de la ubicación al recibir una respuesta
                        JSONObject location = response.getJSONObject("location");
                        layoutLocationInfo.setVisibility(View.VISIBLE);
                        textLocationName.setText(location.getString("name"));
                        textLocationRegionCountry.setText(String.format("%s, %s",
                                location.getString("region"), location.getString("country")));

                        JSONArray forecastDays = response.getJSONObject("forecast").getJSONArray("forecastday");

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

                            forecastList.add(new ForecastItem(
                                    date, maxTemp, minTemp, conditionText, conditionIcon,
                                    avgTemp, totalPrecip, maxWind, avgHumidity));
                        }

                        adapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        Log.e(TAG, "Error al procesar JSON: " + e.getMessage());
                        Toast.makeText(getContext(), "Error al procesar los datos", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    String errorMsg = error.toString();
                    if (error.networkResponse != null) {
                        errorMsg += " - Código: " + error.networkResponse.statusCode;
                    }
                    Toast.makeText(getContext(), "Error al obtener pronóstico: " + errorMsg, Toast.LENGTH_LONG).show();
                }
        );

        request.setRetryPolicy(new DefaultRetryPolicy(10000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request.setTag(TAG);
        requestQueue.add(request);
    }

    // ===== Sensor methods =====

    @Override
    public void onResume() {
        super.onResume();
        if (accelerometer != null) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        double acceleration = Math.sqrt(x * x + y * y + z * z);
        long currentTime = System.currentTimeMillis();

        if (acceleration > SHAKE_THRESHOLD && (currentTime - lastShakeTime > 1000)) {
            lastShakeTime = currentTime;
            showUndoDialog();
        }
    }

    private void showUndoDialog() {
        new AlertDialog.Builder(requireContext())
                .setTitle("¿Deshacer acción?")
                .setMessage("¿Deseas eliminar los últimos pronósticos obtenidos?")
                .setPositiveButton("Sí", (dialog, which) -> {
                    forecastList.clear();
                    adapter.notifyDataSetChanged();
                    Toast.makeText(getContext(), "Pronósticos eliminados", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (requestQueue != null) {
            requestQueue.cancelAll(TAG);
        }
        sensorManager.unregisterListener(this);
    }
}