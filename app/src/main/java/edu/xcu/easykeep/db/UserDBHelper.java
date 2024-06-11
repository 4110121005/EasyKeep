package edu.xcu.easykeep.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Objects;

public class UserDBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "EasyKeep.db";
    private static final String TABLE_NAME = "user";
    private static final String COLUMN_USERID = "uid";
    private static final String COLUMN_UPASSWORD = "upassword";
    private static final String CREATE_TABLE = String.format("create table %s " +
            "( %s text primary key not null, " +
            "%s text not null)",
            TABLE_NAME, COLUMN_USERID, COLUMN_UPASSWORD);

    private SQLiteDatabase db;
    public UserDBHelper(Context context) {
        super(context, DB_NAME, null,3);
        db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try{
            db.execSQL(CREATE_TABLE);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }


    /*
    * 用户登录时检查用户
    *
    * -1，用户不存在
    * 0，（用户存在）密码错误
    * 1，（用户存在）密码正确
    * */
    public int userLogin(String uid, String upassword){
        Cursor cursor = db.query(TABLE_NAME, new String[]{COLUMN_UPASSWORD},"uid=?", new String[]{uid},null, null, null);
        if(cursor.getCount() > 0){
            cursor.moveToNext();
            @SuppressLint("Range") String pd = cursor.getString(cursor.getColumnIndex(COLUMN_UPASSWORD));
            cursor.close();
            return upassword.equals(pd)?1:0;
        }
        cursor.close();
        return -1;
    }
    /*
    * 用户注册时检查用户
    * -1，注册失败
    * 0，用户存在，不注册
    * 1，注册成功
    * */
    public int userRegister(String uid, String upassword) {
        //仅当用户不存在时才创建账号
        if(userLogin(uid, upassword) == -1){
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_USERID, uid);
            contentValues.put(COLUMN_UPASSWORD, upassword);

            long result = db.insert(TABLE_NAME, null, contentValues);
            return result == -1 ? -1:1;
        }
        return 0;
    }
}
