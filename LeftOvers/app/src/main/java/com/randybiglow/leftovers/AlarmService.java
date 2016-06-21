package com.randybiglow.leftovers;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

/**
 * Created by DarrellG on 6/20/16.
 */
public class AlarmService extends IntentService {

    public AlarmService() {
        super("Alarm Service");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e("ALARM SERVICE", "ON BIND METHOD REACHER");
    return null;

    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.e("ALARM SERVICE", "ON HANDLE INTENT SERVICE WAS REACHED");
        notification("placeholder", "placeholder expires today!");
    }




    public void notification(String notificationTitle, String notificationMessage) {

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);

        Intent intent = new Intent(this, MainActivity.class);

        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);

        mBuilder.setContentTitle(notificationTitle);
        mBuilder.setContentText(notificationMessage);
        mBuilder.setSmallIcon(R.drawable.leftovers_wooden_statusbar);
        mBuilder.setContentIntent(pIntent);
        mBuilder.setPriority(Notification.PRIORITY_HIGH);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, mBuilder.build());
    }
    }


