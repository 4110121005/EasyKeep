package edu.xcu.easykeep;

import android.app.Application;
import android.content.SharedPreferences;

import edu.xcu.easykeep.db.DBHelper;

/**
 * 表示全局应用的类
 * 可以在app启动时执行自定义的初始化操作，并提供了一种在整个应用程式中访问全局数据的方式
 */
public class EasyKeepApp extends Application {

    private SharedPreferences sharedPreferences;
    public static final String DATA_NAME = "yyds"; // 用于保存用户登录状态的文件名

    /**
     * 在应用程序启动时调用，用于执行自定义的初始化操作
     */
    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化数据库连接
        DBHelper.getInstance(this);
        sharedPreferences = getSharedPreferences(DATA_NAME, MODE_PRIVATE);
    }

    /**
     * 在应用程序终止时调用，用于执行一些清理操作，比如关闭数据库连接
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
        // 关闭数据库连接
        DBHelper.getInstance(this).closeDB();
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }
}
