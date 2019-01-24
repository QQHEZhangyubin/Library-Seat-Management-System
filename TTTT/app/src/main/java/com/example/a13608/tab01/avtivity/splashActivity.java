package com.example.a13608.tab01.avtivity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
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
        ImageView imageView=findViewById(R.id.splashh);
        //服务器提供的闪屏页资源
        String img_url="http://192.168.43.123:8080/AndroidWeb/Image/welcome.jpg";
        /**
         * 如果图片类型不是 Gif 图的话就会当作 load 失败来处理，
         * 因此 error() 会被回调。
         * 即使这个url的图片是好的，
         * 也是不会显示的。
         */
        Glide.with(splashActivity.this)
                .load(img_url)
                .asGif()
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .error(R.mipmap.splash_default)
                .into(imageView);
        handler.sendEmptyMessageDelayed(StaticClass.HANDLER_SPLASH,2000);
    }
}
