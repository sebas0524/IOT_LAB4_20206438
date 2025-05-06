package com.example.lab4_20206438;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;

public class AppActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_activity);

        loadFragment(new LocationsFragment());

        findViewById(R.id.btn_locations).setOnClickListener(v -> loadFragment(new LocationsFragment()));
        findViewById(R.id.btn_forecast).setOnClickListener(v -> loadFragment(new ForecastFragment()));
        findViewById(R.id.btn_sports).setOnClickListener(v -> loadFragment(new FuturoFragment()));
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .disallowAddToBackStack() // Esto evita que se acumulen fragmentos
                .commit();
    }
}
