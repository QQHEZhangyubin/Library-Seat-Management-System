package com.example.a13608.tab01.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import com.example.a13608.tab01.MainActivity;
import com.example.a13608.tab01.R;

import java.util.Locale;

public class MyService extends Service {

    private NotificationCompat.Builder builder;
    private NotificationManager manager;
    private int totalSecond;
    private final int serviceId = 0X3;
    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        manager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        builder=new NotificationCompat.Builder(getApplicationContext(),"2")
                .setSmallIcon(R.drawable.time)
                .setContentTitle("预约通知");
        Intent intent1=new Intent(getApplicationContext(), MainActivity.class);
        PendingIntent pendingIntent=PendingIntent.getActivity(getApplicationContext(),0,intent1,0);
        //builder.setOngoing(true);
        builder.setAutoCancel(true);
        builder.setContentIntent(pendingIntent);
        manager.notify(serviceId,builder.build());
        startForeground(serviceId,builder.build());
        new Thread((new Runnable() {
            @Override
            public void run()
            {
                /**
                 * 30min倒计时功能
                 */
                    for (int i=1800;i>=1;i--){

                        builder.setContentText(getStringTime(i));
                        manager.notify(serviceId,builder.build());
                        try {
                                Thread.sleep(1000);//一秒更新一次
                        }catch(InterruptedException e){
                            e.printStackTrace();
                        }
                    }
                    manager.notify(serviceId,builder.build());
                    startForeground(serviceId,builder.build());
                    stopSelf();
            }
            private String getStringTime(int totalSecond) {
                int hour= totalSecond / 3600;
                int min =totalSecond % 3600 / 60;
                int second=totalSecond % 60;
                return String.format(Locale.CHINA,"%02d:%02d:%02d",hour,min,second);
            }

        })).start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
