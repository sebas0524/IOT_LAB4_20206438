package com.example.lab4_20206438;


public class ForecastItem {
    private String ciudad;
    private String dias;

    public ForecastItem(String ciudad, String dias) {
        this.ciudad = ciudad;
        this.dias = dias;
    }

    public String getCiudad() {
        return ciudad;
    }

    public String getDias() {
        return dias;
    }
}
