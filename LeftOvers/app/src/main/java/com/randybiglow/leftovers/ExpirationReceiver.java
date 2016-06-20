package com.randybiglow.leftovers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by DarrellG on 6/20/16.
 */
public class ExpirationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        notifyNotif(context);
    }

    public static void notifyNotif(Context context) {

        AlarmManager mgr =
                (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, AlarmService.class);
        PendingIntent pi = PendingIntent.getService(context, 0, i, 0);

        mgr.set(3,1, pi);
    }
}
