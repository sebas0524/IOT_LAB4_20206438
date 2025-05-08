package com.example.lab4_20206438;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

public class AppActivity extends AppCompatActivity {

    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_activity);

        NavHostFragment navHostFragment = (NavHostFragment)
                getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();

        Button btnLocations = findViewById(R.id.btn_locations);
        Button btnForecast = findViewById(R.id.btn_forecast);
        Button btnSports = findViewById(R.id.btn_sports);

        btnLocations.setOnClickListener(v -> {
            navController.popBackStack(); // Limpia el anterior
            navController.navigate(R.id.locationFragment);
        });

        btnForecast.setOnClickListener(v -> {
            navController.popBackStack();
            navController.navigate(R.id.forecastFragment);
        });

        btnSports.setOnClickListener(v -> {
            navController.popBackStack();
            navController.navigate(R.id.futuroFragment);
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}