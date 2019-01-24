package com.example.a13608.alarm;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class RingActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ring);
        //时间一到跳转Activity,在这个Activity中播放音乐
        mediaPlayer = MediaPlayer.create(this, R.raw.one);
        mediaPlayer.start();
    }
    public void stop(View view){
        mediaPlayer.stop();
        finish();
    }

}
