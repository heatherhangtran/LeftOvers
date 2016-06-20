package com.randybiglow.leftovers;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

/**
 * Created by DarrellG on 6/20/16.
 */
public class AlarmService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i("BROADCAST", "The broadcast was received");
        notification("placeholder", "placeholder expires today!");
    return null;

    }

    public void notification(String notificationTitle, String notificationMessage) {

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);

        Intent intent = new Intent(this, RecipesFragment.class);

        PendingIntent pIntent = PendingIntent.getActivity(this, (int)System.currentTimeMillis(), intent, 0);


//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.leftovers_wooden_statusbar);
//        NotificationCompat.BigPictureStyle bigPictureStyle = new NotificationCompat.BigPictureStyle().bigPicture(bitmap);
//        mBuilder.setStyle(bigPictureStyle);
        mBuilder.setContentTitle(notificationTitle);
        mBuilder.setContentText(notificationMessage);
        mBuilder.setSmallIcon(R.drawable.leftovers_wooden_statusbar);
        mBuilder.setContentIntent(pIntent);
        mBuilder.setPriority(Notification.PRIORITY_HIGH);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, mBuilder.build());

    }

    }


