package com.example.sanjaya.imit.Notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.example.sanjaya.imit.Activity.Login_activity;
import com.example.sanjaya.imit.Activity.MainActivity;
import com.example.sanjaya.imit.Activity.NoticeActivity;
import com.example.sanjaya.imit.R;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.example.sanjaya.imit.App.CHANNEL;


/**
 * Created by Sanjaya on 2/20/2017.
 */

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService  {
    private static final String TAG = "FirebaseMessagingServic";
    //PrefernceManager prefernceManager;

    String jsonMessage;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
            try {
                JSONObject data = new JSONObject(remoteMessage.getData());
                jsonMessage = data.getString("extra_information");
                Log.d(TAG, "onMessageReceived: \n" +
                        "Extra Information: " + jsonMessage);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            String title = remoteMessage.getNotification().getTitle(); //get title
            String message = remoteMessage.getNotification().getBody(); //get message
            String click_action = remoteMessage.getNotification().getClickAction(); //get click_action
            //img=remoteMessage.getData().get("img_url");


            Log.d(TAG,"Message"+message+"title"+title);

            Log.d(TAG, "Message Notification Title: " + title);
            Log.d(TAG, "Message Notification Body: " + message);
            Log.d(TAG, "Message Notification click_action: " + click_action);

           // if(prefernceManager.equals(prefernceManager.getString("")))

            sendNotification(title, message,click_action);
        }

    }

    @Override
    public void onDeletedMessages() {

    }

    private void sendNotification(String title, String messageBody, String click_action) {

//        String UserName=prefernceManager.getString("name");
//        if(UserName.isEmpty()){
//            click_action="NoName";
//        }

        if(jsonMessage.equals("simple")) {
            Intent intent;
            Bitmap pic = BitmapFactory.decodeResource(getResources(), R.drawable.logovector);
            if (click_action.equals("NOTICE")) {
                intent = new Intent(this, NoticeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            } else if (click_action.equals("MAIN")) {
                intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            } else if(click_action.equals("NoName")) {
                intent = new Intent(this, Login_activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            }else{
                intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            }

            NotificationCompat.BigTextStyle bik = new NotificationCompat.BigTextStyle();
            bik.setBigContentTitle(title);
            bik.setSummaryText(messageBody);

            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                    PendingIntent.FLAG_ONE_SHOT);
            //Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Notification notificationBuilderhu = new NotificationCompat.Builder(this, CHANNEL)
                    .setSmallIcon(R.drawable.notification_small)
                    .setContentTitle(title)
                    .setStyle(bik)
                    .setLargeIcon(pic)
                    .setVibrate(new long[]{1000, 1000})
                    .setContentText(messageBody)
                    .setAutoCancel(true)
                    .setColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary))
                    // .setSound(defaultSoundUri)
                    .setDefaults(Notification.DEFAULT_SOUND)
                    .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)//show in lockscreen
                    // .setContentIntent(pendingIntent)
//                .addAction(R.drawable.notification_small, getString(R.string.going),
//                        pendingIntent)
                    .addAction(0, "Go to app", pendingIntent)
                    .build();


            NotificationManager notificationManagerk =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManagerk.notify(5 /* ID of notification */, notificationBuilderhu);
        }else if(jsonMessage.equals("imitlogo")){

            Intent intent;
            Bitmap pic = BitmapFactory.decodeResource(getResources(), R.drawable.logovector);
            Bitmap pic1 = BitmapFactory.decodeResource(getResources(), R.drawable.logoo);
            if (click_action.equals("NOTICE")) {
                intent = new Intent(this, NoticeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            } else if (click_action.equals("MAIN")) {
                intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            } else {
                intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            }

//            byte[] b=jsonMessage.getBytes();
//            Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);





            NotificationCompat.BigPictureStyle bik = new NotificationCompat.BigPictureStyle();
            bik.bigPicture(pic1);

            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                    PendingIntent.FLAG_ONE_SHOT);
            //Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Notification notificationBuilderhu = new NotificationCompat.Builder(this, CHANNEL)
                    .setSmallIcon(R.drawable.notification_small)
                    .setContentTitle(title)
                    .setStyle(bik)
                    .setLargeIcon(pic)
                    .setVibrate(new long[]{1000, 1000})
                    .setContentText(messageBody)
                    .setAutoCancel(true)
                    .setColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary))
                    // .setSound(defaultSoundUri)
                    .setDefaults(Notification.DEFAULT_SOUND)
                    .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)//show in lockscreen
                    // .setContentIntent(pendingIntent)
//                .addAction(R.drawable.notification_small, getString(R.string.going),
//                        pendingIntent)
                    .addAction(0, "Go To App", pendingIntent)
                    .build();


            NotificationManager notificationManagerk =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManagerk.notify(6 /* ID of notification */, notificationBuilderhu);



//            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this,CHANNEL)
//                    .setSmallIcon(R.drawable.notification_small)
//                    //.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.schoolbellq_notification_large))
//                    .setColor(getResources().getColor(R.color.colorPrimary))
//                    .setContentTitle("SchoolBellQ*")
//                    .setContentText(messageBody)
//                    .setAutoCancel(true)
//                    //.setSound(defaultSoundUri)
//                    .setContentIntent(pendingIntent);
//            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//            notificationManager.notify(0, notificationBuilder.build());

        }
        else if(jsonMessage.equals("skult")){

            Intent intent;
            Bitmap pic = BitmapFactory.decodeResource(getResources(), R.drawable.newdp);
            if (click_action.equals("NOTICE")) {
                intent = new Intent(this, NoticeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            } else if (click_action.equals("MAIN")) {
                intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            } else {
                intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            }

//            byte[] b=jsonMessage.getBytes();
//            Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);





            NotificationCompat.BigPictureStyle bik = new NotificationCompat.BigPictureStyle();
            bik.bigPicture(pic);

            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                    PendingIntent.FLAG_ONE_SHOT);
            //Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Notification notificationBuilderhu = new NotificationCompat.Builder(this, CHANNEL)
                    .setSmallIcon(R.drawable.notification_small)
                    .setContentTitle(title)
                    .setStyle(bik)
                    .setLargeIcon(pic)
                    .setVibrate(new long[]{1000, 1000})
                    .setContentText(messageBody)
                    .setAutoCancel(true)
                    .setColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary))
                    // .setSound(defaultSoundUri)
                    .setDefaults(Notification.DEFAULT_SOUND)
                    .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)//show in lockscreen
                    // .setContentIntent(pendingIntent)
//                .addAction(R.drawable.notification_small, getString(R.string.going),
//                        pendingIntent)
                    .addAction(0, "Go To App", pendingIntent)
                    .build();


            NotificationManager notificationManagerk =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManagerk.notify(6 /* ID of notification */, notificationBuilderhu);

        }
//        else if(!jsonMessage.isEmpty()) {
//            Intent intent;
//            Bitmap pic = BitmapFactory.decodeResource(getResources(), R.drawable.logovector);
//            if (click_action.equals("NOTICE")) {
//                intent = new Intent(this, NoticeActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            } else if (click_action.equals("MAIN")) {
//                intent = new Intent(this, MainActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            } else {
//                intent = new Intent(this, MainActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            }
//
//            byte[] bytea= Base64.decode(jsonMessage,Base64.DEFAULT);
//            Bitmap bitmap=BitmapFactory.decodeByteArray(bytea,0,bytea.length);
//
//            final NotificationCompat.BigPictureStyle bik = new NotificationCompat.BigPictureStyle();
//            bik.bigPicture(bitmap);
////            ImageRequest imageRequest=new ImageRequest(img, new Response.Listener<Bitmap>() {
////                @Override
////                public void onResponse(Bitmap response) {
////
////
////
//////
////                }
////            }, 0, 0, null, new Response.ErrorListener() {
////                @Override
////                public void onErrorResponse(VolleyError error) {
////
////                }
////            });
//
//
//            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
//                    PendingIntent.FLAG_ONE_SHOT);
//            //Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//            Notification notificationBuilderhu = new NotificationCompat.Builder(this, CHANNEL)
//                    .setSmallIcon(R.drawable.notification_small)
//                    .setContentTitle(title)
//                    .setStyle(bik)
//                    .setLargeIcon(pic)
//                    .setVibrate(new long[]{1000, 1000})
//                    .setContentText(messageBody)
//                    .setAutoCancel(true)
//                    .setColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary))
//                    // .setSound(defaultSoundUri)
//                    .setDefaults(Notification.DEFAULT_SOUND)
//                    .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)//show in lockscreen
//                    // .setContentIntent(pendingIntent)
////                .addAction(R.drawable.notification_small, getString(R.string.going),
////                        pendingIntent)
//                    .addAction(0, "Go To App", pendingIntent)
//                    .build();
//
//
//            NotificationManager notificationManagerk =
//                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//            notificationManagerk.notify(7 /* ID of notification */, notificationBuilderhu);
//            //requestQueue.add(imageRequest);
//        }
        else if (jsonMessage.isEmpty())
        {
            Intent intent;
            Bitmap pic = BitmapFactory.decodeResource(getResources(), R.drawable.logovector);
            if (click_action.equals("Notice")) {
                intent = new Intent(this, NoticeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            } else if (click_action.equals("Home")) {
                intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            } else {
                intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            }

//            byte[] bytea= Base64.decode(jsonMessage,Base64.DEFAULT);
//            Bitmap bitmap=BitmapFactory.decodeByteArray(bytea,0,bytea.length);


            NotificationCompat.BigTextStyle bik = new NotificationCompat.BigTextStyle();
            bik.setBigContentTitle(title);
            bik.setSummaryText(messageBody);


            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                    PendingIntent.FLAG_ONE_SHOT);
            //Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Notification notificationBuilderhu = new NotificationCompat.Builder(this, CHANNEL)
                    .setSmallIcon(R.drawable.notification_small)
                    .setContentTitle(title)
                    .setStyle(bik)
                    .setLargeIcon(pic)
                    .setVibrate(new long[]{1000, 1000})
                    .setContentText(messageBody)
                    .setAutoCancel(true)
                    .setColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary))
                    // .setSound(defaultSoundUri)
                    .setDefaults(Notification.DEFAULT_SOUND)
                    .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)//show in lockscreen
                    // .setContentIntent(pendingIntent)
//                .addAction(R.drawable.notification_small, getString(R.string.going),
//                        pendingIntent)
                    .addAction(0, "Go To App", pendingIntent)
                    .build();


            NotificationManager notificationManagerk =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManagerk.notify(7 /* ID of notification */, notificationBuilderhu);
        }
        else if (!jsonMessage.isEmpty())
        {
            Intent intent;
            Bitmap pic = BitmapFactory.decodeResource(getResources(), R.drawable.logovector);
            if (click_action.equals("Notice")) {
                intent = new Intent(this, NoticeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            } else if (click_action.equals("Home")) {
                intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            } else {
                intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            }

            String baseUrl="https://firebasestorage.googleapis.com/v0/b/imit-9a8b1.appspot.com/o/notification%2Fnotice.jpg?alt=media&";
            String finalUrl=baseUrl+jsonMessage;
           Bitmap bitmap=getBitmapFromURL(finalUrl);


//            NotificationCompat.BigTextStyle bik = new NotificationCompat.BigTextStyle();
//            bik.setBigContentTitle(title);
//            bik.setSummaryText(messageBody);

            NotificationCompat.BigPictureStyle bik = new NotificationCompat.BigPictureStyle();
            bik.bigPicture(bitmap);


            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                    PendingIntent.FLAG_ONE_SHOT);
            Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Notification notificationBuilderhu = new NotificationCompat.Builder(this, CHANNEL)
                    .setSmallIcon(R.drawable.notification_small)
                    .setContentTitle(title)
                    .setStyle(bik)
                    .setLargeIcon(pic)
                    .setVibrate(new long[]{1000, 1000})
                    .setContentText(messageBody)
                    .setAutoCancel(true)
                    .setColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary))
                    .setSound(defaultSoundUri)
                    .setDefaults(Notification.DEFAULT_SOUND)
                    .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)//show in lockscreen
                    // .setContentIntent(pendingIntent)
//                .addAction(R.drawable.notification_small, getString(R.string.going),
//                        pendingIntent)
                    .addAction(0, "Go To App", pendingIntent)
                    .build();


            NotificationManager notificationManagerk =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManagerk.notify(7 /* ID of notification */, notificationBuilderhu);
        }

    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }
}
