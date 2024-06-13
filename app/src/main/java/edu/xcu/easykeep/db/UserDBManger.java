package edu.xcu.easykeep.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import edu.xcu.easykeep.bean.UserBean;

/**
 * 用户数据管理类，负责对用户表进行操作
 */
public class UserDBManger {
    private final SQLiteDatabase db;
    private final DBHelper dbHelper;

    /**
     * 构造函数
     * @param context 上下文对象
     */
    public UserDBManger(Context context) {
        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    /**
     * 用户登录时检查用户
     * @param user 用户信息
     * @return  -1：用户不存在
     *          0：密码错误
     *          1：密码正确
     */
    public int userLogin(UserBean user) {
        Cursor cursor = db.query("user", new String[]{"uid", "upassword"}, "uid=?", new String[]{user.getUid()}, null, null, null);
        if (cursor.getCount() > 0) {
            cursor.moveToNext();
            @SuppressLint("Range") String pd = cursor.getString(cursor.getColumnIndex("upassword"));
            cursor.close();
            return user.getUpassword().equals(pd) ? 1 : 0;
        }
        cursor.close();
        return -1;
    }

    /**
     * 用户注册时检查用户
     * @param user 用户信息
     * @return  -1：注册失败
     *          0：用户已存在
     *          1：注册成功
     */
    public int userRegister(UserBean user) {
        // 仅当用户不存在时才创建账号
        if (userLogin(user) == -1) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("uid", user.getUid());
            contentValues.put("upassword", user.getUpassword());

            long result = db.insert("user", null, contentValues);
            return result == -1 ? -1 : 1;
        }
        return 0;
    }

    /**
     * 关闭数据库连接
     */
    public void close() {
        if (db != null && db.isOpen()) {
            db.close();
        }
        if (dbHelper != null) {
            dbHelper.close();
        }
    }
}
