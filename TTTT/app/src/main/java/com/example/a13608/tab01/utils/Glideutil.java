package com.example.a13608.tab01.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by 13608 on 2018/6/3.
 */

public class Glideutil {
    //默认加载图片(指定大小)
    public static void glidesize(Context mContext, String url, int width, int height, ImageView imageView){
        Glide.with(mContext)
                .load(url)
                .override(width,height)
                .centerCrop()
                .into(imageView);
    }
}
