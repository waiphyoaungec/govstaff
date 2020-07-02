package com.example.civilservantapp.push_notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.civilservantapp.R;
import com.example.civilservantapp.view.LoginActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class FirebaseMessagingServices extends FirebaseMessagingService {
    private String CH_ID = "ruby";
    private String CH_NA = "ruby";
    private String CH_DEC = "ruby";
    private  NotificationCompat.Builder builder;
    private NotificationManagerCompat managerCompat;

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(CH_ID,CH_NA, NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription(CH_DEC);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(notificationChannel);
        }
        Map<String,String> data = remoteMessage.getData();
        if(data.get("title")!=null && data.get("body")!=null)
        createNotification(data.get("title"),data.get("body"));
    }
    public void createNotification(String title,String body){
        builder =
                new NotificationCompat.Builder(getApplicationContext(),CH_ID)
                        .setSmallIcon(R.drawable.milogo)
                        .setContentTitle(title)
                        .setContentText(body)
                        .setVibrate(new long[] { 2000, 2000})
                        .setAutoCancel(true)
                        .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);


        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, LoginActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);


        builder.setContentIntent(contentIntent);
        managerCompat =  NotificationManagerCompat.from(getApplicationContext());
        managerCompat.notify(1,builder.build());
    }
}
