package com.esaburruss.openweathermapapi;

/**
 * Created by esaburruss on 10/19/16.
 */
import java.io.Serializable;
import java.text.SimpleDateFormat;

import java.util.Calendar;

import java.util.Date;


public class DailyWeatherInfo implements Serializable{

    String date;

    String date2;

    String high;

    String low;

    String weather;

    String humidity;

    String pressure;

    String wind;



    DailyWeatherInfo(){}



    public String getDate() {

        return date;

    }

    public String getDate2() {

        return date2;

    }

    public String getHigh() {

        return high;

    }

    public void setHigh(int high) {

        this.high = high + "\u00B0";

    }

    public String getLow() {

        return low;

    }

    public void setLow(int low) {

        this.low = low + "\u00B0";

    }

    public String getWeather() {

        return weather;

    }

    public void setWeather(String weather) {

        this.weather = weather;

    }

    public String getHumidity() {

        return humidity;

    }

    public void setHumidity(int humidity) {

        this.humidity = "Humidity: " + humidity + " %";

    }

    public String getPressure() {

        return pressure;

    }

    public void setPressure(double pressure) {

        this.pressure = "Pressure: " + ((int)pressure) + " hPa";

    }

    public String getWind() {

        return wind;

    }

    public void setWind(double s, double d) {

        String[] compass = new String[]{"N","NNE","NE","ENE","E","ESE", "SE",
                "SSE","S","SSW","SW","WSW","W","WNW","NW","NNW"};

        int x = ((int) ((d/22.5) +0.5));

        this.wind = "Wind: " + s + " km/h " + compass[x%16];

    }

    public void setToday(long l){

        this.date = "Today";

        setDayOfWeek(l);

    }

    public void setTomorrow(long l){

        this.date = "Tomorrow";

        setDayOfWeek(l);

    }

    public void setDayOfWeek(long l){

        Date d = new Date(l*1000L);

        Calendar c = Calendar.getInstance();

        c.setTime(d);

        if(this.date == null)

            this.date = new SimpleDateFormat("EEEE").format(c.getTime());

        this.date2 = new SimpleDateFormat("MMMM d").format(c.getTime());

    }

}
