package gov.cmp430.multiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WeatherMainActivity extends AppCompatActivity {

    TextView location;
    TextView temperature;
    TextView weather_description;

    ImageView weather_image;
    EditText search;
    FloatingActionButton search_floating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.weather_main);

        location=findViewById(R.id.location);
        location.setText("-------------------");

        temperature=findViewById(R.id.temperature);
        temperature.setText("0");
        weather_description = findViewById(R.id.weather_condition);
        weather_description.setText("Waiting for location...");
        weather_image = findViewById(R.id.wheather_image);
        search = findViewById(R.id.search_edit);
        search_floating = findViewById(R.id.floating_search);
        search_floating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //hide Keyboard
                InputMethodManager imm=(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getRootView().getWindowToken(),0);

                //
                String search_converted = String.valueOf(search.getText());
                api_key(capitalizeWord(search_converted));
            }


        });

    }

    public static String capitalizeWord(String str){

        String words[] = str.split("\\s");
        String capitalizeWord="";

        for(String w:words){
            String first = w.substring(0,1);
            String afterfirst = w.substring(1);
            capitalizeWord += first.toUpperCase() + afterfirst + " ";
        }
        return capitalizeWord.trim();
    }


    private void api_key(final String City) {

        // provides an implementation of HttpURLConnection and Apache Client interfaces by working directly on a top of java Socket without using any extra dependencies
        OkHttpClient client=new OkHttpClient();

        Request request=new Request.Builder()
                .url("https://api.openweathermap.org/data/2.5/weather?q="+City+"&appid=a3d71e67567bb8e3aefd4bfc3eb67a5c&units=metric")
                .get()
                .build();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {

            Response response= client.newCall(request).execute();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {

                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    String responseData= response.body().string();
                    try {
                        JSONObject json=new JSONObject(responseData);
                        JSONArray array=json.getJSONArray("weather");
                        JSONObject object=array.getJSONObject(0);

                        String description=object.getString("description");
                        String icons = object.getString("icon");

                        JSONObject temp1= json.getJSONObject("main");
                        Double Temperature=temp1.getDouble("temp");

                        setText(location,City);

                        String temps= (int)Math.floor(Math.round(Temperature) * 1.8 + 32) + "";
                        setText(temperature,temps);
                        setText(weather_description,description);
                        setImage(weather_image,icons);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }catch (IOException e){
            e.printStackTrace();
        }


    }
    private void setText(final TextView text, final String value){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                text.setText(value);
            }
        });
    }


    private void setImage(final ImageView imageView, final String value){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //paste switch
                switch (value){
                    case "01d": imageView.setImageDrawable(getResources().getDrawable(R.drawable.sunny_01));
                        break;
                    case "01n": imageView.setImageDrawable(getResources().getDrawable(R.drawable.sunny_01));
                        break;
                    case "02d": imageView.setImageDrawable(getResources().getDrawable(R.drawable.sun_02));
                        break;
                    case "02n": imageView.setImageDrawable(getResources().getDrawable(R.drawable.sun_02));
                        break;
                    case "03d": imageView.setImageDrawable(getResources().getDrawable(R.drawable.sun_02));
                        break;
                    case "03n": imageView.setImageDrawable(getResources().getDrawable(R.drawable.sun_02));
                        break;
                    case "04d": imageView.setImageDrawable(getResources().getDrawable(R.drawable.cloud_04));
                        break;
                    case "04n": imageView.setImageDrawable(getResources().getDrawable(R.drawable.cloud_04));
                        break;
                    case "09d": imageView.setImageDrawable(getResources().getDrawable(R.drawable.cloud_rain_09));
                        break;
                    case "09n": imageView.setImageDrawable(getResources().getDrawable(R.drawable.cloud_rain_09));
                        break;
                    case "10d": imageView.setImageDrawable(getResources().getDrawable(R.drawable.cloud_rain_10));
                        break;
                    case "10n": imageView.setImageDrawable(getResources().getDrawable(R.drawable.cloud_rain_10));
                        break;
                    case "11d": imageView.setImageDrawable(getResources().getDrawable(R.drawable.thunder_rain_11));
                        break;
                    case "11n": imageView.setImageDrawable(getResources().getDrawable(R.drawable.thunder_rain_11));
                        break;
                    case "13d": imageView.setImageDrawable(getResources().getDrawable(R.drawable.snow_13));
                        break;
                    case "13n": imageView.setImageDrawable(getResources().getDrawable(R.drawable.snow_13));
                        break;

                    case "50n": imageView.setImageDrawable(getResources().getDrawable(R.drawable.mist));
                        break;

                    default:
                        imageView.setImageDrawable(getResources().getDrawable(R.drawable.sunny_01));

                }

            }
        });

    }
}
