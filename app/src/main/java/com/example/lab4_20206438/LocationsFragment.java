package com.example.lab4_20206438;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.text.TextUtils;

import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/*public class LocationsFragment extends Fragment {

    private static final String TAG = "LocationsFragment";
    private RecyclerView recyclerView;
    private LocationAdapter adapter;
    private List<LocationItem> locationList = new ArrayList<>();
    private EditText inputSearch;
    private Button btnSearch;
    private RequestQueue requestQueue;

    private static final String API_KEY = "ec24b1c6dd8a4d528c1205500250305";
    private static final String BASE_URL = "https://api.weatherapi.com/v1/search.json";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_locations, container, false);

        // Inicializar la cola de peticiones de Volley
        requestQueue = Volley.newRequestQueue(requireContext());

        // Inicializar vistas
        recyclerView = view.findViewById(R.id.recyclerViewLocation);
        inputSearch = view.findViewById(R.id.input_location);
        btnSearch = view.findViewById(R.id.btn_search);

        // Configurar RecyclerView
        adapter = new LocationAdapter(locationList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        // Configurar botón de búsqueda
        btnSearch.setOnClickListener(v -> searchLocation());

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (requestQueue != null) {
            requestQueue.cancelAll(TAG);
        }
    }

    private void searchLocation() {
        String query = inputSearch.getText().toString().trim();

        // Validar entrada
        if (TextUtils.isEmpty(query)) {
            Toast.makeText(getContext(), "Por favor ingresa una ubicación", Toast.LENGTH_SHORT).show();
            return;
        }

        // Mostrar indicador de carga
        Toast.makeText(getContext(), "Buscando...", Toast.LENGTH_SHORT).show();

        // Construir URL con HTTPS
        String url = BASE_URL + "?key=" + API_KEY + "&q=" + query;
        Log.d(TAG, "URL de búsqueda: " + url);

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                response -> {
                    Log.d(TAG, "Respuesta recibida: " + response.toString());
                    locationList.clear();

                    if (response.length() == 0) {
                        Toast.makeText(getContext(), "No se encontraron ubicaciones", Toast.LENGTH_SHORT).show();
                    }

                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject obj = response.getJSONObject(i);
                            String id = obj.has("id") ? obj.getString("id") : UUID.randomUUID().toString();
                            String name = obj.getString("name");
                            String region = obj.getString("region");
                            String country = obj.getString("country");

                            LocationItem item = new LocationItem(id, name, region, country);
                            locationList.add(item);
                            Log.d(TAG, "Ubicación añadida: " + name + ", " + country);
                        } catch (JSONException e) {
                            Log.e(TAG, "Error al procesar JSON: " + e.getMessage());
                        }
                    }

                    adapter.updateList(locationList);
                },
                error -> {
                    String errorMsg = error.toString();
                    if (error.networkResponse != null) {
                        errorMsg += " - Código: " + error.networkResponse.statusCode;
                    }
                    Log.e(TAG, "Error en la solicitud: " + errorMsg);
                    Toast.makeText(getContext(), "Error al buscar: " + errorMsg, Toast.LENGTH_LONG).show();
                }
        );

        // Configurar política de reintentos
        request.setRetryPolicy(new DefaultRetryPolicy(
                10000,  // 10 segundos de timeout
                2,      // Número máximo de reintentos
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));

        // Añadir etiqueta a la solicitud para poder cancelarla después
        request.setTag(TAG);

        // Añadir a la cola
        requestQueue.add(request);
    }
}*/

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.text.TextUtils;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class LocationsFragment extends Fragment {

    private static final String TAG = "LocationsFragment";
    private RecyclerView recyclerView;
    private LocationAdapter adapter;
    private List<LocationItem> locationList;
    private EditText inputSearch;
    private Button btnSearch;
    private TextView textResultsTitle;
    private RequestQueue requestQueue;

    private static final String API_KEY = "ec24b1c6dd8a4d528c1205500250305";
    private static final String BASE_URL = "https://api.weatherapi.com/v1/search.json";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        locationList = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_locations, container, false);

        // Inicializar la cola de peticiones de Volley una sola vez
        requestQueue = Volley.newRequestQueue(requireContext());

        // Inicializar vistas
        recyclerView = view.findViewById(R.id.recyclerViewLocation);
        inputSearch = view.findViewById(R.id.input_location);
        btnSearch = view.findViewById(R.id.btn_search);
        textResultsTitle = view.findViewById(R.id.text_results_title);

        // Configurar RecyclerView con un nuevo adaptador
        adapter = new LocationAdapter(locationList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        // Configurar clics en elementos de la lista
        /// ////////////////////////////////////////////////
        /*adapter.setOnItemClickListener(location -> {
            Toast.makeText(getContext(),
                    "Seleccionado: " + location.getName() + ", " + location.getCountry(),
                    Toast.LENGTH_SHORT).show();
            // Aquí puedes implementar la navegación a otra pantalla con los detalles
        });*/
        /////////////////////////////////////////////////////////
        adapter.setOnItemClickListener(location -> {
            Toast.makeText(getContext(),
                    "Seleccionado: " + location.getName() + ", " + location.getCountry(),
                    Toast.LENGTH_SHORT).show();

            // Aquí estamos creando la instancia de ForecastFragment y pasando los datos
            ForecastFragment forecastFragment = ForecastFragment.newInstance(
                    location.getId(),
                    location.getName(),
                    location.getRegion(),
                    location.getCountry(),
                    14 // Puedes pasar los días que prefieras, o incluso hacer que el usuario los elija
            );

            // Realizamos la navegación al fragmento de pronóstico
            /*getParentFragmentManager().beginTransaction()
                    .replace(R.id.nav_host_fragment, forecastFragment) // Asegúrate de que este ID sea el correcto
                    .addToBackStack(null) // Esto agrega el fragmento al back stack para que el botón "atrás" funcione
                    .commit();*/
            // Usar NavController para navegar
            NavController navController = NavHostFragment.findNavController(LocationsFragment.this);
            // Navegar al ForecastFragment usando la acción definida en el gráfico de navegación
            navController.navigate(R.id.action_locationsFragment_to_forecastFragment, forecastFragment.getArguments());

        });

        // Configurar botón de búsqueda
        btnSearch.setOnClickListener(v -> searchLocation());

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (requestQueue != null) {
            requestQueue.cancelAll(TAG);
        }
    }

    private void searchLocation() {
        String query = inputSearch.getText().toString().trim();

        // Validar entrada
        if (TextUtils.isEmpty(query)) {
            Toast.makeText(getContext(), "Por favor ingresa una ubicación", Toast.LENGTH_SHORT).show();
            return;
        }

        // Mostrar indicador de carga
        Toast.makeText(getContext(), "Buscando...", Toast.LENGTH_SHORT).show();

        // Ocultar título de resultados mientras se carga
        textResultsTitle.setVisibility(View.GONE);

        // Construir URL con HTTPS
        String url = BASE_URL + "?key=" + API_KEY + "&q=" + query;
        Log.d(TAG, "URL de búsqueda: " + url);

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                response -> {
                    Log.d(TAG, "Respuesta recibida: " + response.toString());
                    locationList.clear();

                    if (response.length() == 0) {
                        Toast.makeText(getContext(), "No se encontraron ubicaciones", Toast.LENGTH_SHORT).show();
                        textResultsTitle.setVisibility(View.GONE);
                    } else {
                        textResultsTitle.setVisibility(View.VISIBLE);

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject obj = response.getJSONObject(i);
                                String id = obj.has("id") ? obj.getString("id") : UUID.randomUUID().toString();
                                String name = obj.getString("name");
                                String region = obj.getString("region");
                                String country = obj.getString("country");

                                LocationItem item = new LocationItem(id, name, region, country);
                                locationList.add(item);
                                Log.d(TAG, "Ubicación añadida: " + name + ", " + country);
                            } catch (JSONException e) {
                                Log.e(TAG, "Error al procesar JSON: " + e.getMessage());
                            }
                        }
                    }

                    // Notificar al adaptador que los datos han cambiado
                    adapter.notifyDataSetChanged();
                },
                error -> {
                    String errorMsg = error.toString();
                    if (error.networkResponse != null) {
                        errorMsg += " - Código: " + error.networkResponse.statusCode;
                    }
                    Log.e(TAG, "Error en la solicitud: " + errorMsg);
                    Toast.makeText(getContext(), "Error al buscar: " + errorMsg, Toast.LENGTH_LONG).show();
                    textResultsTitle.setVisibility(View.GONE);
                }
        );

        // Configurar política de reintentos
        request.setRetryPolicy(new DefaultRetryPolicy(
                10000,  // 10 segundos de timeout
                2,      // Número máximo de reintentos
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));

        // Añadir etiqueta a la solicitud para poder cancelarla después
        request.setTag(TAG);

        // Añadir a la cola
        requestQueue.add(request);
    }
}