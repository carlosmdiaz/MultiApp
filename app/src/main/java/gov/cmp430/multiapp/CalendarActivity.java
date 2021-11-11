package gov.cmp430.multiapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CalendarActivity extends AppCompatActivity {

    CalendarView calendar;
    TextView dateText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_calendar);

        calendar = (CalendarView) findViewById(R.id.calendar_id);
        dateText = (TextView) findViewById(R.id.text_date);

        SimpleDateFormat formatter= new SimpleDateFormat("MM/dd/yyyy");
        Date date = new Date(System.currentTimeMillis());
        String strdate = formatter.format(date);
        dateText.setText(strdate);

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String date = (month + 1) + "/" + (dayOfMonth) + "/" + year;
                dateText.setText(date);
            }
        });
    }
}