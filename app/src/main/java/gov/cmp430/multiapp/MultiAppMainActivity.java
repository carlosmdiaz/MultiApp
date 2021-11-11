package gov.cmp430.multiapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.util.Calendar;


//@SuppressLint("Registered")
public class MultiAppMainActivity extends AppCompatActivity implements View.OnClickListener {

    private CardView weather, checkList, barcode, calculator, flashlight, alarm, calendar, whereAmI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);


        weather = (CardView) findViewById(R.id.weather_card);
        checkList = (CardView) findViewById(R.id.check_list_card);
        barcode = (CardView) findViewById(R.id.barcode_card);
        calculator = (CardView) findViewById(R.id.calculator_card);
        flashlight = (CardView) findViewById(R.id.flashlight_card);
        alarm = (CardView) findViewById(R.id.alarm_card);
        calendar = (CardView) findViewById(R.id.calendar_card);
        whereAmI = (CardView) findViewById(R.id.where_am_i_card);

        //Click Listener

        weather.setOnClickListener(this);
        checkList.setOnClickListener(this);
        barcode.setOnClickListener(this);
        calculator.setOnClickListener(this);
        flashlight.setOnClickListener(this);
        alarm.setOnClickListener(this);
        calendar.setOnClickListener(this);
        whereAmI.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        Intent i;

        if(v.getId() == R.id.weather_card){

            i = new Intent(this, WeatherMainActivity.class);
            startActivity(i);

        } else if (v.getId() == R.id.where_am_i_card){

            i = new Intent(this, WhereAmI.class);
            startActivity(i);

        } else if(v.getId() == R.id.barcode_card){

            i = new Intent(this, BarCodeActivity.class);
            startActivity(i);

        } else if(v.getId() == R.id.calculator_card){

            i = new Intent(this, ScientificCalculator.class);
            startActivity(i);

        } else if(v.getId() == R.id.check_list_card){

            i = new Intent(this, CheckList.class);
            startActivity(i);

        } else if(v.getId() == R.id.flashlight_card) {

            i = new Intent(this, FlashLightActivity.class);
            startActivity(i);

        } else if(v.getId() == R.id.calendar_card) {

            i = new Intent(this, CalendarActivity.class);
            startActivity(i);
        } else if(v.getId() == R.id.alarm_card) {

            i = new Intent(this, AlarmActivity.class);
            startActivity(i);
        }

//        switch (v.getId()){
//
//            case R.id.weather_card : i = new Intent(this, WeatherMainActivity.class);
//        }



    }
    }

