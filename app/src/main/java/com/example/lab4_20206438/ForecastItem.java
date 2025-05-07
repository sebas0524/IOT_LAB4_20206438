package com.example.lab4_20206438;


/*public class ForecastItem {
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
}*/
// ForecastItem.java


public class ForecastItem {
    private String date;
    private double maxTemp;
    private double minTemp;
    private String condition;
    private String conditionIcon;
    private double avgTemp;
    private double totalPrecipitation;
    private double windSpeed;
    private int humidity;

    public ForecastItem(String date, double maxTemp, double minTemp, String condition,
                        String conditionIcon, double avgTemp, double totalPrecipitation,
                        double windSpeed, int humidity) {
        this.date = date;
        this.maxTemp = maxTemp;
        this.minTemp = minTemp;
        this.condition = condition;
        this.conditionIcon = conditionIcon;
        this.avgTemp = avgTemp;
        this.totalPrecipitation = totalPrecipitation;
        this.windSpeed = windSpeed;
        this.humidity = humidity;
    }

    public String getDate() {
        return date;
    }

    public double getMaxTemp() {
        return maxTemp;
    }

    public double getMinTemp() {
        return minTemp;
    }

    public String getCondition() {
        return condition;
    }

    public String getConditionIcon() {
        return conditionIcon;
    }

    public double getAvgTemp() {
        return avgTemp;
    }

    public double getTotalPrecipitation() {
        return totalPrecipitation;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public int getHumidity() {
        return humidity;
    }
}
