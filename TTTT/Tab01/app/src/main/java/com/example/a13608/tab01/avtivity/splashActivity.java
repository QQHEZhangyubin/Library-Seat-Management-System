package com.example.a13608.tab01.avtivity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.a13608.tab01.R;
import com.example.a13608.tab01.utils.ShareUtils;
import com.example.a13608.tab01.utils.StaticClass;

public class splashActivity extends AppCompatActivity {
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case StaticClass.HANDLER_SPLASH:
                    if(isFirst()){
                        startActivity(new Intent(splashActivity.this,guideActivity.class));
                    }else{
                        startActivity(new Intent(splashActivity.this,loginActivity.class));
                    }
                    finish();
                    break;
            }
        }
    };

    private boolean isFirst() {
        boolean isFirst= ShareUtils.getBoolean(this,StaticClass.SHARE_IS_FIRST,true);
        if (isFirst){
            ShareUtils.putBoolean(this,StaticClass.SHARE_IS_FIRST,false);
            return true;
        }else {
            return false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        handler.sendEmptyMessageDelayed(StaticClass.HANDLER_SPLASH,2000);
    }
}
