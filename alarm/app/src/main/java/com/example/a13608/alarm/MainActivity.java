package com.example.a13608.alarm;


import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.util.Calendar;


public class MainActivity extends AppCompatActivity {
    private Button bt_alarm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt_alarm = (Button) findViewById(R.id.bt_alarm);

        bt_alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this,LocalReceiver.class);
                PendingIntent sender = PendingIntent.getBroadcast(MainActivity.this,0,intent,0);
                Calendar calendar=Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());//将时间设定为系统目前的时间
                calendar.add(Calendar.SECOND,5);
                AlarmManager alarm =(AlarmManager)getSystemService(ALARM_SERVICE);
                alarm.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),sender);
            }
        });
    }
}
