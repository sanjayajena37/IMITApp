package com.example.sanjaya.imit;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.multidex.MultiDex;


public class App extends Application {

    public static final String CHANNEL = "CHANNEL 1";
   // public static final String CHANNEL_2_ID = "channel2";

    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationChannels();
    }

    private void createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationChannel channelNotice = new NotificationChannel(
                    CHANNEL,
                    "Model 1",
                    NotificationManager.IMPORTANCE_HIGH

            );
            channelNotice.setDescription("This is Channel 1");
            channelNotice.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            channelNotice.setSound(defaultSoundUri, null);
            channelNotice.setImportance(NotificationManager.IMPORTANCE_HIGH);
            channelNotice.setVibrationPattern(new long[]{1000, 1000});


//            NotificationChannel channel2 = new NotificationChannel(
//                    CHANNEL_2_ID,
//                    "Channel 2",
//                    NotificationManager.IMPORTANCE_LOW
//            );
//            channel2.setDescription("This is Channel 2");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channelNotice);
           // manager.createNotificationChannel(channel2);
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}