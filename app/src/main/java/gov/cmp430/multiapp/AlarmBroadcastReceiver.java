package gov.cmp430.multiapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Objects;

public class AlarmBroadcastReceiver extends BroadcastReceiver {

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onReceive(Context context, Intent intent) {


            String onService = Objects.requireNonNull(intent.getExtras()).getString("extra");
            Intent serviceIntent = new Intent(context, MediaPlayerService.class);
            serviceIntent.putExtra("extra", onService);
            context.startService(serviceIntent);



    }
}
