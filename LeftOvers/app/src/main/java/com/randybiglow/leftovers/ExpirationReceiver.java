package com.randybiglow.leftovers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by DarrellG on 6/20/16.
 */
public class ExpirationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent notification = new Intent(context,  AlarmService.class);
        context.startService(notification);
        Log.e("ON RECEIVE>>>>", "GOT TO THE ON RECEIVE");

    }

    public static void notify(Context context) {

        AlarmManager mgr =
                (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, ExpirationReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);
        Log.e("NOTIFY METHOD>>>>", "GOT TO THE NOTIFY METHOD");
        mgr.set(AlarmManager.RTC, 1, pi);
    }
}
