package com.example.a13608.tab01.utils;

import android.app.Application;

/**
 * Created by 13608 on 2018/5/19.
 */

public class APP extends Application{
    private static APP mAPP;

    @Override
    public void onCreate() {
        super.onCreate();
        mAPP = this;
    }

    @Override
    public void onTerminate() {
        ToastHelper.cancelToast();
        super.onTerminate();
    }

    public static APP getApp() {
        return mAPP;
    }

}
