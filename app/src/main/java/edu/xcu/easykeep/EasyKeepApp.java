package edu.xcu.easykeep;

import android.app.Application;

/*
* 表示全局应用的类
* 可以在app启动时执行自定义的初始化操作，并提供了一种在整个应用程式中访问全局数据的方式
* */
public class EasyKeepApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
    }

}
