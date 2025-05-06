package com.example.lab4_20206438;

public class FuturoItem {
    private String partido;
    private String ciudad;

    public FuturoItem(String partido, String ciudad) {
        this.partido = partido;
        this.ciudad = ciudad;
    }

    public String getPartido() {
        return partido;
    }

    public String getCiudad() {
        return ciudad;
    }
}