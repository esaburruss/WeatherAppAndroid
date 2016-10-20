package com.esaburruss.openweathermapapi;

import android.app.Activity;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class FiveDayActivity extends AppCompatActivity {
    TextView todayHigh;
    TextView todayLow;
    TextView todayDate;
    TextView todayWeather;
    ImageView img0;

    ArrayList<DailyWeatherInfo> info;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_five_day);
        todayHigh = (TextView) findViewById(R.id.todayHigh);
        todayLow = (TextView) findViewById(R.id.todayLow);
        todayDate = (TextView) findViewById(R.id.todayDate);
        todayWeather = (TextView) findViewById(R.id.todayWeather);
        img0 = (ImageView) findViewById(R.id.img0);
        info = new ArrayList<DailyWeatherInfo>();
        //new TodayWeather().execute("http://api.openweathermap.org/data/2.5/weather?q=Atlanta,ga&units=imperial&APPID=132b7c8774b94d853f9746f8470a4925");
        new TodayWeather().execute("http://api.openweathermap.org/data/2.5/forecast/daily?id=4180439&cnt=6&APPID=132b7c8774b94d853f9746f8470a4925");
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public static String GET(String url) {
        InputStream inputStream = null;
        String result = "";
        try {

            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // make GET request to the given URL
            HttpResponse httpResponse = httpclient.execute(new HttpGet(url));

            // receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // convert inputstream to string
            if (inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        return result;
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }

    public boolean isConnected() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "FiveDay Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.esaburruss.openweathermapapi/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "FiveDay Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.esaburruss.openweathermapapi/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    public void gotoDetail(View view) {
        Intent intent = new Intent(this, DetailActivity.class);
        String z = view.getResources().getResourceName(view.getId());

        intent.putExtra("WeatherData", info.get(Integer.parseInt(z.substring(z.length() -1))));
        startActivity(intent);
    }

    class TodayWeather extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            return GET(urls[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                JSONObject obj = new JSONObject(result);

                JSONArray jArray = obj.getJSONArray("list");

                for (int i = 0; i < jArray.length(); i++)

                {

                    try {

                        DailyWeatherInfo t = new DailyWeatherInfo();

                        JSONObject o = jArray.getJSONObject(i);

                        long l = o.getLong("dt");

                        if (i == 0)

                            t.setToday(l);

                        else if (i == 1)

                            t.setTomorrow(l);

                        else

                            t.setDayOfWeek(l);


                        t.setPressure(o.getDouble("pressure"));

                        t.setHumidity(o.getInt("humidity"));

                        t.setWind(o.getDouble("speed"), o.getDouble("deg"));

                        //JSONObject main = o.getJSONObject("main");


                        JSONObject temp = o.getJSONObject("temp");

                        t.setLow((int) (temp.getDouble("min") - 273.15));

                        t.setHigh((int) (temp.getDouble("max") - 273.15));


                        JSONArray wArray = o.getJSONArray("weather");

                        JSONObject w = wArray.getJSONObject(0);

                        t.setWeather(w.getString("main"));


                        info.add(t);
                    } catch (JSONException e) {
                        System.out.println("Shit" + e.getLocalizedMessage());
                    }
                }
            } catch (JSONException e) {

            }

            setMainScreen();
        }

        protected void setMainScreen() {
            DailyWeatherInfo d = info.get(0);

            todayHigh.setText(d.getHigh());
            todayLow.setText(d.getLow());
            todayDate.setText(d.getDate() + ", " + d.getDate2());
            todayWeather.setText(d.getWeather());
            img0.setImageResource(getResources().getIdentifier("art_" + d.getWeather().toLowerCase(), "drawable", getPackageName()));

            for (int i = 1; i < info.size(); i++) {
                DailyWeatherInfo day = info.get(i);
                String lowId = "low" + i;
                String highId = "high" + i;
                String dateId = "date" + i;
                String weatherId = "weather" + i;
                String imgId = "img" + i;
                String imgName = "ic_" + day.getWeather().toLowerCase();
                ((TextView) findViewById(getResources().getIdentifier(lowId, "id", getPackageName()))).setText(day.getLow());
                ((TextView) findViewById(getResources().getIdentifier(highId, "id", getPackageName()))).setText(day.getHigh());
                ((TextView) findViewById(getResources().getIdentifier(dateId, "id", getPackageName()))).setText(day.getDate());
                ((TextView) findViewById(getResources().getIdentifier(weatherId, "id", getPackageName()))).setText(day.getWeather());
                ((ImageView) findViewById(getResources().getIdentifier(imgId, "id", getPackageName()))).setImageResource(getResources().getIdentifier(imgName, "drawable", getPackageName()));
            }
        }
    }


}