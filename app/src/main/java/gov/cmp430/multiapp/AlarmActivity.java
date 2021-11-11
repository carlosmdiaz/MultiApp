package gov.cmp430.multiapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class AlarmActivity extends AppCompatActivity {

    private TimePicker mTimePicker;
    private TextView mTextView;
    private Button mAlarmOn;
    private Button mAlarmOff;
    private AlarmManager mAlarmManager;
    private PendingIntent mPendingIntent;
    private AlarmActivity mA;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_alarm);
        this.context = this;


        final Intent intent = new Intent(this.context, AlarmBroadcastReceiver.class);
        final Calendar calendar = Calendar.getInstance();

        mTextView = (TextView) findViewById(R.id.alarmSetTV);
        mAlarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        mTimePicker = (TimePicker) findViewById(R.id.timePicker);

        mAlarmOn = (Button) findViewById(R.id.alarmOn);
        mAlarmOn.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {

                final int hour = mTimePicker.getHour();
                final int minute = mTimePicker.getMinute();

                calendar.add(Calendar.SECOND, 1);
                calendar.set(Calendar.HOUR_OF_DAY, mTimePicker.getHour());
                calendar.set(Calendar.MINUTE, mTimePicker.getMinute());
                intent.putExtra("extra", "on");
                mPendingIntent = PendingIntent.getBroadcast(AlarmActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                mAlarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), mPendingIntent);
                Toast.makeText(AlarmActivity.this, "Alarm Turned On", Toast.LENGTH_LONG).show();
                setTextForAlarm("Alarm On - Set To Time Above");
            }

        });

        mAlarmOff = (Button) findViewById(R.id.alarmOff);
        mAlarmOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("extra", "off");
                Toast.makeText(AlarmActivity.this, "Alarm Turned Off", Toast.LENGTH_LONG).show();
                sendBroadcast(intent);
                mAlarmManager.cancel(mPendingIntent);
                setTextForAlarm("Alarm Off");
            }
        });

    }

    public void setTextForAlarm(String alarmTxt) {
        mTextView.setText(alarmTxt);
    }

    @Override
    public void onStart() {
        super.onStart();
        mA = this;
    }


}
