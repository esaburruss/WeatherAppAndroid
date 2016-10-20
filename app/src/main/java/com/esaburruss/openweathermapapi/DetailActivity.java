package com.esaburruss.openweathermapapi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {
    ImageView img;
    TextView high;
    TextView low;
    TextView d1;
    TextView d2;
    TextView weather;
    TextView humid;
    TextView pressure;
    TextView wind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        DailyWeatherInfo todayInfo = (DailyWeatherInfo) getIntent().getSerializableExtra("WeatherData");
        //((TextView) findViewById(R.id.aaa)).setText(todayInfo.getDate2());
        img = (ImageView) findViewById(R.id.img);
        d1 = (TextView) findViewById(R.id.d1);
        d2 = (TextView) findViewById(R.id.d2);
        low = (TextView) findViewById(R.id.low);
        high = (TextView) findViewById(R.id.high);
        weather = (TextView) findViewById(R.id.weather);
        humid = (TextView) findViewById(R.id.humid);
        pressure = (TextView) findViewById(R.id.pressure);
        wind = (TextView) findViewById(R.id.wind);

        img.setImageResource(getResources().getIdentifier("art_" + todayInfo.getIcon(), "drawable", getPackageName()));
        d1.setText(todayInfo.getDate());
        d2.setText(todayInfo.getDate2());
        low.setText(todayInfo.getLow());
        high.setText(todayInfo.getHigh());
        weather.setText(todayInfo.getWeather());
        humid.setText(todayInfo.getHumidity());
        pressure.setText(todayInfo.getPressure());
        wind.setText(todayInfo.getWind());

    }
}
