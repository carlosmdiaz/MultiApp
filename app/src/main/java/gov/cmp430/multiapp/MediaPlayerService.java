package gov.cmp430.multiapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.RequiresApi;

public class MediaPlayerService extends Service {

    private boolean mRingerOn;
    private MediaPlayer mMediaPlayer;
    private int startId;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final NotificationManager nM = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);

        Intent intent1 = new Intent(this.getApplicationContext(), AlarmActivity.class);
        PendingIntent mPendingIntent = PendingIntent.getActivity(this, 0, intent1, 0);

        Notification mNotification  = new Notification.Builder(this)
                .setContentTitle("Alarm On")
                .setSmallIcon(R.drawable.alarm_icon)
                .setContentIntent(mPendingIntent)
                .setAutoCancel(true)
                .build();

        String onService = intent.getExtras().getString("extra");
        assert onService != null;
        switch (onService) {
            case "off":
                startId = 0;
                break;
            case "on":
                startId = 1;
                break;
            default:
                startId = 0;
                break;
        }
        if(!this.mRingerOn && startId == 1) {
            mMediaPlayer = MediaPlayer.create(this, R.raw.cool);
            mMediaPlayer.start();
            nM.notify(0, mNotification);
            this.mRingerOn = true;
            this.startId = 0;
        }
        else if(!this.mRingerOn && startId == 0){
            this.mRingerOn = false;
            this.startId = 0;
        }
        else if(this.mRingerOn && startId == 1){
            this.mRingerOn = true;
            this.startId = 0;
        }
        else{
            mMediaPlayer.stop();
            mMediaPlayer.reset();
            this.mRingerOn = false;
            this.startId = 0;
        }
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mRingerOn = false;
    }




}
