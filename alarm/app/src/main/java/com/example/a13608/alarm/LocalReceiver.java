package com.example.a13608.alarm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import java.io.File;

/**
 * Created by 13608 on 2018/5/30.
 */

public class LocalReceiver extends BroadcastReceiver {
@Override
public void onReceive(Context context, Intent intent) {
    /*Intent intent1=new Intent(context,RingActivity.class);
    PendingIntent pendingIntent=PendingIntent.getActivity(context,0,intent1,0);
        NotificationManager manager=(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new NotificationCompat.Builder(context)
        .setContentTitle("闹钟提醒")
        .setContentText("昨天预定的")
        .setSmallIcon(R.drawable.ic_launcher_background)
        .setContentIntent(pendingIntent)
        .setAutoCancel(true)
        .setSound(Uri.fromFile(new File("/system/media/audio/ringtones/Luna.ogg")))
        .build();
        manager.notify(1,notification);
        */
    Toast.makeText(context, "short alarm", Toast.LENGTH_LONG).show();
}
}