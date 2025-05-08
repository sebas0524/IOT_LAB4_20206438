package com.example.lab4_20206438;

public class FuturoItem {
    private String stadium;
    private String country;
    private String region;
    private String tournament;
    private String start;
    private String match;

    // Constructor
    public FuturoItem(String stadium, String country, String region, String tournament, String start, String match) {
        this.stadium = stadium;
        this.country = country;
        this.region = region;
        this.tournament = tournament;
        this.start = start;
        this.match = match;
    }

    // Getters
    public String getStadium() {
        return stadium;
    }

    public String getCountry() {
        return country;
    }

    public String getRegion() {
        return region;
    }

    public String getTournament() {
        return tournament;
    }

    public String getStart() {
        return start;
    }

    public String getMatch() {
        return match;
    }
}