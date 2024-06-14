package edu.xcu.easykeep.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import edu.xcu.easykeep.EasyKeepApp;
import edu.xcu.easykeep.bean.UserBean;

/**
 * 用户数据管理类，负责对用户表进行操作
 */
public class UserDBManger {
    private static final String TABLE_USER = "user";
    private static final String COLUMN_UID = "uid";
    private static final String COLUMN_PASSWORD = "upassword";

    private final SQLiteDatabase db;

    /**
     * 构造函数
     *
     * @param context 上下文对象
     */
    public UserDBManger(Context context) {
        DBHelper dbHelper = DBHelper.getInstance(context);
        db = dbHelper.getWritableDatabase();
    }

    /**
     * 用户登录时检查用户
     *
     * @param user 用户信息
     * @return -1：用户不存在
     * 0：密码错误
     * 1：密码正确
     */
    public int userLogin(UserBean user) {
        String[] projection = {COLUMN_PASSWORD};
        String selection = COLUMN_UID + " = ?";
        String[] selectionArgs = {user.getUid()};

        try (Cursor cursor = db.query(TABLE_USER, projection, selection, selectionArgs, null, null, null)) {
            if (cursor.moveToFirst()) {
                String storedPassword = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSWORD));
                return user.getUpassword().equals(storedPassword) ? 1 : 0;
            } else {
                return -1;
            }
        }
    }

    /**
     * 用户注册时检查用户
     *
     * @param user 用户信息
     * @return -1：注册失败
     * 0：用户已存在
     * 1：注册成功
     */
    public int userRegister(UserBean user) {
        if (userLogin(user) == -1) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_UID, user.getUid());
            values.put(COLUMN_PASSWORD, user.getUpassword());

            long result = db.insert(TABLE_USER, null, values);
            return result != -1 ? 1 : -1;
        } else {
            return 0;
        }
    }
}
